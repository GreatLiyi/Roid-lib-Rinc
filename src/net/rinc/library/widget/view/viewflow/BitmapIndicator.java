package net.rinc.library.widget.view.viewflow;

import net.rinc.library.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

public class BitmapIndicator extends BaseIndicator{
	private final Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
	private Bitmap activeBitmap,inactiveBitmap;

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public BitmapIndicator(Context context,AttributeSet attrs){
		super(context,attrs);
		TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.Indicator);
		try{
			activeBitmap=((BitmapDrawable)typedArray.getDrawable(R.styleable.Indicator_bitmapActive)).getBitmap();
		}catch(Exception e){
			throw new IllegalArgumentException("ActiveBitmap should be a BitmapDrawable object.");
		}
		try{
			inactiveBitmap=((BitmapDrawable)typedArray.getDrawable(R.styleable.Indicator_bitmapInactive)).getBitmap();
		}catch(Exception e){
			throw new IllegalArgumentException("InactiveBitmap should be a BitmapDrawable object.");
		}
		typedArray.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(activeBitmap!=null&&inactiveBitmap!=null){
			int index=getPosition();
			for(int i=0;i<getViewFlow().getViewsCount();i++){
				if(i==index){
					canvas.drawBitmap(activeBitmap,
							getPaddingLeft()+(getInnerMargin()+activeBitmap.getWidth())*index,
							getPaddingTop(),
							mPaint);
				}else{
					canvas.drawBitmap(inactiveBitmap,
							getPaddingLeft()+(getInnerMargin()+inactiveBitmap.getWidth())*i,
							getPaddingTop(),
							mPaint);
				}
			}
			
		}
	}
	
	@Override
	protected int onMeasureWidth(int measureSpec){
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);
		int width=specSize;
		if(specMode!=MeasureSpec.EXACTLY){
			if(activeBitmap!=null&&inactiveBitmap!=null){
				int count=getViewFlow().getAdapter().getCount();
				width=(int)(getPaddingLeft()+getPaddingRight()
						+(count*Math.max(activeBitmap.getWidth(),inactiveBitmap.getWidth()))
						+(count-1)*getInnerMargin()+1);
			}
			if(specMode==MeasureSpec.AT_MOST){
				width=Math.min(width,specSize);
			}
		}
		return width;
	}
	
	@Override
	protected int onMeasureHeight(int measureSpec){
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);
		int height=specSize;
		if(specMode!=MeasureSpec.EXACTLY){
			if(activeBitmap!=null&&inactiveBitmap!=null){
				height=(int)(Math.max(activeBitmap.getHeight(),inactiveBitmap.getHeight())
					+getPaddingTop()+getPaddingBottom()+1);
			}
			if(specMode==MeasureSpec.AT_MOST){
				height=Math.min(height,specSize);
			}
		}
		return height;
	}
}