package com.example.bomy.contentprovidertest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> mContactLIst = new ArrayList<String>();
    private Button mgetContact,bookbtn;
    ArrayAdapter<String> mContactAdapter;
    ListView mListviewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListviewContacts = (ListView) findViewById(R.id.contactList);
        mgetContact = (Button) findViewById(R.id.getContact);
        bookbtn = (Button) findViewById(R.id.tobook);
        mContactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mContactLIst);
        mListviewContacts = (ListView) findViewById(R.id.contactList);
        mgetContact = (Button) findViewById(R.id.getContact);
        mgetContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContact();
                mListviewContacts.setAdapter(mContactAdapter);
            }
        });
        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookdataResolver.class);
                startActivity(intent);
            }
        });
    }

    private void getContact() {
        Cursor mContactCursor = null;
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mContactCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " desc");
            while(mContactCursor.moveToNext())
            {
                String name = mContactCursor.getString(mContactCursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String date = mContactCursor.getString(mContactCursor.getColumnIndex(CallLog.Calls.DATE));
                String phonenum =mContactCursor.getString(mContactCursor.getColumnIndex(CallLog.Calls.NUMBER));
                mContactLIst.add(name+"  "+"  "+date+" "+phonenum);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(mContactCursor!=null)
                mContactCursor.close();
        }

    }
}
