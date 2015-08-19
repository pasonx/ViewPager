package com.pason.justtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Admin on 2015/8/10.
 */
public class ChangeColorIconWithText extends View {

    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;
    private int mTextSize = (int) TypedValue
            .applyDimension(TypedValue
                    .COMPLEX_UNIT_SP, 12, getResources()
                    .getDisplayMetrics());
    private String mText = "微信";

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private float mAlpha;

    private Rect iconRect;
    private Rect textBound;
    private Paint textPaint;

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    public ChangeColorIconWithText(Context context) {
        this(context, null);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 获取自定义属性的值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue
                            .applyDimension(TypedValue
                                    .COMPLEX_UNIT_SP, 12, getResources()
                                    .getDisplayMetrics()));
                    break;


            }
        }
        a.recycle();

        textBound = new Rect();
        textPaint = new Paint();
        textPaint.setTextSize(mTextSize);
        textPaint.setColor(0Xff555555);
        textPaint.getTextBounds(mText, 0, mText.length(), textBound);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - textBound.height());


        int left = (getMeasuredWidth() - iconWidth) / 2;
        int top = (getMeasuredHeight() - (iconWidth + textBound.height())) / 2;
        iconRect = new Rect(left,top,left + iconWidth,top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, iconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);
        //准备绘制纯色，设置透明度，纯色，Xfermode,图标
        setTargetBitmap(alpha);
        //绘制底侧文本
        drawSouceText(canvas,alpha);
        //绘制变色文本
        drawTargetText(canvas,alpha);
        canvas.drawBitmap(bitmap, 0, 0, null);

    }

    private void drawTargetText(Canvas canvas, int alpha) {
        textPaint.setColor(mColor);
        textPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - textBound.width() / 2;
        int y = iconRect.bottom + textBound.height();
        canvas.drawText(mText, x, y, textPaint);
    }

    /**
     * 绘制原文本
     *
     */
    private void drawSouceText(Canvas canvas,int alpha) {
        textPaint.setColor(0xff333333);
        textPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - textBound.width() / 2;
        int y = iconRect.bottom + textBound.height();
        canvas.drawText(mText, x, y, textPaint);
    }

    private void setTargetBitmap(int alpha) {
        bitmap = bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setAlpha(alpha);

        canvas.drawRect(iconRect, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        paint.setAlpha(255);
        canvas.drawBitmap(mIconBitmap, null, iconRect, paint);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS,super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA,mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle)state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setIconAlpha(float alpha){
        mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()){
            invalidate();
        }else {
            postInvalidate();
        }
    }

}