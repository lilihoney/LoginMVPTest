package qll.com.block.DrawDetail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/9/2.
 */

public class MyRectView extends Shape{
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public MyRectView(Context context) {
        super(context);
    }

    public MyRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
