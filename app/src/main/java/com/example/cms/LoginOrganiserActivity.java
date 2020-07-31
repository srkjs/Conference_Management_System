package com.example.cms;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginOrganiserActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin,mButtonStudentLogin,mButtonReviewerLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_organiser);


        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonStudentLogin=(Button)findViewById(R.id.button_studentLogin);
        mButtonReviewerLogin = (Button) findViewById(R.id.button_reviewerLogin);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkOrganiser(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(LoginOrganiserActivity.this,OrgActivity.class);
                    HomePage.putExtra("email",user);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginOrganiserActivity.this,"Login Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(LoginOrganiserActivity.this,LoginActivity.class);
                startActivity(homeIntent);
            }
        });

        mButtonReviewerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(LoginOrganiserActivity.this,LoginReviewerActivity.class);
                startActivity(homeIntent);
            }
        });
    }

}

