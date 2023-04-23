package com.example.fragmentrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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
            public void onLongUserClick( View view, int position) {
                showMenu(view, position);
            }

        };

        adapter = new UserAdapter(this, users(), userClickListener);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    //----------начало работы со свайпом---------------
    //запоминаю удаленный объект
    User deletedUser = null;
    ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:{
                    //--------Тут начинаем задавать различные вопросы
                    deletedUser = users.get(position);
                    new AlertDialog.Builder(MainActivity.this)
                            //.setIcon(R.drawable.ic_delete)
                            .setTitle("Удаление пользователя")
                            .setMessage("Удалить пользователя " + deletedUser.getName() + "?")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    users.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    //в качестве бонуса добавив внизу снэкбар для восстановления удаленной записи
                                    Snackbar.make(recyclerView, "Пользователь " + deletedUser.getName() + " удален!", Snackbar.LENGTH_LONG)
                                            .setAction("Восстановить?", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    users.add(position, deletedUser);
                                                    //adapter.notifyDataSetChanged(); //можно так, но...
                                                    adapter.notifyItemInserted(position); //так лучше!
                                                }
                                            }).show();
                                }
                            })
                            // если передумали, то просто обновляем наш Recycler
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    adapter.notifyItemRemoved(position); //так лучше!
                                    adapter.notifyItemInserted(position);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    break;
                }
                case ItemTouchHelper.RIGHT:{
                    onEdit(findViewById(R.id.userList), position);
                    break;
                }
                default: return;
            }
        }

        @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.teal_200))
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    //---------------------------------------------------------------------------------------------
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
