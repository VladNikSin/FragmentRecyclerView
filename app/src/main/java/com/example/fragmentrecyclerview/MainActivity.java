package com.example.fragmentrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.fragmentrecyclerview.data.User;
import com.example.fragmentrecyclerview.data.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> users = new ArrayList<>();
    RecyclerView recyclerView;
    PopupMenu popupMenu;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.userList);

        UserAdapter.OnLongUserClickListener userClickListener = new UserAdapter.OnLongUserClickListener() {
            @Override
            //public void onLongUserClick(User user, int position, View view) {
            public void onLongUserClick( View view, int position) {
                //showMenu(findViewById(R.id.itemName));
                showMenu(view, position);
            }

        };

        adapter = new UserAdapter(this, users(), userClickListener);
        recyclerView.setAdapter(adapter);
    }

    private List<User> users(){

        users.add(new User("Иван", "+7959595959595"));
        users.add(new User("Иван", "+7959595959595"));
        users.add(new User("Иван", "+7959595959595"));
        users.add(new User("Иван", "+7959595959595"));
        users.add(new User("Иван", "+7959595959595"));
        return users;
    }

    public void onAdd(View view) {
        Dialog addDialog = new Dialog(this, R.style.Theme_FragmentRecyclerView);
        addDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100,0,0,0)));
        addDialog.setContentView(R.layout.add_user_dialog);
        //addDialog.cancel();

        EditText dialogName = addDialog.findViewById(R.id.personName);
        EditText dialogNumber = addDialog.findViewById(R.id.personNumber);
        addDialog.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Данные добавлены!", Toast.LENGTH_SHORT).show();
                users.add(new User(dialogName.getText().toString(), dialogNumber.getText().toString()));
                addDialog.hide();
            }
        });
        addDialog.show();
    }
    public void onEdit(View view, int position) {
        Dialog editDialog = new Dialog(this, R.style.Theme_FragmentRecyclerView);
        editDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100,0,0,0)));
        editDialog.setContentView(R.layout.edit_user_dialog);
        //addDialog.cancel();
        User user = users.get(position);
        EditText dialogName = editDialog.findViewById(R.id.personName);
        EditText dialogNumber = editDialog.findViewById(R.id.personNumber);
        dialogName.setText(user.getName());
        dialogNumber.setText(user.getNumber());
        editDialog.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                users.set(position, (new User(dialogName.getText().toString(),dialogNumber.getText().toString())));
                editDialog.hide();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Данные обновлены!", Toast.LENGTH_SHORT).show();
            }
        });
        editDialog.show();
    }

    private void onDelete(int position){
        users.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Данные удалены!", Toast.LENGTH_SHORT).show();
    }
    private void showMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        onEdit(view, position);
                        return true;
                    case R.id.delete:
                        onDelete(position);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.inflate(R.menu.popup_menu); // Здесь menu_main - это файл ресурсов с описанием меню
        popupMenu.show();
    }
    }