package com.example.scheduletutor.base;

import android.util.Log;

import com.example.scheduletutor.model.User.UserResponse;
import com.example.scheduletutor.service.Login.LoginViewInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements Presenter {
    private CompositeDisposable mCompositeDisposeable;
    private LoginViewInterface mViewInterface;

    public LoginPresenter(LoginViewInterface mViewInterface) {
        this.mViewInterface = mViewInterface;
    }

//    @Override
//    public void onError(Throwable e) {
//        mViewInterface.error(e.getMessage());
//    }
//
//    @Override
//    public void onComplete() {
//    }
//
//    @Override
//    public void onSubscribe(Disposable d) {
////        mCompositeDisposeable.add(d);
//    }
//
//    @Override
//    public void onNext(UserResponse userResponse) {
//        mViewInterface.addUser(userResponse.getUser());
//    }

    private void subscribe(Observable<UserResponse> users) {
        mCompositeDisposeable = new CompositeDisposable();

        mCompositeDisposeable.add(users
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserResponse>() {
                    @Override
                    public void onNext(UserResponse users) {
                        Log.d("BIJIQ",users.getUser().size()+"");
                        mViewInterface.complete(users.getUser());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewInterface.error(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("BIJIQ","lelele");
                    }
                }));
    }

    @Override
    public void onCreate() {
        subscribe(mViewInterface.getUser());
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        configureSubscription();
    }

    @Override
    public void onDestroy() {
        unSubscribeAll();
    }

    protected void unSubscribeAll(){
        if(mCompositeDisposeable != null){
            mCompositeDisposeable.dispose();
            mCompositeDisposeable.clear();
        }
    }

    private CompositeDisposable configureSubscription() {
        if(mCompositeDisposeable == null || mCompositeDisposeable.isDisposed()){
            mCompositeDisposeable = new CompositeDisposable();
        }
        return mCompositeDisposeable;
    }
}
