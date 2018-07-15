package com.kurashiru.kurashirutrial.repository.recipes;

import android.content.SharedPreferences;

import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.model.RecipeData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipesLocalDataSource {

    private SharedPreferences mSharedPreferences;

    @Inject
    public RecipesLocalDataSource(SharedPreferences sharedPreferences){
        mSharedPreferences = sharedPreferences;
    }

    public Single<RecipeData> findAll() {
        //TODO
        return null;
    }

    public void updateAllAsync(List<Recipe> recipes){
        //TODO
    }
}
