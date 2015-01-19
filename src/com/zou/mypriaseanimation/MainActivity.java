package com.zou.mypriaseanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {
	private LinearLayout mLayoutPraise;
	private ImageView mPraiseView;
	private ImageView mAnimationImageView = null;  //做动画的view

	private ViewGroup mContentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mContentContainer = (ViewGroup) findViewById(R.id.content_container);

		mLayoutPraise = (LinearLayout) findViewById(R.id.layout_praise);
		mPraiseView = (ImageView) findViewById(R.id.img_praise);
		mLayoutPraise.setOnClickListener(this);
		mPraiseView.setOnClickListener(this);
		mAnimationImageView = new ImageView(getApplicationContext());
		// 获取到源图片的当前view，并设置到Drawable
		mAnimationImageView.setImageDrawable(this.getResources().getDrawable(
				R.drawable.gomarket_gostore_theme_detail_bottom_button_layout_praise));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.layout_praise) {
			praiseFlyOutAnimation();
		}
	}

	public void praiseFlyOutAnimation() {
		int[] locationSrc = new int[2];
		mPraiseView.getLocationInWindow(locationSrc); // 要下载应用图标的位置,相对于window的位置
		int width = mPraiseView.getWidth();
		int height = mPraiseView.getHeight();

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);

		int[] locationDest = new int[2];
		if (mContentContainer != null) {
			mContentContainer.getLocationInWindow(locationDest);
			mContentContainer.addView(mAnimationImageView, params);
		}

		int startX = locationSrc[0];
		int startY = locationSrc[1] - locationDest[1] - 48;
		int endY = startY - 300;

		Animation translateAnimation = new TranslateAnimation(startX, startX, startY, endY);
		translateAnimation.setDuration(3000);

		// 缩放
		Animation scaleAnimation = new ScaleAnimation(2.0f, 2.0f, 2.0f, 2.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(3000);
		
		// 自定义旋转动画效果，含透明效果
		PraiseRotateAnimation rotateAnimation = new PraiseRotateAnimation(-15, 20);
		rotateAnimation.setDuration(3000);

		//系统旋转动画效果
		//		Animation rotateAnimation1 = new RotateAnimation(-20, 20, Animation.RELATIVE_TO_SELF, 0.5f,
		//				Animation.RELATIVE_TO_SELF, 0.5f);
		//		rotateAnimation1.setDuration(3000);

		//		// 动画透明效果
		//		Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
		//		alphaAnimation.setDuration(1000);

		AnimationSet animationSet = new AnimationSet(true);
		animationSet.setInterpolator(new DecelerateInterpolator(2.0f));

		//		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(translateAnimation);

		mLayoutPraise.setClickable(false);
		animationSet.setFillAfter(true);
		animationSet.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				mContentContainer.removeView(mAnimationImageView);
				mLayoutPraise.setClickable(true);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {

			}
		});
		mAnimationImageView.startAnimation(animationSet);
		mAnimationImageView.setTag(animationSet);
	}

}
