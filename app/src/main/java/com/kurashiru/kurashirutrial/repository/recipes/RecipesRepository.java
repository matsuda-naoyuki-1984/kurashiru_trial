package com.kurashiru.kurashirutrial.repository.recipes;

import com.kurashiru.kurashirutrial.model.Recipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RecipesRepository {

    private RecipesRemoteDataSource mRecipesRemoteDataSource;

    private RecipesLocalDataSource mRecipesLocalDataSource;

    private Map<String, Recipe> cachedRecipes;

    private boolean isDirty;

    @Inject
    public RecipesRepository(RecipesRemoteDataSource recipesRemoteDataSource, RecipesLocalDataSource recipesLocalDataSource){
        mRecipesRemoteDataSource = recipesRemoteDataSource;
        mRecipesLocalDataSource = recipesLocalDataSource;
        this.isDirty = true;
    }


    public Single<List<Recipe>> findAll() {
        if (cachedRecipes != null && !cachedRecipes.isEmpty() && !isDirty) {
            return Single.create(emitter -> {
                emitter.onSuccess(new ArrayList<>(cachedRecipes.values()));
            });
        }

        if (isDirty) {
            return findAllFromRemote();
        } else {
            return findAllFromLocal();
        }
    }


    private Single<List<Recipe>> findAllFromRemote() {
        return mRecipesRemoteDataSource.findAll()
                .doOnSuccess(Recipes -> {
                    refreshCache(Recipes);
                    mRecipesLocalDataSource.updateAllAsync(Recipes);
                });
    }

    private Single<List<Recipe>> findAllFromLocal() {
        return mRecipesLocalDataSource.findAll().flatMap(Recipes -> {
            if (Recipes.isEmpty()) {
                return findAllFromRemote();
            } else {
                refreshCache(Recipes);
                return Single.create(emitter -> emitter.onSuccess(Recipes));
            }
        });
    }

    private void refreshCache(List<Recipe> Recipes) {
        if (cachedRecipes == null) {
            cachedRecipes = new LinkedHashMap<>();
        }
        cachedRecipes.clear();
        for (Recipe Recipe : Recipes) {
            cachedRecipes.put(Recipe.getId(), Recipe);
        }
        isDirty = false;
    }
}
