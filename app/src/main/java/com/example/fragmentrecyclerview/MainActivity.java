package com.example.fragmentrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fragmentrecyclerview.data.User;
import com.example.fragmentrecyclerview.data.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.userList);
        UserAdapter adapter = new UserAdapter(this, users());
        recyclerView.setAdapter(adapter);
    }


    private List<User> users(){
        ArrayList<User> users = new ArrayList<>();
            users.add(new User("Иван", "+7959595959595"));
            users.add(new User("Иван", "+7959595959595"));
            users.add(new User("Иван", "+7959595959595"));
            users.add(new User("Иван", "+7959595959595"));
            users.add(new User("Иван", "+7959595959595"));
        return users;
    }
}