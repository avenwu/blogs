package com.avenwu.deepinandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by chaobin on 12/25/14.
 */
public class PolygonWithQuadraticBezirView extends View {
    Paint mCirclePaint;
    float mPointOneX;
    float mPointOneY;
    float mPointTwoX;
    float mPointTwoY;
    float mPointOneRadius;
    float mPointTwoRadius;
    Path mPath;
    Paint mShape;

    public PolygonWithQuadraticBezirView(Context context) {
        this(context, null);
    }

    public PolygonWithQuadraticBezirView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolygonWithQuadraticBezirView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(0xff00bcd4);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mPointOneX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, metrics);
        mPointOneY = mPointOneX;
        mPointTwoX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, metrics);
        mPointTwoY = mPointOneY * 1.5f;
        mPointOneRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, metrics);
        mPointTwoRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, metrics);
        mPath = new Path();
        mShape = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShape.setColor(Color.RED);
        mShape.setStyle(Paint.Style.STROKE);
        mShape.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(mPointOneX, mPointOneY, mPointOneRadius, mCirclePaint);
        canvas.drawCircle(mPointTwoX, mPointTwoY, mPointTwoRadius, mCirclePaint);
        mPath.reset();
        float dx = mPointTwoX - mPointOneX;
        float dy = mPointTwoY - mPointOneY;
        float d = (float) Math.sqrt(dx * dx + dy * dy);
        float midX = (mPointOneX + mPointTwoX) / 2.0f;
        float midY = (mPointOneY + mPointTwoY) / 2.0f;

        float sin = Math.abs(mPointOneRadius - mPointTwoRadius) / d;
        float cos = (float) Math.sqrt(1 - sin * sin);

        mPath.moveTo(mPointOneX + sin * mPointOneRadius, mPointOneY - cos * mPointOneRadius);
        mPath.lineTo(mPointOneX, mPointOneY);
        mPath.lineTo(mPointOneX + sin * mPointOneRadius, mPointOneY + cos * mPointOneRadius);

        mPath.quadTo(midX, midY, mPointTwoX + sin * mPointTwoRadius, mPointTwoY + cos * mPointTwoRadius);
//        mPath.lineTo(mPointTwoX + sin * mPointTwoRadius, mPointTwoY + cos * mPointTwoRadius);
        mPath.lineTo(mPointTwoX, mPointTwoY);
        mPath.lineTo(mPointTwoX + sin * mPointTwoRadius, mPointTwoY - cos * mPointTwoRadius);
        mPath.quadTo(midX, midY, mPointOneX + sin * mPointOneRadius, mPointOneY - cos * mPointOneRadius);
//        mPath.lineTo(mPointOneX + sin * mPointOneRadius, mPointOneY - cos * mPointOneRadius);
        canvas.drawPath(mPath, mShape);
    }

    public void setFilled(boolean fill) {
        if (fill) {
            mShape.setStyle(Paint.Style.FILL);
            mShape.setColor(mCirclePaint.getColor());
        } else {
            mShape.setStyle(Paint.Style.STROKE);
            mShape.setColor(Color.RED);
        }
        postInvalidate();
    }
}
