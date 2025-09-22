package com.androidwallpaper

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

import Utilities.ScreenUtils
import Utilities.WallpaperScreenType
import android.app.WallpaperManager
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.facebook.react.bridge.Promise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ReactModule(name = AndroidWallpaperModule.NAME)
class AndroidWallpaperModule(reactContext: ReactApplicationContext) :
  NativeAndroidWallpaperSpec(reactContext) {

  companion object {
    const val NAME = "AndroidWallpaper"
  }

  private var isWallpaperSettingUp: Job? = null;
  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  override fun isSetWallpaperAllowed( ):Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val wallpaperManager = WallpaperManager.getInstance(reactApplicationContext)
      return wallpaperManager.isSetWallpaperAllowed;
    }  
      return false;
  }

  override fun setWallpaper(imageUrl: String, whichScreen: String, promise: Promise?):Unit {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      promise?.reject("Your device doesn't support changing wallpaper")
      return
    }
    if (this.isWallpaperSettingUp != null && this.isWallpaperSettingUp!!.isActive) {
      this.isWallpaperSettingUp?.cancel();
    }

    this.isWallpaperSettingUp = GlobalScope.launch(Dispatchers.IO) {
      val bitMap = ImageUtils.loadImageToBitmap(imageUrl)
      if (bitMap != null) {
        ScreenUtils.setWallpaper(
          WallpaperScreenType.valueOf(whichScreen),
          bitMap,
          reactApplicationContext
        )
      }
      withContext(Dispatchers.Main) {
        if (bitMap == null) {
          Toast.makeText(reactApplicationContext, "Applying wallpaper failed!", Toast.LENGTH_SHORT)
            .show();
          promise?.reject("Applying wallpaper failed!")
        } else {
          Toast.makeText(reactApplicationContext, "Wallpaper Applied!", Toast.LENGTH_SHORT).show();
          promise?.resolve("Wallpaper Applied!")
        }

      }
    }
  }


  override fun getCropSetWallpaper(imageUrl: String, whichScreen: String):Unit {
    val intent = Intent(reactApplicationContext.currentActivity, ImageCropActivity::class.java)
    intent.putExtra("imageUrl", imageUrl)
    intent.putExtra("whichScreen", whichScreen)
    reactApplicationContext.currentActivity?.startActivity(intent)
  }
}
