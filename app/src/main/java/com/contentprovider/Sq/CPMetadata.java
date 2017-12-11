package com.contentprovider.Sq;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User: 张亚博
 * Date: 2017-12-11 11:14
 * Description：
 */
public class CPMetadata {
    public static final String AUTHORITY = "com.contentprovider";
    public static final String DATABASE_NAME = "DemoProviderDB";
    public static final int DATABASE_VERSION = 1;

    public static final int CONTENT = 1;
    public static final int CONTENT_ITEM = 2;

    public static final class users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/users");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.demoprovider.user";
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.demoprovider.user";
        public static final String NAME = "name";
        public static final String AGE = "age";
    }
}
