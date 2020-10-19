package com.shruti.searchapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shruti.searchapplication.R;
import com.shruti.searchapplication.activities.viewModels.MainViewModel;
import com.shruti.searchapplication.adapters.ArticlesAdapter;
import com.shruti.searchapplication.dao.ArticlesResponse;
import com.shruti.searchapplication.databinding.ActivityMainBinding;
import com.shruti.searchapplication.db.DbHelper;
import com.shruti.searchapplication.retrofit.RetrofitClass;
import com.shruti.searchapplication.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static RecyclerView recycler;
    static ArticlesAdapter adapter;
    static List<ArticlesResponse.Docs> responseArrayList = new ArrayList<>();
    static ActivityMainBinding binding;
    static DbHelper helper;
    static Context context;
    static ProgressDialog dialog;

    public static ObservableBoolean isDataAvailable = new ObservableBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setViewModel(new MainViewModel());
        binding.executePendingBindings();
        binding.setMain(this);
        context = this;
        dialog = new ProgressDialog(context);
    }

    private static void initRecyclerView() {
        recycler = binding.recycler;
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new ArticlesAdapter(responseArrayList);
        recycler.setAdapter(adapter);
        if (dialog != null)
            if (dialog.isShowing())
                dialog.dismiss();
    }

    private static void getData(Context context, String title) {
        helper = DbHelper.getInstance(context);
        responseArrayList = helper.getDocs(title);
        if (responseArrayList != null && responseArrayList.size() > 0) {
            initRecyclerView();
            isDataAvailable.set(true);
        } else {
            isDataAvailable.set(false);
        }
    }

    @BindingAdapter({"dbData"})
    public static void getDataFromDb(View view, String title) {
        boolean flag = false;
        if (title != null && title.trim().length() > 0) {
            dialog.setMessage("Please Wait!!!");
            dialog.show();
            helper = DbHelper.getInstance(view.getContext());
            responseArrayList = helper.getDocs(title);
            if (responseArrayList != null && responseArrayList.size() > 0) {
                flag = true;
                initRecyclerView();
                isDataAvailable.set(true);
            } else {
                if (Util.isNetworkAvailable(view.getContext())) {
                    Map<String, String> data = new HashMap<>();
                    data.put("q", title);
                    RetrofitClass.getRetrofitClient()
                            .getWeather(data)
                            .enqueue(new Callback<ArticlesResponse>() {
                                @Override
                                public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response1) {
                                    if (response1.body() != null) {
                                        responseArrayList = response1.body().getResponse().getDocs();
                                        for (ArticlesResponse.Docs docs : responseArrayList) {
                                            helper.addPlayer(docs);
                                        }
                                        isDataAvailable.set(true);
                                        //initRecyclerView();
                                        getData(view.getContext(), title);
                                    } else {
                                        //setToastMessage("Data not found!!!");
                                        isDataAvailable.set(false);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                                    runMe(view, t.getMessage());
                                    isDataAvailable.set(false);
                                }
                            });
                } else {
                    runMe(view, "No Internet Connection Available.");
                    isDataAvailable.set(false);
                }

            }
        }
    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}