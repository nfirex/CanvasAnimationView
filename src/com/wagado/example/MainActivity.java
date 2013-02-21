package com.wagado.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wagado.widget.CanvasAnimation;
import com.wagado.widget.CanvasAnimationView;
import com.wagado.widget.OvalCanvasAnimation;

public class MainActivity extends Activity {

	private CanvasAnimationView mAnimationView;
	private CanvasAnimation mEllipseAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAnimationView = (CanvasAnimationView) findViewById(R.id.animation_view);

		mEllipseAnimation = new OvalCanvasAnimation(3000, true, 6, 6);
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

			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}
}
