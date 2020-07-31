package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrgAssignReviewerActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mcid, mcname,mname,mrid,mremail,mpassword;
    EditText mEmail;
    EditText mMob;
    Button mButtonRequest;
    TextView mTextViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_assign_reviewer);
        final String email = getIntent().getStringExtra("email");
        db = new DatabaseHelper(this);
        mcid = (EditText)findViewById(R.id.cid);
        mcname = (EditText)findViewById(R.id.cname);
        mname = (EditText)findViewById(R.id.name);
        mrid = (EditText)findViewById(R.id.id);
        mremail = (EditText)findViewById(R.id.remail);
        mpassword = (EditText) findViewById(R.id.password);

        mButtonRequest = (Button)findViewById(R.id.button_addReviewer);

        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(OrgAssignReviewerActivity.this,OrgActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cid = mcid.getText().toString().trim();
                String cname = mcname.getText().toString().trim();
                String name = mname.getText().toString().trim();
                String rid = mrid.getText().toString().trim();
                String remail = mremail.getText().toString().trim();
                String pass = mpassword.getText().toString().trim();
                long val = db.addReviewer(rid,remail,name,cname,cid,pass);
                if(val > 0){
                    Toast.makeText(OrgAssignReviewerActivity.this,"Reviewer Added!",Toast.LENGTH_SHORT).show();
                    Intent moveToLogin = new Intent(OrgAssignReviewerActivity.this,OrgActivity.class);
                    startActivity(moveToLogin);
                }
                else{
                    Toast.makeText(OrgAssignReviewerActivity.this,"Error in adding reviewer!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}