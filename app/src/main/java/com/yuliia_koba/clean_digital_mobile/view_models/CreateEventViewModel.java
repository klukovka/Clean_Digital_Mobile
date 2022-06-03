package com.yuliia_koba.clean_digital_mobile.view_models;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Constants;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.models.api.PayForEventRequest;
import com.yuliia_koba.clean_digital_mobile.models.dto.Client;
import com.yuliia_koba.clean_digital_mobile.models.dto.Event;
import com.yuliia_koba.clean_digital_mobile.services.ClientService;
import com.yuliia_koba.clean_digital_mobile.services.EventService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateEventViewModel extends AndroidViewModel {
    private final EventService eventService;
    private final ClientService clientService;
    private Integer bonuses = 0;
    private String eventId = "";

    private MutableLiveData<Status> status;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Event> event;

    private MutableLiveData<Integer> leftBonuses = new MutableLiveData<>();

    public CreateEventViewModel(@NonNull Application application) {
        super(application);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        eventService = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EventService.class);


        clientService = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ClientService.class);
    }

    void setBonuses(int bonus){
        leftBonuses.postValue(bonuses - bonus);
    }

    public LiveData<Integer> getLeftBonuses(){
        return leftBonuses;
    }

    public LiveData<Status> getStatus() {
        if (status == null){
            status = new MutableLiveData<>();
            status.postValue(Status.LOADING);
        }
        return status;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (errorMessage == null){
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }

    public MutableLiveData<Event> getEvent(String eventId, Context context){
        if (event == null){
            event = new MutableLiveData<>();
            init(eventId, context);
        }

        return event;
    }

    public void init(String eventId, Context context){
        status.postValue(Status.LOADING);
        this.eventId = eventId;

        eventService.getEvent(eventId, PreferencesService.getHeader())
                .enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if (response.isSuccessful()){
                            event.postValue(response.body());
                            status.postValue(Status.SUCCESS);
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        status.postValue(Status.ERROR);
                        errorMessage.postValue(context.getString(R.string.something_went_wrong));
                    }
                });

        status.postValue(Status.LOADING);

        clientService.getPersonalInfo(PreferencesService.getHeader())
                .enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.isSuccessful()){
                            leftBonuses.postValue(response.body().bonuses);
                            bonuses = response.body().bonuses;
                            status.postValue(Status.SUCCESS);
                        }
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        status.postValue(Status.ERROR);
                        errorMessage.postValue(context.getString(R.string.something_went_wrong));
                    }
                });
    }

    public void save(Context context){
        status.postValue(Status.LOADING);

        eventService.payForEvent(new PayForEventRequest(eventId, leftBonuses.getValue()), PreferencesService.getHeader())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        status.postValue(Status.SAVED);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        status.postValue(Status.ERROR);
                        errorMessage.postValue(context.getString(R.string.something_went_wrong));
                    }
                });
    }
}
