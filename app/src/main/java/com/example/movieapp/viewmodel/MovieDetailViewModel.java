package com.example.movieapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.database.MovieDao;
import com.example.movieapp.database.MovieDataBase;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Review;
import com.example.movieapp.models.ReviewResponse;
import com.example.movieapp.models.TrailerResponse;
import com.example.movieapp.models.TrailersList;
import com.example.movieapp.network.ApiFactory;
import com.example.movieapp.network.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailViewModel";

    private final ApiService apiService;
    private final MovieDao movieDao;
    private final MutableLiveData<TrailersList> trailersListLD = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviewsListLD = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiFactory.getApiService();
        movieDao = MovieDataBase.
                getInstance(application)
                .getMovieDao();
    }

    public LiveData<Movie> getMovieById(int id) {
        return movieDao.getMovieById(id);
    }

    public void addMovie(Movie movie) {
        Disposable disposable = movieDao.add(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void deleteMovie(int id) {
        Disposable disposable = movieDao.delete(id)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public LiveData<TrailersList> getTrailersListLD() {
        return trailersListLD;
    }

    public LiveData<List<Review>> getReviewsListLD() {
        return reviewsListLD;
    }

    public void loadMovieById(int movieId) {
        Disposable disposable = apiService.findMovieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(TrailerResponse::getTrailers)
                .subscribe(
                        trailersListLD::setValue,
                        throwable -> Log.d(TAG, throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void loadAllReviews(int movieId) {
        Disposable disposable = apiService.getAllReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ReviewResponse::getReviewList)
                .subscribe(
                        reviewsListLD::setValue,
                        throwable -> Log.d(TAG, throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
