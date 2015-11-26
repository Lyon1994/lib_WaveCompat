package org.lyon_yan.android.utils.anim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;

public class WaveAnim {
	private View view = null;
	private int width;
	private int height;
	private boolean isUP = true;
	private int color_main = Color.RED;

	private View getView() {
		return view;
	}

	public static WaveAnim bindWaveUpAnim(View view) {
		return new WaveAnim(view, true);
	}

	public static WaveAnim bindWaveDownAnim(View view) {
		return new WaveAnim(view, false);
	}

	private WaveAnim(View view, boolean isUP) {
		this.view = view;
		waveList = Collections.synchronizedList(new ArrayList<Wave>());
		setUP(isUP);
		if (isUP) {
			setAlphaInterpolator(new AlphaUpInterpolator());
			setRadiusInterpolator(new RadiusUpInterpolator());
		} else {
			setAlphaInterpolator(new AlphaDownInterpolator());
			setRadiusInterpolator(new RadiusDownInterpolator());
		}
	}

	/**
	 * 自定义差值器
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年11月25日 下午2:00:57
	 */
	public interface WaveAnimInterpolator extends Interpolator {

		public void clear();
		
		public boolean isCancel(Wave wave);

	}

	public static class RadiusUpInterpolator implements WaveAnimInterpolator {

		/**
		 * 初始动画扩散速度
		 */
		private int speed = 50;
		/**
		 * 每次需要变化速度的差值
		 */
		private final int speed_d = 2;

		@Override
		public float getInterpolation(float input) {
			// TODO Auto-generated method stub
			speed += speed_d;
			return input + speed;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			speed = 50;
		}

		@Override
		public boolean isCancel(Wave wave) {
			// TODO Auto-generated method stub
			return wave.getRadius() > wave.getMax_radius();
		}
	}

	public static class RadiusDownInterpolator implements WaveAnimInterpolator {

		/**
		 * 初始动画扩散速度
		 */
		private int speed = -100;
		/**
		 * 每次需要变化速度的差值
		 */
		private final int speed_d = 1;

		@Override
		public float getInterpolation(float input) {
			// TODO Auto-generated method stub
			if (speed + speed_d < 0) {
				speed += speed_d;
			}
			return input + speed;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			speed = -100;
		}
		
		@Override
		public boolean isCancel(Wave wave) {
			// TODO Auto-generated method stub
			return wave.getRadius() <0;
		}
	}

	public static class AlphaUpInterpolator implements WaveAnimInterpolator {

		/**
		 * 初始动画扩散速度
		 */
		@SuppressWarnings("unused")
		private int speed = 0;
		/**
		 * 每次需要变化速度的差值
		 */
		@SuppressWarnings("unused")
		private final int speed_d = 5;

		@Override
		public float getInterpolation(float input) {
			// TODO Auto-generated method stub
			// speed += speed_d;
			// return input - speed_d;
			return input;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			speed = 0;
		}

		@Override
		public boolean isCancel(Wave wave) {
			// TODO Auto-generated method stub
			return wave.getAlpha()==0;
		}

	}

	public static class AlphaDownInterpolator implements WaveAnimInterpolator {

		/**
		 * 初始动画扩散速度
		 */
		@SuppressWarnings("unused")
		private int speed = 0;
		/**
		 * 每次需要变化速度的差值
		 */
		@SuppressWarnings("unused")
		private final int speed_d = -5;

		@Override
		public float getInterpolation(float input) {
			// TODO Auto-generated method stub
//			 speed += speed_d;
//			 return input - speed_d;
			return input;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			speed = 0;
		}

		@Override
		public boolean isCancel(Wave wave) {
			// TODO Auto-generated method stub
			return wave.getAlpha()==255;
		}

	}

	private OnItemAnimFinishListener onItemAnimFinishListener = new OnItemAnimFinishListener() {

		@Override
		public void onCancel(int index) {
			// TODO Auto-generated method stub

		}
	};

	public interface OnItemAnimFinishListener {
		public void onCancel(int index);
	}

	private WaveAnimInterpolator alphaInterpolator = new AlphaUpInterpolator();

