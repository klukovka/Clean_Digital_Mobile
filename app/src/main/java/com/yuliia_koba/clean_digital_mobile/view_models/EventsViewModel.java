package com.yuliia_koba.clean_digital_mobile.view_models;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Constants;
import com.yuliia_koba.clean_digital_mobile.models.Event;
import com.yuliia_koba.clean_digital_mobile.models.EventPagination;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.services.EventService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsViewModel extends AndroidViewModel {
    private final EventService eventService;

    private MutableLiveData<Status> status;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Event[]> events;
    private final MutableLiveData<Integer> page = new MutableLiveData<>();
    private int totalPages = 0;
    private Boolean isFirst = true;

    public EventsViewModel(@NonNull Application application) {
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

    public MutableLiveData<Event[]> getEvents(){
        if (events == null){
            events = new MutableLiveData<>();
            page.postValue(0);
        }
        return events;
    }

    public MutableLiveData<Integer> getPage(){
        return page;
    }

    public void loadEvents(int pageNumber, Context context){
        if (isFirst || pageNumber < totalPages){
            status.postValue(Status.LOADING);

            eventService.getEvents(pageNumber, PreferencesService.getHeader())
                    .enqueue(new Callback<EventPagination>() {
                        @Override
                        public void onResponse(Call<EventPagination> call, Response<EventPagination> response) {
                            if (response.isSuccessful()){
                                if (events.getValue()!=null){
                                    events.postValue(Utils.concat(events.getValue(),
                                            response.body().getContent()));
                                } else {
                                    events.postValue(response.body().getContent());
                                }

                                status.postValue(Status.SUCCESS);
                                isFirst = false;
                                totalPages = response.body().getTotalPages();
                                page.postValue(pageNumber+1);
                            }
                        }

                        @Override
                        public void onFailure(Call<EventPagination> call, Throwable t) {
                            status.postValue(Status.ERROR);
                            errorMessage.postValue(context.getString(R.string.something_went_wrong));
                        }
                    });

        }
    }

    public void restart(){
        page.postValue(0);
        isFirst = true;
    }
}
