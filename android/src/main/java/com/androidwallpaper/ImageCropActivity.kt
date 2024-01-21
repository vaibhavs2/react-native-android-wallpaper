package com.androidwallpaper

import Utilities.ScreenUtils
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.canhub.cropper.CropImageView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import androidx.core.animation.doOnEnd


class ImageCropActivity : AppCompatActivity() {
  private final lateinit var imageUrlToSet: String;
  private final lateinit var whichScreenToSet: String;
  private var cropImageView: CropImageView? = null;
  private var screenLayout: ConstraintLayout? = null;
  private var imageLoadingJob: Job? = null;
  private lateinit var loopedAnimator: AnimatorSet
  private var isLopperAnimationCancelled: Boolean = false
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_crop);
    this.whichScreenToSet = this.intent.getStringExtra("whichScreen").toString();
    this.imageUrlToSet = this.intent.getStringExtra("imageUrl").toString();
    this.screenLayout = findViewById(R.id.screen_layout)
    this.cropImageView = findViewById(R.id.cropImageView);
    val previewCroppedButton = findViewById<Button>(R.id.previewCroppedButton);
    val backButton = findViewById<ImageButton>(R.id.backButton);
    this.startLoopedAlphaAnimation(this.cropImageView!!)
    val dimentions = ScreenUtils.fullScreenDimention(this)

    cropImageView?.setFixedAspectRatio(true)
    cropImageView?.setAspectRatio(dimentions.x, dimentions.y)
    cropImageView?.aspectRatio.toString()


    backButton.setOnClickListener {
      this.imageLoadingJob?.cancel();
      finish()
    }
    previewCroppedButton.setOnClickListener {
      this.previewWallpaperInFragment()
    }

    if (this.imageLoadingJob != null && this.imageLoadingJob!!.isActive) {
      this.imageLoadingJob?.cancel();
    }
    this.imageLoadingJob = GlobalScope.launch(Dispatchers.IO) {
      val bitmap = loadImageToBitmap(imageUrlToSet)

      // Switch back to the main thread to update UI
      withContext(Dispatchers.Main) {
        // Use the bitmap in your UI
        if (bitmap != null) {
          cropImageView?.setImageBitmap(bitmap)
          previewCroppedButton.isEnabled=true
          stopLoopedAlphaAnimation()
        } else {
          showSnackBarMessage("Due to network error, Image failed to load, try again!", true)
        }
      }
    }

  }


  private fun showSnackBarMessage(message: String, error:Boolean=false) {
    this.screenLayout?.let {
      val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
      if(error){
        snackbar.setTextColor(Color.RED)
      }
      snackbar.show() }
  }

  fun loadImageToBitmap(imageUrl: String): Bitmap? {
    val url = URL(imageUrl)
    val urlConnection = url.openConnection() as HttpURLConnection
    var bitMap:Bitmap? = null
    try {
      val inputStream = urlConnection.inputStream
      val options = BitmapFactory.Options()
      options.inPreferredConfig = Bitmap.Config.ARGB_8888
       bitMap = BitmapFactory.decodeStream(inputStream, null, options)
    } catch (e: Exception) {
        this.showSnackBarMessage("Encountered network error, try again!", true)
    }
    finally {
      urlConnection.disconnect();
    }
    return  bitMap;
  }

  private fun previewWallpaperInFragment() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      this.showSnackBarMessage("Your device doesn't support changing wallpaper", true)
      return
    }

    val croppedBitmap: Bitmap? = cropImageView?.getCroppedImage();
    if (croppedBitmap == null) {
      this.showSnackBarMessage("Something wrong happened, try later please!", true)
      return
    }
    val fragment = ImagePreviewFragment.newInstance(croppedBitmap, this.whichScreenToSet)
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    fragmentTransaction.replace(R.id.previewFragment, fragment)
    fragmentTransaction.commit()
  }

  override fun onStop() {
    if (this.imageLoadingJob != null && this.imageLoadingJob!!.isActive) {
      this.imageLoadingJob?.cancel()
    }
    super.onStop()
  }

  @Deprecated("Deprecated in Java")
  override fun onBackPressed() {
    val currentFragment = supportFragmentManager.findFragmentById(R.id.previewFragment)
    if (currentFragment != null) {
      supportFragmentManager.beginTransaction().remove(currentFragment).commit()
    } else {
      super.onBackPressed()
    }
  }

  override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    if (hasFocus) {
      ScreenUtils.getStatusBarHeight(this);
      ScreenUtils.fullScreenDimention(this)
    }
  }

  fun startLoopedAlphaAnimation(view: View) {

    val anim1 = ObjectAnimator.ofFloat(view, View.ALPHA, .2f, .9f, .4f, .8f, .1f)
    anim1.duration = 3000
    val animatorSet = AnimatorSet()
    animatorSet.play(anim1)

    this.loopedAnimator = AnimatorSet()
    this.loopedAnimator.play(AnimatorSet().apply {
      play(animatorSet)
    }).before(animatorSet)

    this.loopedAnimator.doOnEnd {
      if (this.isLopperAnimationCancelled) {
        this.loopedAnimator.cancel();
        view.alpha = 1f
      } else {
        this.loopedAnimator.start()
      }
    }

    this.loopedAnimator.start()
  }

  fun stopLoopedAlphaAnimation() {
    if (this.loopedAnimator.isRunning) {
      this.isLopperAnimationCancelled = true
      this.loopedAnimator.cancel()
    }

  }


}
