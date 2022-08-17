package com.gunder.sharedpreference.data

import android.content.Context
import com.gunder.sharedpreference.model.UserModel

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFES_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "islove"
    }

    //    create sharedPreference
    private val preference = context.getSharedPreferences(PREFES_NAME, Context.MODE_PRIVATE)

    //    this method use to create user data
    fun setUser(value: UserModel) {
        val editor = preference.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putInt(AGE, value.age)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putBoolean(LOVE_MU, value.isLove)
        editor.apply()
    }

    //    this method use to read/get user data
    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preference.getString(NAME, "")
        model.email = preference.getString(EMAIL, "")
        model.age = preference.getInt(AGE, 0)
        model.phoneNumber = preference.getString(PHONE_NUMBER, "")
        model.isLove = preference.getBoolean(LOVE_MU, false)
        return model

    }

}