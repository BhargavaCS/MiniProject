package com.example.bhargava.cse;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }



    //When Quiz Button Is Chosen
    public void take_quiz_clicked(View view)
    {
        Intent i=new Intent(this,Quiz.class);
        startActivity(i);
    }

    //When Text Books Button is Chosen
    public void find_tb_clicked(View view)
    {
        //Toast.makeText(this,"You clicked Corct",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Semester.class);
        startActivity(intent);
    }


    //When Notification Button Is Chosen
    public void notification_clicked(View view)
    {
        Toast.makeText(this,"Choose a date to add a reminder",Toast.LENGTH_SHORT).show();


        Calendar today = Calendar.getInstance();

        Uri uriCalendar = Uri.parse("content://com.android.calendar/time/" + String.valueOf(today.getTimeInMillis()));
        Intent intentCalendar = new Intent(Intent.ACTION_VIEW,uriCalendar);

        //Use the native calendar app to view the date
        startActivity(intentCalendar);
    }

    //When FeedBack Button Is Chosen
    public void feedBack_clicked(View view)
    {
        Intent intent=new Intent(this,feedBack.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
