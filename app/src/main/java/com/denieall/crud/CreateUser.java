package com.denieall.crud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.denieall.crud.Model.DBIntentService;
import com.denieall.crud.Model.User;

public class CreateUser extends AppCompatActivity {

    ConstraintLayout createUserLayout;

    private static final String TAG = "CreateUser";

    EditText fname, lname, email;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        fname = findViewById(R.id.editText_fname);
        lname = findViewById(R.id.editText_lname);
        email = findViewById(R.id.editText_email);

        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // TODO: Save data to database
            if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please fill up all the fields!", Toast.LENGTH_SHORT).show();


            } else {

                Intent intent = new Intent(getApplicationContext(), DBIntentService.class);
                intent.setAction("INSERT");

                intent.putExtra("fname", fname.getText().toString());
                intent.putExtra("lname", lname.getText().toString());
                intent.putExtra("email", email.getText().toString());

                intent.putExtra(DBIntentService.BUNDLED_LISTENER, new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);

                        if (resultCode == 200) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                    }
                });

                startService(intent);

            }
            }
        });

    }
}
