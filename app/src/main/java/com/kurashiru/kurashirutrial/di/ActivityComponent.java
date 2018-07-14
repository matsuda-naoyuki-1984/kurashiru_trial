package com.kurashiru.kurashirutrial.di;


import com.kurashiru.kurashirutrial.di.scope.ActivityScope;
import com.kurashiru.kurashirutrial.view.activity.BaseActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent{

    void inject(BaseActivity activity);

    FragmentComponent plus(FragmentModule module);
}
