package com.androidwallpaper

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Build
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.net.HttpURLConnection
import java.net.URL


class AndroidWallpaperModule internal constructor(context: ReactApplicationContext):
  ReactContextBaseJavaModule(context){
    private var isWallpaperSettingUp = false;

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
   fun isSetWallpaperAllowed(promise:Promise){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val wallpaperManager = WallpaperManager.getInstance(reactApplicationContext)
      promise.resolve(wallpaperManager.isSetWallpaperAllowed)
    } else {
      promise.resolve(false)
    }
  }

  @ReactMethod
   fun setWallpaper(imageUrl:String, whichScreen:String="both", promise:Promise) {
     if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
       promise.reject("Your device doesn't support changing wallpaper")
       return
     }
     if(this.isWallpaperSettingUp){
       promise.reject("Previous wallpaper is applying, Try again!")
       return;
     }
    changeWallpaperSettingUp(true)
     try {
      val url = URL(imageUrl)
      val urlConnection = url.openConnection() as HttpURLConnection
      try {

        val inputStream = urlConnection.inputStream
        val decoder = BitmapRegionDecoder.newInstance(inputStream, false)
        val bitMap =  decoder!!.decodeRegion(Rect(0, 0, decoder.width, decoder.height), null)
        val wallpaperManager = WallpaperManager.getInstance(reactApplicationContext)

        if (whichScreen == "lock") {
          wallpaperManager.setBitmap(bitMap, null, true, WallpaperManager.FLAG_LOCK)
        } else if (whichScreen == "home") {
          wallpaperManager.setBitmap(bitMap, null, true, WallpaperManager.FLAG_SYSTEM)
        } else{
          wallpaperManager.setBitmap(
            bitMap,
            null,
            true,
            WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
          )
        }

        promise.resolve("Wallpaper Applied!")
      }
      catch (e:Exception){
        promise.reject("Wallpaper Applying failed, try again!")
      }
      finally {
        urlConnection.disconnect();
        changeWallpaperSettingUp(false)
      }

    } catch (e: Exception) {
       promise.reject("Wallpaper Applying failed, try again!")
       changeWallpaperSettingUp(false)
    }
  }

  @ReactMethod
  fun getCropSetWallpaper(imageUrl:String,whichScreen:String="both"){
    val intent = Intent(reactApplicationContext.currentActivity, ImageCropActivity::class.java )
    intent.putExtra("imageUrl", imageUrl)
    intent.putExtra("whichScreen", whichScreen)
    reactApplicationContext.currentActivity?.startActivity(intent)
  }

  fun changeWallpaperSettingUp(status:Boolean){
    this.isWallpaperSettingUp = status
  }


  companion object {
    const val NAME = "AndroidWallpaper"
  }
}
