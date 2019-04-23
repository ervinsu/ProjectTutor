package com.example.scheduletutor.di.component;

import com.example.scheduletutor.di.modul.HomeModule;
import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.ui.HomeActivity;

import dagger.Component;

@CustomScope
@Component (modules = HomeModule.class)
public interface HomeComponent {
    void Inject(HomeActivity homeActivity);
}
