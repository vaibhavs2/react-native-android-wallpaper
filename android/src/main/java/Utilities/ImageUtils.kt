import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class ImageUtils {
  companion object {
    fun loadImageToBitmap(imageUrl: String): Bitmap? {
      val url = URL(imageUrl)
      val urlConnection = url.openConnection() as HttpURLConnection
      var bitMap: Bitmap? = null
      try {
        val inputStream = urlConnection.inputStream
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitMap = BitmapFactory.decodeStream(inputStream, null, options)
      } catch (e: Exception) {
      } finally {
        urlConnection.disconnect();
      }
      return bitMap;
    }
  }
}
