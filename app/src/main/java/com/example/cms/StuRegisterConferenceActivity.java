package com.example.cms;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cms.DatabaseHelper;
import com.example.cms.LoginReviewerActivity;
import com.example.cms.R;
public class StuRegisterConferenceActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    TableLayout tl;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_register_conference);
        mydb =new DatabaseHelper(this);

        final String email = getIntent().getStringExtra("email");
        final String name = getIntent().getStringExtra("name");
        final String age = getIntent().getStringExtra("age");
        final String phone = getIntent().getStringExtra("phone");
        final String address = getIntent().getStringExtra("address");
        final String qua = getIntent().getStringExtra("qua");
        final String dept = getIntent().getStringExtra("dept");
        final String regno = getIntent().getStringExtra("regno");


        final Cursor res = mydb.registerDetails(email);
        res.moveToFirst();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(this,"Status not updated!",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) findViewById(R.id.students);
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);

            TextView studid = new TextView(this);
            studid.setText(" C-ID ");
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);

            TextView depart1 = new TextView(this);
            depart1.setText(" Click to register");
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
                final String cid = res.getString(0);
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);

                TextView btn = new TextView(this);
                btn.setText(" Register ");
                btn.setBackgroundResource(R.drawable.cell_shape);
                btn.setTextAppearance(android.R.style.TextAppearance_Medium);
                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {


                        final long val = mydb.addRegistration(cid,regno,name,age,phone,email,address,qua,dept);
                        if (val <= 0) {
                            Toast.makeText(StuRegisterConferenceActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Intent moveToAdmin = new Intent(StuRegisterConferenceActivity.this, StuActivity.class);
                            moveToAdmin.putExtra("email",email);
                            startActivity(moveToAdmin);
                        }
                        else {
                            Toast.makeText(StuRegisterConferenceActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
//                        sendMessage(regno,name,std,parent,dob,blood,pass,mob,email,add);
                            Intent moveToAdmin = new Intent(StuRegisterConferenceActivity.this, StuActivity.class);
                            moveToAdmin.putExtra("email",email);
                            startActivity(moveToAdmin);
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
                Intent BackIntent = new Intent(StuRegisterConferenceActivity.this, StuActivity.class);
                BackIntent.putExtra("email",email);
                startActivity(BackIntent);
            }
        });

        res.close();
    }

}
