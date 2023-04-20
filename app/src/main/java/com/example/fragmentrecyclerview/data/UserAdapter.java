package com.example.fragmentrecyclerview.data;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentrecyclerview.MainActivity;
import com.example.fragmentrecyclerview.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<User> users;
    private OnLongUserClickListener onClickListener; //добавлено позже
    public UserAdapter(Context context, List<User> users, OnLongUserClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.onClickListener = onClickListener; //добавлено позже
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewNumber.setText(user.getNumber());
        // обработка нажатия
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) { //нужно сюда передать метод и юзера
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onLongUserClick(view, position);
                return true;
            }
        });

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

    public interface OnLongUserClickListener{
        void onLongUserClick(View view, int position);
    }
}
