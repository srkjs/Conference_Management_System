package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin,mButtonAdminLogin,mButtonReviewerLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonAdminLogin=(Button)findViewById(R.id.button_orgLogin);
        mButtonReviewerLogin = (Button) findViewById(R.id.button_reviewerLogin);
        mTextViewRegister = (TextView)findViewById(R.id.textview_signup);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,CandidateSignupActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkCandidate(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(LoginActivity.this,StuActivity.class);
                    HomePage.putExtra("email",user);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(LoginActivity.this,LoginOrganiserActivity.class);
                startActivity(homeIntent);
            }
        });

        mButtonReviewerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(LoginActivity.this,LoginReviewerActivity.class);
                startActivity(homeIntent);
            }
        });
    }

}
