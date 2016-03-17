package music.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanghaoshao on 16/1/21.
 */
public class DBContext {
    private DatabaseHelper dbContext;
    public DBContext(Context context){
        dbContext=new DatabaseHelper(context,"node.db",null,1);
    }
    public int insert(ContentValues values){

        SQLiteDatabase db=dbContext.getWritableDatabase();

        int id=(int)db.insert("bianqian",null,values);

        Log.i("TAG","insert ok!");

        db.close();

        return id;
    };
    public void delete(String id){
        SQLiteDatabase db=dbContext.getWritableDatabase();
        db.delete("bianqian","id=?",new String[]{id});
        Log.i("TAG", "delete ok!");
        db.close();
    }
    public List<Map<String,Object>> query(){

        SQLiteDatabase db=
                dbContext.getReadableDatabase();

        Cursor c=db.query(
                "bianqian", new String[]{"id", "title", "logtime", "alarmClock"},
                null, null, null, null, null);
        List<Map<String, Object>> list =
                new ArrayList<Map<String, Object>>();
//            List<Map<String, Object>> list =
//                    new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            while (c.moveToNext()) {//循环一次取一行
                map = new HashMap<String, Object>();
                map.put("id", c.getInt(0));
                map.put("title", c.getString(1));
                map.put("logtime", c.getString(2));
                map.put("alarmClock", c.getString(3));
                list.add(map);
            }
        Log.i("TAG", "list=" + list);
        return list;
    }
    public List<Map<String,Object>> query(String id){
        SQLiteDatabase db=
                dbContext.getReadableDatabase();
        Cursor c=db.query(
                "bianqian", new String[]{"id","content","logtime"},
                "id=?",new String[]{id}, null, null, null);
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=null;
        while(c.moveToNext()){//循环一次取一行
            map=new HashMap<String, Object>();
            map.put("id", c.getInt(0));
            map.put("content",c.getString(1));
            map.put("logtime",c.getString(2));
            list.add(map);
        }
        return list;
    }
    public void update(ContentValues values,String id){

        SQLiteDatabase db=dbContext.getWritableDatabase();

        db.update("bianqian",values,"id=?",new String[]{id});

        Log.i("TAG","update ok!");

        db.close();

    }
    public void deleteTable(Context context){

        SQLiteDatabase database=dbContext.getWritableDatabase();

        database.execSQL("DROP TABLE bianqian");

        Log.i("TAG", "11111112222223333333444444445555555566666666677777777");

        dbContext=new DatabaseHelper(context,"node.db",null,1);

        dbContext.onUpgrade(database,1,2);

        Log.i("TAG", "delete table ok");

    }
}
