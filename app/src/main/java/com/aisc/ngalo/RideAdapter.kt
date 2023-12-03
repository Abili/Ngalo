package com.aisc.ngalo

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.databinding.BookRideItemBinding
import com.aisc.ngalo.models.BookRide
import com.aisc.ngalo.rides.RidesActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class RideAdapter : RecyclerView.Adapter<RideAdapter.RideViewHolder>() {
    private val rideList = mutableListOf<BookRide>()

    fun add(bookRide: BookRide) {
        rideList.add(bookRide)
    }

    interface bookRide {
        fun OnRideBooked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val binding =
            BookRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val currentRide = rideList[position]
        holder.bind(currentRide)
    }

    override fun getItemCount(): Int {
        return rideList.size
    }

    inner class RideViewHolder(private val binding: BookRideItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ride: BookRide) {
            // Bind the data to the views
            binding.name.text = ride.name
            binding.distance.text = ride.distance
            binding.date.text = "Start Date: ${ride.date}"
            binding.time.text = "Start Time: ${ride.time}"

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)
            var isRideBooked = sharedPreferences.getBoolean(ride.rideId, false)
            val bookRideRef = FirebaseDatabase.getInstance().reference.child("bookedRides")
            val mBookedRideRef = FirebaseDatabase.getInstance().reference.child("bookride")

            binding.bookride.setOnClickListener {
                isRideBooked = true

                // Book the ride
                val bookedRide = BookRide(
                    ride.rideId, ride.name, ride.distance, ride.date, ride.time, true
                )

                // Save the booking state in SharedPreferences
                sharedPreferences.edit().putBoolean(ride.rideId, true).apply()

                // Update the UI
                updateButtonVisibility(isRideBooked)
                notifyDataSetChanged()

                // Show a Snackbar
                Snackbar.make(itemView, "${ride.name} Booked", Snackbar.LENGTH_SHORT).show()

                // Offer to add the ride to the user's calendar
                offerToAddToCalendar(bookedRide, itemView.context)
            }


            val customFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            mBookedRideRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (booked in snapshot.children) {
                            val bookedRideId = booked.child("rideId").getValue(String::class.java)
                            val firebaseDateTimeString =
                                booked.child("isoDateTime").getValue(String::class.java)

                            if (bookedRideId == ride.rideId) {
                                val firebaseDateTime = LocalDateTime.parse(
                                    firebaseDateTimeString, customFormatter
                                )
                                val localDateTime = LocalDateTime.now()

                                updateButtonVisibility(
                                    isRideBooked, localDateTime, firebaseDateTime
                                )

                                break // No need to continue checking if ride is found
                            }
                        }
                    } else {
                        // No booked rides, show default state
                        updateButtonVisibility(isRideBooked)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            })

            binding.cancelride.setOnClickListener {
                isRideBooked = false
                sharedPreferences.edit().putBoolean(ride.rideId, false).apply()
                bookRideRef.child(ride.rideId!!).removeValue()
                updateButtonVisibility(isRideBooked)
                notifyDataSetChanged()
            }

            binding.startRide.setOnClickListener {
                val intent = Intent(itemView.context, RidesActivity::class.java)
                intent.putExtra("rideId", ride.rideId)
                intent.putExtra("name", ride.name)
                intent.putExtra("distance", ride.distance)
                itemView.context.startActivity(intent)
            }
        }

        private fun offerToAddToCalendar(bookedRide: BookRide, context: Context) {
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Ride: ${bookedRide.name}")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Details for the ride: ${bookedRide.distance}")
                //.putExtra(CalendarContract.Events.EVENT_LOCATION, "Location: ${bookedRide.location}")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getDateTimeInMillis(bookedRide.date!!, bookedRide.time!!))

            if (intent.resolveActivity(context.packageManager) != null) {
                // There's an app that can handle this Intent, prompt the user to choose an app
                context.startActivity(Intent.createChooser(intent, "Sync with Calendar"))
            } else {
                // There's no app to handle this Intent
                Snackbar.make(itemView, "No calendar app found", Snackbar.LENGTH_SHORT).show()
            }
        }

        private fun getDateTimeInMillis(date: String, time: String): Long {
            val dateTimeString = "$date $time"
            val formatter = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
            val dateTime = formatter.parse(dateTimeString)
            return dateTime?.time ?: 0
        }



        private fun updateButtonVisibility(
            isRideBooked: Boolean,
            localDateTime: LocalDateTime = LocalDateTime.now(),
            firebaseDateTime: LocalDateTime = LocalDateTime.MIN
        ) {
            // Always show the bookride button if the ride is not booked
            binding.bookride.visibility = if (!isRideBooked) View.VISIBLE else View.GONE

            // Show cancelride button if the ride is booked and the date and time conditions are not met
            binding.cancelride.visibility =
                if (isRideBooked && !(localDateTime.isEqual(firebaseDateTime) || localDateTime.isAfter(firebaseDateTime)))
                    View.VISIBLE
                else
                    View.GONE

            // Show startride button if the ride is booked and the date and time conditions are met
            binding.startRide.visibility =
                if (isRideBooked && (localDateTime.isEqual(firebaseDateTime) || localDateTime.isAfter(firebaseDateTime)))
                    View.VISIBLE
                else
                    View.GONE
        }


    }
}




