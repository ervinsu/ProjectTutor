package com.example.scheduletutor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scheduletutor.BuildConfig;
import com.example.scheduletutor.R;
import com.example.scheduletutor.base.HomeClassListPresenter;
import com.example.scheduletutor.di.component.DaggerHomeComponent;
import com.example.scheduletutor.di.component.DaggerListFragmentComponent;
import com.example.scheduletutor.di.component.DaggerLoginComponent;
import com.example.scheduletutor.di.component.DaggerNetworkComponent;
import com.example.scheduletutor.di.component.HomeComponent;
import com.example.scheduletutor.di.component.ListFragmentComponent;
import com.example.scheduletutor.di.component.NetworkComponent;
import com.example.scheduletutor.di.modul.ListFragmentModule;
import com.example.scheduletutor.di.modul.NetworkModule;
import com.example.scheduletutor.model.Class.ClassTutor;
import com.example.scheduletutor.model.Class.ClassTutorAdapter;
import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.ListClassService;
import com.example.scheduletutor.service.ListMyClassService;
import com.example.scheduletutor.service.home.HomeClassListInterface;
import com.example.scheduletutor.ui.HomeActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ListFragment extends Fragment implements HomeClassListInterface {

    public ListFragmentComponent listFragmentComponent;

    private HomeClassListPresenter presenter;
    private ClassTutorAdapter classTutorAdapter;
    private SwipeRefreshLayout srlListKelas;
    @Inject
    ListClassService listClassService;

    @Inject
    ListMyClassService listMyClassService;

    @Inject
    User currUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        RecyclerView rvListClass = view.findViewById(R.id.rvClassList);
        rvListClass.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        classTutorAdapter = new ClassTutorAdapter(getLayoutInflater(),getActivity());
        rvListClass.setAdapter(classTutorAdapter);
        listFragmentComponent = DaggerListFragmentComponent.builder()
                .listFragmentModule(new ListFragmentModule(getActivity()))
                .networkComponent(getNetworkComponent())
                .build();
        listFragmentComponent.Inject(this);
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
        classTutorAdapter.addClassList(classTutors);
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
        if(currUser.getUserRoleID().equals("1")) return listClassService.getClasses();
        else return listMyClassService.getMyClass(objAdd);
    }
}
