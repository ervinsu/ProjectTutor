package com.example.scheduletutor.di.component;

import com.example.scheduletutor.di.modul.ActiveClassModule;
import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.ui.fragment.ActiveClassFragment;

import dagger.Component;

@CustomScope
@Component(modules = ActiveClassModule.class, dependencies = NetworkComponent.class)
public interface ActiveClassComponent {
    void Inject (ActiveClassFragment activity);
}
