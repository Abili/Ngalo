package com.aisc.ngalo.reciept

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityGenerateReciptBinding
import com.aisc.ngalo.purchases.PurchasesViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GenerateReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerateReciptBinding
    private lateinit var purchasesViewModel: PurchasesViewModel
    private val adapter = ReceiptAdapter()
    var id: String? = null
    private lateinit var userId: String
    lateinit var username: String
    private lateinit var currentDate: String
    private lateinit var currentTime: String
    private lateinit var progressDialogSaved: ProgressDialog
    private lateinit var progressDialog: Dialog

    // Convert the bitmap to a Base64-encoded string
//    val imageString = convertBitmapToBase64(bitmap)
//
//    // Create the HTML content with the image
//    val htmlContent = getReceiptHtml(imageString)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateReciptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        progressDialogSaved = ProgressDialog(this)
        progressDialog = Dialog(this)
        // Receive intent data
        val intent = intent
        userId = intent.getStringExtra("userId").toString()
        val time = intent.getStringExtra("time")
        val grandTotal = intent.getStringExtra("gt")
        val transportationFare = intent.getStringExtra("tp")
        val pickupLocation = intent.getStringExtra("pickup_location")
        username = intent.getStringExtra("username")!!
        val contact = intent.getStringExtra("contact")
        val paymentmethod = intent.getStringExtra("paymentmethod")

        val data = "$username$contact$grandTotal$pickupLocation"
        //generateAndDisplayBarcode(data)

        if (userId != null) {
            // Set up RecyclerView and adapter
            purchasesViewModel.loadReciptItems(
                time!!, userId
            )
            purchasesViewModel.getReciptItems().observe(this) { completedList ->
                adapter.add(completedList)
                Toast.makeText(this, time, Toast.LENGTH_SHORT).show()
            }
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            // Update grand total
            binding.customerContact.text = "Contact: $contact"
            binding.username.text = "Customer: $username"
            binding.pickuplocation.text = "Pickup Location: $pickupLocation"
            binding.paymentMethod.text = "Payment Method: $paymentmethod"
            binding.textViewTransportFares.text = "TransportationFare: $transportationFare"
            binding.textViewGrandTotal.text = "Grand Total: $grandTotal"
            binding.date.text = "Date: $currentDate"
        } else {
            // Handle the case when items are not received properly
            // You can show an error message or take appropriate action
            binding.textViewTransportFares.text = "TransportationFare: 0"
            binding.textViewGrandTotal.text = "Grand Total: 0"
        }
    }

    private fun generateAndDisplayBarcode(data: String?) {
        try {
            val multiFormatWriter = MultiFormatWriter()
            val bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.EAN_13, 400, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            binding.barcodeImage.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
            // Handle the error, e.g., display a message or log it
            Toast.makeText(this, "Error generating barcode", Toast.LENGTH_SHORT).show()
        }
    }

    fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.reciept, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_reciept -> {
                val screenshot = captureScreenshot()
                showProgressBar()
                uploadScreenshot(screenshot)
                return true
            }

            R.id.print_reciept -> {
                connectPrinter()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    private fun captureScreenshot(): Bitmap {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        // Log to check if the bitmap is captured
        Log.d("CaptureScreenshot", "Bitmap captured successfully")

        return bitmap
    }

    private fun printReceipt() {
        val webView = WebView(this)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                // Create a PrintDocumentAdapter
                val printAdapter: PrintDocumentAdapter =
                    webView.createPrintDocumentAdapter("Receipt")

                // Get the PrintManager service
                val printManager = getSystemService(PRINT_SERVICE) as PrintManager

                // Specify the print job settings
                val printJob = printManager.print(
                    "Receipt",
                    printAdapter,
                    PrintAttributes.Builder().build()
                )
                // The printJob variable can be used if you need to track the print job status or perform additional actions
                if (printJob.isCompleted) {
                    Snackbar.make(binding.root, printJob.info.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        }

        // Load the HTML content of your receipt into the WebView
        // For example, you can load the receipt HTML from a string or a UR
// Load the HTML content into the WebView
        // webView.loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
    }

    fun getReceiptHtml(imageString: String): String {
        return """
        <!DOCTYPE html>
        <html>
        <body>
            <img src="data:image/png;base64, $imageString" alt="Receipt Image">
            <!-- Include other HTML content here -->
        </body>
        </html>
    """
    }


    private fun uploadScreenshot(bitmap: Bitmap) {

        // Convert bitmap to byte array
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        // Define a reference to your Firebase Storage location
        val storageRef =
            FirebaseStorage.getInstance().reference.child("receipts/${currentTime}_receipt.png")

        // Upload the data
        val uploadTask = storageRef.putBytes(data)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Screenshot uploaded successfully
                // Get the download URL
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    if (urlTask.isSuccessful) {
                        val downloadUrl = urlTask.result.toString()

                        // Call a function to store downloadUrl in Firebase Database
                        storeDownloadUrlInDatabase(downloadUrl)
                    }
                }
            } else {
                // Handle the error
                Toast.makeText(this, "Failed to upload screenshot", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun storeDownloadUrlInDatabase(downloadUrl: String) {
        // Assume you have a reference to the Firebase Database
        val databaseReference = FirebaseDatabase.getInstance().reference

        // Get a unique key for the new entry
        val key = databaseReference.child("receipts").push().key

        // Get the current date and time


        // Create a Receipt object or use a Map to represent the data
        val receiptData = mapOf(
            "userId" to userId,
            "downloadUrl" to downloadUrl,
            "username" to username, // Add the username
            "date" to currentDate,   // Add the current date
            "time" to currentTime    // Add the current time
            // Add other data fields as needed
        )


        // Update the database with the new data
        if (key != null) {
            databaseReference.child("receipts").child(key).setValue(receiptData)
                .addOnCompleteListener { task ->
                    progressDialogSaved.dismiss()
                    onSaved()
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Receipt saved successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Failed to save receipt", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun showProgressBar() {
        // Show a progress loader
        progressDialogSaved.setTitle("Saving Receipt")
        progressDialogSaved.setMessage("Please wait...")
        progressDialogSaved.setCanceledOnTouchOutside(false)
        progressDialogSaved.show()

    }

    fun onSaved() {
        progressDialog.setContentView(R.layout.saved)
        progressDialog.setCanceledOnTouchOutside(true)
        progressDialog.show()
    }

    private fun connectPrinter() {
        progressDialog.setContentView(R.layout.connect_printer)
        progressDialog.setCanceledOnTouchOutside(true)
        progressDialog.show()
    }


}
