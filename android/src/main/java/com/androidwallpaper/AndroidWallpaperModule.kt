package com.androidwallpaper

import Utilities.ScreenUtils
import Utilities.WallpaperScreenType
import android.app.WallpaperManager
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AndroidWallpaperModule internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {
  private var isWallpaperSettingUp: Job? = null;

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun isSetWallpaperAllowed(promise: Promise?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val wallpaperManager = WallpaperManager.getInstance(reactApplicationContext)
      promise?.resolve(wallpaperManager.isSetWallpaperAllowed)
    } else {
      promise?.resolve(false)
    }
  }

  @ReactMethod
  fun setWallpaper(imageUrl: String, whichScreen: String, promise: Promise?) {
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


  @ReactMethod
  fun getCropSetWallpaper(imageUrl: String, whichScreen: String = "BOTH") {
    val intent = Intent(reactApplicationContext.currentActivity, ImageCropActivity::class.java)
    intent.putExtra("imageUrl", imageUrl)
    intent.putExtra("whichScreen", whichScreen)
    reactApplicationContext.currentActivity?.startActivity(intent)
  }


  companion object {
    const val NAME = "AndroidWallpaper"
  }
}
