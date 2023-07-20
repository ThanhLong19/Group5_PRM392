package com.prm392team.shopf.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.prm392team.shopf.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE userId IN (:userIds)")
    User getUserByIds(int userIds);

    @Insert
    void insertUser(User users);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User getAccount(String username, String password);

    @Query("UPDATE  user SET email = :email, phone = :phone, dob = :dob WHERE userId = :id")
    void updateUser(int id, String email, String phone, String dob);

    @Query("UPDATE  user SET password = :password WHERE userId = :id")
    void changePw(String password, int id);
}
