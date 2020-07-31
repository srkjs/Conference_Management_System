package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateSignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mRegNo,mName,mPhone,mPass,mAddress;
    EditText mEmail;
    EditText mAge, mQua, mDept;
    Button mButtonRequest;
    TextView mTextViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_signup);

        db = new DatabaseHelper(this);

        mRegNo = (EditText)findViewById(R.id.regno);
        mName = (EditText)findViewById(R.id.name);
        mAge = (EditText) findViewById(R.id.age);
        mPhone = (EditText)findViewById(R.id.phone);
        mEmail = (EditText)findViewById(R.id.email);
        mPass = (EditText)findViewById(R.id.password);
        mAddress = (EditText)findViewById(R.id.address);
        mQua = (EditText)findViewById(R.id.qualification);
        mDept = (EditText)findViewById(R.id.department);


        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(CandidateSignupActivity.this,LoginActivity.class);
                startActivity(BackIntent);
            }
        });

        mButtonRequest = (Button)findViewById(R.id.button_add);
        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String regno = mRegNo.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String age = mAge.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                String add = mAddress.getText().toString().trim();
                String qua = mQua.getText().toString().trim();
                String dept = mDept.getText().toString().trim();

                    long val = db.addCandidate(regno, name, age, phone, email, pass, add, qua, dept);
                    if (val <= 0) {
                        Toast.makeText(CandidateSignupActivity.this, "Error in adding student!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(CandidateSignupActivity.this, LoginActivity.class);
                        startActivity(moveToAdmin);
                    }
                    else {
                        Toast.makeText(CandidateSignupActivity.this, "Student added!", Toast.LENGTH_SHORT).show();
//                        sendMessage(regno,name,std,parent,dob,blood,pass,mob,email,add);
                        Intent moveToAdmin = new Intent(CandidateSignupActivity.this, LoginActivity.class);
                        startActivity(moveToAdmin);
                    }

            }
        });
    }
//
//    private void sendMessage(final String regno,final String name,final String std,final String parent, final String dob,
//                             final String blood,final String pass,final String mob, final String email,final String add)
//    {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setTitle("Sending Email");
//        dialog.setMessage("Please wait");
//        dialog.show();
//        Thread sender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    GMailSender sender = new GMailSender("srks2999@gmail.com", "Sjss@2999");
//                    sender.sendMail("Parent Portal - Student Details", "Login to our parent portal app - \nRegistration Number: " + regno + "\n" + "Password: " + pass +
//                            "\n\nStudent name - "+name+"\nStandard -"+std+"\nParent Name - "+parent+"\nDate of Birth - "+dob+"\nBlood Group - "+blood+
//                            "\nAddress - "+add+"\nE-Mail - "+email+"\nPhone Number - "+mob, "srks2999@gmail.com", email);
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    Log.e("mylog", "Error: " + e.getMessage());
//                }
//            }
//        });
//        sender.start();
//    }
//
//    private void sendMessage1(final String regno,final String name,final String std,final String parent, final String dob,
//                              final String blood,final String pass,final String mob, final String email,final String add)
//    {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setTitle("Sending Email");
//        dialog.setMessage("Please wait");
//        dialog.show();
//        Thread sender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    GMailSender sender = new GMailSender("srks2999@gmail.com", "Sjss@2999");
//                    sender.sendMail("Parent Portal - Updated Details", "Login to our parent portal app - \nRegistration Number: " + regno + "\n" + "Password: " + pass +
//                            "\n\nStudent name - "+name+"\nStandard -"+std+"\nParent Name - "+parent+"\nDate of Birth - "+dob+"\nBlood Group - "+blood+
//                            "\nAddress - "+add+"\nE-Mail - "+email+"\nPhone Number - "+mob, "srks2999@gmail.com", email);
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    Log.e("mylog", "Error: " + e.getMessage());
//                }
//            }
//        });
//        sender.start();
//    }

}