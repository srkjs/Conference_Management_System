package com.example.cms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StuSubmitPaperActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText cid,pname;
    Button mButtonRequest,Attachment;
    TextView mTextViewLogin;
    private static final int PICK_FROM_GALLERY = 101;
    String subject,message;
    Uri URI = null;
    String attachmentFile;
    int columnIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_submit_paper);

        final String email = getIntent().getStringExtra("email");

        db = new DatabaseHelper(this);

        cid = (EditText)findViewById(R.id.cid);
        pname = (EditText)findViewById(R.id.pname);

        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(StuSubmitPaperActivity.this,StuActivity.class);
                BackIntent.putExtra("email",email);
                startActivity(BackIntent);
            }
        });



        mButtonRequest = (Button)findViewById(R.id.button_submitPaper);
        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conid = cid.getText().toString().trim();
                String papername = pname.getText().toString().trim();

                long val = db.submitPaper(email,papername,conid);

                final Cursor c1= db.getOrgMail(conid);
                final Cursor c2 = db.getReviewerMail(conid);
                c1.moveToFirst();
                String OMail = c1.getString(0);
                c2.moveToFirst();
                String RMail = c2.getString(0);

                sendEmail(conid,papername,OMail,RMail);


                    if(val <= 0){
                        Toast.makeText(StuSubmitPaperActivity.this,"Error in submitting paper!",Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(StuSubmitPaperActivity.this,StuActivity.class);
                        startActivity(moveToAdmin);
                    }
                    else{
                        Toast.makeText(StuSubmitPaperActivity.this,"Paper Submitted!",Toast.LENGTH_SHORT).show();
//                        Intent moveToAdmin = new Intent(StuSubmitPaperActivity.this,StuActivity.class);
//                        moveToAdmin.putExtra("email",email);
//                        startActivity(moveToAdmin);
                    }

                c1.close();
                c2.close();
            }
        });



    }


    public void openFolder() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);

    }

    public void sendEmail(final String cid, final String pname, final String email1, final String email2)
    {
        try
        {
            subject = "Paper Submission for Conference - "+cid;
            message = "Paper Name - "+pname+".\nPFA";
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { email1,email2 });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
            if (URI != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
            }
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            this.startActivity(Intent.createChooser(emailIntent,"Sending email..."));
        }
        catch (Throwable t)
        {
            Toast.makeText(this, "Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();
        }
    }



}

