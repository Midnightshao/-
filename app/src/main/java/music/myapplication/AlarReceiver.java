package music.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;



public class AlarReceiver extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    public AlarmManager alarmManager;
    public AlarReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        DBContext dbContext=new DBContext(context);

        Intent intent1=new Intent(context,AlertDialogActivity.class);

        intent1.putExtra("title", intent.getStringExtra("title"));
        intent1.putExtra("id", intent.getStringExtra("id"));

        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        List<Map<String,Object>> list=dbContext.query(intent.getStringExtra("id"));

        if(list.size()!=0){
            context.startActivity(intent1);
        }else {

            Log.i("Tag","okokokokokkokokokokokk");
            pendingIntent= PendingIntent.getBroadcast(context,Integer.valueOf(intent.getStringExtra("id")), intent, 0);

            alarmManager= (AlarmManager) context.getSystemService(InsertActivity.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);

        }

    }
}
