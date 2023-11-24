package com.aisc.ngalo;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aisc.ngalo.databinding.ActivityBikeDetailsBindingImpl;
import com.aisc.ngalo.databinding.ActivityBikeDetailsBindingLandImpl;
import com.aisc.ngalo.databinding.ActivityBikesScreenBindingImpl;
import com.aisc.ngalo.databinding.ActivityBikesScreenBindingLandImpl;
import com.aisc.ngalo.databinding.ActivityPurchaseHistoryDetailsBindingImpl;
import com.aisc.ngalo.databinding.ActivityUserProfileBindingImpl;
import com.aisc.ngalo.databinding.BikeItemBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYBIKEDETAILS = 1;

  private static final int LAYOUT_ACTIVITYBIKESSCREEN = 2;

  private static final int LAYOUT_ACTIVITYPURCHASEHISTORYDETAILS = 3;

  private static final int LAYOUT_ACTIVITYUSERPROFILE = 4;

  private static final int LAYOUT_BIKEITEM = 5;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(5);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.aisc.ngalo.R.layout.activity_bike_details, LAYOUT_ACTIVITYBIKEDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.aisc.ngalo.R.layout.activity_bikes_screen, LAYOUT_ACTIVITYBIKESSCREEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.aisc.ngalo.R.layout.activity_purchase_history_details, LAYOUT_ACTIVITYPURCHASEHISTORYDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.aisc.ngalo.R.layout.activity_user_profile, LAYOUT_ACTIVITYUSERPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.aisc.ngalo.R.layout.bike_item, LAYOUT_BIKEITEM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYBIKEDETAILS: {
          if ("layout-land/activity_bike_details_0".equals(tag)) {
            return new ActivityBikeDetailsBindingLandImpl(component, view);
          }
          if ("layout/activity_bike_details_0".equals(tag)) {
            return new ActivityBikeDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_bike_details is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYBIKESSCREEN: {
          if ("layout-land/activity_bikes_screen_0".equals(tag)) {
            return new ActivityBikesScreenBindingLandImpl(component, view);
          }
          if ("layout/activity_bikes_screen_0".equals(tag)) {
            return new ActivityBikesScreenBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_bikes_screen is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPURCHASEHISTORYDETAILS: {
          if ("layout/activity_purchase_history_details_0".equals(tag)) {
            return new ActivityPurchaseHistoryDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_purchase_history_details is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYUSERPROFILE: {
          if ("layout/activity_user_profile_0".equals(tag)) {
            return new ActivityUserProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_user_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_BIKEITEM: {
          if ("layout/bike_item_0".equals(tag)) {
            return new BikeItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for bike_item is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(7);

    static {
      sKeys.put("layout-land/activity_bike_details_0", com.aisc.ngalo.R.layout.activity_bike_details);
      sKeys.put("layout/activity_bike_details_0", com.aisc.ngalo.R.layout.activity_bike_details);
      sKeys.put("layout-land/activity_bikes_screen_0", com.aisc.ngalo.R.layout.activity_bikes_screen);
      sKeys.put("layout/activity_bikes_screen_0", com.aisc.ngalo.R.layout.activity_bikes_screen);
      sKeys.put("layout/activity_purchase_history_details_0", com.aisc.ngalo.R.layout.activity_purchase_history_details);
      sKeys.put("layout/activity_user_profile_0", com.aisc.ngalo.R.layout.activity_user_profile);
      sKeys.put("layout/bike_item_0", com.aisc.ngalo.R.layout.bike_item);
    }
  }
}
