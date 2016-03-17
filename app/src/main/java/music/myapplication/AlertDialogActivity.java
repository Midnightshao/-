package music.myapplication;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AlertDialogActivity extends BaseAppCompatActivity {

    private AppCompatButton button_cencel;
    private MediaPlayer mediaPlayer;
    private PendingIntent pendingIntent;
    private int position;

    DBContext context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alert_dialog);

        alarm_message(this);

        context=new DBContext(this);

        final Intent intent=getIntent();

        TextView textView=(TextView)findViewById(R.id.textView);

        textView.setText(intent.getStringExtra("title"));

        position=Integer.valueOf(intent.getStringExtra("id"));

        Log.i("Tag","tag222222222"+position);

       mediaPlayer=new MediaPlayer();
       mediaPlayer = MediaPlayer.create(AlertDialogActivity.this, getSystemDefultRingtoneUri());
       mediaPlayer.setLooping(true);//设置循环
       mediaPlayer.start();

        button_cencel=(AppCompatButton)findViewById(R.id.button_cencel);

        final Intent intent2=new Intent(AlertDialogActivity.this,InsertActivity.class);

        ContentValues contentValues = new ContentValues();

        contentValues.put("alarmClock","");

        context.update(contentValues, String.valueOf(position));

        button_cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AlertDialogActivity.this,"取消闹钟",Toast.LENGTH_SHORT).show();

                pendingIntent=PendingIntent.getBroadcast(AlertDialogActivity.this.getBaseContext(),position, intent1, 0);

                alarmManager.cancel(pendingIntent);

                mediaPlayer.stop();

                intent2.putExtra("data",String.valueOf(position));

                startActivity(intent2);

                finish();

            }
        });
        this.setFinishOnTouchOutside(false);
    }

    @Override
    protected void onStart() {

        super.onStart();

    }

    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_RINGTONE);
    }
}
