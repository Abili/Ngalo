package com.aisc.ngalo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.databinding.ActivityBookRiddingLessionsBinding
import com.aisc.ngalo.models.BookedLesson
import com.aisc.ngalo.util.CurrencyUtil
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookRidingLessons : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityBookRiddingLessionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBookRiddingLessionsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize FirebaseAuth and FirebaseDatabase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val curId = auth.currentUser!!.uid

        MobileAds.initialize(this) { initiallized ->
            if (initiallized.equals(true)) {
                val adRequest = AdRequest.Builder().build()
                binding.adView!!.loadAd(adRequest)
            }
        }

        database.reference.child("booklessons")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (bookSnap in snapshot.children) {
                        val imageUrl = bookSnap.child("imageUrl").getValue(String::class.java)
                        val fee = bookSnap.child("fee").getValue(String::class.java)
                        val details = bookSnap.child("description").getValue(String::class.java)
                        Glide.with(binding.root)
                            .load(imageUrl)
                            .into(binding.lessonView)

                        binding.fee.text = buildString {
                            append("FEE: ")
                            append(CurrencyUtil.formatCurrency(fee!!.toInt(), "UGX"))
                        }
                        binding.details.text = "More Info: \n${details}"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        FirebaseDatabase.getInstance().reference.child("users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.bookLessonButton.setOnClickListener {
                        for (usersnap in snapshot.children) {
                            binding.bookLessonButton.isEnabled = false
                            binding.progressBar!!.visibility = View.VISIBLE
                            val useId = usersnap.child("id").getValue(String::class.java)
                            if (useId == curId) {
                                val imageUrl =
                                    usersnap.child("imageUrl").getValue(String::class.java)
                                val username =
                                    usersnap.child("username").getValue(String::class.java)
                                val phoneNumber =
                                    usersnap.child("phone").getValue(String::class.java)

                                val selectedDate =
                                    binding.calendarView.date // Get the selected date in milliseconds
                                if (username != null && phoneNumber != null) {
                                    // Save the booked lesson information to the Firebase database
                                    saveBookedLesson(username, phoneNumber, selectedDate, imageUrl)
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


    }

    private fun saveBookedLesson(
        username: String,
        phoneNumber: String,
        selectedDate: Long,
        imageUrl: String?
    ) {
        // Create a reference to the "bookedlessons" node in your Firebase database
        val bookedLessonsRef = database.reference.child("bookedlessons")

        // Create a unique key for the booked lesson
        val lessonId = bookedLessonsRef.push().key

        // Create a BookedLesson object with the selected date, username, and phone number
        val bookedLesson = BookedLesson(
            lessonId,
            selectedDate,
            username,
            phoneNumber,
            imageUrl
        )

        // Save the booked lesson to the Firebase database under the "bookedlessons" node
        bookedLessonsRef.child(lessonId!!).setValue(bookedLesson)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Handle successful booking
                    binding.progressBar!!.visibility = View.GONE
                    Toast.makeText(this, "Lesson booked successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle booking failure
                    binding.bookLessonButton.isEnabled = true
                    Toast.makeText(this, "Failed to book lesson", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
