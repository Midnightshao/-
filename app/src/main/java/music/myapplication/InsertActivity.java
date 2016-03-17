package music.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/*
* 如果你能看懂我也很高兴
* */

public class InsertActivity extends BaseAppCompatActivity{


    private static final int TITLE_LENTH=15;
    private AlertDialog.Builder alertDialog;
    private DBContext context;
    private EditText editText;
    private Boolean aBoolean;
    private String ids;
    private   int years=0;
    private int months=0;
    private int days=0;
    private int hour=0;
    private int minute=0;
    private PendingIntent pendingIntent;
    private int position;
    private String alarmClocks=null;
    private Intent intent2;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.alarm_message(this);

        context=new DBContext(this);

        setContentView(R.layout.activity_insert);

        insert();

        intent=getIntent();

        String content=intent.getStringExtra("data");

        intent2=new Intent(InsertActivity.this,MainActivity.class);

        if(content==null||content.equals("")){

            aBoolean=false;

        } else {

            position=Integer.valueOf(content);

            aBoolean=true;

            Listener(content);
        }
    }
    //nao
    public PendingIntent alert(){

        if(aBoolean==false){

            List list=context.query();

            if(list.size()==0){
                position=0;
            }else {
                Map map= (Map) list.get(list.size()-1);
                position= Integer.parseInt(String.valueOf(map.get("id")));
            }
            position=position+1;
        }

        String tr=editText.getText().toString();

        if (tr.length() < TITLE_LENTH) {

                Log.i("TAG", "tag" + editText.getText().toString());
                String title = tr.substring(0,editText.getText().toString().length());
                intent1.putExtra("title", title);
                intent1.putExtra("id", String.valueOf(position));
                Log.d("TAG", "tag+++++++++++++++++++++++" + title);
            return pendingIntent=PendingIntent.getBroadcast(InsertActivity.this.getBaseContext(), position, intent1, 0);

            }else {
                String title=tr.substring(0,TITLE_LENTH);
                intent1.putExtra("title", title);
                intent1.putExtra("id",String.valueOf(position));
                Log.d("TAG", "tag+++++++++++++++++++++++" + title);
            return pendingIntent=PendingIntent.getBroadcast(InsertActivity.this.getBaseContext(), position, intent1, 0);
        }
    }
    public void insert(){
        if(editText==null){
            editText=(EditText)findViewById(R.id.edit_insert);
        }
    }
    public void Listener(String content){

        editText.setTextSize(30);

        List<Map<String,Object>> list=context.query(content);

        Log.i("TAG","LIST"+list);

        Map<String,Object> map=list.get(0);

        Log.i("TAG","Map"+map);

        String contents=(String)map.get("content");

        Log.i("TAG","String"+contents);

        ids=String.valueOf(map.get("id"));

        editText.setText(contents);
    }

    @Override
    protected void onStart() {
        super.onStart();
        alarmClocks=null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);

    }
    public void insertupdate(){
        if(editText.getText().toString().trim().equals(null)||editText.getText().toString().trim().equals("")){
            if(aBoolean){

                query_alarm_message(Integer.valueOf(this.ids));

                context.delete(this.ids);
                if(context.query().isEmpty()){
                    context.deleteTable(this);
                }
            }else {
//                query_alarm_message(position);
            }
        }else {
            if (!aBoolean) {
                ContentValues contentValues = new ContentValues();
                String tr = editText.getText().toString();
                String title = null;
                if (tr.length() < TITLE_LENTH) {
                    Log.i("TAG", "tag" + title);
                    title = tr.substring(0, tr.length()).trim();
                } else {
                    title = tr.substring(0, TITLE_LENTH).trim();
                }
                contentValues.put("title", title.trim());
                contentValues.put("content", editText.getText().toString());

                if(alarmClocks!=null){
                    contentValues.put("alarmClock", alarmClocks);
                }

                int i = context.insert(contentValues);

                Log.i("Log", "log" + i);
            } else {
                ContentValues contentValues = new ContentValues();
                String tr = editText.getText().toString();
                String title = null;
                if (tr.length() < TITLE_LENTH) {
                    title = tr.substring(0, tr.length()).trim();
                    Log.i("TAG", "tag" + title);
                } else {
                    title = tr.substring(0, TITLE_LENTH).trim();
                }
                contentValues.put("title", title.trim());
                contentValues.put("content", editText.getText().toString());
                if(alarmClocks!= null) {
                    contentValues.put("alarmClock", alarmClocks);
                }
                context.update(contentValues, this.ids);
            }
        }
    }
    //判断是否拥有闹钟 有就删除
    public void query_alarm_message(){

        if(aBoolean==true) {
            List list = context.query(intent.getStringExtra("data"));
            Map map = (Map) list.get(0);
            String alarmClock=(String)map.get("alarmClock");
            Log.i("Log","     jaofi;jaoif"+alarmClock);
            if (alarmClock!= null) {
                pendingIntent = PendingIntent.getBroadcast(InsertActivity.this.getBaseContext(), position, intent1, 0);
                alarmManager.cancel(pendingIntent);
            }
        }
    }
    //判断是否拥有闹钟 有就删除
    public void query_alarm_message(int position){

            List list = context.query(intent.getStringExtra("data"));
        if(list!=null) {
            Map map = (Map) list.get(0);
            String alarmClock = (String) map.get("alarmClock");
            Log.i("Log", "     判断是否拥有时间" + alarmClock);
            if (alarmClock != null) {
                Log.i("Tag", "tag_clock");
                pendingIntent = PendingIntent.getBroadcast(this.getBaseContext(), position, intent1, 0);
                alarmManager.cancel(pendingIntent);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.action_settings){

            insertupdate();

            InsertActivity.this.startActivity(intent2);

            InsertActivity.this.finish();

            return true;
        }
        if(id==R.id.action_clock){

            if(!editText.getText().toString().isEmpty()) {

                alertDialog = new AlertDialog.Builder(this);

                final AlertDialog.Builder AlertDialog1 = new AlertDialog.Builder(this);

                alertDialog.setTitle("提示");

                final View view = RelativeLayout.inflate(InsertActivity.this, R.layout.dialog_a, null);
                final View view1 = LinearLayout.inflate(InsertActivity.this, R.layout.dialog_b, null);

                CalendarView calendarView = (CalendarView) view.findViewById(R.id.card_view);
                calendarView.onWindowFocusChanged(true);

                final Calendar calendar = Calendar.getInstance();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(2016, 11, 15);
                calendarView.setScrollContainer(true);
                calendarView.offsetTopAndBottom(10);

                calendarView.setScrollY(0);
                calendarView.setVerticalScrollbarPosition(5);
                calendarView.setMaxDate(calendar1.getTimeInMillis());
//
                alertDialog.setView(view);

                TimePicker timePicker = (TimePicker) view1.findViewById(R.id.timePicker);

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        InsertActivity.this.hour = hourOfDay;
                        InsertActivity.this.minute = minute;
                    }
                });

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        InsertActivity.this.years = year;
                        InsertActivity.this.months = month;
                        InsertActivity.this.days = dayOfMonth;
                    }
                });

                //TimePicker
                AlertDialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        query_alarm_message();

                        InsertActivity.super.alarm_message(InsertActivity.this);

                        Log.i("Tag", "          year   " + InsertActivity.this.years);
                        Log.i("Tag", "          mouths  " + InsertActivity.this.months);
                        Log.i("Tag", "          day    " + InsertActivity.this.days);

                        final Calendar calendar_dialog=Calendar.getInstance();
                        if (InsertActivity.this.days != 0) {
                            calendar_dialog.set(Calendar.YEAR, InsertActivity.this.years);
                            calendar_dialog.set(Calendar.MONTH, InsertActivity.this.months);
                            calendar_dialog.set(Calendar.DAY_OF_MONTH, InsertActivity.this.days);
                        }

                        calendar_dialog.set(Calendar.HOUR_OF_DAY, InsertActivity.this.hour);
                        calendar_dialog.set(Calendar.MINUTE, InsertActivity.this.minute);
                        calendar_dialog.set(Calendar.SECOND, 00);


                        alarmClocks=calendar_dialog.get(Calendar.YEAR)+"-"+(calendar_dialog.get(Calendar.MONTH)+1)+"-"+calendar_dialog.get(Calendar.DATE)+" "+calendar_dialog.get(Calendar.HOUR_OF_DAY)+":"+calendar_dialog.get(Calendar.MINUTE)+":"+calendar_dialog.get(Calendar.SECOND);

                        alarmManager.setRepeating(AlarmManager.RTC, calendar_dialog.getTimeInMillis(), 10000, alert());

                        Toast.makeText(InsertActivity.this,calendar_dialog.get(Calendar.YEAR)+"-"+(calendar_dialog.get(Calendar.MONTH)+1)+"-"+calendar_dialog.get(Calendar.DATE)+" "+calendar_dialog.get(Calendar.HOUR_OF_DAY)+":"+calendar_dialog.get(Calendar.MINUTE)+":"+calendar_dialog.get(Calendar.SECOND),Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog1.create().dismiss();
                    }
                });
                //日历
                alertDialog.setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog1.setView(view1);
                        AlertDialog1.show();
                        Toast.makeText(InsertActivity.this, "下一步", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(InsertActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                WindowManager.LayoutParams params = alertDialog.create().getWindow().getAttributes();

                params.width = 100;
                params.height = 900;

                alertDialog.create().getWindow().setLayout(100, 900);
                alertDialog.create().getWindow().setAttributes(params);

                alertDialog.show();
                return true;
            }else {
                Toast.makeText(InsertActivity.this,"文字不能为空",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        insertupdate();

        InsertActivity.this.startActivity(intent2);

        InsertActivity.this.finish();

    }
}
