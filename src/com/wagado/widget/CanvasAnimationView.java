package com.wagado.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

public class CanvasAnimationView extends ImageView {
	public static final String TAG = "widget.CanvasAnimationView";

	private CanvasAnimation mAnimation;
	private Bitmap mBitmap;
	private Canvas mCanvas;

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
			if (mBitmap == null) {
				mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
				mCanvas = new Canvas(mBitmap);
			}

			mCanvas.drawColor(Color.TRANSPARENT);

			super.onDraw(mCanvas);

			getCanvasAnimation().animate(this, mCanvas);
			canvas.drawBitmap(mBitmap, 0, 0, null);
		} else {
			super.onDraw(canvas);
		}
	}




	/** 
	 * Set CanvasAnimation or null it
	 * @param animation - CanvasAnimation or null
	 */
	public void setCanvasAnimation(CanvasAnimation animation) {
		mAnimation = animation;

		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}

		mCanvas = null;
	}

	/**
	 * Get setted CanvasAnimation
	 * @return CanvasAnimation or null
	 */
	public CanvasAnimation getCanvasAnimation() {
		return mAnimation;
	}

	/**
	 * Start setted CanvasAnimation. If CanvasAnimation is null - nothing happen
	 */
	public void startCanvasAnimation() {
		if (getCanvasAnimation() != null) {
			getCanvasAnimation().reset();
			invalidate();
		}
	}




	/**
	 * Abstract CanvasAnimation that implements only logic for work
	 * @author n.kleshin
	 *
	 */
	public static abstract class CanvasAnimation {
		final private long mDuration;
		final private boolean isFillAfter;

		public boolean isStarted;
		public boolean isExpired;
		public boolean isEnded;

		private long mStartTime;
		private IAnimationListener mListener;

		public CanvasAnimation(long duration, boolean fillAfter) {
			mDuration = duration;
			isFillAfter = fillAfter;
		}

		/**
		 * Set default/start values
		 */
		public void reset() {
			isStarted = false;
			isExpired = false;
			isEnded = false;

			mStartTime = Animation.START_ON_FIRST_FRAME;
		}

		/**
		 * Set listener to know when animation begin and complete
		 * @param listener - IAnimationListener or null
		 */
		public void setListener(IAnimationListener listener) {
			mListener = listener;
		}

		/**
		 * Get listener
		 * @return
		 */
		public IAnimationListener getListener() {
			return mListener;
		}

		/**
		 * Get duration of animation
		 * @return duration in milliseconds
		 */
		public long getDuration() {
			return mDuration;
		}

		/**
		 * Draw last frame of animation
		 * @return true - yes
		 */
		public boolean isFillAfter() {
			return isFillAfter;
		}

		/**
		 * Itterate animation
		 * @param view - parent view
		 * @param canvas - canvas of parent for drawing animation (already contains background and other elements of ImageView)
		 */
		protected void animate(View view, Canvas canvas) {
			if (mStartTime == Animation.START_ON_FIRST_FRAME) {
				mStartTime = System.currentTimeMillis();
			}

			final long currentTime = System.currentTimeMillis();
			final float normalizedTime;
			if (getDuration() != 0) {
				normalizedTime = ((float) (currentTime - mStartTime)) / (float) getDuration();
			} else {
				// time is a step-change with a zero duration
				normalizedTime = currentTime < mStartTime ? 0.0f : 1.0f;
			}

			isExpired = normalizedTime >= 1.0f;

			if (normalizedTime >= 0.0f && normalizedTime <= 1.0f) {
				if (!isStarted) {
					fireAnimationStart();
					isStarted = true;
				}

				handle(canvas, normalizedTime);
			}

			if (isExpired) {
				if (!isEnded) {
					isEnded = true;
					fireAnimationEnd();
				}

				if (isFillAfter()) {
					handle(canvas, normalizedTime);
				}
			} else {
				view.invalidate();
			}
		}

		/**
		 * One step of animation
		 * @param canvas - canvas of parent for drawing animation
		 * @param normalizedTime - value is from 0 to 1
		 */
		protected abstract void handle(Canvas canvas, float normalizedTime);

		/**
		 * Notify when animation is begin
		 */
		protected void fireAnimationStart() {
			if (getListener() != null) {
				getListener().onAnimationStart(this);
			}
		}

		/**
		 * Notify when animation is complete
		 */
		protected void fireAnimationEnd() {
			if (getListener() != null) {
				getListener().onAnimationEnd(this);
			}
		}
	}

	public static interface IAnimationListener {
		/**
		 * <p>Notifies the start of the animation.</p>
		 *
		 * @param animation The started animation.
		 */
		void onAnimationStart(CanvasAnimation animation);

		/**
		 * <p>Notifies the end of the animation. This callback is not invoked
		 * for animations with repeat count set to INFINITE.</p>
		 *
		 * @param animation The animation which reached its end.
		 */
		void onAnimationEnd(CanvasAnimation animation);
	}
}
