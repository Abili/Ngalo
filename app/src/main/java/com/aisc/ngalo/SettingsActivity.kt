package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
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

    override fun onDestroy() {
        super.onDestroy()
        // Save the selected theme option to SharedPreferences when the activity is destroyed
        saveThemeToPreferences()
    }

    private fun saveThemeToPreferences() {
        // Store the selected theme option in SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePreference: ListPreference? = SettingsFragment().findPreference("theme")
        val themeOption = themePreference!!.value
        sharedPreferences.edit().putString("theme", themeOption).apply()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings, rootKey)
            val themePreference: ListPreference = findPreference("theme")!!
            val aboutPreference: Preference = findPreference("about")!!
            val signOutPreference: Preference = findPreference("signout")!!
            themePreference.summary = themePreference.entry

            // Retrieve the previously selected theme option from SharedPreferences
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val savedThemeOption = sharedPreferences.getString("theme", "dark")
            themePreference.setValue(savedThemeOption)

            themePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    val themeOption = newValue as String?
                    ThemeHelper.applyTheme(themeOption!!)
                    true
                }

            aboutPreference.setOnPreferenceClickListener {
                val intent = Intent(requireContext(), SocialMediaActivity::class.java)
                startActivity(intent)
                true
            }
            signOutPreference.setOnPreferenceClickListener {
                FirebaseAuth.getInstance().signOut()
                true
            }
        }
    }
}
