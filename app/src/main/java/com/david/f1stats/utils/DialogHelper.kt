package com.david.f1stats.utils

import android.app.Dialog
import androidx.fragment.app.FragmentActivity
import com.david.f1stats.R
import com.david.f1stats.databinding.DialogImageFullscreenBinding
import com.squareup.picasso.Picasso

object DialogHelper {
    fun showImageDialog(activity: FragmentActivity, picasso: Picasso, imageUrl: String) {
        val dialog = Dialog(activity)
        val window = dialog.window

        val width = activity.resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = activity.resources.getDimensionPixelSize(R.dimen.dialog_height)
        window?.setLayout(width, height)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val binding = DialogImageFullscreenBinding.inflate(activity.layoutInflater)
        dialog.setContentView(binding.root)

        picasso.load(imageUrl).into(binding.fullscreenImageView)

        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}