package com.zou.mypriaseanimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * <br>类描述:自定义点赞弧度抖动动画
 * <br>功能详细描述:走S路线
 * 
 * @author  zou
 * @date  [2015-1-16]
 */
public class PraiseRotateAnimation extends Animation {
	private int mPivotXType = RELATIVE_TO_SELF;
	private int mPivotYType = RELATIVE_TO_SELF;
	private float mPivotXValue = 0.5f;
	private float mPivotYValue = 0.5f;

	private float mPivotX;
	private float mPivotY;

	private float mDegree0;
	private float mDegree1;
	private float mDegree2;

    private float mFromAlpha = 1.0f;
    private float mToAlpha = 0.0f;
    
	/**
	 *  <默认构造函数>
	 */
	public PraiseRotateAnimation(float degree1, float degree2) {
		mDegree0 = 0;
		mDegree1 = degree1;
		mDegree2 = degree2;
	}

	/** 
	 * <默认构造函数>
	 */
	public PraiseRotateAnimation(float degree1, float degree2, int pivotXType, float pivotXValue,
			int pivotYType, float pivotYValue) {
		mDegree0 = 0;
		mDegree1 = degree1;
		mDegree2 = -degree1;

		mPivotXValue = pivotXValue;
		mPivotXType = pivotXType;
		mPivotYValue = pivotYValue;
		mPivotYType = pivotYType;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float degrees = 0f;
		final float alpha = mFromAlpha;
		if (interpolatedTime < 0.35f) { // 第一段 摆动到左最大角度
			degrees = mDegree0 + ((mDegree1 - mDegree0) * ((interpolatedTime - 0) / 0.35f));
		} else if (interpolatedTime <= 1) { // 第二段 从左最大角度 到 右最大角度
			degrees = mDegree1 + ((mDegree2 - mDegree1) * ((interpolatedTime - 0.35f) / 0.65f));
		}

		if (mPivotX == 0.0f && mPivotY == 0.0f) {
			t.getMatrix().setRotate(degrees);
		} else {
			t.getMatrix().setRotate(degrees, mPivotX, mPivotY);
			if (interpolatedTime > 0.5 && interpolatedTime <= 1) {
				t.setAlpha(alpha + ((mToAlpha - alpha) * interpolatedTime));
			}
		}
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
		mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
	}

}
