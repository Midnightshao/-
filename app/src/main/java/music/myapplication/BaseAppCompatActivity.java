package music.myapplication;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by guanghaoshao on 16/2/9.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    public AlarmManager alarmManager;
    public Intent intent1;

    public  void alarm_message(Context context){
        alarmManager= (AlarmManager) context.getSystemService(InsertActivity.ALARM_SERVICE);
        intent1=new Intent(context, AlarReceiver.class);
    }
}
