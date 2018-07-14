package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableList;
import android.view.View;

import com.kurashiru.kurashirutrial.BR;

import java.util.List;

import javax.inject.Inject;

public class RecipeListViewModel extends BaseObservable implements ViewModel {

    private int mLoadingVisibility;

    private ObservableList<RecipeViewModel> mRecipeViewModels;

    @Inject
    public RecipeListViewModel(){

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

    private void loadRecipes(){
        setLoadingVisibility(View.VISIBLE);
        //TODO
    }

    private void renderRecipeViews(List<RecipeViewModel> recipeViewModels) {
        recipeViewModels.clear();
        recipeViewModels.addAll(recipeViewModels);
        setLoadingVisibility(View.GONE);
    }
}
