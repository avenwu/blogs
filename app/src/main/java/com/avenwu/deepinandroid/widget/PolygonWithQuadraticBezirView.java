package com.avenwu.deepinandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
    float MAX_HORIZOTNAL_DISTANCE;
    float MAX_VERTICAL_DISTANCE;

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
        MAX_HORIZOTNAL_DISTANCE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, metrics);
        MAX_VERTICAL_DISTANCE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, metrics);

        mPointOneX = MAX_VERTICAL_DISTANCE * 0.4f;
        mPointOneY = mPointOneX;
        mPointTwoX = mPointOneX * 1.75f;
        mPointTwoY = MAX_HORIZOTNAL_DISTANCE * 0.5f;
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
        float k = (mPointTwoY - mPointOneY) / (mPointTwoX - mPointOneX);

        float sin = Math.abs(mPointOneRadius - mPointTwoRadius) / d;
        float cos = (float) Math.sqrt(1 - sin * sin);
        if (k >= -1 && k <= 1) {
            mPath.moveTo(mPointOneX + sin * mPointOneRadius, mPointOneY - cos * mPointOneRadius);
            mPath.lineTo(mPointOneX, mPointOneY);
            mPath.lineTo(mPointOneX + sin * mPointOneRadius, mPointOneY + cos * mPointOneRadius);

            mPath.quadTo(midX, midY, mPointTwoX + sin * mPointTwoRadius, mPointTwoY + cos * mPointTwoRadius);
            mPath.lineTo(mPointTwoX, mPointTwoY);
            mPath.lineTo(mPointTwoX + sin * mPointTwoRadius, mPointTwoY - cos * mPointTwoRadius);
            mPath.quadTo(midX, midY, mPointOneX + sin * mPointOneRadius, mPointOneY - cos * mPointOneRadius);
        } else {
            mPath.moveTo(mPointOneX + cos * mPointOneRadius, mPointOneY + sin * mPointOneRadius);
            mPath.lineTo(mPointOneX, mPointOneY);
            mPath.lineTo(mPointOneX - cos * mPointOneRadius, mPointOneY + sin * mPointOneRadius);

            mPath.quadTo(midX, midY, mPointTwoX - cos * mPointTwoRadius, mPointTwoY + sin * mPointTwoRadius);
            mPath.lineTo(mPointTwoX, mPointTwoY);
            mPath.lineTo(mPointTwoX + cos * mPointTwoRadius, mPointTwoY + sin * mPointTwoRadius);
            mPath.quadTo(midX, midY, mPointOneX + cos * mPointOneRadius, mPointOneY + sin * mPointOneRadius);
        }
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
        invalidate();
    }

    public void moveHorizontal(float percent) {
        mPointTwoX = MAX_HORIZOTNAL_DISTANCE * percent;
        invalidate();
    }

    public void moveVertical(float percent) {
        mPointTwoY = MAX_VERTICAL_DISTANCE * percent;
        invalidate();
    }
}
