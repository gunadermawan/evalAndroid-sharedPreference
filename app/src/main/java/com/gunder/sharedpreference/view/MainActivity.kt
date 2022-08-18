package com.gunder.sharedpreference.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.gunder.sharedpreference.R
import com.gunder.sharedpreference.data.UserPreference
import com.gunder.sharedpreference.databinding.ActivityMainBinding
import com.gunder.sharedpreference.model.UserModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
        supportActionBar?.title = "My User Preference"
        mUserPreference = UserPreference(this)
        showExitingPreference()
    }

    private fun showExitingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    private fun checkForm(userModel: UserModel) {
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

    private val resultLouncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null && result.resultCode == FormUserPreferenceActivity.EXTRA_CODE) {
            userModel =
                (result.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel)
            populateView(userModel)
            checkForm(userModel)
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_save) {
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            when {
                isPreferenceEmpty -> {
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_FORM_TYPE,
                        FormUserPreferenceActivity.TYPE_ADD
                    )
                    intent.putExtra("USER", userModel)
                }
            }
            resultLouncher.launch(intent)
        }
    }
}