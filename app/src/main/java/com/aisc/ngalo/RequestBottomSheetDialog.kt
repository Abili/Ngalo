package com.aisc.ngalo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.aisc.ngalo.databinding.BikerepairBottomSheetBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class RequestBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: BikerepairBottomSheetBinding
    lateinit var descriptionOfProblems:String
    lateinit var location:String
    lateinit var imageUrl:MutableList<Uri>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BikerepairBottomSheetBinding.inflate(inflater, container, false)
        Places.initialize(requireContext(), getString(R.string.Api_key));

        val desc = binding.repairDescriptionEditText.text.toString()
        binding.userLocationEditText.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                val fields = listOf(Place.Field.ID, Place.Field.NAME)

                // Start the autocomplete intent.
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(requireContext())
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
            }
            false

        }



        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)

                        val userLocation = Editable.Factory.getInstance().newEditable(place.name)
                        binding.userLocationEditText.text = userLocation

                        Snackbar.make(
                            binding.root,
                            "Place: ${place.name}, ${place.id}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Snackbar.make(
                            binding.root,
                            status.statusMessage ?: "",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
    }


}
