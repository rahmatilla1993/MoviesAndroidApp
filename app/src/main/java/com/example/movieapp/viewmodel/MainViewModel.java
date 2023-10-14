package com.example.movieapp.viewmodel;

import static com.example.movieapp.network.HttpConstants.QUERY_PARAMS;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.Movie;
import com.example.movieapp.network.ApiFactory;
import com.example.movieapp.network.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private final MutableLiveData<List<Movie>> moviesLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadedLD = new MutableLiveData<>(false);
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ApiService apiService;
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiFactory.getApiService();
        loadMovies();
    }

    public LiveData<List<Movie>> getMoviesLD() {
        return moviesLD;
    }

    public LiveData<Boolean> getIsLoadedLD() {
        return isLoadedLD;
    }

    public void loadMovies() {
        Boolean isLoaded = isLoadedLD.getValue();
        if (isLoaded != null && isLoaded) {
            return;
        }
        Disposable disposable = apiService
                .loadMovies(QUERY_PARAMS, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(dis -> isLoadedLD.setValue(true))
                .doFinally(() -> isLoadedLD.setValue(false))
                .subscribe(apiResponse -> {
                    List<Movie> movieList = moviesLD.getValue();
                    if (movieList != null) {
                        movieList.addAll(apiResponse.getMovies());
                        moviesLD.setValue(movieList);
                    } else {
                        moviesLD.setValue(apiResponse.getMovies());
                    }
                    page++;
                }, throwable -> Log.d(TAG, throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
