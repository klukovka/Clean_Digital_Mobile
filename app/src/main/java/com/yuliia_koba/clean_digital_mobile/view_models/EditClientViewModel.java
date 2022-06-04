package com.yuliia_koba.clean_digital_mobile.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yuliia_koba.clean_digital_mobile.models.Constants;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.models.api.UpdateClientRequest;
import com.yuliia_koba.clean_digital_mobile.models.dto.Client;
import com.yuliia_koba.clean_digital_mobile.services.ClientService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditClientViewModel extends AndroidViewModel {
    private final ClientService clientService;

    private MutableLiveData<Status> statusMutableLiveData;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Client> client;

    public EditClientViewModel(@NonNull Application application) {
        super(application);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        clientService = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ClientService.class);
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

    public LiveData<Client> getClient(){
        if (client==null){
            client = new MutableLiveData<>();
            init();
        }
        return client;
    }

    private void init(){
        statusMutableLiveData.postValue(Status.LOADING);

        clientService.getPersonalInfo(PreferencesService.getHeader())
                .enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.isSuccessful()){
                            client.postValue(response.body());
                            statusMutableLiveData.postValue(Status.SUCCESS);
                        }
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        statusMutableLiveData.postValue(Status.ERROR);
                        errorMessage.postValue(t.getLocalizedMessage());
                    }
                });
    }

    public void save(String email, String name, String phone){
        statusMutableLiveData.postValue(Status.LOADING);

        clientService.updateClientInfo(PreferencesService.getHeader(),
                new UpdateClientRequest(email, name, phone))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            statusMutableLiveData.postValue(Status.SAVED);
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
