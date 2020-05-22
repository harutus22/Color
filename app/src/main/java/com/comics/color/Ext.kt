package com.comics.color

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View


fun View.createBitmapFromView(width: Float, height: Float): Bitmap {
    if (width > 0 && height > 0) {
        this.measure(
            View.MeasureSpec.makeMeasureSpec(
                DynamicUnitUtils
                    .convertDpToPixels(width), View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                DynamicUnitUtils
                    .convertDpToPixels(height), View.MeasureSpec.EXACTLY
            )
        )
    }
    this.layout(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight())
    val bitmap = Bitmap.createBitmap(
        this.getMeasuredWidth(),
        this.getMeasuredHeight(), Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    val background: Drawable = this.getBackground()
    background?.draw(canvas)
    this.draw(canvas)
    return bitmap
}

object DynamicUnitUtils {
    /**
     * Converts DP into pixels.
     *
     * @param dp The value in DP to be converted into pixels.
     *
     * @return The converted value in pixels.
     */
    fun convertDpToPixels(dp: Float): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().getDisplayMetrics()
            )
        )
    }

    /**
     * Converts pixels into DP.
     *
     * @param pixels The value in pixels to be converted into DP.
     *
     * @return The converted value in DP.
     */
    fun convertPixelsToDp(pixels: Int): Int {
        return Math.round(pixels / Resources.getSystem().getDisplayMetrics().density).toInt()
    }

    /**
     * Converts SP into pixels.
     *
     * @param sp The value in SP to be converted into pixels.
     *
     * @return The converted value in pixels.
     */
    fun convertSpToPixels(sp: Float): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp, Resources.getSystem().getDisplayMetrics()
            )
        )
    }

    /**
     * Converts pixels into SP.
     *
     * @param pixels The value in pixels to be converted into SP.
     *
     * @return The converted value in SP.
     */
    fun convertPixelsToSp(pixels: Int): Int {
        return Math.round(pixels / Resources.getSystem().getDisplayMetrics().density).toInt()
    }

    /**
     * Converts DP into SP.
     *
     * @param dp The value in DP to be converted into SP.
     *
     * @return The converted value in SP.
     */
    fun convertDpToSp(dp: Float): Int {
        return Math.round(
            convertDpToPixels(dp) / convertSpToPixels(
                dp
            ).toFloat()
        )
    }
}