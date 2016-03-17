package music.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseAppCompatActivity {
    private DBContext dbContext;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Map<String,Object>> data;
    private boolean delete;
    private List checkbox_list;
    private List list;
    private FloatingActionButton fab;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

        super.alarm_message(this);

        list=new ArrayList();

        sqlliteDBContext();

        listarray();

        Log.i("Tag", "dsfasdfasdf");

        visit();

        Listener();
    }
    public void sqlliteDBContext(){
        dbContext=new DBContext(this);
    };
    public void listarray(){
        data=dbContext.query();
    };
    public void visit(){

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerViewAdapter=new RecyclerViewAdapter(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.requestDisallowInterceptTouchEvent(false);
        recyclerView.setAdapter(recyclerViewAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!delete){
                    Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                    intent.putExtra("id", String.valueOf(data.size() + 1));
                    startActivity(intent);
                    finish();
                }else {
                    for (int i=0;i<checkbox_list.size();i++){

                        query_alarm_message((String)checkbox_list.get(i));

                        dbContext.delete((String) checkbox_list.get(i));
                    }
                    if(dbContext.query().isEmpty()){
                        dbContext.deleteTable(MainActivity.this);
                    }
                    listarray();
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

    }
    public void Listener(){
        recyclerViewAdapter.onRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, InsertActivity.class);
                intent.putExtra("data", data);
                intent.putExtra("id", String.valueOf(position));
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
        recyclerViewAdapter.setOnRecyclerViewCheckBox(new OnRecyclerViewCheckBoxV() {
            @Override
            public void onClickCheckBoxView(View v, String positionid) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    checkbox_list.add(positionid);
                }
            }
        });
    }
    //判断是否拥有闹钟 有就删除
    public void query_alarm_message(String position){

            List list = dbContext.query(position);

            Map map = (Map) list.get(0);
            String alarmClock=(String)map.get("alarmClock");
            Log.i("Log","     jaofi;jaoif"+alarmClock);
            if (alarmClock!= null) {
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this.getBaseContext(), Integer.valueOf(position), intent1, 0);
                alarmManager.cancel(pendingIntent);
            }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        sqlliteDBContext();
        listarray();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!data.isEmpty()){
            menus=true;

        }else {
            menus=false;
        }
        Log.i("TAG", "onStart");
    }
    private boolean menus;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_shouye, menu);

        Log.i("TAG", "menu" + "oncreateMenu+++++++++++++++1");
        super.onPrepareOptionsMenu(menu);

        if(!data.isEmpty()){
            menus=true;
         return menus;
        }else {
            menus=false;
            return menus;
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Log.i("TAG","menu"+"oncreateMenu+++++++++++++++2");

        onPrepareOptionsMenu(menu);

        super.onOptionsMenuClosed(menu);

        onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //上边的垃圾桶
        if(item!=null) {
            int id = item.getItemId();

            if (id == R.id.action_settings) {

                if (!delete) {
                    fab.setImageResource(android.R.drawable.ic_menu_delete);
                    oncheckbox(!delete);
                    delete = true;
                    checkbox_list=new ArrayList();
                } else {
                    fab.setImageResource(R.mipmap.edit_query);
                    oncheckbox(!delete);
                    delete = false;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //
    public OnRecyclerViewCheckBox onRecyclerViewCheckBox;

    public void oncheckbox(boolean oncheckbox){
        onRecyclerViewCheckBox.onClickGone(oncheckbox);
    }

    private void oncheckbox(OnRecyclerViewCheckBox onRecyclerViewCheckBox) {
        this.onRecyclerViewCheckBox=onRecyclerViewCheckBox;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter {

        private boolean aBoolean;

        private List<Map<String,Object>> list;

        public RecyclerViewAdapter(List list){
            this.list=list;
        }

        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener=null;

        OnRecyclerViewCheckBoxV onRecyclerViewCheckBoxV=null;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         final   View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.context_view, parent, false);

            XiaoXi xi=new XiaoXi(view);

            CardView canderView=(CardView)view.findViewById(R.id.card_view);

            canderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(view, (String) view.getTag(R.id.tag_first), (int) view.getTag(R.id.tag_second));
                    }
                }
            });

            CheckBox checkbox=(CheckBox)view.findViewById(R.id.checkBox2);


            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewCheckBoxV.onClickCheckBoxView(v,(String)view.getTag(R.id.tag_first));
                }
            });

            MainActivity.this.oncheckbox(new OnRecyclerViewCheckBox() {
                @Override
                public void onClickGone(boolean onclicks) {

                    Log.i("TAG", "TAG boolean" + onclicks);

                    if (onclicks == true) {

                        aBoolean = true;

                        recyclerViewAdapter.notifyDataSetChanged();

                    } else if (onclicks == false) {
                        aBoolean = false;
                        recyclerViewAdapter.notifyDataSetChanged();

                    }
                }
            });
            return xi;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Map map = data.get(position);

            String string= (String) map.get("title");

            Log.i("Tag", "tag这个是" + position);

            ((XiaoXi) holder).getTv().setText(string);

           ((XiaoXi) holder).getTextView().setText((String) map.get("alarmClock"));

            //绑定一个checkbox
            ((XiaoXi) holder).itemView.setTag(R.id.tag_first, String.valueOf(map.get("id")));

            ((XiaoXi) holder).itemView.setTag(R.id.tag_second, position);

            if(!aBoolean){

                ((XiaoXi) holder).getCheckBox().setVisibility(View.GONE);

            }else {
                ((XiaoXi) holder).getCheckBox().setChecked(false);
                ((XiaoXi) holder).getCheckBox().setVisibility(View.VISIBLE);
            }
        }

        public void onRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;
        }
        public void setOnRecyclerViewCheckBox(OnRecyclerViewCheckBoxV onrecyclerViewCheckBoxV){
            this.onRecyclerViewCheckBoxV=onrecyclerViewCheckBoxV;
        }
        class XiaoXi extends RecyclerView.ViewHolder{

            private TextView tv;

            private CheckBox checkBox;

            private TextView textView;

            public XiaoXi(View itemView) {

                super(itemView);

                checkBox=(CheckBox)itemView.findViewById(R.id.checkBox2);

                tv=(TextView)itemView.findViewById(R.id.texts);

                textView=(TextView)itemView.findViewById(R.id.alarm_text);

            }

            public TextView getTextView(){
                return textView;
            }
            public CheckBox getCheckBox(){
                return checkBox;
            }
            public TextView getTv() {
                return tv;
            }
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
    };
}
