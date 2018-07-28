package com.denieall.crud.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denieall.crud.Model.User;
import com.denieall.crud.R;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    Context context;

    private TextView fname, lname, email;

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
    public void onBindViewHolder(@NonNull UserRecyclerViewAdapter.UserViewHolder holder, int position) {

        fname.setText(users_list.get(position).getFirst_name());
        lname.setText(users_list.get(position).getLast_name());
        email.setText(users_list.get(position).getEmail());

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
        }
    }
}
