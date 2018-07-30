package com.denieall.crud;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.denieall.crud.Model.DBIntentService;
import com.denieall.crud.Model.User;

public class UserDetailsActivity extends AppCompatActivity {

    private static final String TAG = "UserDetailsActivity";

    TextView fname, email;
    Button btn_user_details_edit;

    User user;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        fname = findViewById(R.id.full_name);
        email = findViewById(R.id.email_tv);
        btn_user_details_edit = findViewById(R.id.user_details_btn_edit);

        btn_user_details_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

        id = getIntent().getIntExtra("id", 0);

        Intent intent = new Intent(this, DBIntentService.class);
        intent.setAction("SELECT SINGLE USER");
        intent.putExtra("id", id);
        intent.putExtra(DBIntentService.BUNDLED_LISTENER, new ResultReceiver(new Handler()){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);

                Log.i(TAG, "onReceiveResult: Success");

                if (resultCode == 200) {

                    user = (User) resultData.getSerializable("User");

                    Log.i(TAG, user.getFirst_name());

                    displayText(user.getFirst_name() + " " + user.getLast_name(), user.getEmail());
                }
            }
        });

        startService(intent);

        Log.i(TAG, "" + id);

    }

    private void displayText(String name, String mail) {
        fname.setText(name);
        email.setText(mail);
    }
}
