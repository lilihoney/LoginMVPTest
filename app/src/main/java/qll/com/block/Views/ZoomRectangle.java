package qll.com.block.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/2.
 */

public class ZoomRectangle extends View{

    //size
    private int mMeasureWidth;
    private int mMeasureHeight;

    private Paint rectPaint;
    private RectF rectF;
    private float rectWidth;
    private float rectHeight;

    private Paint textPaint;
    private String textShow;
    private float textSize;


    public ZoomRectangle(Context context){
        super(context);
    }
    public ZoomRectangle(Context context, AttributeSet attrs){
        super(context,attrs);
    }


    //用wrap_content属性的话必须要重写onMeasure
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureWidth = (int)MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mMeasureWidth,mMeasureHeight);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rectF,rectPaint);
        canvas.drawText(textShow,rectF.centerX(),rectF.centerY(),textPaint);
    }

    //初始化绘制用的Paint及其他
    private void initView(){
        rectHeight = mMeasureHeight/2;
        rectWidth = mMeasureWidth/2;
        rectF = new RectF((float)(mMeasureWidth*0.1),(float)(mMeasureHeight*0.1),(float)(mMeasureWidth*0.5),(float)(mMeasureHeight*0.3));
        rectPaint = new Paint();
        rectPaint.setColor(Color.CYAN);
        rectPaint.setStyle(Paint.Style.STROKE);//FILL是实心，STROKE是空心
        rectPaint.setStrokeWidth(10);
        rectPaint.setAntiAlias(true);

        textShow = getText();
        textSize = getTextSize();
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private float getTextSize(){
        this.invalidate();
        return 30;
    }

    private String getText(){
        this.invalidate();
        return "矩形";
    }
}
