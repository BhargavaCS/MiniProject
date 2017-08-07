package com.example.bhargava.cse;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Semester extends AppCompatActivity {

    String []Semesters=new String[] {"First Semester","Second Semester","Third Semester","Fourth Semester","Fifth Semester","Sixth Semester","Seventh Semester","Eighth Semester","Miscellaneous"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_semester);
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_semester,R.id.tv_id,Semesters);
            ListView l = (ListView) findViewById(R.id.lv);
            TextView tv=(TextView) findViewById(R.id.tv_id);
            tv.setText("Choose A Semester From the Following");
            l.setAdapter(adapter);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String name = (String) parent.getItemAtPosition(position);
                        name = name.split(" ")[0];
                        Intent intent = new Intent(Semester.this, SemSubjects.class);
                        intent.putExtra("Selected", name);
                        startActivity(intent);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Semester.this,"Alert--Please Restart The App Again!!!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
