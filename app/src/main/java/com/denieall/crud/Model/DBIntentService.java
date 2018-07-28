package com.denieall.crud.Model;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.denieall.crud.AppDatabase;

import java.io.Serializable;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DBIntentService extends IntentService {

    public final static String BUNDLED_LISTENER = "db_listener";
    ResultReceiver dbreceiver;

    AppDatabase appDB;
    List<User> users;

    public DBIntentService() {
        super("DBIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appDB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "testing").build();
    }

    @Override
    public int onStartCommand(@Nullable final Intent intent, int flags, int startId) {


        if (intent != null) {

            String intentAction = intent.getAction();
            dbreceiver = intent.getParcelableExtra(BUNDLED_LISTENER);

            if (intentAction.equals("SELECT ALL")) {

                Thread thread_get_users = new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        getUsersList();
                    }
                };

                thread_get_users.start();

            } else if (intentAction.equals("INSERT")) {

                Thread thread_insert_user = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        insertUser(intent.getStringExtra("fname"), intent.getStringExtra("lname"), intent.getStringExtra("email"));
                    }
                };

                thread_insert_user.start();

            } else if (intentAction.equals("SELECT SINGLE USER")) {


                Thread thread_get_single_user = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        getSingleUser(intent.getIntExtra("id", 0));
                    }
                };

                thread_get_single_user.start();

            }

        }

        return START_NOT_STICKY;
    }

    public void getSingleUser(int id) {

        User user = appDB.userDAO().getSingleUser(id);
        Bundle b = new Bundle();
        b.putSerializable("User", (Serializable) user);
        dbreceiver.send(200, b);
    }

    public void getUsersList() {
        users = appDB.userDAO().getAllUsers();

        Bundle b = new Bundle();
        b.putSerializable("All users", (Serializable) users);
        dbreceiver.send(200, b);
    }

    public void insertUser(String fname, String lname, String email) {
        appDB.userDAO().insertAll(new User(fname, lname, email));
        dbreceiver.send(200, null);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
