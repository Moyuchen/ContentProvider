package com.contentresolver;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button getdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ContentValues values=new ContentValues();
//        for (int i=0;i<5;i++){
//            values.put("","张三");
//            values.put("",23);
//        }
//        getContentResolver().in


        tv = (TextView) findViewById(R.id.textview);
        getdata = (Button) findViewById(R.id.getData);
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriStr = "content://com.contentprovider/users";
                Uri uri = Uri.parse(uriStr);
                StringBuffer show = new StringBuffer();
                Cursor cursor = getContentResolver().query(uri, null, "name=?", new String[]{"张三"}, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    Log.i("==================", "onClick: name:"+name);
                    String age = cursor.getString(cursor.getColumnIndex("age"));
                    show.append("ID : " + id + "  NAME : " + name + "  AGE : " + age + "\n");
                }
                cursor.close();
                tv.setText(show.toString());

            }
        });

    }
}
