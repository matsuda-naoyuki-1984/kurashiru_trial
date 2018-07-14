package com.kurashiru.kurashirutrial.api;

import com.kurashiru.kurashirutrial.api.service.KurashiruService;
import com.kurashiru.kurashirutrial.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class KurashiruClient {
    private KurashiruService mKurashiruService;

    @Inject
    public KurashiruClient(KurashiruService kurashiruService) {
        mKurashiruService = kurashiruService;
    }

    public Single<List<Recipe>> getRecipes() {
        return mKurashiruService.getRecipes();
    }
}
