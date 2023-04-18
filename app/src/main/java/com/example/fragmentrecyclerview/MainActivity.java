package com.example.fragmentrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmentrecyclerview.data.User;
import com.example.fragmentrecyclerview.data.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> users = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.userList);
        UserAdapter adapter = new UserAdapter(this, users());
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
        EditText dialogAge = addDialog.findViewById(R.id.personNumber);
        addDialog.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Данные добавлены!", Toast.LENGTH_SHORT).show();
                users.add(new User(dialogName.getText().toString(), dialogAge.getText().toString()));
                onResume();
                addDialog.hide();
            }
        });
        addDialog.show();

    }
}