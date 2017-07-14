package com.example.bhargava.cse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextBooksList extends AppCompatActivity {

    boolean flag = true;
    private Map<String, String> links;
    ArrayAdapter<String> adapter;
    public Map<String, String> sa_map = new HashMap<>();
    public ArrayList<String> sa = new ArrayList<>();
    String subject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SemSubjects sc = new SemSubjects();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_books_list);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        storeTextBooks();
    }

    public void displayBooksList() {
        try {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sa);
            ListView lv = (ListView) findViewById(R.id.lv3);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openUrlInChrome(sa_map.get(sa.get(position)));
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
        }
    }

    public void storeTextBooks() {

        DatabaseReference fb = FirebaseDatabase.getInstance().getReference("TextBooks/" + subject);
        Query subj = fb.orderByKey();
        subj.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    sa.add(i.getKey());
                    sa_map.put(i.getKey().toString(), i.getValue().toString());
                }
                displayBooksList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do Nothing
            }

        });
    }


    void openUrlInChrome(String url) {
        String urlString = url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            this.startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        /*switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);*/
        return true;
    }

}

