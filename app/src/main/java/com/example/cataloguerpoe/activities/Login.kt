package com.example.cataloguerpoe.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
class Login : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        @Suppress("DEPRECIATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        tv_register.setOnClickListener {
            val intent = Intent(this@Login, RegisterActivity::class.java)
            startActivity(intent)
        }
        tv_forgot_password.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.tv_forgot_password -> {}
                R.id.btn_login -> {
                    validateLoginDetails()
                    logInRegisteredUser()


                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_email), errorMessage = true
                )
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), errorMessage = true)
                false
            }
            else -> {
                true
            }
        }
    }
    private fun logInRegisteredUser(){
        if(validateLoginDetails()){
            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        // send to main
                        val intent = Intent(this@Login, MapsActivity::class.java)
                            startActivity(intent)

                        showErrorSnackBar(message = "You are logged in successfully.", errorMessage = false)
                    }
                    else{
                        showErrorSnackBar(task.exception!!.message.toString(), errorMessage = true)
                    }
                }

        }
    }
}