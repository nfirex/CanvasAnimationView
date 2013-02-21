package com.wagado.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;


public class OvalCanvasAnimation extends CanvasAnimation {
	private final int mCellsInWidth;
	private final int mCellsInHeight;
	private final Paint mPaint;

	private int mCellWidth;
	private int mCellHeight;

	public OvalCanvasAnimation(long duration, boolean fillAfter, int cellsInWidth, int cellsInHeight) {
		super(duration, fillAfter);

		mCellsInWidth = cellsInWidth;
		mCellsInHeight = cellsInHeight;

		mPaint = new Paint();
		mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
//		mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));
	}




	@Override
	protected void handle(Canvas canvas, float normalizedTime) {
		final int heightDevTwo = canvas.getHeight() / 2;
		final int widthDevTwo = canvas.getWidth() / 2;

		final RectF rect = new RectF(widthDevTwo * normalizedTime, widthDevTwo * (1 +  normalizedTime), heightDevTwo * normalizedTime, heightDevTwo * (1 +  normalizedTime));

		canvas.drawOval(rect, mPaint);
		canvas.drawText("time is " + normalizedTime, 100, 100, mPaint);
	}
}