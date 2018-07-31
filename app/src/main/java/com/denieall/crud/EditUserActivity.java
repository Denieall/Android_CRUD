package com.denieall.crud;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.denieall.crud.Model.DBIntentService;
import com.denieall.crud.Model.User;

public class EditUserActivity extends AppCompatActivity {


    TextInputEditText edit_fname, edit_lname, edit_email;
    Button btn_save_changes;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Get user data from list originally from db
        user = (User) getIntent().getSerializableExtra("User");

        edit_fname = findViewById(R.id.edit_fname);
        edit_lname = findViewById(R.id.edit_lname);
        edit_email = findViewById(R.id.edit_email);

        edit_fname.setText(user.getFirst_name());
        edit_lname.setText(user.getLast_name());
        edit_email.setText(user.getEmail());

        btn_save_changes = findViewById(R.id.edit_btn_save_changes);

        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit_fname.getText().toString().isEmpty() || edit_lname.getText().toString().isEmpty() || edit_email.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill up all the fields!", Toast.LENGTH_SHORT).show();

                    edit_fname.setText(user.getFirst_name());
                    edit_lname.setText(user.getLast_name());
                    edit_email.setText(user.getEmail());

                } else {

                    // Changed the data in user according to EditText input except for the id
                    user.setFirst_name(edit_fname.getText().toString());
                    user.setLast_name(edit_lname.getText().toString());
                    user.setEmail(edit_email.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), DBIntentService.class);
                    intent.putExtra("User", user);
                    intent.setAction("UPDATE");

                    intent.putExtra(DBIntentService.BUNDLED_LISTENER, new ResultReceiver(new Handler()) {
                        @Override
                        protected void onReceiveResult(int resultCode, Bundle resultData) {
                            super.onReceiveResult(resultCode, resultData);

                            if (resultCode == 200) {

                                Toast.makeText(getApplicationContext(), "User updated!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            }
                        }
                    });

                    startService(intent);
                }

            }
        });

    }
}
