package com.wagado.example.animations;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

import com.wagado.widget.CanvasAnimationView.CanvasAnimation;

public class CellCanvasAnimation extends CanvasAnimation {
	private final int mCellsInWidth;
	private final int mCellsInHeight;
	private final Paint mPaint;

	private boolean isInitialized;
	private int mCellWidth;
	private int mCellHeight;

	public CellCanvasAnimation(long duration, boolean fillAfter, int cellsInWidth, int cellsInHeight) {
		super(duration, fillAfter);

		mCellsInWidth = cellsInWidth;
		mCellsInHeight = cellsInHeight;

		mPaint = new Paint();
		mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
	}



	@Override
	public void reset() {
		super.reset();
		isInitialized = false;
	}

	@Override
	protected void handle(Canvas canvas, float normalizedTime) {
		if (!isInitialized) {
			initialize(canvas.getWidth(), canvas.getHeight());
		}
		

		final float widthX = normalizedTime * mCellWidth;
		final float widthY = normalizedTime * mCellHeight;

		mPaint.setStrokeWidth(widthX);
		for (int i = 0; i <= mCellsInWidth; i ++) {
			final int x = i * mCellWidth;
			canvas.drawLine(x, 0, x, canvas.getHeight(), mPaint);
		}

		mPaint.setStrokeWidth(widthY);
		for (int i = 0; i <= mCellsInHeight; i ++) {
			final int y = i * mCellHeight;
			canvas.drawLine(0, y, canvas.getWidth(), y, mPaint);
		}
	}




	public void initialize(int width, int height) {
		mCellWidth = width / mCellsInWidth;
		mCellHeight = height / mCellsInHeight;
		isInitialized = true;
	}
}