package org.lyon_yan.android.utils.anim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Paint;
import android.util.Log;

public class Wave {
	private int screenHeight = 0;
	private int screenWidth = 0;

	public Wave(int screenHeight, int screenWidth) {
		super();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	/**
	 * 最大的不透明度，完全不透明
	 */
	public static final int MAX_ALPHA = 255;
	public static final int MIN_ALPHA = 0;

	public void setCircleCenter(int x, int y) {
		// TODO Auto-generated method stub
		this.xDown = x;
		this.yDown = y;
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(this.max_radius = getLongSideSize(0, 0));
		integers.add(getLongSideSize(0, screenHeight));
		integers.add(getLongSideSize(screenWidth, 0));
		integers.add(getLongSideSize(screenWidth, screenHeight));
		for (int i = 1; i < 4; i++) {
			this.max_radius = Math.max(this.max_radius, integers.get(i));
		}
		integers.clear();
		integers = null;
	}

	private int getLongSideSize(int xx, int yy) {
		Log.e("lyon7", "getLongSideSize(" + xx + "," + yy + ")");
		return (int) Math.sqrt(Math.pow(xDown - xx, 2)
				+ Math.pow(yDown - yy, 2));
	}

	/**
	 * 用来表示圆环的半径
	 */
	private float radius;
	private int max_radius;

	public int getMax_radius() {
		return max_radius;
	}

	private Paint paint;

	public Paint getPaint() {
		if (paint == null) {
			initPaint();
		}
		return paint;
	}

	private Paint initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);

		// 设置是环形方式绘制
		paint.setStyle(Paint.Style.FILL);
		return paint;
	}

	/**
	 * 按下的时候x坐标
	 */
	private int xDown;
	/**
	 * 按下的时候y的坐标
	 */
	private int yDown;

	public int getX() {
		return xDown;
	}

	public int getY() {
		return yDown;
	}

	private int alpha;

	public void setAlpha(int alpha) {
		this.alpha = alpha;
		getPaint().setAlpha(alpha);
		if (alpha < 0) {
			setAlpha(Wave.MIN_ALPHA);
		}
	}

	public void setAlpha(float alpha) {
		setAlpha(Math.round(alpha));
	}

	public int getAlpha() {
		return alpha;
	}

	public void GC() {
		paint = null;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}