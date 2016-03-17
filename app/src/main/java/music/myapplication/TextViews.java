package music.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by guanghaoshao on 16/1/22.
 */
public class TextViews extends EditText {

    public TextViews(Context context) {
        super(context);
    }

    public TextViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //创建画笔
        Paint mPaint = new Paint();

        canvas.drawLine(0,20,30,20,mPaint);
        
        }
}
