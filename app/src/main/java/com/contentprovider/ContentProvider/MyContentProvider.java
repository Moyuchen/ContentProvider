package com.contentprovider.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.contentprovider.Sq.CPMetadata;
import com.contentprovider.Sq.sqLITE;

/**
 * User: 张亚博
 * Date: 2017-12-11 11:17
 * Description：
 */
public class MyContentProvider extends ContentProvider {


    private sqLITE sq;
    private SQLiteDatabase write;
    private static final  UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CPMetadata.AUTHORITY, CPMetadata.users.TABLE_NAME, CPMetadata.CONTENT);
        uriMatcher.addURI(CPMetadata.AUTHORITY, CPMetadata.users.TABLE_NAME + "/#",
                CPMetadata.CONTENT_ITEM);
    }
    @Override
    public boolean onCreate() {
        sq = new sqLITE(getContext());
        write = sq.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        String  whereStr = getWhereStr(uri, selection);
        Log.d("ContentProvider", "Query Where  :" + whereStr);
        cursor = write.query(CPMetadata.users.TABLE_NAME, projection, whereStr, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CPMetadata.CONTENT:
                return CPMetadata.users.CONTENT_TYPE;
            case CPMetadata.CONTENT_ITEM:
                return CPMetadata.users.CONTENT_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("unknown URI:" + uri);
        }
    }
    /**
     * 整理where子句
     * 条件分为有ID和无ID两种，ID为主键具有唯一性，不用添加其他条件
     * 无ID则使用传入的条件处理
     * @param uri
     * @param selection
     * @return
     */
    public String getWhereStr(Uri uri, String selection){
        String whereStr;
        switch (uriMatcher.match(uri)) {
            case CPMetadata.CONTENT:
                whereStr = selection;
                break;
            case CPMetadata.CONTENT_ITEM:
                whereStr = CPMetadata.users._ID + "=" + uri.getPathSegments().get(1);
                break;
            default:
                throw new IllegalArgumentException("unknown URI :" + uri);
        }
        return whereStr;
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowid = write.insert(CPMetadata.users.TABLE_NAME, null, values);
        if (rowid > 0) {
            Uri insertUri = ContentUris.withAppendedId(CPMetadata.users.CONTENT_URI, rowid);
            getContext().getContentResolver().notifyChange(insertUri, null);
            Log.d("ContentProvider", "Insert Success  :" + insertUri);
            return insertUri;
        }
        throw new IllegalArgumentException("unknown URI: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        String  whereStr = getWhereStr(uri, selection);
        Log.d("ContentProvider", "Delete Where  :" + whereStr);
        count = write.delete(CPMetadata.users.TABLE_NAME, whereStr, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        String  whereStr = getWhereStr(uri, selection);
        Log.d("ContentProvider", "Update Where  :" + whereStr);
        count = write.update(CPMetadata.users.TABLE_NAME, values, whereStr, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
