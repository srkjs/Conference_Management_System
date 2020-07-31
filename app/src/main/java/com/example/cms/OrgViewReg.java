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


public class OrgViewReg extends AppCompatActivity {
    DatabaseHelper mydb;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_view_review);
        mydb =new DatabaseHelper(this);

        final EditText mcid = (EditText) findViewById(R.id.cid);
        Button mButton5 = (Button) findViewById(R.id.button_getDetails);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String cid = mcid.getText().toString().trim();
                final Cursor res = mydb.viewReg(cid);
                res.moveToFirst();
                final int count = res.getCount();
                if(count == 0)
                {
                    Toast.makeText(OrgViewReg.this,"No registrations done!",Toast.LENGTH_LONG).show();
                }
                else {
                    tl = (TableLayout) findViewById(R.id.students);
                    TableRow row1 = new TableRow(OrgViewReg.this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    row1.setBackgroundColor(Color.TRANSPARENT);
                    row1.setPadding(5,0,5,5);
                    row1.setLayoutParams(lp1);

                    TextView studid = new TextView(OrgViewReg.this);
                    studid.setText(" E-mail ");
                    studid.setBackgroundResource(R.drawable.cell_shape);
                    studid.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row1.addView(studid);

                    TextView stname = new TextView(OrgViewReg.this);
                    stname.setText(" Name ");
                    stname.setBackgroundResource(R.drawable.cell_shape);
                    stname.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row1.addView(stname);

                    TextView depart = new TextView(OrgViewReg.this);
                    depart.setText(" Details ");
                    depart.setBackgroundResource(R.drawable.cell_shape);
                    depart.setTextAppearance(android.R.style.TextAppearance_Medium);
                    row1.addView(depart);

                    tl.addView(row1);
                    res.moveToFirst();
                    do {
                        TableRow row = new TableRow(OrgViewReg.this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                        row.setBackgroundColor(Color.TRANSPARENT);
                        row.setPadding(5,0,5,5);
                        row.setLayoutParams(lp);

                        TextView sid = new TextView(OrgViewReg.this);
                        sid.setText(res.getString(0));
                        final String stuemail = res.getString(0);
                        sid.setBackgroundResource(R.drawable.cell_shape);
                        sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                        row.addView(sid);

                        TextView sname = new TextView(OrgViewReg.this);
                        sname.setText(res.getString(1));
                        sname.setBackgroundResource(R.drawable.cell_shape);
                        sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                        row.addView(sname);


                        TextView btn = new TextView(OrgViewReg.this);
                        btn.setText(" View ");
                        btn.setBackgroundResource(R.drawable.cell_shape);
                        btn.setTextAppearance(android.R.style.TextAppearance_Medium);
                        btn.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {
//                                sendMessage(cid,email);
                                Intent intent = new Intent(OrgViewReg.this, ViewDetails.class);
                                intent.putExtra("stuemail",stuemail);
                                startActivity(intent);
                            }
                        });
                        row.addView(btn);

                        tl.addView(row);
                    }while(res.moveToNext());
                }

                res.close();


            }
        });





        TextView mTextViewBack;
        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(OrgViewReg.this, OrgActivity.class);
                startActivity(BackIntent);
            }
        });


    }

//    private void sendMessage(final String cid, final String email) {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setTitle("Sending Email");
//        dialog.setMessage("Please wait");
//        dialog.show();
//        Thread sender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    GMailSender sender = new GMailSender("srks2999@gmail.com", "Sjss@2999");
//                    sender.sendMail("Selection for Conference", "Conference ID: " + cid + "\n" + "You have been selected for the Conference. Please complete the registration!", email);
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    Log.e("mylog", "Error: " + e.getMessage());
//                }
//            }
//        });
//        sender.start();
//    }
}

