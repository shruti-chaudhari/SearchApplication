package com.shruti.searchapplication.activities.viewModels;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;

import com.shruti.searchapplication.BR;
import com.shruti.searchapplication.dao.ArticlesResponse;
import com.shruti.searchapplication.db.DbHelper;
import com.shruti.searchapplication.retrofit.RetrofitClass;
import com.shruti.searchapplication.utils.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseObservable {

    ArticlesResponse response;
    DbHelper handler;
    @Bindable
    private String toastMessage = null;
    @Bindable
    private String dbData;
    @Bindable
    private String addDisplayData;

    String title;
    public ObservableBoolean isDataAvailable = new ObservableBoolean(false);

    public MainViewModel() {
        response = new ArticlesResponse();
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public String getDbData() {
        return dbData;
    }

    public void setDbData(String title) {
        this.dbData = title;
        notifyPropertyChanged(BR.dbData);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void onSearchClicked(View view) {
        if (isInputValid()) {
            setDbData(getTitle());
        } else {
            setToastMessage("Please enter a valid city name");
            isDataAvailable.set(false);
        }
    }

    public boolean isInputValid() {
        return !getTitle().isEmpty() && !getTitle().trim().isEmpty();
    }

}
