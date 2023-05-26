package com.aisc.ngalo.orders;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class OrdersRepository_Factory implements Factory<OrdersRepository> {
  @Override
  public OrdersRepository get() {
    return newInstance();
  }

  public static OrdersRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OrdersRepository newInstance() {
    return new OrdersRepository();
  }

  private static final class InstanceHolder {
    private static final OrdersRepository_Factory INSTANCE = new OrdersRepository_Factory();
  }
}
