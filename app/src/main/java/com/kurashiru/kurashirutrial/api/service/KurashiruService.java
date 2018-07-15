package com.kurashiru.kurashirutrial.api.service;

import com.kurashiru.kurashirutrial.model.RecipeData;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface KurashiruService {
    @GET("videos_sample.json")
    Single<RecipeData> getRecipes();
}
