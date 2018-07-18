package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.kurashiru.kurashirutrial.R;
import com.kurashiru.kurashirutrial.BR;
import com.kurashiru.kurashirutrial.di.scope.FragmentScope;
import com.kurashiru.kurashirutrial.model.Attributes;
import com.kurashiru.kurashirutrial.model.Recipe;
import com.kurashiru.kurashirutrial.repository.favorite.FavoritesRepository;

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
        setFavorite(mFavoritesRepository.exists(mId));

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
        this.mFavorite = favorite ? R.drawable.favorite_icon : 0;
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

    private Recipe convertToViewModel() {
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


