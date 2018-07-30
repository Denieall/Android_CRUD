package com.denieall.crud.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.denieall.crud.EditUserActivity;
import com.denieall.crud.MainActivity;
import com.denieall.crud.Model.DBIntentService;
import com.denieall.crud.Model.User;
import com.denieall.crud.R;
import com.denieall.crud.UserDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    Context context;

    private TextView fname, lname, email;
    private CardView cardView;

    private List<User> users_list;

    public UserRecyclerViewAdapter(Context context, List<User> users) {
        this.context = context;
        this.users_list = users;
    }

    @NonNull
    @Override
    public UserRecyclerViewAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_recycler_view_item, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerViewAdapter.UserViewHolder holder, final int position) {

        fname.setText(users_list.get(position).getFirst_name());
        lname.setText(users_list.get(position).getLast_name());
        email.setText(users_list.get(position).getEmail());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra("id", users_list.get(position).getId());
                context.startActivity(intent);
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // Create popup menu for each item
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.list_item_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.list_item_delete_menu) {

                            // Display alert dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
                            builder.setTitle("Confirmation").setMessage("Are sure about deleting this user?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    // Process delete request
                                    Intent intent = new Intent(context, DBIntentService.class);
                                    intent.setAction("DELETE");
                                    intent.putExtra("User", users_list.get(position));

                                    intent.putExtra(DBIntentService.BUNDLED_LISTENER, new ResultReceiver(new Handler()) {
                                        @Override
                                        protected void onReceiveResult(int resultCode, Bundle resultData) {
                                            super.onReceiveResult(resultCode, resultData);

                                            Toast.makeText(context, "Succesfully Deleted", Toast.LENGTH_LONG).show();

                                            // Deletes and restarts the Main activity
                                            Intent intent1 = new Intent(context, MainActivity.class);
                                            context.startActivity(intent1);
                                        }
                                    });

                                    context.startService(intent);

                                }
                            })
                            .setNegativeButton("No", null)
                            .setIcon(R.mipmap.ic_launcher)
                            .show();

                            return true;

                        } else if (menuItem.getItemId() == R.id.list_item_edit_menu) {

                            Intent intent = new Intent(context, EditUserActivity.class);
                            intent.putExtra("User", users_list.get(position));
                            context.startActivity(intent);

                            return true;

                        }

                        return false;
                    }
                });

                popupMenu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return users_list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public UserViewHolder(View itemView) {
            super(itemView);

            fname = itemView.findViewById(R.id.item_fname_tv);
            lname = itemView.findViewById(R.id.item_lname_tv);
            email = itemView.findViewById(R.id.item_email_tv);
            cardView = itemView.findViewById(R.id.item_user_cardView);

        }
    }
}
