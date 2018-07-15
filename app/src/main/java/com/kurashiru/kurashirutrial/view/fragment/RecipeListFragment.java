package com.kurashiru.kurashirutrial.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurashiru.kurashirutrial.R;
import com.kurashiru.kurashirutrial.databinding.FragmentRecipeListBinding;
import com.kurashiru.kurashirutrial.databinding.ViewRecipeBinding;
import com.kurashiru.kurashirutrial.view.customView.ArrayRecyclerAdapter;
import com.kurashiru.kurashirutrial.view.customView.BindingHolder;
import com.kurashiru.kurashirutrial.viewModel.RecipeListViewModel;
import com.kurashiru.kurashirutrial.viewModel.RecipeViewModel;

import javax.inject.Inject;

public class RecipeListFragment extends BaseFragment {

    public static final String TAG = RecipeListFragment.class.getSimpleName();

    @Inject
    RecipeListViewModel viewModel;

    FragmentRecipeListBinding mBinding;

    private AlertDialog mConfirmDialog;

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    public RecipeListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentRecipeListBinding.inflate(inflater, container, false);
        mBinding.setViewModel(viewModel);
        initRecyclerView();
        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        RecipeListAdapter adapter = new RecipeListAdapter(getContext(), viewModel.getRecipeViewModels());
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setHasFixedSize(true);
//        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mConfirmDialog = null;
    }


    private class RecipeListAdapter
            extends ArrayRecyclerAdapter<RecipeViewModel, BindingHolder<ViewRecipeBinding>> {
        RecipeListAdapter(@NonNull Context context, ObservableList<RecipeViewModel> list) {
            super(context, list);

            list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<RecipeViewModel>>() {
                @Override
                public void onChanged(ObservableList<RecipeViewModel> contributorViewModels) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<RecipeViewModel> contributorViewModels, int i, int i1) {
                    notifyItemRangeChanged(i, i1);
                }

                @Override
                public void onItemRangeInserted(ObservableList<RecipeViewModel> contributorViewModels, int i, int i1) {
                    notifyItemRangeInserted(i, i1);
                }

                @Override
                public void onItemRangeMoved(ObservableList<RecipeViewModel> contributorViewModels, int i, int i1,
                                             int i2) {
                    notifyItemMoved(i, i1);
                }

                @Override
                public void onItemRangeRemoved(ObservableList<RecipeViewModel> contributorViewModels, int i, int i1) {
                    notifyItemRangeRemoved(i, i1);
                }
            });
        }

        @Override
        public BindingHolder<ViewRecipeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_recipe);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewRecipeBinding> holder, int position) {
            RecipeViewModel viewModel = getItem(position);
            ViewRecipeBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
            holder.itemView.setOnClickListener(view -> {
                final int clickedItemPosition = holder.getAdapterPosition();
                onItemClicked(getItem(clickedItemPosition));
            });
        }

        private void onItemClicked(RecipeViewModel viewModel) {
            mConfirmDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("title")
                    .setMessage("Add to favorite?")
                    .setPositiveButton("OK",
                            (dialog, which) -> viewModel.addToFavorite())
                    .setNegativeButton("Cancel", null)
                    .show();
        }


        @Override
        public long getItemId(int position) {
            RecipeViewModel viewModel = getItem(position);
            return viewModel.getId();
        }
    }
}
