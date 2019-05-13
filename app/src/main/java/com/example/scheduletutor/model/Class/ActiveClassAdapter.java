package com.example.scheduletutor.model.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.scheduletutor.R;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ActiveClassAdapter extends RecyclerView.Adapter<ActiveClassAdapter.ViewHolderClass> {


    private final LayoutInflater mInflater;
    private List<ClassTutor> classTutorList = new ArrayList<>();
    private Context context;
    private User currUser;
    private CompositeDisposable mCompositeDisposeable;
    private activeClassClickedListener mListener;

    public ActiveClassAdapter(activeClassClickedListener listener,LayoutInflater mInflater, Context context) {
        mListener = listener;
        this.mInflater = mInflater;
        this.context = context;
        currUser = new UserLocalStore(context).getLoggedInUser();
    }


    public void addClassList(List<ClassTutor> classTutors){
        this.classTutorList = new ArrayList<>();
        this.classTutorList.addAll(classTutors);
        notifyDataSetChanged();
    }

    public List<ClassTutor> getClassTutorList() {
        return classTutorList;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         return new ViewHolderClass(mInflater.inflate(R.layout.cv_list_class_schedule,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClass holder, int i) {
        final ClassTutor classTutor = classTutorList.get(i);
        if(currUser.getUserRoleID().equals("1")) holder.tvUsername.setText("Tutor : "+classTutor.getTutorName());
        else holder.tvUsername.setText("Siswa : "+classTutor.getTutorName());
        holder.tvClassLocation.setText(classTutor.getClassLocation());
        holder.tvClassName.setText(classTutor.getClassName());
        holder.tvClassShift.setText(classTutor.getClassShift()+" Shift");
        holder.tvClassTime.setText(classTutor.getClassTime());

    }

//    private void subscribe(Observable<ResponseRetrofit> response) {
//        mCompositeDisposeable = new CompositeDisposable();
//
//        mCompositeDisposeable.add(response
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<ResponseRetrofit>() {
//                    @Override
//                    public void onNext(ResponseRetrofit response) {
//                        Log.d("BIJIQ",response.getResponse()+"lala");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("BIJIQ",e+"");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("BIJIQ","lelele");
//                    }
//                }));
//    }

    @Override
    public int getItemCount() {
        return classTutorList.size();
    }

    class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvClassName,tvUsername, tvClassLocation,tvClassTime, tvClassShift;
        private Button btnCancel;
        ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvClassLocation = itemView.findViewById(R.id.tvClassLocation);
            tvClassTime = itemView.findViewById(R.id.tvClassTime);
            tvClassShift = itemView.findViewById(R.id.tvClassShift);
            btnCancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(getAdapterPosition(),classTutorList.get(getAdapterPosition()));
        }
    }

    public interface activeClassClickedListener{
        void onClick(int position,ClassTutor classTutor);
    }
}
