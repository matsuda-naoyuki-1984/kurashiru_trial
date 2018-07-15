package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.kurashiru.kurashirutrial.R;
import com.kurashiru.kurashirutrial.BR;
import com.kurashiru.kurashirutrial.di.scope.FragmentScope;
import com.kurashiru.kurashirutrial.model.Attributes;
import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.repository.favorite.FavoritesRepository;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class RecipeViewModel extends BaseObservable implements ViewModel {
    private String mId;

    private String mTitle;

    private String mImageUrl;

    private int mFavorite;

    FavoritesRepository mFavoritesRepository;

    @Inject
    public RecipeViewModel(Recipe recipe, FavoritesRepository favoritesRepository){
        mId = recipe.getId();
        mTitle = recipe.getAttributes().getTitle();
        mImageUrl = recipe.getAttributes().getThumbnailSquareUrl();

        mFavoritesRepository = favoritesRepository;
    }

    public long getId(){
        return 0;//TODO
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    @Bindable
    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        //FIXME
        this.mFavorite = favorite ? 0 : 1;
        notifyPropertyChanged(BR.favorite);
    }

    public Single<Boolean> addToFavorite(){
        return mFavoritesRepository.saveFavorite(convertToViewModel())
                .subscribeOn(Schedulers.io())
                .map(result -> {
                    setFavorite(result);
                    return result;
                });
    }

    public void removeFavorite(){
        //FIXME
        Disposable disposable = mFavoritesRepository.removeFavorite(convertToViewModel())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> setFavorite(false), throwable -> {});
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

    private Recipe convertToViewModel() {
        //FIXME
        Recipe recipe = new Recipe();
        recipe.setId(mId);
        recipe.setType("video");
        Attributes attributes = new Attributes();
        attributes.setTitle(mTitle);
        attributes.setThumbnailSquareUrl(mImageUrl);
        recipe.setAttributes(attributes);
        return recipe;
    }
}


