package com.aisc.ngalo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aisc.ngalo.databinding.ActivitySocialMediaBinding

class SocialMediaActivity : AppCompatActivity() {
    lateinit var binding: ActivitySocialMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val companyPhoneNumber = "+256706917819"
        val companyFacebookId = "NgaloMobileCyclingServices" // Update with Facebook ID
        val companyInstagramId = "ngalo_mobile_cycling_services" // Update with Instagram ID

        binding.fb.setOnClickListener {
            openFacebookPage(companyFacebookId)
        }
        binding.insta.setOnClickListener {
            openInstagramPage(companyInstagramId)
        }
        binding.whatsApp.setOnClickListener {
            openWhatsAppChat(companyPhoneNumber)
        }
    }

    // Function to open WhatsApp chat
    private fun openWhatsAppChat(companyPhoneNumber: String) {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$companyPhoneNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    // Function to open Facebook page
    private fun openFacebookPage(companyFacebookId: String) {
        val uri = Uri.parse("https://www.facebook.com/$companyFacebookId")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    // Function to open Instagram page
    private fun openInstagramPage(companyInstagramId: String) {
        val uri = Uri.parse("https://www.instagram.com/$companyInstagramId")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
