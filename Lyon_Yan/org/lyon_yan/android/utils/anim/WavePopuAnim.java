package org.lyon_yan.android.utils.anim;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class WavePopuAnim implements OnTouchListener {
	private View contentView = null;
	private View parent = null;

	private void setContentView(View contentView) {
		this.contentView = contentView;
	}

	private void setParent(View parent) {
		this.parent = parent;
	}

	private void setBackground(Drawable background) {
		this.background = background;
	}

	private Drawable background = null;

	public WavePopuAnim(View parent, View contentView, Drawable background) {
		setBackground(background);
		setContentView(contentView);
		setParent(parent);
	}

	private void showPopupWindow(Point focus_point) {
		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

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
		Toast.makeText(parent.getContext(),
				"focus_point:(" + focus_point.x + "," + focus_point.y + ")",
				Toast.LENGTH_SHORT).show();
		// 设置好参数之后再show
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, focus_point.x,
				focus_point.y);

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
