package com.example.scheduletutor.model.Class;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scheduletutor.R;

import java.util.ArrayList;
import java.util.List;

public class ClassTutorAdapter extends RecyclerView.Adapter<ClassTutorAdapter.ViewHolderClass> {

    private final LayoutInflater mInflater;
    private List<ClassTutor> classTutorList = new ArrayList<>();

    public ClassTutorAdapter(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public void addClassList(List<ClassTutor> classTutors){
        this.classTutorList.addAll(classTutors);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderClass(mInflater.inflate(R.layout.cv_list_class,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClass holder, int i) {
        final ClassTutor classTutor = classTutorList.get(i);
        holder.tvClassName.setText(classTutor.getClassName());
        holder.tvClassLocation.setText(classTutor.getClassLocation());
        holder.tvClassShift.setText(classTutor.getClassShift()+" Shift");
        Glide.with(holder.itemView.getContext()).load(classTutor.getTutorPhoto()).into(holder.ivPhotoTutor);
        holder.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Daftar Kelas "+classTutor.getClassName()+" diproses", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return classTutorList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        private ImageView ivPhotoTutor;
        private TextView tvClassName, tvClassLocation,tvClassTime, tvClassShift;
        private Button btnDaftar;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            ivPhotoTutor = itemView.findViewById(R.id.iv_photo_tutor);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvClassLocation = itemView.findViewById(R.id.tvClassLocation);
            tvClassTime = itemView.findViewById(R.id.tvClassTime);
            tvClassShift = itemView.findViewById(R.id.tvClassShift);
            btnDaftar =  itemView.findViewById(R.id.btnDaftar);
        }
    }
}
