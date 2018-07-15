package com.kurashiru.kurashirutrial.di;

import com.kurashiru.kurashirutrial.di.scope.FragmentScope;
import com.kurashiru.kurashirutrial.view.fragment.FavoriteListFragment;
import com.kurashiru.kurashirutrial.view.fragment.RecipeListFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(RecipeListFragment fragment);

    void inject(FavoriteListFragment fragment);
}
