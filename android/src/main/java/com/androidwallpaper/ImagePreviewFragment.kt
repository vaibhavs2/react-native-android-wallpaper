package com.androidwallpaper

import Utilities.ScreenUtils
import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class ImagePreviewFragment : Fragment() {
  @SuppressLint("NewApi")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    this.revertStatusBarChanges(false)

    val view = inflater.inflate(R.layout.fragment_image_preview, container, false)
    val imageView = view.findViewById<ImageView>(R.id.previewImage);
    val bitmap: Bitmap? = arguments?.getParcelable(ARG_BITMAP_DATA)
    bitmap?.let {
      imageView.setImageBitmap(it)
    }

    val goBackButton = view.findViewById<ImageButton>(R.id.fragmentBackButton)
    val param = goBackButton.layoutParams as ViewGroup.MarginLayoutParams
    param.topMargin = ScreenUtils.getStatusBarHeight(requireActivity()) + 10
    goBackButton.layoutParams = param
    goBackButton.setOnClickListener {
      requireActivity().onBackPressed()
    }
    val setWallpaperButton = view.findViewById<Button>(R.id.setWallpaperButton)
    setWallpaperButton.setOnClickListener {
      setWallpaperButton.isEnabled=false
      this.setWalpapper(
        bitmap!!,
        requireActivity(),
        requireArguments().getString(WALLPAPER_PLACEC, "")
      )
    }
    return view
  }

  companion object {
    private const val ARG_BITMAP_DATA = "image_bitmap"
    private const val WALLPAPER_PLACEC = "whereToSetWallpaper"

    @JvmStatic
    fun newInstance(bitmap: Bitmap, whereToSetWallpaper: String): ImagePreviewFragment {
      val fragment = ImagePreviewFragment()
      val args = Bundle()
      args.putParcelable(ARG_BITMAP_DATA, bitmap)
      args.putString(WALLPAPER_PLACEC, whereToSetWallpaper)
      fragment.arguments = args
      return fragment
    }
  }

  @RequiresApi(Build.VERSION_CODES.N)
  fun setWalpapper(bitmap: Bitmap, context: Context, whichScreenToSet: String="BOTH") {
    val wallpaperManager = WallpaperManager.getInstance(context)
    if (whichScreenToSet == "LOCK") {
      wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
    } else if (whichScreenToSet == "HOME") {
      wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
    } else {
      wallpaperManager.setBitmap(
        bitmap,
        null,
        true,
        WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
      )
    }
    Toast.makeText(context, "Wallpaper Applied!", Toast.LENGTH_SHORT).show();
  }


  private fun getDefaultStatusBarColor(): Int {
    val typedValue = TypedValue()
    val theme = requireContext().theme
    theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
    return typedValue.data
  }

  private fun revertStatusBarChanges(revert: Boolean = true) {
    if (revert) {
      activity?.window?.statusBarColor = this.getDefaultStatusBarColor()
      activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
      return
    }
    activity?.window?.statusBarColor =
      ContextCompat.getColor(requireContext(), android.R.color.transparent)
    activity?.window?.decorView?.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

  }

  override fun onDetach() {
    this.revertStatusBarChanges()
    super.onDetach()
  }
}
