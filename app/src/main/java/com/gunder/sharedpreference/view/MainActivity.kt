package com.gunder.sharedpreference.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gunder.sharedpreference.R
import com.gunder.sharedpreference.data.UserPreference
import com.gunder.sharedpreference.databinding.ActivityMainBinding
import com.gunder.sharedpreference.model.UserModel

class MainActivity : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My User Preference"
        showExitingPreference()
    }

    private fun showExitingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm()
    }

    private fun checkForm() {
        when {
            userModel.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                binding.btnSave.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }
    }

    private fun populateView(userModel: UserModel) {
        binding.tvName.text =
            userModel.name.toString().ifEmpty { "nothing else" }
        binding.tvEmail.text =
            userModel.email.toString().ifEmpty { "nothing else" }
        binding.tvAge.text =
            userModel.age.toString().ifEmpty { "nothing else" }
        binding.tvPhone.text = userModel.phoneNumber.toString().ifEmpty { "nothing else" }
        binding.tvIsLoveMu.text = if (userModel.isLove) "ya" else "no"
    }
}