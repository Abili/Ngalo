// Generated by data binding compiler. Do not edit!
package com.aisc.ngalo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aisc.ngalo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityUserProfileBinding extends ViewDataBinding {
  @NonNull
  public final EditText editTextPhone;

  @NonNull
  public final EditText editTextTextEmailAddress;

  @NonNull
  public final EditText editTextTextPersonFirstName;

  @NonNull
  public final EditText editTextTextPersonLastName;

  @NonNull
  public final CircleImageView imageView;

  @NonNull
  public final RelativeLayout imageviewLayout;

  @NonNull
  public final Button profileCreateButton;

  @NonNull
  public final ProgressBar progressBar;

  protected ActivityUserProfileBinding(Object _bindingComponent, View _root, int _localFieldCount,
      EditText editTextPhone, EditText editTextTextEmailAddress,
      EditText editTextTextPersonFirstName, EditText editTextTextPersonLastName,
      CircleImageView imageView, RelativeLayout imageviewLayout, Button profileCreateButton,
      ProgressBar progressBar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.editTextPhone = editTextPhone;
    this.editTextTextEmailAddress = editTextTextEmailAddress;
    this.editTextTextPersonFirstName = editTextTextPersonFirstName;
    this.editTextTextPersonLastName = editTextTextPersonLastName;
    this.imageView = imageView;
    this.imageviewLayout = imageviewLayout;
    this.profileCreateButton = profileCreateButton;
    this.progressBar = progressBar;
  }

  @NonNull
  public static ActivityUserProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_user_profile, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityUserProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityUserProfileBinding>inflateInternal(inflater, R.layout.activity_user_profile, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityUserProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_user_profile, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityUserProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityUserProfileBinding>inflateInternal(inflater, R.layout.activity_user_profile, null, false, component);
  }

  public static ActivityUserProfileBinding bind(@NonNull View view) {
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
  public static ActivityUserProfileBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityUserProfileBinding)bind(component, view, R.layout.activity_user_profile);
  }
}