package com.vasl.magicalcolorpicker.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addLinearSnapHelper(){
    val snapHelper = LinearSnapHelper()
    snapHelper.attachToRecyclerView(this)
    snapHelper.findSnapView(layoutManager)
}

fun String.copyToClipboard(context: Context, label : String){
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label, this))
}