package com.gunder.sharedpreference.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gunder.sharedpreference.R
import com.gunder.sharedpreference.data.UserPreference
import com.gunder.sharedpreference.databinding.ActivityFormUserPreferenceBinding
import com.gunder.sharedpreference.model.UserModel

class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityFormUserPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
        val formType = intent.getIntExtra(EXTRA_FORM_TYPE, 0)

        var actionBarTitle = ""
        var btnTitle = ""
        when (formType) {
            TYPE_ADD -> {
                actionBarTitle = "Create new"
                btnTitle = "Save"
            }
            TYPE_EDIT -> {
                actionBarTitle = "Edit"
                btnTitle = "Edit"
                showPreferenceForm()
            }
        }
    }

    private fun showPreferenceForm() {
        binding.edtName.setText(userModel.name)
        binding.edtEmail.setText(userModel.email)
        binding.edtPhone.setText(userModel.phoneNumber)
        binding.edtAge.setText(userModel.age)
        if (userModel.isLove) {
            binding.rbYes.isChecked = true
        } else {
            binding.rbNo.isChecked = true
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_save) {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phoneNumber = binding.edtPhone.text.toString().trim()
            val isLoveMu = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

            if (name.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
                return
            }
            if (email.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
                return
            }
            if (!isValidEmail(email)) {
                binding.edtName.error = FIELD_IS_NOT_VALID
                return
            }
            if (age.isEmpty()) {
                binding.edtPhone.error = FIELD_REQUIRED
                return
            }
            if (phoneNumber.isEmpty()) {
                binding.edtPhone.error = FIELD_REQUIRED
            }

            if (!TextUtils.isDigitsOnly(phoneNumber)) {
                binding.edtPhone.error = FIELD_DIGIT_ONLY
            }
            saveUser(name, email, age, phoneNumber, isLoveMu)
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RESULT, userModel)
            setResult(EXTRA_CODE, resultIntent)
            finish()
        }
    }

    private fun saveUser(
        name: String,
        email: String,
        age: String,
        phoneNumber: String,
        loveMu: Boolean,
    ) {
        val userPreference = UserPreference(this)
        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phoneNumber
        userModel.isLove = loveMu
        userPreference.setUser(userModel)
        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val EXTRA_FORM_TYPE = "extra_form_type"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_CODE = 101
        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2
        const val FIELD_REQUIRED = "field_is_not_allowed_empty"
        const val FIELD_DIGIT_ONLY = "only_digit_only"
        const val FIELD_IS_NOT_VALID = "email_is_not_valid"
    }
}