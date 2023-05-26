package com.aisc.ngalo.admin.ui.theme;

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
public final class AdminRespository_Factory implements Factory<AdminRespository> {
  @Override
  public AdminRespository get() {
    return newInstance();
  }

  public static AdminRespository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AdminRespository newInstance() {
    return new AdminRespository();
  }

  private static final class InstanceHolder {
    private static final AdminRespository_Factory INSTANCE = new AdminRespository_Factory();
  }
}
