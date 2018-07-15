package com.kurashiru.kurashirutrial.repository.recipes;

import com.kurashiru.kurashirutrial.api.KurashiruClient;
import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.model.RecipeData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipesRemoteDataSource {
    private KurashiruClient mKurashiruClient;

    @Inject
    public RecipesRemoteDataSource(KurashiruClient kurashiruClient) {
        mKurashiruClient = kurashiruClient;
    }

    public Single<RecipeData> findAll() {
        return mKurashiruClient.getRecipes();
    }

}
