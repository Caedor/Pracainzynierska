package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.pracainzynierska.db.AppDatabase;
import com.example.pracainzynierska.db.User;

import java.util.List;
import java.util.Observable;

public class Historia extends AppCompatActivity implements  DBAdapter.HandleDataClick{


private ItemlistViewmodel viewModel;
private DBAdapter dbAdapter;
private AppDatabase db;
RecyclerView recyclerView;

List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
        initViewmodel();
        loadUserList();
}

private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbAdapter= new DBAdapter(this, (DBAdapter.HandleDataClick) this);
        recyclerView.setAdapter(dbAdapter);

}

private void initViewmodel(){
        viewModel= new ViewModelProvider(this).get(ItemlistViewmodel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                if (userList==null){
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    dbAdapter.setUserList(userList);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }
        });



}

    private void loadUserList() {
        db = AppDatabase.getDbInstance(this.getApplicationContext());
        userList =db.userDao().getAllData();
        dbAdapter.setUserList(userList);

    }
    public void back_to_menu(View view){
        finish();

    }


    @Override
    public void removeData(User user) {
        viewModel.deleteItems(user);
    }
}