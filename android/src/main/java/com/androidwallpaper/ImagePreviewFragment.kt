package com.androidwallpaper

import Utilities.ScreenUtils
import Utilities.WallpaperScreenType
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

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
      setWallpaperButton.isEnabled = false

        ScreenUtils.setWallpaper(
          WallpaperScreenType.valueOf(requireArguments().getString(WALLPAPER_PLACEC, "BOTH")),
          bitmap!!,
          requireActivity()
        )
        Toast.makeText(context, "Wallpaper Applied!", Toast.LENGTH_SHORT).show();


    }
    return view
  }

  companion object {
    private const val ARG_BITMAP_DATA = "image_bitmap"
    private const val WALLPAPER_PLACEC = "whereToSetWallpaper"

    @JvmStatic
    fun newInstance(
      bitmap: Bitmap,
      whereToSetWallpaper: WallpaperScreenType
    ): ImagePreviewFragment {
      val fragment = ImagePreviewFragment()
      val args = Bundle()
      args.putParcelable(ARG_BITMAP_DATA, bitmap)
      args.putString(WALLPAPER_PLACEC, whereToSetWallpaper.toString())
      fragment.arguments = args
      return fragment
    }
  }


  private fun getDefaultStatusBarColor(): Int {
    val typedValue = TypedValue()
    val theme = requireContext().theme
    theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
    return typedValue.data
  }

  private fun revertStatusBarChanges(revert: Boolean = true) {
    if (revert) {
      requireActivity().window.clearFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
          or WindowManager.LayoutParams.TYPE_STATUS_BAR
      )
      return
    }
    requireActivity().window.setFlags(
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    );

  }

  override fun onDetach() {
    this.revertStatusBarChanges()
    super.onDetach()
  }
}
