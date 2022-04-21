package com.example.pracainzynierska;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pracainzynierska.db.AppDatabase;
import com.example.pracainzynierska.db.User;

import java.util.List;

public class ItemlistViewmodel extends AndroidViewModel {

    private MutableLiveData<List<User>> listofData;

    private AppDatabase appDatabase;

    public ItemlistViewmodel(@NonNull Application application) {
        super(application);
        listofData = new MutableLiveData<>();

        appDatabase = AppDatabase.getDbInstance(getApplication().getApplicationContext());
    }


    public MutableLiveData<List<User>>  getItemsListObserver()
    {
        return listofData;
    }

    public void getAllDataList() {
        List<User> dataList=  appDatabase.userDao().getAllData();
        if(dataList.size() > 0)
        {
            listofData.postValue(dataList);
        }else {
            listofData.postValue(null);
        }
    }



    public void deleteItems(User user) {
        appDatabase.userDao().delete(user);
        getAllDataList();
    }


}
