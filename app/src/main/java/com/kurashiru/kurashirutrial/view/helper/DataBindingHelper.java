package com.kurashiru.kurashirutrial.view.helper;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.kurashiru.kurashirutrial.R;
import com.squareup.picasso.Picasso;

public class DataBindingHelper {

    @BindingAdapter("recipeImageUrl")
    public static void setRecipeImageUrl(ImageView imageView, @Nullable String imageUrl) {
        setPhotoImageUrl(imageView, imageUrl);
    }

    @BindingAdapter("favoriteIcon")
    public static void setFavoriteIconUrl(ImageView imageView, @Nullable int resourceId) {
        setImageUrl(imageView, resourceId, R.color.grey200);
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

    private static void setImageUrl(ImageView imageView, @Nullable int resourceId,
                                    @DrawableRes int placeholderResId) {
        if (resourceId <= 0) {
            imageView.setImageDrawable(
                    ContextCompat.getDrawable(imageView.getContext(), placeholderResId));
        } else {
            Picasso.with(imageView.getContext())
                    .load(resourceId)
                    .placeholder(placeholderResId)
                    .error(placeholderResId)
                    .into(imageView);
        }
    }

}
