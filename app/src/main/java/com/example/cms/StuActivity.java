package com.example.cms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cms.DatabaseHelper;

public class StuActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    TextView mregno,mname,mage,mphone,maddress,mqua,mdept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu);
        mydb =new DatabaseHelper(this);
        final String email = getIntent().getStringExtra("email");
        final Cursor res = mydb.getCandidateDetails(email);
        final int count = res.getCount();
        res.moveToFirst();

        mregno = (TextView)findViewById(R.id.regno);
        mname = (TextView)findViewById(R.id.name);
        mage = (TextView)findViewById(R.id.age);
        mphone = (TextView)findViewById(R.id.phone);
        maddress = (TextView)findViewById(R.id.address);
        mqua = (TextView)findViewById(R.id.qualification);
        mdept = (TextView)findViewById(R.id.department);

        final String regno = res.getString(0);
        final String name = res.getString(1);
        final String age = res.getString(2);
        final String phone = res.getString(3);
        final String address = res.getString(4);
        final String qua = res.getString(5);
        final String dept = res.getString(6);
        if(count == 0)
        {
            Toast.makeText(this,"No data found",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(StuActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            mregno.setText(res.getString(0));
            mname.setText(res.getString(1));
            mage.setText(res.getString(2));
            mphone.setText(res.getString(3));
            maddress.setText(res.getString(4));
            mqua.setText(res.getString(5));
            mdept.setText(res.getString(6));
        }

        Button mButton1 = (Button) findViewById(R.id.button_searchConference);
        Button mButton2 = (Button) findViewById(R.id.button_submitPaper);
        Button mButton3 = (Button) findViewById(R.id.button_viewStatus);
        Button mButton4 = (Button) findViewById(R.id.button_register);
        Button mButton5 = (Button) findViewById(R.id.button_logout);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StuActivity.this, StuSearchActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StuActivity.this, StuSubmitPaperActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StuActivity.this, StuViewReviewActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StuActivity.this, StuRegisterConferenceActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("regno",regno);
                intent.putExtra("name",name);
                intent.putExtra("age",age);
                intent.putExtra("phone",phone);
                intent.putExtra("address",address);
                intent.putExtra("dept",dept);
                intent.putExtra("qua",qua);

                startActivity(intent);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}

