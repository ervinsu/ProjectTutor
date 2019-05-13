package com.example.scheduletutor.model.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scheduletutor.BuildConfig;
import com.example.scheduletutor.R;
import com.example.scheduletutor.di.component.ClassAdapterComponent;
import com.example.scheduletutor.di.component.DaggerClassAdapterComponent;
import com.example.scheduletutor.di.component.DaggerNetworkComponent;
import com.example.scheduletutor.di.component.NetworkComponent;
import com.example.scheduletutor.di.modul.ClassAdapterModule;
import com.example.scheduletutor.di.modul.NetworkModule;
import com.example.scheduletutor.model.ResponseRetrofit;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.AdapterTutorService;
import com.example.scheduletutor.service.ListClassService;
import org.json.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ClassTutorAdapter extends RecyclerView.Adapter<ClassTutorAdapter.ViewHolderClass> {

    public ClassAdapterComponent adapterComponent;

    private final LayoutInflater mInflater;
    private List<ClassTutor> classTutorList = new ArrayList<>();
    private Context context;
    private User currUser;
    private CompositeDisposable mCompositeDisposeable;
    @Inject
    AdapterTutorService.JoinClassService joinClassService;
    @Inject
    AdapterTutorService.DeleteClassService deleteClassService;

    public ClassTutorAdapter(LayoutInflater mInflater, Context context) {
        this.mInflater = mInflater;
        this.context = context;
        currUser = new UserLocalStore(context).getLoggedInUser();
        adapterComponent = DaggerClassAdapterComponent.builder()
                .classAdapterModule(new ClassAdapterModule())
                .networkComponent(getNetworkComponent())
                .build();
        adapterComponent.Inject(this);
    }
    private NetworkComponent getNetworkComponent() {
        return  DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(BuildConfig.API_KEY))
                .build();
    }

    public void addClassList(List<ClassTutor> classTutors){
        this.classTutorList = new ArrayList<>();
        this.classTutorList.addAll(classTutors);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(currUser.getUserRoleID().equals("1")) return new ViewHolderClass(mInflater.inflate(R.layout.cv_list_class,viewGroup,false));
        else  return new ViewHolderClass(mInflater.inflate(R.layout.cv_list_myclass,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClass holder, int i) {
        final ClassTutor classTutor = classTutorList.get(i);
        if(currUser.getUserRoleID().equals("1")) {
            Glide.with(holder.itemView.getContext()).load(classTutor.getTutorPhoto()).into(holder.ivPhotoTutor);
            holder.tvTutorName.setText(classTutor.getTutorName());
            holder.btnDaftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.itemView.getContext(), "Daftar Kelas " + classTutor.getClassName() + " diproses", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JsonObject objAdd = new JsonObject();
                    try {
                        JsonArray arrData = new JsonArray();
                        JsonObject objDetail = new JsonObject();
                        objDetail.addProperty("classID", classTutor.getClassID());
                        arrData.add(objDetail);
                        objAdd.add("data", arrData);
                    } catch (JsonIOException e1) {
                        e1.printStackTrace();
                    }
                    Log.d("objadd",objAdd+"");
                    subscribe(deleteClassService.deleteClass(objAdd));
                    classTutorList.remove(classTutor);
                    notifyDataSetChanged();
                }
            });
        }
        holder.tvClassName.setText(classTutor.getClassName());
        holder.tvClassTime.setText(classTutor.getClassTime());
        holder.tvClassLocation.setText(classTutor.getClassLocation());
        holder.tvClassShift.setText(classTutor.getClassShift() + " Shift");

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
                        Log.d("BIJIQ","lelele");
                    }
                }));
    }

    @Override
    public int getItemCount() {
        return classTutorList.size();
    }

    class ViewHolderClass extends RecyclerView.ViewHolder{

        private ImageView ivPhotoTutor;
        private TextView tvClassName,tvTutorName, tvClassLocation,tvClassTime, tvClassShift;
        private Button btnDaftar,btnHapus;
        ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            if(currUser.getUserRoleID().equals("1")) {
                ivPhotoTutor = itemView.findViewById(R.id.iv_photo_tutor);
                tvTutorName = itemView.findViewById(R.id.tvTutorName);
                btnDaftar = itemView.findViewById(R.id.btnDaftar);
            }else{
                btnHapus = itemView.findViewById(R.id.btnHapus);
            }
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvClassLocation = itemView.findViewById(R.id.tvClassLocation);
            tvClassTime = itemView.findViewById(R.id.tvClassTime);
            tvClassShift = itemView.findViewById(R.id.tvClassShift);
        }
    }
}
