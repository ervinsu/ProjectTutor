package com.example.scheduletutor.di.component;

import com.example.scheduletutor.di.modul.ListFragmentModule;
import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.ui.fragment.ListFragment;

import dagger.Component;

@CustomScope
@Component(modules = ListFragmentModule.class, dependencies = NetworkComponent.class)
public interface ListFragmentComponent {
    void Inject (ListFragment listFragment);
}
