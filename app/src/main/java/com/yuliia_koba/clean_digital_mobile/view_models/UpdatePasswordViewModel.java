package com.yuliia_koba.clean_digital_mobile.view_models;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yuliia_koba.clean_digital_mobile.models.Constants;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.models.api.UpdatePasswordRequest;
import com.yuliia_koba.clean_digital_mobile.services.AuthService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePasswordViewModel extends AndroidViewModel {
    private AuthService authService;
    private MutableLiveData<Status> statusMutableLiveData;
    private MutableLiveData<String> errorMessage;


    public UpdatePasswordViewModel(@NonNull Application application) {
        super(application);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        authService = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService.class);
    }


    public LiveData<Status> getStatus() {
        if (statusMutableLiveData == null){
            statusMutableLiveData = new MutableLiveData<>();
            statusMutableLiveData.postValue(Status.INITIAL);
        }
        return statusMutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (errorMessage == null){
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }
    public void save(String password, String newPassword){
        statusMutableLiveData.postValue(Status.LOADING);

        authService.updatePassword(new UpdatePasswordRequest(password, newPassword),
                PreferencesService.getHeader())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            statusMutableLiveData.postValue(Status.SUCCESS);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        statusMutableLiveData.postValue(Status.ERROR);
                        errorMessage.postValue(t.getLocalizedMessage());
                    }
                });
    }
}
