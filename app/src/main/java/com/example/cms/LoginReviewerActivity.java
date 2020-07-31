package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginReviewerActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin,mButtonStudentLogin,mButtonReviewerLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reviewer);


        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonStudentLogin=(Button)findViewById(R.id.button_studentLogin);
        mButtonReviewerLogin = (Button) findViewById(R.id.button_orgLogin);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkReviewer(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(LoginReviewerActivity.this,ReviewerActivity.class);
                    HomePage.putExtra("email",user);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginReviewerActivity.this,"Login Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(LoginReviewerActivity.this,LoginActivity.class);
                startActivity(homeIntent);
            }
        });

        mButtonReviewerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(LoginReviewerActivity.this,LoginOrganiserActivity.class);
                startActivity(homeIntent);
            }
        });
    }

}

