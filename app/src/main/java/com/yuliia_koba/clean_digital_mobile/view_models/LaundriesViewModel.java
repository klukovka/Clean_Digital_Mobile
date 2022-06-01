package com.yuliia_koba.clean_digital_mobile.view_models;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Constants;
import com.yuliia_koba.clean_digital_mobile.models.Laundry;
import com.yuliia_koba.clean_digital_mobile.models.LaundryPagination;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.services.LaundryService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaundriesViewModel extends AndroidViewModel {
    private final LaundryService laundryService;

    private MutableLiveData<Status> statusMutableLiveData;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Laundry[]> laundries;
    private final MutableLiveData<Integer> page = new MutableLiveData<>();
    private int totalPages = 10;
    private Boolean isFirst = true;

    public LaundriesViewModel(@NonNull Application application) {
        super(application);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        laundryService = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LaundryService.class);
    }

    public LiveData<Status> getStatus() {
        if (statusMutableLiveData == null){
            statusMutableLiveData = new MutableLiveData<>();
            statusMutableLiveData.postValue(Status.LOADING);
        }
        return statusMutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (errorMessage == null){
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }

    public MutableLiveData<Laundry[]> getLaundries(){
     if (laundries == null){
         laundries = new MutableLiveData<>();
         page.postValue(0);
     }
     return laundries;
    }

    public MutableLiveData<Integer> getPage(){
        return page;
    }

    public void loadLaundries(int pageNumber, Context context){
        if (isFirst || pageNumber < totalPages){
            statusMutableLiveData.postValue(Status.LOADING);

            laundryService.getLaundries(pageNumber, PreferencesService.getHeader())
                    .enqueue(new Callback<LaundryPagination>() {
                        @Override
                        public void onResponse(@NonNull Call<LaundryPagination> call,
                                               @NonNull Response<LaundryPagination> response) {
                            if (response.isSuccessful()){
                                if (laundries.getValue()!=null){
                                    laundries.postValue(Utils.concat(laundries.getValue(),
                                            response.body().getContent()));
                                } else {
                                    laundries.postValue(response.body().getContent());
                                }

                                statusMutableLiveData.postValue(Status.SUCCESS);
                                isFirst = false;
                                totalPages = response.body().getTotalPages();
                                page.postValue(pageNumber+1);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LaundryPagination> call,
                                              @NonNull Throwable t) {
                            statusMutableLiveData.postValue(Status.ERROR);
                            errorMessage.postValue(context.getString(R.string.something_went_wrong));
                        }
                    });

        }
    }
}
