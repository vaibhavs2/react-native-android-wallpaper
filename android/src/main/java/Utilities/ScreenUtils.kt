package Utilities

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.Display
import android.view.Window

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

  }

}
