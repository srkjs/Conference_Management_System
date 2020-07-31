package com.example.cms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cms.DatabaseHelper;

public class ViewDetails extends AppCompatActivity {
    DatabaseHelper mydb;
    TextView mregno,mname,mage,mphone,maddress,mqua,mdept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        mydb =new DatabaseHelper(this);



        mregno = (TextView)findViewById(R.id.regno);
        mname = (TextView)findViewById(R.id.name);
        mage = (TextView)findViewById(R.id.age);
        mphone = (TextView)findViewById(R.id.phone);
        maddress = (TextView)findViewById(R.id.address);
        mqua = (TextView)findViewById(R.id.qualification);
        mdept = (TextView)findViewById(R.id.department);


        final String email = getIntent().getStringExtra("stuemail");
        final Cursor res = mydb.stuDetails(email);
        final int count = res.getCount();
        res.moveToFirst();

        if(count == 0)
        {
            Toast.makeText(this,"No data found",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ViewDetails.this,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            mregno.setText(res.getString(0));
            mname.setText(res.getString(1));
            mage.setText(res.getString(2));
            mphone.setText(res.getString(3));
            maddress.setText(res.getString(4));
            mqua.setText(res.getString(5));
            mdept.setText(res.getString(6));
        }

        TextView mTextViewLogin;
        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(ViewDetails.this,OrgViewReg.class);
                startActivity(BackIntent);
            }
        });

    }
}

