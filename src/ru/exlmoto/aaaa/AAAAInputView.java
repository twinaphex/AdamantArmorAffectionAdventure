package ru.exlmoto.aaaa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.libsdl.app.SDLActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class AAAAInputView extends View {

	private Paint paint = null;

	private int width = 0;
	private int height = 0;

	private int radius = 0;
	private static int[] pid = new int[10];

	private static final int KEYCODE_A = 8;
	private static final int KEYCODE_B = 9;

	public AAAAInputView(Context context) {

		super(context);

		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setARGB(128, 255, 255, 255);

		paint.setFilterBitmap(true);
		paint.setAntiAlias(true);
		if (p0id >= 0) {
			paint.setARGB(16, 255, 255, 255);
			canvas.drawCircle(p0x, p0y, radius << 1, paint);
			for (int i = 0; i < 8; ++i) {
				paint.setARGB(i == p1b ? 96 : 32, 255, 128, 128);
				canvas.drawCircle((float) (p0x + 7 * radius * Math.cos(i * Math.PI / 4) / 4),
						(float) (p0y - 7 * radius * Math.sin(i * Math.PI / 4) / 4),
						radius / 4, paint);
			}
			paint.setARGB(128, 255, 255, 255);
			canvas.drawCircle(p0x, p0y, radius / 3, paint);
			canvas.drawCircle(p1x, p1y, radius, paint);
		}

		// buttons
		paint.setARGB(pid[KEYCODE_A] < 0 ? 64 : 128, 255, 255, 255);
		canvas.drawCircle(width - radius - radius / 3, height - radius - radius / 3, radius, paint);
		paint.setARGB(pid[KEYCODE_B] < 0 ? 64 : 128, 255, 255, 255);
		canvas.drawCircle(width - radius - radius / 3, radius + radius / 3, radius, paint);

		invalidate();
		super.onDraw(canvas);
	}

	private static List<MotionEvent> events = new ArrayList<MotionEvent>();

	public static void addEvent(MotionEvent event) {
		synchronized (events) {
			events.add(event);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		addEvent(MotionEvent.obtainNoHistory(event));
		checkInput();
		//event.recycle();
		return true;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		width = getWidth();
		height = getHeight();

		radius = Math.min(width, height) / 11;
		Arrays.fill(pid, -1);

		AAAAActivity.toDebugLog("Set Resolution of Input layer as: " + width + "x" + height);
	}

	private int p0x, p0y, p1x, p1y, p1b, p0id = -1;

	public void checkInput() {
		List<MotionEvent> copy;
		synchronized (events) {
			copy = new ArrayList<MotionEvent>(events);
			events.clear();
		}
		int[] npid = Arrays.copyOf(pid, pid.length);
		int id, index, b, x;
		for (MotionEvent e : copy) {
			switch(e.getActionMasked()) {
			case MotionEvent.ACTION_MOVE:
				int len = e.getPointerCount();
				Arrays.fill(npid, -1);
				p1b = -1;
				for (index = 0; index < len; ++index) {
					id = e.getPointerId(index);
					if (id == p0id) {
						b = getDirection((int) e.getX(index), (int) e.getY(index));
					} else {
						b = getButton((int) e.getX(index), (int) e.getY(index));
					}
					if (b >= 0) {
						npid[b] = id;
					}
				}
				break;
			case MotionEvent.ACTION_DOWN:
				index = e.getActionIndex();
				id = e.getPointerId(index);
				cleanPointer(npid, id);
				x = (int) e.getX(index);
				if (x < (2 * width / 3)) {
					p1b = -1;
					p0x = x;
					p0y = (int) e.getY(index);
					p1x = p0x;
					p1y = p0y;
					p0id = id;
				} else {
					b = getButton((int) e.getX(index), (int) e.getY(index));
					if (b >= 0) {
						npid[b] = id;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				id = e.getPointerId(e.getActionIndex());
				cleanPointer(npid, id);
				if (id == p0id) {
					p0id = -1;
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				index = e.getActionIndex();
				id = e.getPointerId(index);
				cleanPointer(npid, id);
				x = (int) e.getX(index);
				if (x < (2 * width / 3)) {
					if (p0id < 0) {
						p1b = -1;
						p0x = x;
						p0y = (int) e.getY(index);
						p1x = p0x;
						p1y = p0y;
						p0id = id;
					}
				} else {
					b = getButton((int) e.getX(index), (int) e.getY(index));
					if (b >= 0) {
						npid[b] = id;
					}
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
				id = e.getPointerId(e.getActionIndex());
				cleanPointer(npid, id);
				if (id == p0id) {
					p0id = -1;
				}
				break;
			}
		}
		//lastKeyTyped = 0;
		//lastKey = 0;
		for (int i = 0; i < npid.length; ++i) {
			if (npid[i] < 0) {
				if (pid[i] >= 0) {
					releaseKey(i);
				}
			} else {
				pressKey(i);
			}
			pid[i] = npid[i];
		}
	}

	public void releaseKey(int keyCode) {
		AAAAActivity.toDebugLog(keyCode + " released!");
		switch (keyCode) {
		case KEYCODE_A:
			SDLActivity.onNativeKeyUp(66);
			break;
		case KEYCODE_B:
			SDLActivity.onNativeKeyUp(67);
			break;
		default:
			break;
		}
	}

	public void pressKey(int keyCode) {
		AAAAActivity.toDebugLog(keyCode + " pushed!");
		switch (keyCode) {
		case KEYCODE_A:
			SDLActivity.onNativeKeyDown(66);
			break;
		case KEYCODE_B:
			SDLActivity.onNativeKeyDown(67);
			break;
		default:
			break;
		}
	}

	public void cleanPointer(int[] npid, int id) {
		for (int i = 0; i < npid.length; ++i) {
			if (npid[i] == id) {
				npid[i] = -1;
				break;
			}
		}
	}

	public int getDirection(int bx, int by) {
		int abx = bx - p0x;
		int aby = p0y - by;

		double lenab = Math.sqrt(abx * abx + aby * aby);

		if (lenab > radius) {
			p1x = p0x + (int) ((bx - p0x) * (radius / lenab));
			p1y = p0y + (int) ((by - p0y) * (radius / lenab));
		} else {
			p1x = bx;
			p1y = by;
		}

		if (lenab < (radius / 3)) {
			p1b = -1;
		} else {
			double rslt = aby == 0 ? (abx > 0 ? 0 : Math.PI) : Math.acos(abx / lenab);
			if (by > p0y) {
				rslt = (Math.PI * 2) - rslt;
			}
			p1b = ((int) ((rslt + Math.PI / 16) / (Math.PI / 4))) & 0x7;
		}
		return p1b;
	}

	public int getButton(int x, int y) {
		int b = -1;
		if (x > (2 * width / 3)) {
			if (y > (height >> 1)) {
				b = KEYCODE_A;
			} else {
				b = KEYCODE_B;
			}
		}
		return b;
	}
}