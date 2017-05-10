package com.study.news.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于定义数据库名称，数据库版本，创建表名。
 */

public class BaseSQLiteOpenHelper extends SQLiteOpenHelper {
    String create_table_id_list = "create table id_list(id text primary key)";
    public static BaseSQLiteOpenHelper baseSQLiteOpenHelper;

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static BaseSQLiteOpenHelper newInstance(Context context) {
        if (baseSQLiteOpenHelper == null) {
            return new BaseSQLiteOpenHelper(context, "NEWS", null, 1);
        }
        return baseSQLiteOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        db.execSQL(create_table_id_list);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 插入一个id
     *
     * @param tableName
     * @param columnName
     * @param id
     * @return 如果返回-1代表插入错误
     */
    public long insertId(String tableName, String columnName, String id) {
        ContentValues values = new ContentValues();
        values.put(columnName, id);

        return getWritableDatabase().insert(tableName, null, values);
    }

    /**
     * 查询一个id. 查询规则：从数据库里面的第starSize+1条开始取PageSize条数据
     * @param tableName
     * @param columnName
     * @param starSize
     * @param PageSize
     * @return 返回查询到的一个id
     */
    public String queryIdLimit(String tableName, String columnName, int starSize, int PageSize) {
        String id = null;
        Cursor cursor = getReadableDatabase().query(tableName, null, null, null, null, null, null, starSize + "," + PageSize);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getString(cursor.getColumnIndex(columnName));
            }
        }
        return id;
    }
}
