package com.wagado.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wagado.example.animations.CellCanvasAnimation;
import com.wagado.example.animations.CirclesCanvasAnimation;
import com.wagado.example.animations.EllipseCanvasAnimation;
import com.wagado.example.animations.EllipseInfiniteCanvasAnimation;
import com.wagado.example.animations.CirclesCanvasAnimation.Circle;
import com.wagado.widget.CanvasAnimationView;
import com.wagado.widget.CanvasAnimationView.CanvasAnimation;

public class MainActivity extends Activity {

	private CanvasAnimationView mAnimationView;
	private CanvasAnimation mEllipseAnimation;
	private CanvasAnimation mEllipseInfiniteAnimation;
	private CanvasAnimation mCellAnimation;
	private CirclesCanvasAnimation mCirclesAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAnimationView = (CanvasAnimationView) findViewById(R.id.animation_view);

		mEllipseAnimation = new EllipseCanvasAnimation(3000, true);
		mEllipseInfiniteAnimation = new EllipseInfiniteCanvasAnimation(6000, true);
		mCellAnimation = new CellCanvasAnimation(2000, true, 6, 6);
		mCirclesAnimation = new CirclesCanvasAnimation(3000, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_ellipse:
				mAnimationView.setCanvasAnimation(mEllipseAnimation);
				mAnimationView.startCanvasAnimation();
				return true;

			case R.id.item_ellipse_infinite:
				mAnimationView.setCanvasAnimation(mEllipseInfiniteAnimation);
				mAnimationView.startCanvasAnimation();
				return true;

			case R.id.item_cell:
				mAnimationView.setCanvasAnimation(mCellAnimation);
				mAnimationView.startCanvasAnimation();
				return true;

			case R.id.item_circles:
				final Collection<Circle> circles = new ArrayList<Circle>();
				final Random random = new Random(); 
				final int minRadiusMaxValue = Math.min(mAnimationView.getMeasuredWidth(), mAnimationView.getMeasuredHeight()) / 4;
				for (int i = 0; i < 4; i ++) {
					final int x = random.nextInt(mAnimationView.getMeasuredWidth());
					final int y = random.nextInt(mAnimationView.getMeasuredHeight());
					final int minRadius = random.nextInt(minRadiusMaxValue);
					final int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
					circles.add(new Circle(x, y, minRadius, minRadiusMaxValue, color));
				}
				mCirclesAnimation.setCircles(circles);

				mAnimationView.setCanvasAnimation(mCirclesAnimation);
				mAnimationView.startCanvasAnimation();
				return true;

			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}
}