package com.aisc.ngalo.cart;

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
  private final Provider<CartRepository> repositoryProvider;

  public AddToCartViewModel_Factory(Provider<CartRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddToCartViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddToCartViewModel_Factory create(Provider<CartRepository> repositoryProvider) {
    return new AddToCartViewModel_Factory(repositoryProvider);
  }

  public static AddToCartViewModel newInstance(CartRepository repository) {
    return new AddToCartViewModel(repository);
  }
}
