package com.aisc.ngalo

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private var accountManagementClicked = false

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings, rootKey)

            val accountManagementPreference: Preference = findPreference("accountManagement")!!
            val signOutPreference: Preference = findPreference("signout")!!
            val deleteAccountPreference: Preference? = findPreference("deleteAccount")

            // Initially disable the "Delete Account" preference
            deleteAccountPreference?.isEnabled = false

            accountManagementPreference.setOnPreferenceClickListener {
                // Enable the "Delete Account" preference when Account Management is clicked
                deleteAccountPreference?.isEnabled = true
                val accountManagementIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://abili.github.io/ABiGTech/account_management.html")
                )
                startActivity(accountManagementIntent)
                true
            }

            deleteAccountPreference!!.setOnPreferenceClickListener {
                showDeleteAccountConfirmationDialog()
            }

            signOutPreference.setOnPreferenceClickListener {
                val currentUser = FirebaseAuth.getInstance().currentUser
                FirebaseAuth.getInstance().signOut()
                deleteUserData(currentUser)
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireContext(), SignUp::class.java))
                true
            }
        }

        private fun showDeleteAccountConfirmationDialog(): Boolean {

            val currentUser = FirebaseAuth.getInstance().currentUser
            // Implement a dialog to confirm the deletion
            // For simplicity, you can use AlertDialog or any other custom dialog

            val dialog = AlertDialog.Builder(requireContext()).setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes") { _, _ ->
                    // User confirmed the deletion
                    deleteUserData(currentUser)

                }.setNegativeButton("No", null).create()

            dialog.show()
            return true
        }

        private fun deleteUserData(currentUser: FirebaseUser?) {
            if (currentUser != null) {
                val userRef =
                    FirebaseDatabase.getInstance().reference.child("users").child(currentUser.uid)
                userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            // Store the delete request
                            storeDeletionRequest(currentUser.uid)

                            // Remove the user's data
                            snapshot.ref.removeValue()

                            // Sign out the user
                            signOutUser()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle onCancelled, if needed
                    }
                })
            }
        }

        private fun signOutUser() {
            FirebaseAuth.getInstance().signOut()
            requireActivity().finish()
            requireActivity().startActivity(Intent(requireContext(), SignUp::class.java))
        }

        private fun storeDeletionRequest(uid: String) {
            // Store the deletion request in the "deletionrequests" node in your database
            val deletionRequestRef =
                FirebaseDatabase.getInstance().reference.child("deletionrequests")

            // You can store additional information along with the user ID, such as timestamp
            val deletionRequestData = mapOf(
                "userId" to uid, "timestamp" to ServerValue.TIMESTAMP
            )

            deletionRequestRef.push().setValue(deletionRequestData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Deletion request stored successfully
                    // You may want to navigate to the login screen or perform any other action
                    Snackbar.make(
                        requireView(),
                        "Deleting Account Please wait...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    // Handle the failure to store deletion request
                    Snackbar.make(
                        requireView(),
                        "Failed to submit deletion request. Please try again.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            when (key) {
                "theme" -> {
                    val selectedTheme = sharedPreferences?.getString("theme", "system") ?: "system"
                    applyTheme(selectedTheme)
                }
            }
        }

        private fun applyTheme(theme: String) {
            when (theme) {
                "light" -> ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
                "dark" -> ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
                else -> ThemeHelper.applySystemTheme(requireContext())
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            preferenceScreen.sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onDisplayPreferenceDialog(preference: Preference) {
            // Hide the "Delete Account" preference if "Account Management" has been clicked
            if (accountManagementClicked && preference.key == "deleteAccount") {
                return
            }
            super.onDisplayPreferenceDialog(preference)
        }

        override fun setPreferenceScreen(preferenceScreen: PreferenceScreen?) {
            // Filter out the "Delete Account" preference if "Account Management" has been clicked
            if (accountManagementClicked) {
                preferenceScreen?.removePreference(findPreference("deleteAccount")!!)
            }
            super.setPreferenceScreen(preferenceScreen)
        }
    }
}
