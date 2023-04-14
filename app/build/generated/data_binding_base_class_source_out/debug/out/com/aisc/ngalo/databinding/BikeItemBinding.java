// Generated by data binding compiler. Do not edit!
package com.aisc.ngalo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aisc.ngalo.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class BikeItemBinding extends ViewDataBinding {
  @NonNull
  public final TextView addToCart;

  @NonNull
  public final ImageView bikeImage;

  @NonNull
  public final ImageView deleteBike;

  @NonNull
  public final TextView textBikeName;

  @NonNull
  public final TextView textViewDesc;

  @NonNull
  public final TextView textViewPrice;

  @NonNull
  public final LinearLayout textViewsContainer;

  protected BikeItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView addToCart, ImageView bikeImage, ImageView deleteBike, TextView textBikeName,
      TextView textViewDesc, TextView textViewPrice, LinearLayout textViewsContainer) {
    super(_bindingComponent, _root, _localFieldCount);
    this.addToCart = addToCart;
    this.bikeImage = bikeImage;
    this.deleteBike = deleteBike;
    this.textBikeName = textBikeName;
    this.textViewDesc = textViewDesc;
    this.textViewPrice = textViewPrice;
    this.textViewsContainer = textViewsContainer;
  }

  @NonNull
  public static BikeItemBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.bike_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static BikeItemBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<BikeItemBinding>inflateInternal(inflater, R.layout.bike_item, root, attachToRoot, component);
  }

  @NonNull
  public static BikeItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.bike_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static BikeItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<BikeItemBinding>inflateInternal(inflater, R.layout.bike_item, null, false, component);
  }

  public static BikeItemBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static BikeItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (BikeItemBinding)bind(component, view, R.layout.bike_item);
  }
}
