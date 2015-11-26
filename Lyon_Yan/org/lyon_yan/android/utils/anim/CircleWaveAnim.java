package org.lyon_yan.android.utils.anim;

import org.lyon_yan.android.utils.anim.WaveAnim.OnItemAnimFinishListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @author Lyon_Yan <br/>
 *         <b>time</b>: 2015年11月26日 上午10:35:32
 */
public class CircleWaveAnim extends View {
	private WaveAnim waveAnimUP = WaveAnim.bindWaveUpAnim(this);
	private WaveAnim waveAnimDown = WaveAnim.bindWaveDownAnim(this);

	public CircleWaveAnim(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init();
	}

	public CircleWaveAnim(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public CircleWaveAnim(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CircleWaveAnim(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

	}

	private OnItemAnimUPFinishListener onItemAnimUPFinishListener = new OnItemAnimUPFinishListener() {

		@Override
		public void onCancel(int index) {
			// TODO Auto-generated method stub

		}
	};
	private OnItemAnimDownFinishListener onItemAnimDownFinishListener = new OnItemAnimDownFinishListener() {

		@Override
		public void onCancel(int index) {
			// TODO Auto-generated method stub

		}
	};

	public interface OnItemAnimUPFinishListener extends
			OnItemAnimFinishListener {

	}

	public interface OnItemAnimDownFinishListener extends
			OnItemAnimFinishListener {

	}

	/**
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年11月26日 上午10:46:01
	 * @param startPoint
	 * @param endPoint
	 */
	public void focusPoints(Point startPoint, final Point endPoint) {
		// TODO Auto-generated method stub
		waveAnimUP.setOnItemAnimFinishListener(new OnItemAnimFinishListener() {

			@Override
			public void onCancel(int index) {
				// TODO Auto-generated method stub
				getOnItemAnimUPFinishListener().onCancel(index);
				waveAnimDown.focusPoint(endPoint);
			}
		});
		waveAnimDown
				.setOnItemAnimFinishListener(getOnItemAnimDownFinishListener());
		waveAnimUP.focusPoint(startPoint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		waveAnimUP.bindWaveDraw(canvas);
		waveAnimDown.bindWaveDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		waveAnimDown.BindWaveMeasure(widthMeasureSpec, heightMeasureSpec);
		waveAnimUP.BindWaveMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public OnItemAnimUPFinishListener getOnItemAnimUPFinishListener() {
		return onItemAnimUPFinishListener;
	}

	public void setOnItemAnimUPFinishListener(
			OnItemAnimUPFinishListener onItemAnimUPFinishListener) {
		this.onItemAnimUPFinishListener = onItemAnimUPFinishListener;
	}

	public OnItemAnimDownFinishListener getOnItemAnimDownFinishListener() {
		return onItemAnimDownFinishListener;
	}

	public void setOnItemAnimDownFinishListener(
			OnItemAnimDownFinishListener onItemAnimDownFinishListener) {
		this.onItemAnimDownFinishListener = onItemAnimDownFinishListener;
	}
}
