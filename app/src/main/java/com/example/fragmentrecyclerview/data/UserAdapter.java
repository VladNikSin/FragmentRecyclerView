package com.example.fragmentrecyclerview.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentrecyclerview.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<User> users;
    public UserAdapter(Context context, List<User> users) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewNumber.setText(user.getNumber());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewName, textViewNumber;
        ViewHolder(View view){
            super(view);
            textViewName = view.findViewById(R.id.itemName);
            textViewNumber = view.findViewById(R.id.itemNumber);
        }
    }
}
