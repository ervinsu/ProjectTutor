package com.example.scheduletutor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scheduletutor.BuildConfig;
import com.example.scheduletutor.R;
import com.example.scheduletutor.base.HomeClassListPresenter;
import com.example.scheduletutor.di.component.ActiveClassComponent;
import com.example.scheduletutor.di.component.DaggerActiveClassComponent;
import com.example.scheduletutor.di.component.DaggerNetworkComponent;
import com.example.scheduletutor.di.component.NetworkComponent;
import com.example.scheduletutor.di.modul.ActiveClassModule;
import com.example.scheduletutor.di.modul.NetworkModule;
import com.example.scheduletutor.model.Class.ActiveClassAdapter;
import com.example.scheduletutor.model.Class.ClassTutor;
import com.example.scheduletutor.model.Class.ClassTutorAdapter;
import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.example.scheduletutor.model.ResponseRetrofit;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.service.ListActiveClassService;
import com.example.scheduletutor.service.home.HomeClassListInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ActiveClassFragment extends Fragment  implements HomeClassListInterface, ActiveClassAdapter.activeClassClickedListener {

    private SwipeRefreshLayout srlListKelas;
    public ActiveClassComponent listActiveClassComponent;

    private ActiveClassAdapter activeClassAdapter;
    private CompositeDisposable mCompositeDisposeable;
    private HomeClassListPresenter presenter;

    @Inject
    ListActiveClassService listActiveClassService;

    @Inject
    User currUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_class,container,false);
        RecyclerView rvListClass = view.findViewById(R.id.rvActiveClassList);
        rvListClass.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        activeClassAdapter = new ActiveClassAdapter(this,getLayoutInflater(),getActivity());
        rvListClass.setAdapter(activeClassAdapter);
        listActiveClassComponent = DaggerActiveClassComponent.builder()
                .activeClassModule(new ActiveClassModule(getActivity()))
                .networkComponent(getNetworkComponent())
                .build();
        listActiveClassComponent.Inject(this);
        presenter = new HomeClassListPresenter(this);
        presenter.onCreate();
        srlListKelas = view.findViewById(R.id.srlListKelas);
        srlListKelas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onCreate();
            }
        });
        return view;
    }


    private NetworkComponent getNetworkComponent() {
        return  DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(BuildConfig.API_KEY))
                .build();
    }

    @Override
    public void complete(List<ClassTutor> classTutors) {
        if(srlListKelas.isRefreshing()){
            srlListKelas.setRefreshing(false);
        }
        activeClassAdapter.addClassList(classTutors);
    }

    @Override
    public void error(String message) {
        if(srlListKelas.isRefreshing()){
            srlListKelas.setRefreshing(false);
        }
        Toast.makeText(getActivity(), "Sedang terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Observable<ClassTutorResponse> getClasses() {
        JsonObject objAdd = new JsonObject();
        try {
            JsonArray arrData = new JsonArray();
            JsonObject objDetail = new JsonObject();
            objDetail.addProperty("userID", currUser.getUserID());
            arrData.add(objDetail);
            objAdd.add("data", arrData);
        } catch (JsonIOException e1) {
            e1.printStackTrace();
        }
        return listActiveClassService.getActiveClasses(objAdd);
    }

    @Override
    public void onClick(int position,ClassTutor classTutor) {
        JsonObject objAdd = new JsonObject();
        try {
            JsonArray arrData = new JsonArray();
            JsonObject objDetail = new JsonObject();
            objDetail.addProperty("classID", classTutor.getClassID());
            objDetail.addProperty("userID", currUser.getUserID());
            arrData.add(objDetail);
            objAdd.add("data", arrData);
        } catch (JsonIOException e1) {
            e1.printStackTrace();
        }
        Log.d("objadd",objAdd+"");
        subscribe(listActiveClassService.deleteActiveClass(objAdd));
        List<ClassTutor> list = new ArrayList<>(activeClassAdapter.getClassTutorList());
        list.remove(classTutor);
        activeClassAdapter.addClassList(list);
        activeClassAdapter.notifyDataSetChanged();
    }

    private void subscribe(Observable<ResponseRetrofit> response) {
        mCompositeDisposeable = new CompositeDisposable();

        mCompositeDisposeable.add(response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseRetrofit>() {
                    @Override
                    public void onNext(ResponseRetrofit response) {
                        Log.d("BIJIQ",response.getResponse()+"lala");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("BIJIQ",e+"");
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getActivity(), "Class Berhasil Cancel", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

}
