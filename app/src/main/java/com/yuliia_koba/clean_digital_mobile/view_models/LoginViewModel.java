package com.yuliia_koba.clean_digital_mobile.view_models;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.dto.AuthMeta;
import com.yuliia_koba.clean_digital_mobile.models.Constants;
import com.yuliia_koba.clean_digital_mobile.models.api.LoginRequest;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.services.AuthService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginViewModel extends AndroidViewModel {
    private AuthService authService;
    private MutableLiveData<Status> statusMutableLiveData;
    private MutableLiveData<String> errorMessage;

    public LoginViewModel(@NonNull Application application) {
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

    public void login(String email, String password, Context context){
        statusMutableLiveData.postValue(Status.LOADING);
        authService.login(new LoginRequest(email, password))
                .enqueue(new Callback<AuthMeta>() {
                    @Override
                    public void onResponse(Call<AuthMeta> call, Response<AuthMeta> response) {
                        if (response.body() != null 
                                && response.isSuccessful() 
                                && response.body().getRole().equals(Constants.CLIENT)){
                            PreferencesService.setToken(response.body().getToken());
                            statusMutableLiveData.postValue(Status.SUCCESS);
                        } else if (response.body() != null
                                && response.isSuccessful()
                                && !response.body().getRole().equals(Constants.CLIENT)){
                            statusMutableLiveData.postValue(Status.ERROR);
                            errorMessage.postValue(context.getString(R.string.it_is_not_client));
                        } else {
                            statusMutableLiveData.postValue(Status.ERROR);
                            errorMessage.postValue(context.getString(R.string.auth_failed));
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthMeta> call, Throwable t) {
                        errorMessage.postValue(t.getLocalizedMessage());
                        statusMutableLiveData.postValue(Status.ERROR);
                    }
                });
    }

    public LiveData<Status> getStatusMutableLiveData() {
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
}
