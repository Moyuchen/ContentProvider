package com.contentprovider;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.contentprovider.Sq.CPMetadata;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i=0;i<5;i++){
        ContentValues values=new ContentValues();

            values.put(CPMetadata.users.NAME,"张三");
            values.put(CPMetadata.users.AGE,23);

        getContentResolver().insert(CPMetadata.users.CONTENT_URI,values);
        }
    }
}
