package com.denieall.crud;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.denieall.crud.Model.User;

public class CreateUser extends AppCompatActivity {

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
                Log.i(TAG, fname.getText().toString() + " " + lname.getText().toString() + " --- " + email.getText().toString());

                MainActivity.users.add(new User(fname.getText().toString(), lname.getText().toString(), email.getText().toString()));

                Snackbar.make(view, fname + "'s information saved", Snackbar.LENGTH_LONG);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

    }
}