	public void setAlphaInterpolator(WaveAnimInterpolator alphaInterpolator) {
		this.alphaInterpolator = alphaInterpolator;
	}

	public WaveAnimInterpolator getAlphaInterpolator() {
		return alphaInterpolator;
	}

	public void setRadiusInterpolator(WaveAnimInterpolator radiusInterpolator) {
		this.radiusInterpolator = radiusInterpolator;
	}

	public WaveAnimInterpolator getRadiusInterpolator() {
		return radiusInterpolator;
	}

	/**
	 * 用於擴散的差值器
	 */
	private WaveAnimInterpolator radiusInterpolator = new RadiusUpInterpolator();
	/**
	 * 最大的不透明度，完全不透明
	 */
	private static final int MAX_ALPHA = 255;
	private boolean isStart = true;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				flushState();

				getView().invalidate();

				if (waveList != null && waveList.size() > 0) {
					handler.sendEmptyMessageDelayed(0, 16);
				}

				break;

			default:
				break;
			}
		}

	};

	/**
	 * 刷新状态
	 */
	private void flushState() {
		/**
		 * 为每一个wave对象刷新状态
		 */
		for (int i = 0; i < waveList.size(); i++) {
			Wave wave = waveList.get(i);
			Log.v("lyon8", "lyon8:----->\nwave.getRadius():" + wave.getRadius()
					+ "\nwave.getMax_radius():" + wave.getMax_radius());
			if (isStart == false
					&& radiusInterpolator.isCancel(wave)) {
				waveList.remove(i);
				wave.GC();
				wave = null;
				alphaInterpolator.clear();
				radiusInterpolator.clear();
				getOnItemAnimFinishListener().onCancel(i);
				continue;
			} else if (isStart == true) {
				isStart = false;
			}
			wave.setRadius(radiusInterpolator.getInterpolation(wave.getRadius()));
			wave.setAlpha(alphaInterpolator.getInterpolation(wave.getAlpha()));
		}

	}

	public void focusPoint(int x, int y) {
		Wave wave = new Wave(width, height);
		wave.setCircleCenter(x, y);
		if (isUP()) {
			wave.setRadius(0);
			wave.setAlpha(MAX_ALPHA);
		} else {
			wave.setRadius(wave.getMax_radius());
			wave.setAlpha(MAX_ALPHA);
		}
		wave.getPaint().setColor(Color.RED);
		if (waveList.size() == 0) {
			isStart = true;
		}
		System.out.println("isStart=" + isStart);
		waveList.add(wave);
		// 点击之后刷洗一次图形
		getView().invalidate();
		if (isStart) {
			handler.sendEmptyMessage(0);
		}
	}

	public void focusPoint(Point point) {
		focusPoint(point.x, point.y);
	}

	/**
	 * 触摸事件的方法
	 */
	public boolean bindWaveTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			focusPoint(Math.round(event.getX()), Math.round(event.getY()));
			break;

		default:
			break;
		}
		return true;
	}

	/**
	 * 画出需要的图形的方法，这个方法比较关键
	 */
	public void bindWaveDraw(Canvas canvas) {
		// 重绘所有圆环
		for (int i = 0; i < waveList.size(); i++) {
			Wave wave = waveList.get(i);
			canvas.drawCircle(wave.getX(), wave.getY(), wave.getRadius(),
					wave.getPaint());
		}
	}

	public void BindWaveMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.width = MeasureSpec.getSize(widthMeasureSpec);
		;
		this.height = MeasureSpec.getSize(heightMeasureSpec);
		;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isUP() {
		return isUP;
	}

	private void setUP(boolean isUP) {
		this.isUP = isUP;
	}

	public OnItemAnimFinishListener getOnItemAnimFinishListener() {
		return onItemAnimFinishListener;
	}

	public void setOnItemAnimFinishListener(
			OnItemAnimFinishListener onItemAnimFinishListener) {
		this.onItemAnimFinishListener = onItemAnimFinishListener;
	}

	/**
	 * 波形的List
	 */
	private List<Wave> waveList;

	public List<Wave> getWaveList() {
		return new ArrayList<>(waveList);
	}
}
