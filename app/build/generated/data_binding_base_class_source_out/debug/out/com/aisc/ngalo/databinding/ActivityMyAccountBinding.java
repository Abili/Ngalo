// Generated by view binder compiler. Do not edit!
package com.aisc.ngalo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.aisc.ngalo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMyAccountBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView editProfileImage;

  @NonNull
  public final ProgressBar profileProgressBbar;

  @NonNull
  public final TextView userEmail;

  @NonNull
  public final TextView userPhone;

  @NonNull
  public final CircleImageView userProfile;

  @NonNull
  public final TextView username;

  private ActivityMyAccountBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView editProfileImage, @NonNull ProgressBar profileProgressBbar,
      @NonNull TextView userEmail, @NonNull TextView userPhone,
      @NonNull CircleImageView userProfile, @NonNull TextView username) {
    this.rootView = rootView;
    this.editProfileImage = editProfileImage;
    this.profileProgressBbar = profileProgressBbar;
    this.userEmail = userEmail;
    this.userPhone = userPhone;
    this.userProfile = userProfile;
    this.username = username;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMyAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMyAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_my_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMyAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.editProfileImage;
      ImageView editProfileImage = ViewBindings.findChildViewById(rootView, id);
      if (editProfileImage == null) {
        break missingId;
      }

      id = R.id.profileProgressBbar;
      ProgressBar profileProgressBbar = ViewBindings.findChildViewById(rootView, id);
      if (profileProgressBbar == null) {
        break missingId;
      }

      id = R.id.userEmail;
      TextView userEmail = ViewBindings.findChildViewById(rootView, id);
      if (userEmail == null) {
        break missingId;
      }

      id = R.id.userPhone;
      TextView userPhone = ViewBindings.findChildViewById(rootView, id);
      if (userPhone == null) {
        break missingId;
      }

      id = R.id.user_profile;
      CircleImageView userProfile = ViewBindings.findChildViewById(rootView, id);
      if (userProfile == null) {
        break missingId;
      }

      id = R.id.username;
      TextView username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivityMyAccountBinding((LinearLayout) rootView, editProfileImage,
          profileProgressBbar, userEmail, userPhone, userProfile, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
