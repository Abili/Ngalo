package com.aisc.ngalo.cart;

import android.app.Application;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AddToCartViewModel_Factory implements Factory<AddToCartViewModel> {
  private final Provider<Application> applicationProvider;

  public AddToCartViewModel_Factory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public AddToCartViewModel get() {
    return newInstance(applicationProvider.get());
  }

  public static AddToCartViewModel_Factory create(Provider<Application> applicationProvider) {
    return new AddToCartViewModel_Factory(applicationProvider);
  }

  public static AddToCartViewModel newInstance(Application application) {
    return new AddToCartViewModel(application);
  }
}
