/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 PankaJJakhar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package master.jakhar.colorplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class HomeScreenView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener {
	
	private final String TAG = "HomeScreenView";
	private MainActivity mMainActivity = null;
	private Context mContext = null;
	private SurfaceHolder mHolder;

	private int mCanvasHeight;
	private int mCanvasWidth;
	private int mCanvasCenterX;
	private int mCanvasCenterY;
	
	private Paint mInnerCirclePaint = null;
	private Paint mOuterCirclePaint = null;
	
	private float mInnerCircleRadius = 0;
	private float mOuterCircleRadius = 0;
	private boolean isTouch;

	public HomeScreenView(Context context) {
		super(context);
		
		mMainActivity = (MainActivity) context;
		mContext = context;
		mHolder= getHolder();
        mHolder.addCallback(this);
        setWillNotDraw(false);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public HomeScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mMainActivity = (MainActivity) context;
		mContext = context;
		mHolder= getHolder();
        mHolder.addCallback(this);
        setWillNotDraw(false);
        
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Initializes all the Canvas related objects.
	 * @param context
	 */
	private void init(Context context) {
		
		mInnerCircleRadius = mContext.getResources().getDimensionPixelSize(R.dimen.inner_circle_radius);
		mOuterCircleRadius = mContext.getResources().getDimensionPixelSize(R.dimen.outer_circle_radius);
		
		// TODO Auto-generated method stub
		mInnerCirclePaint = new Paint();
		//mOuterCirclePaint.setTypeface(miniSystem);
		mInnerCirclePaint.setColor(android.graphics.Color.WHITE);
		mInnerCirclePaint.setStyle(Paint.Style.FILL);
		mInnerCirclePaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.inner_circle_stroke_width_home));
		mInnerCirclePaint.setDither(true);
		mInnerCirclePaint.setStrokeJoin(Paint.Join.ROUND);
		mInnerCirclePaint.setStrokeCap(Paint.Cap.ROUND);
		mInnerCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		/**Initialize Outer Circle paint.*/
		mOuterCirclePaint = new Paint();
		//mOuterCirclePaint.setTypeface(miniSystem);
		mOuterCirclePaint.setColor(Color.parseColor("#6053ad"));
		//mOuterCirclePaint.setColor(Color.CYAN);
		mOuterCirclePaint.setStyle(Paint.Style.FILL);
		mOuterCirclePaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.outer_circle_stroke_width_home));
		mOuterCirclePaint.setDither(true);
		mOuterCirclePaint.setStrokeJoin(Paint.Join.ROUND);
		mOuterCirclePaint.setStrokeCap(Paint.Cap.ROUND);
		mOuterCirclePaint.setMaskFilter(new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL));
		mOuterCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}
	
	/**
	 * MyPaint is custom Paint instance which will wrap similar properties of multiple
	 * Paint instance in itself.
	 * @author JAKHAR
	 *
	 */
	public static class MyPaint{
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		mCanvasWidth = w;
		mCanvasHeight = h;

		/** Center Points of Canvas. */
		mCanvasCenterX = mCanvasWidth / 2;
		mCanvasCenterY = mCanvasHeight / 2;

		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDraw() called.");

		//canvas.drawColor(Color.parseColor("#0099cc"));
		canvas.drawColor(Color.WHITE);
		
		canvas.drawCircle(mCanvasCenterX, mCanvasCenterY,
				mOuterCircleRadius, mOuterCirclePaint);
		
		/** Draw little circle over path. */
		canvas.drawCircle(mCanvasCenterX, mCanvasCenterY,
				mInnerCircleRadius, mInnerCirclePaint);
		
		
		super.onDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	/**
	 * Checks if user Clicked inside the circle or not.
	 * @param x
	 * @param y
	 * @param circleCenterX
	 * @param circleCenterY
	 * @param circleRadius
	 * @return
	 */
	private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
	    double dx = Math.pow(x - circleCenterX, 2);
	    double dy = Math.pow(y - circleCenterY, 2);

	    if ((dx + dy) < Math.pow(circleRadius, 2)) {
	        return true;
	    } else {
	        return false;
	    }
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

	    int X = (int) event.getX();
	    int Y = (int) event.getY();

	    int eventaction = event.getAction();

	    switch (eventaction) {

	    case MotionEvent.ACTION_DOWN:

	        //Toast.makeText(mContext, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
	        if(inCircle(X, Y, mCanvasCenterX, mCanvasCenterY, mInnerCircleRadius)){
	        	Toast.makeText(mContext, "Starting Game...", Toast.LENGTH_SHORT).show();
	        	animateOuterCircle();
	        	Intent intent = new Intent(mMainActivity, GameActivity.class);
	        	mMainActivity.startActivity(intent);
	        }
	        isTouch = true;
	        break;

	    case MotionEvent.ACTION_MOVE:

	        //Toast.makeText(mContext, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();

	        break;

	    case MotionEvent.ACTION_UP:

	        //Toast.makeText(mContext, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();

	        break;

	    }

	    return true;

	}

	private void animateOuterCircle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
