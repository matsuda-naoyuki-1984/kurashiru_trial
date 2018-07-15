package com.kurashiru.kurashirutrial.repository.favorite;

import com.google.gson.GsonBuilder;
import com.kurashiru.kurashirutrial.model.RecipeData;
import com.kurashiru.kurashirutrial.pref.DefaultPreference;

import javax.inject.Inject;

import io.reactivex.Single;

public class FavoritesLocalDataSource {

    @Inject
    DefaultPreference mDefaultPreference;

    @Inject
    public FavoritesLocalDataSource() {
    }

    public Single<RecipeData> findAll() {
        return Single.create(e -> {
            if("".equals(mDefaultPreference.getFavorites())) {
                //FIXME
                e.onError(new Throwable());
                return;
            }
            RecipeData recipeData = new GsonBuilder()
                    .create()
                    .fromJson(mDefaultPreference.getFavorites(), RecipeData.class);
            e.onSuccess(recipeData);
        });
    }

    public void saveRecipe(RecipeData recipeData) {
        mDefaultPreference.setFavorites(new GsonBuilder()
                .create().toJson(recipeData));
    }

    public void removeRecipe(RecipeData recipeData) {
        mDefaultPreference.setFavorites(new GsonBuilder()
                .create().toJson(recipeData));
    }
}
