package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cms.DatabaseHelper;
import com.example.cms.LoginReviewerActivity;
import com.example.cms.R;

import java.io.File;

public class ReviewerActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer);
        mydb =new DatabaseHelper(this);
        final String mail = getIntent().getStringExtra("email");
        final Cursor det = mydb.getReviewerDetails(mail);
        det.moveToFirst();
        final int c = det.getCount();

        TextView mcid = (TextView) findViewById(R.id.cid);
        TextView mcname = (TextView) findViewById(R.id.cname);
        final String cid = det.getString(0);
        mcid.setText(det.getString(0));
        mcname.setText(det.getString(1));

        final Cursor res = mydb.getPapers(cid);
        res.moveToFirst();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(this,"No Submissions so far!",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) findViewById(R.id.students);
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);

            TextView studid = new TextView(this);
            studid.setText(" Paper Name ");
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);

            TextView stname = new TextView(this);
            stname.setText(" E-mail ");
            stname.setBackgroundResource(R.drawable.cell_shape);
            stname.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(stname);

            TextView depart1 = new TextView(this);
            depart1.setText(" Select ");
            depart1.setBackgroundResource(R.drawable.cell_shape);
            depart1.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart1);

            tl.addView(row1);
            res.moveToFirst();
            do {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(5,0,5,5);
                row.setLayoutParams(lp);

                TextView sid = new TextView(this);
                sid.setText(res.getString(0));
                final String pname=res.getString(0);
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);

                TextView sname = new TextView(this);
                sname.setText(res.getString(1));
                sname.setBackgroundResource(R.drawable.cell_shape);
                sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sname);

                TextView btn = new TextView(this);
                btn.setText(" Accept? ");
                btn.setBackgroundResource(R.drawable.cell_shape);
                btn.setTextAppearance(android.R.style.TextAppearance_Medium);
                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        final boolean update = mydb.updateStatus(pname);
                        if (update==true){
                            Toast.makeText(ReviewerActivity.this,"Accepted!",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(ReviewerActivity.this,"Rejected!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                row.addView(btn);

                tl.addView(row);
            }while(res.moveToNext());
        }


        TextView mTextViewBack;
        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(ReviewerActivity.this, LoginReviewerActivity.class);
                startActivity(BackIntent);
            }
        });

        res.close();

        final Cursor cursor = mydb.getPapers(cid);
        cursor.moveToFirst();
        final String mailid = cursor.getString(0);

        Button send = (Button) findViewById(R.id.button_notify);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                    sendMessage(mailid,cid);
                    Toast.makeText(ReviewerActivity.this,"Message Sent!!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendMessage(final String email, final String cid) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/folder/ebrec.pdf");
        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("srks2999@gmail.com", "Sjss@2999");
                    sender.sendMail("Message from Reviewer","Conference ID: "+ cid +"\n"+"Reviews updated. Please Check. \nThank you!","srks2999@gmail.com","srks2999@gmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}