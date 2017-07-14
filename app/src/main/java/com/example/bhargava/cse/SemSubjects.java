//package com.example.cse.drivetest;

package com.example.bhargava.cse;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhargava.cse.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemSubjects extends AppCompatActivity {
    public ListView l;
    public ArrayList<String> li;
    public ArrayAdapter<String> adapter;
    public String [] st;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sem_subjects);
        Intent intent =getIntent();
        String extr=intent.getStringExtra("Selected");
        int resourceId = this.getResources().
                getIdentifier(extr, "string", this.getPackageName());
        //Toast.makeText(this,resourceId+"",Toast.LENGTH_SHORT).show();
        st=getResources().getStringArray(getArrayId(extr));
        adapter = new ArrayAdapter<String>(this,R.layout.activity_sem_subjects,R.id.etv,getResources().getStringArray(getArrayId(extr)));
        LinearLayout ll=(LinearLayout)findViewById(R.id.rl);
        TextView tv=(TextView)findViewById(R.id.etv);
        tv.setText("Select Subjects from "+extr+" semester : ");
        l=(ListView)findViewById(R.id.lv2);
        //l.setBackgroundColor(Color.DKGRAY);
        //l.setEmptyView(findViewById(R.id.tv));
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         String s=  st[position];
                                         Intent intent1=new Intent(SemSubjects.this,TextBooksList.class);
                                         intent1.putExtra("Subject",s);
                                         startActivity(intent1);
                                         Toast.makeText(SemSubjects.this,s,Toast.LENGTH_SHORT).show();
                                     }
                                 }
        );
    }



    public int getArrayId(String arranameString) {

        int id = this.getResources().getIdentifier(arranameString, "array", this.getPackageName());

        return id;

    }

    public void makeList(String extr)
    {
        ListView nl=new ListView(this);
        Button b1=new Button(this);
        b1.setText(extr);
        LinearLayout ll=(LinearLayout) findViewById(R.id.rl);
        ll.addView(b1);
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


