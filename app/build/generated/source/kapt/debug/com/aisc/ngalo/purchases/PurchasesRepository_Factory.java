package com.aisc.ngalo.purchases;

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
public final class PurchasesRepository_Factory implements Factory<PurchasesRepository> {
  @Override
  public PurchasesRepository get() {
    return newInstance();
  }

  public static PurchasesRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PurchasesRepository newInstance() {
    return new PurchasesRepository();
  }

  private static final class InstanceHolder {
    private static final PurchasesRepository_Factory INSTANCE = new PurchasesRepository_Factory();
  }
}
