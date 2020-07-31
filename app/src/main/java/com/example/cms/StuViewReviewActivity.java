package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class StuViewReviewActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view_review);
        mydb =new DatabaseHelper(this);

        final String email = getIntent().getStringExtra("email");

        final Cursor res = mydb.viewStatus(email);
                res.moveToFirst();
                final int count = res.getCount();
                if(count == 0)
                {
                    Toast.makeText(StuViewReviewActivity.this,"Reviews not updated!",Toast.LENGTH_LONG).show();
                }
                else {
                    tl = (TableLayout) findViewById(R.id.students);
                    TableRow row1 = new TableRow(StuViewReviewActivity.this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    row1.setBackgroundColor(Color.TRANSPARENT);
                    row1.setPadding(5, 0, 5, 5);
                    row1.setLayoutParams(lp1);

                    TextView studid = new TextView(StuViewReviewActivity.this);
                    studid.setText(" C-ID ");
                    studid.setBackgroundResource(R.drawable.cell_shape);
                    studid.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row1.addView(studid);

                    TextView stname = new TextView(StuViewReviewActivity.this);
                    stname.setText(" Paper Name ");
                    stname.setBackgroundResource(R.drawable.cell_shape);
                    stname.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row1.addView(stname);

                    TextView depart = new TextView(StuViewReviewActivity.this);
                    depart.setText(" Status ");
                    depart.setBackgroundResource(R.drawable.cell_shape);
                    depart.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row1.addView(depart);

                    tl.addView(row1);
                    res.moveToFirst();
                    do {
                        TableRow row = new TableRow(StuViewReviewActivity.this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                        row.setBackgroundColor(Color.TRANSPARENT);
                        row.setPadding(5, 0, 5, 5);
                        row.setLayoutParams(lp);

                        TextView sid = new TextView(StuViewReviewActivity.this);
                        sid.setText(res.getString(0));
                        sid.setBackgroundResource(R.drawable.cell_shape);
                        sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                        row.addView(sid);

                        TextView sname = new TextView(StuViewReviewActivity.this);
                        sname.setText(res.getString(1));
                        sname.setBackgroundResource(R.drawable.cell_shape);
                        sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                        row.addView(sname);

                        TextView dept = new TextView(StuViewReviewActivity.this);
                        dept.setText(res.getString(2));
                        dept.setBackgroundResource(R.drawable.cell_shape);
                        dept.setTextAppearance(android.R.style.TextAppearance_Medium);
                        row.addView(dept);

                        tl.addView(row);
                    } while (res.moveToNext());

                }
                res.close();





        TextView mTextViewBack;
        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(StuViewReviewActivity.this, StuActivity.class);
                BackIntent.putExtra("email",email);
                startActivity(BackIntent);
            }
        });


    }

}

