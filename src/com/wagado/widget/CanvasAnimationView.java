package com.wagado.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CanvasAnimationView extends ImageView {
	public static final String TAG = "widget.CanvasAnimationView";

	private CanvasAnimation mAnimation;

	public CanvasAnimationView(Context context) {
		this(context, null);
	}

	public CanvasAnimationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasAnimationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getCanvasAnimation() != null) {
			final Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
			final Canvas tmpCanvas = new Canvas(bitmap);

			super.onDraw(tmpCanvas);

			getCanvasAnimation().animate(this, tmpCanvas);
			
			canvas.drawBitmap(bitmap, 0, 0, null);
		} else {
			super.onDraw(canvas);
		}
	}




	public void setCanvasAnimation(CanvasAnimation animation) {
		mAnimation = animation;
	}

	public CanvasAnimation getCanvasAnimation() {
		return mAnimation;
	}

	public void startCanvasAnimation() {
		if (getCanvasAnimation() != null) {
			getCanvasAnimation().reset();
			invalidate();
		}
	}
}