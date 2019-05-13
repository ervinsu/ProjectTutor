package com.example.scheduletutor.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.scheduletutor.BuildConfig;
import com.example.scheduletutor.R;
import com.example.scheduletutor.di.component.AddClassTutorComponent;
import com.example.scheduletutor.di.component.DaggerAddClassTutorComponent;
import com.example.scheduletutor.di.component.DaggerNetworkComponent;
import com.example.scheduletutor.di.component.NetworkComponent;
import com.example.scheduletutor.di.modul.AddClassTutorModule;
import com.example.scheduletutor.di.modul.NetworkModule;
import com.example.scheduletutor.model.ResponseRetrofit;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.AddClassService;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AddClassActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnKirim;
    private EditText etShift,etDateTime,etAddress;
    private AddClassTutorComponent adapterComponent;
    final Calendar myCalendar = Calendar.getInstance();
    private String dateTime ="";
    private CompositeDisposable mCompositeDisposeable;
    @Inject
    AddClassService addClassService;

    @Inject
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final String[] items = new String[]{"Kalkulus 1", "Kalkulus 2", "Kalkulus 3","Fisika 1","Fisika 2","Fisika 3",};
        spinner = findViewById(R.id.inNama);
        btnKirim = findViewById(R.id.Kirim);
        etShift = findViewById(R.id.etShift);
        etDateTime = findViewById(R.id.etDateTime);
        etAddress = findViewById(R.id.etAddress);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);


        etDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime;
                mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.DAY_OF_YEAR);
                int month = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_WEEK);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddClassActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int i, final int i1,final int i2) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(AddClassActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                etDateTime.setText(i+"-"+i1+1+"-"+i2+ " " + selectedHour + ":" + selectedMinute+":00");
                                dateTime =i+"-"+i1+1+"-"+i2+ " " + selectedHour + ":" + selectedMinute+":00";
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                },year,month,day);
                mDatePicker.setTitle("Select dateTime");
                mDatePicker.show();
            }
        });


        adapterComponent = DaggerAddClassTutorComponent.builder()
                .addClassTutorModule(new AddClassTutorModule(this))
                .networkComponent(getNetworkComponent())
                .build();
        adapterComponent.Inject(this);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject objAdd = new JsonObject();
                try {
                    JsonArray arrData = new JsonArray();
                    JsonObject objDetail = new JsonObject();
                    objDetail.addProperty("lessonID", spinner.getSelectedItemPosition()+1);
                    objDetail.addProperty("lessonDate", etDateTime.getText().toString());
                    objDetail.addProperty("lessonShift", etShift.getText().toString());
                    objDetail.addProperty("lessonLocation", etAddress.getText().toString());
                    objDetail.addProperty("tutorID", userLocalStore.getLoggedInUser().getUserID());
                    arrData.add(objDetail);
                    objAdd.add("data", arrData);
                    Log.d("datetime",objAdd+"");
                } catch (JsonIOException e1) {
                    e1.printStackTrace();
                }
                subscribe(addClassService.addClass(objAdd));
            }
        });

    }

    private void subscribe(Observable<ResponseRetrofit> response) {
        mCompositeDisposeable = new CompositeDisposable();

        mCompositeDisposeable.add(response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseRetrofit>() {
                    @Override
                    public void onNext(ResponseRetrofit response) {
                        Log.d("BIJIQ",response.getResponse());
                        Toast.makeText(AddClassActivity.this, "Berhasil tambah kelas", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddClassActivity.this,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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

    private NetworkComponent getNetworkComponent() {
        return  DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(BuildConfig.API_KEY))
                .build();
    }
}
