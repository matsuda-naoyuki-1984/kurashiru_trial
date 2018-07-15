package com.kurashiru.kurashirutrial.repository.recipes;

import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.model.RecipeData;

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
    RecipesRepository(RecipesRemoteDataSource recipesRemoteDataSource, RecipesLocalDataSource recipesLocalDataSource){
        mRecipesRemoteDataSource = recipesRemoteDataSource;
        mRecipesLocalDataSource = recipesLocalDataSource;
        this.isDirty = true;
    }

    public Single<RecipeData> findAll() {
        if (cachedRecipes != null && !cachedRecipes.isEmpty() && !isDirty) {
            return Single.create(emitter -> {
                RecipeData recipeData = new RecipeData();
                recipeData.setData(new ArrayList<>(cachedRecipes.values()));
                emitter.onSuccess(recipeData);
            });
        }

        if (isDirty) {
            return findAllFromRemote();
        } else {
            return findAllFromLocal();
        }
    }

    private Single<RecipeData> findAllFromRemote() {
        return mRecipesRemoteDataSource.findAll()
                .doOnSuccess(recipeData -> {
                    refreshCache(recipeData.getData());
                    mRecipesLocalDataSource.updateAllAsync(recipeData.getData());
                });
    }

    private Single<RecipeData> findAllFromLocal() {
        return mRecipesLocalDataSource.findAll().flatMap(recipeData -> {
            if (recipeData == null || recipeData.getData().isEmpty()) {
                return findAllFromRemote();
            } else {
                refreshCache(recipeData.getData());
                return Single.create(emitter -> emitter.onSuccess(recipeData));
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
