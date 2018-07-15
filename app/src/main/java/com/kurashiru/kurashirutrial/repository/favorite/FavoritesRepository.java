package com.kurashiru.kurashirutrial.repository.favorite;

import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.model.RecipeData;
import com.kurashiru.kurashirutrial.repository.recipes.RecipesLocalDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class FavoritesRepository {
    private RecipesLocalDataSource mRecipesLocalDataSource;

    private Map<String, Recipe> cachedRecipes;

    @Inject
    FavoritesRepository(RecipesLocalDataSource recipesLocalDataSource) {
        mRecipesLocalDataSource = recipesLocalDataSource;
    }

    public Single<RecipeData> findAll() {
        if (cachedRecipes != null && !cachedRecipes.isEmpty()) {
            return Single.create(emitter -> {
                RecipeData recipeData = new RecipeData();
                recipeData.setData(new ArrayList<>(cachedRecipes.values()));
                emitter.onSuccess(recipeData);
            });
        }

        return findAllFromLocal();
    }

    private Single<RecipeData> findAllFromLocal() {
        return mRecipesLocalDataSource.findAll().flatMap(recipeData -> {
            if (recipeData == null || recipeData.getData().isEmpty()) {
                //TODO
                return null;
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
    }

    public void saveFavorite(Recipe recipe) {
        //TODO
        if (cachedRecipes.containsKey(recipe.getId())) {

        }
    }
}
