package com.kurashiru.kurashirutrial.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.annimon.stream.Stream;
import com.kurashiru.kurashirutrial.BR;
import com.kurashiru.kurashirutrial.di.scope.FragmentScope;
import com.kurashiru.kurashirutrial.model.RecipeData;
import com.kurashiru.kurashirutrial.repository.favorite.FavoritesRepository;
import com.kurashiru.kurashirutrial.viewModel.event.FavoriteUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class FavoriteListViewModel extends BaseObservable implements ViewModel {

    private int mLoadingVisibility;

    private FavoritesRepository mFavoritesRepository;

    private ObservableList<RecipeViewModel> mRecipeViewModels;

    private CompositeDisposable mCompositeDisposable;

    private final Bus mEventBus;

    @Inject
    public FavoriteListViewModel(FavoritesRepository favoritesRepository,
                                 CompositeDisposable compositeDisposable,
                                 Bus bus) {
        mRecipeViewModels = new ObservableArrayList<>();
        mFavoritesRepository = favoritesRepository;
        mCompositeDisposable = compositeDisposable;
        mEventBus = bus;
        mEventBus.register(this);
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
        if (mRecipeViewModels.isEmpty()) {
            loadRecipes();
        }
    }

    private void loadRecipes() {
        setLoadingVisibility(View.VISIBLE);

        Disposable disposable = mFavoritesRepository
                .findAll()
                .map(this::convertToViewModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::renderRecipeViews,
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    private List<RecipeViewModel> convertToViewModel(RecipeData recipeData) {
        return Stream.of(recipeData.getData()).map(recipe ->
                new RecipeViewModel(recipe, mFavoritesRepository,
                        mCompositeDisposable)).toList();
    }

    private void renderRecipeViews(List<RecipeViewModel> recipeViewModels) {
        mRecipeViewModels.clear();
        mRecipeViewModels.addAll(recipeViewModels);
        setLoadingVisibility(View.GONE);
    }

    @Subscribe
    public void onFavoriteUpdated(FavoriteUpdatedEvent event) {
        loadRecipes();
    }

    @Override
    public void destroy() {
        mEventBus.unregister(this);
    }
}
