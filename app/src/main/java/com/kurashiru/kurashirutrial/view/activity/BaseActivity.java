package com.kurashiru.kurashirutrial.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.kurashiru.kurashirutrial.MainApplication;
import com.kurashiru.kurashirutrial.di.ActivityComponent;
import com.kurashiru.kurashirutrial.di.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @NonNull
    public ActivityComponent getComponent() {
        if (activityComponent == null) {
            MainApplication mainApplication = (MainApplication) getApplication();
            activityComponent = mainApplication.getComponent().plus(new ActivityModule(this));
        }
        return activityComponent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    final void replaceFragment(@NonNull Fragment fragment, @IdRes @LayoutRes int layoutResId) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutResId, fragment, fragment.getClass().getSimpleName());
        ft.commit();
    }

}
