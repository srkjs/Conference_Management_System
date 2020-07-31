package com.example.cms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class StuSearchActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    TextView std,studname,parent,mob,email,address,dob,blood,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_search);
        mydb =new DatabaseHelper(this);
        final String bemail = getIntent().getStringExtra("email");

        final EditText manything = (EditText) findViewById(R.id.regno);
        Button mButton5 = (Button) findViewById(R.id.button_getDetails);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String anything = manything.getText().toString().trim();
                final Cursor res = mydb.searchConference(anything);

                final int count = res.getCount();
                res.moveToFirst();

                studname = (TextView)findViewById(R.id.cid);
                std = (TextView)findViewById(R.id.cname);
                parent = (TextView)findViewById(R.id.topic);
                mob = (TextView)findViewById(R.id.date);
                email = (TextView)findViewById(R.id.place);


                if(count == 0){
                    Toast.makeText(StuSearchActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    studname.setText(res.getString(0));
                    std.setText(res.getString(1));
                    parent.setText(res.getString(2));
                    mob.setText(res.getString(3));
                    email.setText(res.getString(4));
                }
            }
        });





        TextView mTextViewLogin;
        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(StuSearchActivity.this,StuActivity.class);
                BackIntent.putExtra("email",bemail);
                startActivity(BackIntent);
            }
        });
    }
}
