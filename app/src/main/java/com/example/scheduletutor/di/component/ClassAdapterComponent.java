package com.example.scheduletutor.di.component;

import com.example.scheduletutor.di.modul.ClassAdapterModule;
import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.model.Class.ClassTutorAdapter;

import dagger.Component;

@CustomScope
@Component(modules = ClassAdapterModule.class, dependencies = NetworkComponent.class)
public interface ClassAdapterComponent {
    void Inject (ClassTutorAdapter injector);
}
