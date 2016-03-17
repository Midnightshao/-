package music.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by guanghaoshao on 16/1/20.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("log", "创建日志");
        String sql="create table if not exists bianqian(id integer primary key autoincrement,title varchar(1024),content varchar(1024),logtime TIMESTAMP default(datetime('now','localtime')),alarmClock varchar(30))";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("log", "创建日志");

        String sql="create table if not exists bianqian(id integer primary key autoincrement,title varchar(1024),content varchar(1024),logtime TIMESTAMP default(datetime('now','localtime')),alarmClock varchar(30))";

        db.execSQL(sql);

        onCreate(db);
    }
}
