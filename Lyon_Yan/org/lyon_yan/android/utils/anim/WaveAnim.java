package org.lyon_yan.android.utils.anim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

public class WaveAnim {
	private View view = null;


	private View getView() {
		return view;
	}

	public static WaveAnim bindWaveAnim(View view) {
		return new WaveAnim(view);
	}

	private WaveAnim(View view) {
		this.view = view;
		waveList = Collections.synchronizedList(new ArrayList<Wave>());
	}

	/**
	 * 最大的不透明度，完全不透明
	 */
	private static final int MAX_ALPHA = 255;

	protected static final int FLUSH_ALL = -1;

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
	 * 初始动画扩散速度
	 */
	private int speed = 0;
	/**
	 * 每次需要变化速度的差值
	 */
	private int speed_d = 5;

	/**
	 * 刷新状态
	 */
	private void flushState() {
		/**
		 * 为每一个wave对象刷新状态
		 */
		for (int i = 0; i < waveList.size(); i++) {
			Wave wave = waveList.get(i);
			if (isStart == false && wave.alpha == 0) {
				waveList.remove(i);
				wave.paint = null;
				wave = null;
				speed = 0;
				continue;
			} else if (isStart == true) {
				isStart = false;
			}
			speed += speed_d;
			wave.radius += speed;
			wave.alpha -= 5;
			if (wave.alpha < 0) {
				wave.alpha = 0;
			}
			wave.width = wave.radius / 4;
			wave.paint.setAlpha(wave.alpha);
			wave.paint.setStrokeWidth(wave.width);
		}

	}

	/**
	 * 初始化paint
	 */
	private Paint initPaint(int alpha, float width) {
		/*
		 * 新建一个画笔
		 */
		Paint paint = new Paint();

		paint.setAntiAlias(true);
		paint.setStrokeWidth(width);

		// 设置是环形方式绘制
		paint.setStyle(Paint.Style.FILL);

		// System.out.println(alpha= + alpha);
		paint.setAlpha(alpha);
		// System.out.println(得到的透明度： + paint.getAlpha());

		paint.setColor(Color.RED);
		return paint;
	}

	/**
	 * 触摸事件的方法
	 */
	public boolean bindWaveTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Wave wave = new Wave();
			wave.radius = 0;
			wave.alpha = MAX_ALPHA;
			wave.width = wave.radius / 4;
			wave.xDown = (int) event.getX();
			wave.yDown = (int) event.getY();
			wave.paint = initPaint(wave.alpha, wave.width);
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
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

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
			canvas.drawCircle(wave.xDown, wave.yDown, wave.radius, wave.paint);
		}
	}

	/**
	 * 波形的List
	 */
	private List<Wave> waveList;

	private class Wave {
		/**
		 * 用来表示圆环的半径
		 */
		float radius;
		Paint paint;
		/**
		 * 按下的时候x坐标
		 */
		int xDown;
		/**
		 * 按下的时候y的坐标
		 */
		int yDown;
		float width;
		int alpha;
	}
}
