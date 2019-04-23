package com.example.scheduletutor.di.component;


import com.example.scheduletutor.di.modul.LoginModule;
import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.ui.LoginActivity;

import dagger.Component;

@CustomScope
@Component(modules = LoginModule.class, dependencies = NetworkComponent.class)
public interface LoginComponent {
    void Inject(LoginActivity loginActivity);
}
