package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author tranglet.
 * @create by tranglet. on 3/13/18.
 */

public class DrawFrameLayout extends View {
    private float mLeft;
    private float mTop;
    private float mRight;
    private float mBottom;

    private DrawFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DrawFrameLayout(@NonNull Context context, float left, float top, float right, float bottom) {
        super(context);
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawBorderContainer(canvas);
    }

    private void drawBorderContainer(Canvas canvas) {
        Paint paintViewContainer = new Paint();
        paintViewContainer.setColor(Color.GREEN);
        paintViewContainer.setStrokeWidth(3);
        paintViewContainer.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paintViewContainer);
    }
}
