package qll.com.block.DrawDetail;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/9/2.
 */

public class MyArcView extends Shape{
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public MyArcView(Context context) {
        super(context);
    }

    public MyArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyArcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
