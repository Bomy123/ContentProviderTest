package com.example.bomy.contentprovidertest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookdataResolver extends AppCompatActivity{
private List<String> bookList = new ArrayList<String>();
    private Button getbookinfo;
    private ListView booklist;
    private ArrayAdapter<String> bookadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdata_resolver);
        getbookinfo = (Button) findViewById(R.id.book);
        booklist = (ListView) findViewById(R.id.booklist);
        bookadapter = new ArrayAdapter<String>(BookdataResolver.this,android.R.layout.simple_list_item_1,bookList);
        getbookinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("123","456");
                Toast.makeText(BookdataResolver.this,"bookcursor",Toast.LENGTH_SHORT).show();
                getContent();
                booklist.setAdapter(bookadapter);
            }
        });

    }
    private void getContent()
    {
        Cursor bookcursor = null;
        bookcursor = getContentResolver().query(Uri.parse("content://com.example.bomy.sqlitetest.provider/bookdata"),null,null,null,null);
        while (bookcursor.moveToNext())
        {
            Toast.makeText(BookdataResolver.this,"bookcursor",Toast.LENGTH_SHORT).show();
            String name=bookcursor.getString(bookcursor.getColumnIndex("name"));
            double price = bookcursor.getDouble(bookcursor.getColumnIndex("price"));
            String writer = bookcursor.getString(bookcursor.getColumnIndex("writer"));
            int page = bookcursor.getInt(bookcursor.getColumnIndex("page"));
            bookList.add(name+"****"+price+"****"+writer+"****"+page);
        }
    }
}
