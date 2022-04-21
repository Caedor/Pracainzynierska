package com.example.pracainzynierska.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM USER")
    List<User> getAllData();

    @Insert
    void insertData(User... Users);

    @Delete
    void delete(User users);


}
