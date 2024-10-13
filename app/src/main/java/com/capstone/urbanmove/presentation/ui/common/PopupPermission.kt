package com.capstone.urbanmove.presentation.ui.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.capstone.urbanmove.R

class PopupPermission(private val activity: Activity) {

    fun permissionType(permission: String): String {
        return when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> "ubicación"
            else -> "desconocido"
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    fun showPermissionDeniedPopup(permissionType: String) {
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.permision_popup, null)

        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0)

        val btnGoToSettings = popupView.findViewById<Button>(R.id.btn_go_to_settings)
        val permissionMessage = popupView.findViewById<TextView>(R.id.permission_message)

        permissionMessage.text = "Se rechazó el permiso de $permissionType."

        btnGoToSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", activity.packageName, null)
            }
            activity.startActivity(intent)
            popupWindow.dismiss()
        }
    }
}