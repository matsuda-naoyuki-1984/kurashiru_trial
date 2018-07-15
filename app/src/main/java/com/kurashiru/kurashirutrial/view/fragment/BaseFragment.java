package com.kurashiru.kurashirutrial.view.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kurashiru.kurashirutrial.R;
import com.kurashiru.kurashirutrial.di.FragmentComponent;
import com.kurashiru.kurashirutrial.di.FragmentModule;
import com.kurashiru.kurashirutrial.view.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    private FragmentComponent fragmentComponent;

    @NonNull
    public FragmentComponent getComponent() {
        if (fragmentComponent != null) {
            return fragmentComponent;
        }

        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            fragmentComponent = ((BaseActivity) activity).getComponent().plus(new FragmentModule(this));
            return fragmentComponent;
        } else {
            throw new IllegalStateException(
                    "The activity of this fragment is not an instance of BaseActivity");
        }
    }

    void showSnackbar(int messageResouce, int actionTextResouce, View.OnClickListener listener){
        Activity activity = getActivity();
        if (activity != null) {
            final View viewPos = activity.findViewById(R.id.myCoordinatorLayout);
            Snackbar.make(viewPos, messageResouce, Snackbar.LENGTH_LONG)
                    .setAction(actionTextResouce, listener)
                    .show();
        }
    }

    void showSnackbar(String message, int actionTextResouce, View.OnClickListener listener){
        Activity activity = getActivity();
        if (activity != null) {
            final View viewPos = activity.findViewById(R.id.myCoordinatorLayout);
            Snackbar.make(viewPos, message, Snackbar.LENGTH_LONG)
                    .setAction(actionTextResouce, listener)
                    .show();
        }
    }

}
