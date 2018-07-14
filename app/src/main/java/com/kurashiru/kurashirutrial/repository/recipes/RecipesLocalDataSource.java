package com.kurashiru.kurashirutrial.repository.recipes;

import com.kurashiru.kurashirutrial.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipesLocalDataSource {

    @Inject
    public RecipesLocalDataSource(){
    }

    public Single<List<Recipe>> findAll() {
        //TODO
        return null;
    }

    public void updateAllAsync(List<Recipe> recipes){
        //TODO
    }
}
