package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.annimon.stream.Stream;
import com.kurashiru.kurashirutrial.BR;
import com.kurashiru.kurashirutrial.model.RecipeData;
import com.kurashiru.kurashirutrial.repository.favorite.FavoritesRepository;
import com.kurashiru.kurashirutrial.repository.recipes.RecipesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteListViewModel extends BaseObservable implements ViewModel {

    private int mLoadingVisibility;

    private FavoritesRepository mFavoritesRepository;

    private ObservableList<RecipeViewModel> mRecipeViewModels;

    @Inject
    public FavoriteListViewModel(FavoritesRepository favoritesRepository) {
        mRecipeViewModels = new ObservableArrayList<>();
        mFavoritesRepository = favoritesRepository;
    }

    @Bindable
    public int getLoadingVisibility() {
        return mLoadingVisibility;
    }

    private void setLoadingVisibility(int visibility) {
        this.mLoadingVisibility = visibility;
        notifyPropertyChanged(BR.loadingVisibility);
    }

    public ObservableList<RecipeViewModel> getRecipeViewModels() {
        return mRecipeViewModels;
    }

    public void start(){
        loadRecipes();
    }

    private void loadRecipes() {
        setLoadingVisibility(View.VISIBLE);

        //TODO
        Disposable disposable = mFavoritesRepository
                .findAll()
                .map(this::convertToViewModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::renderRecipeViews,
                        throwable -> {
                        });
    }

    private List<RecipeViewModel> convertToViewModel(RecipeData recipeData) {
        return Stream.of(recipeData.getData()).map(recipe -> new RecipeViewModel(recipe)).toList();
    }

    private void renderRecipeViews(List<RecipeViewModel> recipeViewModels) {
        mRecipeViewModels.clear();
        mRecipeViewModels.addAll(recipeViewModels);
        setLoadingVisibility(View.GONE);
    }
}