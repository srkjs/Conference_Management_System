package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrgAddConferenceActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mcid, mcname,mtopic,mdate,mplace;
    EditText mEmail;
    EditText mMob;
    Button mButtonRequest;
    TextView mTextViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_add_conference);
        final String email = getIntent().getStringExtra("email");
        db = new DatabaseHelper(this);
        mcid = (EditText)findViewById(R.id.cid);
        mcname = (EditText)findViewById(R.id.cname);
        mtopic = (EditText)findViewById(R.id.topic);
        mdate = (EditText)findViewById(R.id.date);
        mplace = (EditText) findViewById(R.id.place);

        mButtonRequest = (Button)findViewById(R.id.button_addConference);

        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(OrgAddConferenceActivity.this,OrgActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cid = mcid.getText().toString().trim();
                String cname = mcname.getText().toString().trim();
                String topic = mtopic.getText().toString().trim();
                String date = mdate.getText().toString().trim();
                String place = mplace.getText().toString().trim();
                long val = db.addConference(cid,cname,topic,date,place,email);
                if(val > 0){
                    Toast.makeText(OrgAddConferenceActivity.this,"Conference Added!",Toast.LENGTH_SHORT).show();
                    Intent moveToLogin = new Intent(OrgAddConferenceActivity.this,OrgActivity.class);
                    startActivity(moveToLogin);
                }
                else{
                    Toast.makeText(OrgAddConferenceActivity.this,"Error in adding conference!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}