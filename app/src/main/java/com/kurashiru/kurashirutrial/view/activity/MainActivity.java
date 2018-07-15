package com.kurashiru.kurashirutrial.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kurashiru.kurashirutrial.R;
import com.kurashiru.kurashirutrial.view.fragment.RecipeListFragment;

public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Fragment mRecipeListFragment;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getComponent().inject(this);

        initView();
        initFragments(savedInstanceState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFragment(mRecipeListFragment, RecipeListFragment.TAG);
                return true;
            case R.id.navigation_search:
                return true;
            case R.id.navigation_help:
                return true;
        }
        return false;
    };

    private void initView() {

    }

    private void initFragments(Bundle savedInstanceState) {
        final FragmentManager manager = getSupportFragmentManager();
        mRecipeListFragment = manager.findFragmentByTag(RecipeListFragment.TAG);

        if(mRecipeListFragment == null){
            mRecipeListFragment = RecipeListFragment.newInstance();
        }

        if (savedInstanceState == null) {
            switchFragment(mRecipeListFragment, RecipeListFragment.TAG);
        }
    }


    private boolean switchFragment(@NonNull Fragment fragment, @NonNull String tag) {
        if (fragment.isAdded()) {
            return false;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();

        final Fragment currentFragment = manager.findFragmentById(R.id.content_view);
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            ft.attach(fragment);
        } else {
            ft.add(R.id.content_view, fragment, tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        manager.executePendingTransactions();

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
