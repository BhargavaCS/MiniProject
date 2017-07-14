package com.example.bhargava.cse;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ResultPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_result_page);

        Intent intent = getIntent();
        TextView tv=(TextView)findViewById(R.id.ResultText);
        HashMap<String, Integer> hashMap = (HashMap<String, Integer>)intent.getSerializableExtra("result");
        HashMap<String, Integer> hashMap_ques = (HashMap<String, Integer>)intent.getSerializableExtra("result_ques");
        String next;
        String s="<br>Test Analysis<br>";
        next="<font color='#3782A4' size='4'>"+s+"</font><br>";
        tv.append(Html.fromHtml(next));
        for (HashMap.Entry<String,Integer> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            double accuracy;
            accuracy=((double)value/hashMap_ques.get(key))*100;
            s=key+"---->"+(String.format("%.2f",accuracy))+"%";

            if(accuracy>40)
            {
                next = "<font color='#49582C' size='6'>"+s+"</font><br>";
            }
            else
            {
                next = "<font color='#F08874' size='6'>"+s+"</font><br>";
            }
            tv.append(Html.fromHtml(next));
        }
    }

    public void back_to_main(View view)
    {
        Intent intent=new Intent(this,StartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,StartActivity.class);
        startActivity(intent);
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
