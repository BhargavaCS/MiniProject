package com.example.bhargava.cse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    ArrayList<Question> a=new ArrayList<>();
    ArrayList<String> ques=new ArrayList<>();
    ArrayList<String> subjects=new ArrayList<>();
    HashMap<String,Integer> result=new HashMap<>();
    HashMap<String,Integer> result_ques=new HashMap<>();
    public int sub=0;


    public TextView tv,tv1;
    public RadioGroup rg;
    public RadioButton rb1,rb2,rb3,rb4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv=(TextView)findViewById(R.id.ques);
        tv1=(TextView)findViewById(R.id.accuracy);
        rg=(RadioGroup)findViewById(R.id.RGroup);
        rb1=(RadioButton)findViewById(R.id.RbA);
        rb2=(RadioButton)findViewById(R.id.RbB);
        rb3=(RadioButton)findViewById(R.id.RbC);
        rb4=(RadioButton)findViewById(R.id.RbD);



        storeSubjects();
    }

    public void storeSubjects() {
        DatabaseReference fb = FirebaseDatabase.getInstance().getReference("Questions");
        //DatabaseReference table = fb.child();
        Query subj=fb.orderByKey();

        subj.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i : dataSnapshot.getChildren()) {

                    subjects.add(i.getKey());
                }
                Toast.makeText(Quiz.this,subjects.size()+"",Toast.LENGTH_SHORT).show();
                getQuestionsfromFireBase(subjects.get(sub));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do Nothing
            }
        });
    }

    public void getQuestionsfromFireBase(final String subject) {
        DatabaseReference fb = FirebaseDatabase.getInstance().getReference("Questions");
        DatabaseReference table = fb.child(subject);
        ques.clear();
        a.clear();
        Query qu=table.orderByKey();

        qu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    Question q;
                    q = i.getValue(Question.class);
                    ques.add(i.getKey());
                    a.add(q);
                }
                startQuiz(0,subject);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do Nothing
            }
        });
    }

    DatabaseReference fb = FirebaseDatabase.getInstance().getReference("Questions");
    int ac=0;
    public void startQuiz(final int i,final String subject)
    {
        if(i>=ques.size())
        {
            sub++;
            result.put(subject,ac);
            result_ques.put(subject,i);
            ac=0;
            if(sub>=subjects.size())
            {
                Toast.makeText(this,"Quiz Is Done :)",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Quiz.this,ResultPage.class);
                intent.putExtra("result",result);
                intent.putExtra("result_ques",result_ques);
                Quiz.this.startActivity(intent);
                return;
            }
            getQuestionsfromFireBase(subjects.get(sub));
            return;
        }



        float acc=((float)a.get(i).F/a.get(i).G)*100;
        tv.setText(ques.get(i));
        rb1.setText(a.get(i).A);
        rb2.setText(a.get(i).B);
        rb3.setText(a.get(i).C);
        rb4.setText(a.get(i).D);
        tv1.setText("\n\nAccuracy of this Question by all users attempted is : "+acc);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) findViewById(checkedId);
                if(rb.getText().equals(a.get(i).E))
                {
                    Toast.makeText(Quiz.this,"Correct Answer!!!",Toast.LENGTH_SHORT).show();
                    ac++;
                    DatabaseReference table = fb.child(subject+"/"+ques.get(i)+"/F");
                    a.get(i).F++;
                    a.get(i).G++;
                    table.setValue(a.get(i).F);
                    table=fb.child(subject+"/"+ques.get(i)+"/G");
                    table.setValue(a.get(i).G);
                }
                else
                {
                    Toast.makeText(Quiz.this,"Wrong Answer :(",Toast.LENGTH_SHORT).show();
                    DatabaseReference table = fb.child(subject+"/"+ques.get(i)+"/G");
                    a.get(i).G++;
                    table.setValue(a.get(i).G);
                }
                //rg.clearCheck();
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                startQuiz(i+1,subject);
            }
        });
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


