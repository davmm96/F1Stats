package com.david.f1stats.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.david.f1stats.databinding.DialogImageFullscreenBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DialogHelper @Inject constructor() {

    fun showImageDialog(activity: FragmentActivity, picasso: Picasso, imageUrl: String) {
        showDialog(activity) {
            picasso.load(imageUrl).into(it.fullscreenImageView)
        }
    }

    fun showLocalImageDialog(activity: FragmentActivity, picasso: Picasso, imageResId: Int) {
        showDialog(activity) {
            picasso.load(imageResId).into(it.fullscreenImageView)
        }
    }

    private fun showDialog(
        activity: FragmentActivity,
        loadImage: (DialogImageFullscreenBinding) -> Unit
    ) {
        val dialog = Dialog(activity)

        val binding = DialogImageFullscreenBinding.inflate(activity.layoutInflater)
        dialog.setContentView(binding.root)

        val displayMetrics = activity.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = screenWidth
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BF000000")))

        loadImage(binding)

        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        binding.root.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
