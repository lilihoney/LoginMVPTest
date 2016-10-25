package qll.com.block.DrawDetail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/2.
 */

public abstract class Shape extends View{
    private Paint mainPaint;
    private Paint secondPaint;

    public Shape(Context context){
        super(context,null);
    }

    public Shape(Context context, AttributeSet attrs){
        super(context,attrs,0);
    }

    public Shape(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        initPaint();
    }

    private void initPaint(){
        mainPaint = new Paint();
        mainPaint.setColor(Color.RED);
        mainPaint.setStyle(Paint.Style.FILL);
        mainPaint.setStrokeCap(Paint.Cap.ROUND);
        mainPaint.setAlpha(20);

        secondPaint = new Paint();
        secondPaint.setColor(Color.GREEN);
        secondPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
