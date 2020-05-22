package com.comics.color

import android.graphics.*
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val onSeekbarListener: SeekBar.OnSeekBarChangeListener =
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                loadBitmapRotate()
            }
        }

    var bitmapMaster: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            bitmapMaster = result.drawToBitmap()
            loadBitmapRotate()
        }

//        bitmapMaster = BitmapFactory.decodeResource(resources, R.drawable.asd);

        rotbarred.setOnSeekBarChangeListener(onSeekbarListener)
        rotbarblue.setOnSeekBarChangeListener(onSeekbarListener)
        rotbargreen.setOnSeekBarChangeListener(onSeekbarListener)

        rotbarred.progress = 0
        rotbargreen.progress = 0
        rotbarblue.progress = 0

    }

    private fun loadBitmapRotate() {

        if (bitmapMaster != null) {
            val rotRed = rotbarred.progress.toFloat()
            val rotGreen = rotbargreen.progress.toFloat()
            val rotBlue = rotbarblue.progress.toFloat()
            textRot.text = ("setRotate: " + rotRed.toString() + ", "
                    + rotGreen.toString() + ", " + rotBlue.toString())
            val bitmapColorScaled = updateRot(
                bitmapMaster!!, rotRed,
                rotGreen, rotBlue
            )
            result.setImageBitmap(bitmapColorScaled)
        }
    }

    private fun updateRot(
        src: Bitmap, degreesRed: Float, degreesGreen: Float,
        degreesBlue: Float
    ): Bitmap? {
        val w = src.width
        val h = src.height
        val bitmapResult = Bitmap
            .createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvasResult = Canvas(bitmapResult)
        val paint = Paint()
        val colorMatrixRed = ColorMatrix()
        colorMatrixRed.setRotate(0, degreesRed)
        val colorMatrixGreen = ColorMatrix()
        colorMatrixGreen.setRotate(1, degreesGreen)
        val colorMatrixBlue = ColorMatrix()
        colorMatrixBlue.setRotate(2, degreesBlue)
        val colorMatrixConcat = ColorMatrix()
        colorMatrixConcat.setConcat(colorMatrixRed, colorMatrixGreen)
        colorMatrixConcat.setConcat(colorMatrixConcat, colorMatrixBlue)
        val filter = ColorMatrixColorFilter(
            colorMatrixConcat
        )
        paint.colorFilter = filter
        canvasResult.drawBitmap(src, 0f, 0f, paint)
        return bitmapResult
    }
}
