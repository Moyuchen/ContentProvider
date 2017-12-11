package com.contentprovider.Sq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User: 张亚博
 * Date: 2017-12-11 11:12
 * Description：
 */
public class sqLITE extends SQLiteOpenHelper {
    public sqLITE(Context context) {
        super(context, "content.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table " + CPMetadata.users.TABLE_NAME + "(");
        sql.append(CPMetadata.users._ID + " integer primary key autoincrement,");
        sql.append(CPMetadata.users.NAME + " varchar(20),");
        sql.append(CPMetadata.users.AGE + " integer);");
        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
