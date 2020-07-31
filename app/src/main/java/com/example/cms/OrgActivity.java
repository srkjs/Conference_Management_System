package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OrgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);

        final String email = getIntent().getStringExtra("email");
        Button mButton1 = (Button) findViewById(R.id.button_addConference);
        Button mButton2 = (Button) findViewById(R.id.button_assignReviewer);
        Button mButton3 = (Button) findViewById(R.id.button_viewReviews);
        Button mButton4 = (Button) findViewById(R.id.button_viewReg);
        Button mButtonLogout = (Button) findViewById(R.id.button_logout);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgActivity.this, OrgAddConferenceActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgActivity.this, OrgAssignReviewerActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgActivity.this, OrgViewReviewActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgActivity.this, OrgViewReg.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgActivity.this, LoginOrganiserActivity.class);
                startActivity(intent);
            }
        });

    }

}
