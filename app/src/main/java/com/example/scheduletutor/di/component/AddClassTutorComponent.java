package com.example.scheduletutor.di.component;

import com.example.scheduletutor.di.modul.AddClassTutorModule;
import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.ui.AddClassActivity;

import dagger.Component;

@CustomScope
@Component(modules = AddClassTutorModule.class, dependencies = NetworkComponent.class)
public interface AddClassTutorComponent {
    void Inject (AddClassActivity activity);
}
