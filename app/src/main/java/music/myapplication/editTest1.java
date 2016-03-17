package music.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by guanghaoshao on 16/1/22.
 */
public class editTest1  extends EditText{
    private Paint paint;
    public editTest1(Context context) {
        super(context);

    }

    public editTest1(Context context, AttributeSet attrs) {
        super(context, attrs);;

    }

    public editTest1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        paint=new Paint();

        paint.setColor(Color.BLACK);


        int padL = this.getPaddingLeft();//获取框内左边留白
        int padR = this.getPaddingRight();//获取框内右边留白
        int padT = this.getPaddingTop();//获取框内顶部留白

        int lines = this.getLineCount();//获取行数

        float size = this.getTextSize();//获取字体大小
//        float baseTop = padT + size / 6;//从上向下第一条线的位置
//        float gap = this.getLineHeight();//获取行宽


        for(int i = 1;i <= lines+20;i++)
        {
            paint=new Paint();

            float baseTop = padT + size / 6;//从上向下第一条线的位置
            float gap = this.getLineHeight();//获取行宽

            canvas.drawLine(padL//startX
                    , baseTop-25 + gap * i//startY
                    , this.getWidth() - padR//endX
                    , baseTop-25 + gap * i//endY
                    , paint);
        }
        paint.reset();

    }

}
