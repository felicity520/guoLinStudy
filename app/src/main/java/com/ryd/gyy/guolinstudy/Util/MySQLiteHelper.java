package com.ryd.gyy.guolinstudy.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 使用SQLite创建数据库
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";

    //    主键最好加上，这里publishdate采用的格林威治时间，所以算整型
    public static final String CREATE_NEWS = "create table news ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "publishdate integer,"
            + "commentcount integer)";

//    public static final String CREATE_COMMENT = "create table comment ("
//            + "id integer primary key autoincrement, "
//            + "content text)";

    //    增加列
    public static final String CREATE_COMMENT = "create table comment ("
            + "id integer primary key autoincrement, "
            + "content text, "
            + "publishdate integer)";

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    /**
     * 抽象类必须实现：创建数据库
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
//        如果用户已经安装了app，那将不会再执行onCreate方法，也就是说这里的CREATE_COMMENT是无法创建的，这行log也没有打印出来，这时候需要采用升级的方式
        Log.e(TAG, "onCreate: --------------");
//        保证用户第一次安装的时候，可以加载所有的表
        db.execSQL(CREATE_COMMENT);
    }

    /**
     * 抽象类必须实现：升级数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除已经存在的表，再调用onCreate创建所有的表，但是这样会导致数据丢失，不推荐
//        db.execSQL("drop table if exists news");
//        onCreate(db);

//            传统的升级方式，利用版本号判断升级方式，需要人工来管理
//        switch (oldVersion) {
//            case 1:
////                更新：增加一张表
//                db.execSQL(CREATE_COMMENT);
//            case 2:
////                更新：增加一列
//                db.execSQL("alter table comment add column publishdate integer");
//                break;
//            default:
//        }
    }

}
