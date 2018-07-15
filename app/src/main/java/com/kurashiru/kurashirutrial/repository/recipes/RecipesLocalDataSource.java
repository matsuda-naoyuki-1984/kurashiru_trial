package com.kurashiru.kurashirutrial.repository.recipes;

import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.model.RecipeData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipesLocalDataSource {

    @Inject
    public RecipesLocalDataSource(){
    }

    public Single<RecipeData> findAll() {
        //TODO
        return null;
    }

    public void updateAllAsync(List<Recipe> recipes){
        //TODO
    }
}
