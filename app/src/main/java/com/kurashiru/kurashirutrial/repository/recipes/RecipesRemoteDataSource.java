package com.kurashiru.kurashirutrial.repository.recipes;

import android.support.annotation.NonNull;

import com.kurashiru.kurashirutrial.api.KurashiruClient;
import com.kurashiru.kurashirutrial.api.service.KurashiruService;
import com.kurashiru.kurashirutrial.model.Recipe;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipesRemoteDataSource {
    private KurashiruClient mKurashiruClient;

    @Inject
    public RecipesRemoteDataSource(KurashiruClient kurashiruClient) {
        mKurashiruClient = kurashiruClient;
    }

    public Single<List<Recipe>> findAll() {
        return mKurashiruClient.getRecipes();
    }

}
