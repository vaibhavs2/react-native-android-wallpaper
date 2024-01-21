package Utilities

import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.Display
import android.view.Window
import androidx.annotation.RequiresApi

class ScreenUtils {
  companion object {
    private var _statusBarHeight: Int? = null;
    private var _screenDimentions: Point? = null
    fun fullScreenDimention(context: Activity): Point {
      if (this._screenDimentions != null) {
        return this._screenDimentions!!
      }
      val realSize = Point()
      val realDisplay: Display = context.getWindowManager().getDefaultDisplay()
      realDisplay.getRealSize(realSize)
      this._screenDimentions = realSize
      return this._screenDimentions!!
    }

    fun getStatusBarHeight(context: Activity): Int {
      if (this._statusBarHeight != null && this._statusBarHeight != 0) {
        return this._statusBarHeight!!
      }
      val rectangle = Rect()
      val window: Window = context.getWindow()
      window.decorView.getWindowVisibleDisplayFrame(rectangle)
      this._statusBarHeight = rectangle.top;
      return this._statusBarHeight!!
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setWallpaper(whichScreen: WallpaperScreenType, bitMap:Bitmap, context:Context){
      val wallpaperManager = WallpaperManager.getInstance(context)
      if (whichScreen == WallpaperScreenType.LOCK) {
        wallpaperManager.setBitmap(bitMap, null, true, WallpaperManager.FLAG_LOCK)
      } else if (whichScreen == WallpaperScreenType.HOME) {
        wallpaperManager.setBitmap(bitMap, null, true, WallpaperManager.FLAG_SYSTEM)
      } else {
        wallpaperManager.setBitmap(
          bitMap,
          null,
          true,
          WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
        )
      }
    }

  }

}
