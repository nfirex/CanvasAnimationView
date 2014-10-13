package com.wagado.example.animations;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.wagado.widget.CanvasAnimationView.CanvasAnimation;

public class EllipseCanvasAnimation extends CanvasAnimation {
	private final Paint mPaint;

	public EllipseCanvasAnimation(long duration, boolean fillAfter) {
		super(duration, fillAfter);
		mPaint = new Paint();
		mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
	}




	@Override
	protected void handle(Canvas canvas, float normalizedTime) {
		final float width = canvas.getWidth() * normalizedTime;
		final float height = canvas.getHeight() * normalizedTime;

		final float left = (canvas.getWidth() - width) * 0.5f;
		final float top = (canvas.getHeight() - height) * 0.5f;

		final RectF rect = new RectF(left, top, left + width, top + height);

		canvas.drawOval(rect, mPaint);
	}
}