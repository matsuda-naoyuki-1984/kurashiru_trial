package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.kurashiru.kurashirutrial.R;
import com.kurashiru.kurashirutrial.model.Recipe;
import com.squareup.picasso.Picasso;

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
        setPhotoImageUrl(imageView, imageUrl);
    }

    private static void setPhotoImageUrl(ImageView imageView, @Nullable String imageUrl) {
        setImageUrl(imageView, imageUrl, R.color.grey200);
    }

    private static void setImageUrl(ImageView imageView, @Nullable String imageUrl,
                                    @DrawableRes int placeholderResId) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageDrawable(
                    ContextCompat.getDrawable(imageView.getContext(), placeholderResId));
        } else {
            Picasso.with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(placeholderResId)
                    .error(placeholderResId)
                    .into(imageView);
        }
    }
}
