package org.lyon_yan.android.utils.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CircleWaveAnim extends View {
	private WaveAnim waveAnim = WaveAnim.bindWaveAnim(this);

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

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	public WaveAnim getWaveAnim() {
		return waveAnim;
	}

	public void setWaveAnim(WaveAnim waveAnim) {
		this.waveAnim = waveAnim;
	}

}
