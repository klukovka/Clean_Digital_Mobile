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
import com.yuliia_koba.clean_digital_mobile.models.WashMachine;
import com.yuliia_koba.clean_digital_mobile.models.WashMachinePagination;
import com.yuliia_koba.clean_digital_mobile.services.LaundryService;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WashMachinesViewModel extends AndroidViewModel {
    private final LaundryService laundryService;

    private MutableLiveData<Status> statusMutableLiveData;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<WashMachine[]> washMachines;
    private final MutableLiveData<Integer> page = new MutableLiveData<>();
    private int totalPages = 0;
    private Boolean isFirst = true;

    public WashMachinesViewModel(@NonNull Application application) {
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

    public MutableLiveData<WashMachine[]> getWashMachines(){
        if (washMachines == null){
            washMachines = new MutableLiveData<>();
            page.postValue(0);
        }
        return washMachines;
    }

    public MutableLiveData<Integer> getPage(){
        return page;
    }

    public void loadWashMachines(int pageNumber, String laundryId, Context context){
        if (isFirst || pageNumber < totalPages){
            statusMutableLiveData.postValue(Status.LOADING);

            laundryService.getWashMachines(pageNumber, PreferencesService.getHeader(), laundryId)
                    .enqueue(new Callback<WashMachinePagination>() {
                        @Override
                        public void onResponse(Call<WashMachinePagination> call, Response<WashMachinePagination> response) {
                            if (response.isSuccessful()){
                                if (washMachines.getValue()!=null){
                                    washMachines.postValue(Utils.concat(washMachines.getValue(),
                                            response.body().getContent()));
                                } else {
                                    washMachines.postValue(response.body().getContent());
                                }

                                statusMutableLiveData.postValue(Status.SUCCESS);
                                isFirst = false;
                                totalPages = response.body().getTotalPages();
                                page.postValue(pageNumber+1);
                            }
                        }

                        @Override
                        public void onFailure(Call<WashMachinePagination> call, Throwable t) {
                            statusMutableLiveData.postValue(Status.ERROR);
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
