package com.example.scheduletutor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ListFragment extends Fragment implements HomeClassListInterface {

    public ListFragmentComponent listFragmentComponent;

    private HomeClassListPresenter presenter;
    private ClassTutorAdapter classTutorAdapter;
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
        classTutorAdapter = new ClassTutorAdapter(getLayoutInflater());
        rvListClass.setAdapter(classTutorAdapter);
        listFragmentComponent = DaggerListFragmentComponent.builder()
                .listFragmentModule(new ListFragmentModule(getActivity()))
                .networkComponent(getNetworkComponent())
                .build();
        listFragmentComponent.Inject(this);
        presenter = new HomeClassListPresenter(this);
        presenter.onCreate();
        return view;
    }

    private NetworkComponent getNetworkComponent() {
        return  DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(BuildConfig.API_KEY))
                .build();
    }

    @Override
    public void complete(List<ClassTutor> classTutors) {
       classTutorAdapter.addClassList(classTutors);
    }

    @Override
    public void error(String message) {

    }

    @Override
    public Observable<ClassTutorResponse> getClasses() {
        if(currUser.getUserRoleID().equals("1")) return listClassService.getClasses();
        else return listMyClassService.getMyClass();
    }
}
