package org.lyon_yan.android.utils.anim;

import org.lyon_yan.android.utils.anim.CircleWaveAnim.OnItemAnimDownFinishListener;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.FROYO)
public class WavePopuAnim implements OnTouchListener {
	private CircleWaveAnim circleWaveAnim = null;
	private View parent = null;
	private int x = 0;
	private int y = 0;

	private void setParent(View parent) {
		this.parent = parent;
	}

	public WavePopuAnim(View parent, Drawable background, Context context) {
		this(parent, background, context, 0, 0);
	}

	/**
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年11月26日 下午1:09:55
	 * @param parent
	 * @param background
	 * @param context
	 * @param x
	 *            偏移量
	 * @param y
	 *            偏移量
	 */
	public WavePopuAnim(View parent, Drawable background, Context context,
			int x, int y) {
		setParent(parent);
		this.x = x;
		this.y = y;
		circleWaveAnim = new CircleWaveAnim(context);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		circleWaveAnim.setLayoutParams(layoutParams);
		popupWindow = new PopupWindow(circleWaveAnim,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				Log.i("mengdd", "onTouch : ");

				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(background);
		circleWaveAnim
				.setOnItemAnimDownFinishListener(new OnItemAnimDownFinishListener() {

					@Override
					public void onCancel(int index) {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}
				});
		circleWaveAnim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
	}

	private PopupWindow popupWindow = null;

	/**
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年11月26日 下午1:06:26
	 * @param startPoint
	 * @param focus_point
	 */
	public void showPopupWindow(Point startPoint, Point focus_point) {
		Toast.makeText(parent.getContext(),
				"focus_point:(" + focus_point.x + "," + focus_point.y + ")",
				Toast.LENGTH_SHORT).show();
		// 设置好参数之后再show
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
		circleWaveAnim.focusPoints(startPoint, focus_point);
	}

	public void showPopupWindow(Point focus_point) {
		showPopupWindow(focus_point, focus_point);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			showPopupWindow(new Point(Math.round(event.getX()),
					Math.round(event.getY())));
			break;

		default:
			break;
		}
		return true;
	}
}
