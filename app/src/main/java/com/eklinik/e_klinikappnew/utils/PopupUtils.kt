package com.eklinik.e_klinikappnew.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.eklinik.e_klinikappnew.R

object PopupUtils {
    
    private fun getActivity(context: Context): Activity? {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> getActivity(context.baseContext)
            else -> null
        }
    }
    
    fun showSuccessPopup(
        context: Context,
        title: String,
        message: String,
        buttonText: String = "Tamam",
        autoClose: Boolean = true,
        onButtonClick: (() -> Unit)? = null
    ) {
        val activity = getActivity(context)
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return
        }

        val inflater = LayoutInflater.from(activity)
        val dialogView = inflater.inflate(R.layout.popup_success, null)

        val titleView = dialogView.findViewById<TextView>(R.id.popup_title)
        val messageView = dialogView.findViewById<TextView>(R.id.popup_message)
        val buttonView = dialogView.findViewById<Button>(R.id.popup_button)
        val iconView = dialogView.findViewById<ImageView>(R.id.popup_icon)

        titleView.text = title
        messageView.text = message
        buttonView.text = buttonText
        iconView.setImageResource(R.drawable.ic_check_circle)

        val alertDialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        buttonView.setOnClickListener {
            onButtonClick?.invoke()
            alertDialog.dismiss()
        }

        alertDialog.show()

        if (autoClose) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (!activity.isFinishing && !activity.isDestroyed && alertDialog.isShowing) {
                     alertDialog.dismiss()
                 }
            }, 2000)
        }
    }
    
    fun showLoginSuccessPopup(context: Context) {
        showSuccessPopup(
            context = context,
            title = "Başarılı!",
            message = "Giriş işleminiz başarıyla tamamlandı.",
            buttonText = "Devam Et",
            autoClose = true
        )
    }
    
    fun showLogoutSuccessPopup(context: Context) {
        val activity = getActivity(context)
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return
        }

        val inflater = LayoutInflater.from(activity)
        val dialogView = inflater.inflate(R.layout.popup_success, null)

        val titleView = dialogView.findViewById<TextView>(R.id.popup_title)
        val messageView = dialogView.findViewById<TextView>(R.id.popup_message)
        val buttonView = dialogView.findViewById<Button>(R.id.popup_button)
        val iconView = dialogView.findViewById<ImageView>(R.id.popup_icon)

        titleView.text = "Çıkış Başarılı"
        messageView.text = "Başarıyla çıkış yaptınız."
        buttonView.text = "Tamam"
        iconView.setImageResource(R.drawable.ic_check_circle)

        val alertDialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        buttonView.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            if (!activity.isFinishing && !activity.isDestroyed && alertDialog.isShowing) {
                alertDialog.dismiss()
            }
        }, 2000)
    }
    
    fun showErrorPopup(
        context: Context,
        title: String,
        message: String,
        buttonText: String = "Tamam",
        onButtonClick: (() -> Unit)? = null
    ) {
        val activity = getActivity(context)
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return
        }

        val inflater = LayoutInflater.from(activity)
        val dialogView = inflater.inflate(R.layout.popup_error, null)

        val titleView = dialogView.findViewById<TextView>(R.id.popup_title)
        val messageView = dialogView.findViewById<TextView>(R.id.popup_message)
        val buttonView = dialogView.findViewById<Button>(R.id.popup_button)
        val iconView = dialogView.findViewById<ImageView>(R.id.popup_icon)

        titleView.text = title
        messageView.text = message
        buttonView.text = buttonText
        iconView.setImageResource(R.drawable.ic_error_circle)

        val alertDialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        buttonView.setOnClickListener {
            onButtonClick?.invoke()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}