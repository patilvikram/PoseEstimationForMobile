/*
 * Copyright 2018 Zihua Zeng (edvard_hua@live.com), Lang Feng (tearjeaker@hotmail.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edvard.poseestimation

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.graphics.PointF
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.ArrayList

/**
 * Created by edvard on 18-3-23.
 */

@TargetApi(Build.VERSION_CODES.M)
class DrawView : View {

  private var mRatioWidth = 0
  private var mRatioHeight = 0

  private val mDrawPoint = ArrayList<PointF>()
  private var mWidth: Int = 0
  private var mHeight: Int = 0
  private var mRatioX: Float = 0.toFloat()
  private var mRatioY: Float = 0.toFloat()
  private var mImgWidth: Int = 0
  private var mImgHeight: Int = 0

  private val mColorArray = intArrayOf(
          resources.getColor(R.color.color_top, null),
          resources.getColor(R.color.color_neck, null),
          resources.getColor(R.color.color_l_shoulder, null),
          resources.getColor(R.color.color_l_elbow, null),
          resources.getColor(R.color.color_l_wrist, null),
          resources.getColor(R.color.color_r_shoulder, null),
          resources.getColor(R.color.color_r_elbow, null),
          resources.getColor(R.color.color_r_wrist, null),
          resources.getColor(R.color.color_l_hip, null),
          resources.getColor(R.color.color_l_knee, null),
          resources.getColor(R.color.color_l_ankle, null),
          resources.getColor(R.color.color_r_hip, null),
          resources.getColor(R.color.color_r_knee, null),
          resources.getColor(R.color.color_r_ankle, null),
          resources.getColor(R.color.color_background, null)
  )

  private val circleRadius: Float by lazy {
    dip(3).toFloat()
  }

  private val mPaint: Paint by lazy {
    Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
      style = FILL
      strokeWidth = dip(2).toFloat()
      textSize = sp(13).toFloat()
    }
  }

  constructor(context: Context) : super(context)

  constructor(
          context: Context,
          attrs: AttributeSet?
  ) : super(context, attrs)

  constructor(
          context: Context,
          attrs: AttributeSet?,
          defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr)

  fun setImgSize(
          width: Int,
          height: Int
  ) {
    mImgWidth = width
    mImgHeight = height
    requestLayout()
  }

  /**
   * Scale according to the device.
   * @param point 2*14
   */
  fun setDrawPoint(
          point: Array<FloatArray>,
          ratio: Float
  ) {
    mDrawPoint.clear()

    var tempX: Float
    var tempY: Float
    for (i in 0..13) {
      tempX = point[0][i] /  ratio / mRatioX
      tempY = point[1][i] / ratio / mRatioY
      mDrawPoint.add(PointF(tempX, tempY))
    }
    //Log.i("Test",mDrawPoint.toString())
  }

  /**
   * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
   * calculated from the parameters. Note that the actual sizes of parameters don't matter, that is,
   * calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
   *
   * @param width  Relative horizontal size
   * @param height Relative vertical size
   */
  fun setAspectRatio(
          width: Int,
          height: Int
  ) {
    if (width < 0 || height < 0) {
      throw IllegalArgumentException("Size cannot be negative.")
    }
    mRatioWidth = width
    mRatioHeight = height
    requestLayout()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (mDrawPoint.isEmpty()) return
    var prePointF: PointF? = null
    mPaint.color = 0xff6fa8dc.toInt()
    val p1 = mDrawPoint[1]
    val p2 = mDrawPoint[2]
    val p3 = mDrawPoint[3]
    val p4 = mDrawPoint[4]
    val p5 = mDrawPoint[5]
    val p6 = mDrawPoint[6]
    val p7 = mDrawPoint[7]
    val p8 = mDrawPoint[8]
    val p9 = mDrawPoint[9]
    val p10 = mDrawPoint[10]
    val p11 = mDrawPoint[11]
    val p12 = mDrawPoint[12]
    val p13 = mDrawPoint[13]
    //Log.i("Test","Inside drawing loop")
    for ((index, pointF) in mDrawPoint.withIndex()) {
      if (index == 1) continue
      when (index) {
        //0-1
        0 -> {
          canvas.drawLine(pointF.x, pointF.y, p1.x, p1.y, mPaint)
        }
        // 1-2, 1-5, 1-8, 1-11 default sequence later changed to work with new model
        2, 3 -> {
          canvas.drawLine(p1.x, p1.y, pointF.x, pointF.y, mPaint)
          //canvas.drawLine(p2.x, p2.y, p4.x,p4.y, mPaint)
        }
        else -> {
          if (prePointF != null) {
            mPaint.color = 0xff6fa8dc.toInt()
            //canvas.drawLine(prePointF.x, prePointF.y, pointF.x, pointF.y, mPaint)
            canvas.drawLine(p2.x, p2.y, p4.x,p4.y, mPaint)
            canvas.drawLine(p3.x, p3.y, p5.x,p5.y, mPaint)
            canvas.drawLine(p5.x, p5.y, p7.x,p7.y, mPaint)
            canvas.drawLine(p4.x, p4.y, p6.x,p6.y, mPaint)
            canvas.drawLine(p2.x, p2.y, p8.x,p8.y, mPaint)
            canvas.drawLine(p3.x, p3.y, p9.x,p9.y, mPaint)
            canvas.drawLine(p8.x, p8.y, p10.x,p10.y, mPaint)
            canvas.drawLine(p8.x, p8.y, p9.x,p9.y, mPaint)
            canvas.drawLine(p9.x, p9.y, p11.x,p11.y, mPaint)
            canvas.drawLine(p11.x, p11.y, p13.x,p13.y, mPaint)
            canvas.drawLine(p10.x, p10.y, p12.x,p12.y, mPaint)
          }
        }
      }
      prePointF = pointF
    }

    for ((index, pointF) in mDrawPoint.withIndex()) {
      mPaint.color = mColorArray[index]
      canvas.drawCircle(pointF.x, pointF.y, circleRadius, mPaint)
    }
  }

  override fun onMeasure(
          widthMeasureSpec: Int,
          heightMeasureSpec: Int
  ) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    val width = View.MeasureSpec.getSize(widthMeasureSpec)
    val height = View.MeasureSpec.getSize(heightMeasureSpec)
    if (0 == mRatioWidth || 0 == mRatioHeight) {
      setMeasuredDimension(width, height)
    } else {
      if (width < height * mRatioWidth / mRatioHeight) {
        mWidth = width
        mHeight = width * mRatioHeight / mRatioWidth
      } else {
        mWidth = height * mRatioWidth / mRatioHeight
        mHeight = height
      }
    }

    setMeasuredDimension(mWidth, mHeight)

    mRatioX = mImgWidth.toFloat() / mWidth
    mRatioY = mImgHeight.toFloat() / mHeight
  }
}