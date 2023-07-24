package com.aisc.ngalo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth

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
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings, rootKey)
            //val themePreference: ListPreference = findPreference("theme")!!
            val aboutPreference: Preference = findPreference("contactus")!!
            val signOutPreference: Preference = findPreference("signout")!!


            val themePreference: ListPreference? = findPreference("theme")
            themePreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

            // Register the listener for preference changes
            preferenceScreen.sharedPreferences!!.registerOnSharedPreferenceChangeListener(this)


            aboutPreference.setOnPreferenceClickListener {
                val intent = Intent(requireContext(), SocialMediaActivity::class.java)
                startActivity(intent)
                true
            }
            signOutPreference.setOnPreferenceClickListener {
                FirebaseAuth.getInstance().signOut()
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireContext(), SignUp::class.java))
                true
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
            // Unregister the listener when the fragment is destroyed
            preferenceScreen.sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}

