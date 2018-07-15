package com.kurashiru.kurashirutrial.repository.favorite;

import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.model.RecipeData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class FavoritesRepository {
    private FavoritesLocalDataSource mFavoritesLocalDataSource;

    private Map<String, Recipe> mCachedFavoriteRecipes;

    @Inject
    FavoritesRepository(FavoritesLocalDataSource favoritesLocalDataSource) {
        mFavoritesLocalDataSource = favoritesLocalDataSource;
    }

    public Single<RecipeData> findAll() {
        if (mCachedFavoriteRecipes != null && !mCachedFavoriteRecipes.isEmpty()) {
            return Single.create(emitter -> emitter.onSuccess(createFromCachedData()));
        }

        return findAllFromLocal();
    }

    private Single<RecipeData> findAllFromLocal() {
        return mFavoritesLocalDataSource.findAll().flatMap(recipeData -> {
            refreshCache(recipeData);
            return Single.create(emitter -> emitter.onSuccess(recipeData));
        });
    }

    private void refreshCache(RecipeData recipeData) {
        if (mCachedFavoriteRecipes == null) {
            mCachedFavoriteRecipes = new LinkedHashMap<>();
        }
        mCachedFavoriteRecipes.clear();

        if (recipeData == null || recipeData.getData() == null) {
            return;
        }

        for (Recipe Recipe : recipeData.getData()) {
            mCachedFavoriteRecipes.put(Recipe.getId(), Recipe);
        }
    }

    public Single<Boolean> saveFavorite(Recipe recipe) {
        return Single.create(e -> {
            Disposable disposable = findAll().subscribeOn(Schedulers.io()).subscribe(recipeData -> {
                save(recipe);
            }, throwable -> {
                refreshCache(null);
                save(recipe);
            });
            e.onSuccess(true);
        });
    }

    public Single<Boolean> removeFavorite(Recipe recipe) {
        return Single.create(e -> {
            remove(recipe);
            e.onSuccess(true);
        });
    }

    private void save(Recipe recipe) {
        if (!mCachedFavoriteRecipes.containsKey(recipe.getId())) {
            mCachedFavoriteRecipes.put(recipe.getId(), recipe);
        }
        mFavoritesLocalDataSource.saveRecipe(createFromCachedData());
    }

    private void remove(Recipe recipe) {
        if (mCachedFavoriteRecipes.containsKey(recipe.getId())) {
            mCachedFavoriteRecipes.remove(recipe.getId());
        }
        mFavoritesLocalDataSource.saveRecipe(createFromCachedData());
    }

    private RecipeData createFromCachedData() {
        RecipeData recipeData = new RecipeData();
        recipeData.setData(new ArrayList<>(mCachedFavoriteRecipes.values()));
        return recipeData;
    }
}
