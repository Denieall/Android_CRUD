package com.denieall.crud;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.denieall.crud.Adapter.UserRecyclerViewAdapter;
import com.denieall.crud.Model.DBIntentService;
import com.denieall.crud.Model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout mainLayout;

    public static List<User> users;

    FloatingActionButton fab;
    RecyclerView user_recycler_view;
    RecyclerView.Adapter adapter;

    public static final String TAG = "MainActivity";

    public ResultReceiver db_data_receiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (resultCode == 200) {

                users = (List<User>) resultData.getSerializable("All users");

                updateUserRecyclerView();

                Log.i(TAG, "onReceiveResult: Success");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateUser.class));
            }
        });

        // Creating service to handle DB operation -- get All the users
        Intent intent = new Intent(this, DBIntentService.class);
        intent.putExtra(DBIntentService.BUNDLED_LISTENER, db_data_receiver);
        intent.setAction("SELECT ALL");
        startService(intent);

    }

    public void updateUserRecyclerView() {
        //Setup Recycler View adapter
        user_recycler_view = findViewById(R.id.user_recyclerView);
        user_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserRecyclerViewAdapter(this, users);
        user_recycler_view.setAdapter(adapter);
    }

    // This is for action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_all) {

            // Process delete request
            Intent intent = new Intent(this, DBIntentService.class);
            intent.setAction("DELETE ALL");

            intent.putExtra(DBIntentService.BUNDLED_LISTENER, new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    super.onReceiveResult(resultCode, resultData);

                    Toast.makeText(getApplicationContext(), "List cleared", Toast.LENGTH_LONG).show();

                    // Deletes and restarts the Main activity
                    finish();
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                }
            });

            startService(intent);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
