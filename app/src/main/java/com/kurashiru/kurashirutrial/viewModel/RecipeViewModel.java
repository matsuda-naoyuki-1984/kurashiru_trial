package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.kurashiru.kurashirutrial.model.Recipe;

import javax.inject.Inject;

public class RecipeViewModel extends BaseObservable implements ViewModel {
    private long mId;

    private String mTitle;

    private String mImageUrl;

    private boolean isFavorite;

    @Inject
    public RecipeViewModel(Recipe recipe){
        //TODO
//        mId = recipe.getId();
        mTitle = recipe.getAttributes().getTitle();
        mImageUrl = recipe.getAttributes().getThumbnailSquareUrl();
    }

    public long getId(){
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @BindingAdapter("recipeImageUrl")
    public static void setRecipeImageUrl(ImageView imageView, @Nullable String imageUrl) {
        //TODO
    }
}
