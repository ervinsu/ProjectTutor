package com.example.scheduletutor.base;

import android.util.Log;

import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.example.scheduletutor.service.home.HomeClassListInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class HomeClassListPresenter implements Presenter, Observer<ClassTutorResponse> {
    HomeClassListInterface homeClassListInterface;
    private CompositeDisposable mCompositeDisposeable;



    public HomeClassListPresenter(HomeClassListInterface homeClassListInterface) {
        this.homeClassListInterface = homeClassListInterface;
    }

    @Override
    public void onCreate() {
        subscribe(homeClassListInterface.getClasses());
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }


    private void subscribe(Observable<ClassTutorResponse> classes) {
        mCompositeDisposeable = new CompositeDisposable();
        mCompositeDisposeable.add(classes
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ClassTutorResponse>() {
                    @Override
                    public void onNext(ClassTutorResponse classes) {
                        Log.d("bijiqclass",classes.getClasses().size()+"");
                        homeClassListInterface.complete(classes.getClasses());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("bijiqclass",e+"");
                        homeClassListInterface.error(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("bijiqclass","lelele");
                    }
                }));
    }

    @Override
    public void onNext(ClassTutorResponse classTutorResponse) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
