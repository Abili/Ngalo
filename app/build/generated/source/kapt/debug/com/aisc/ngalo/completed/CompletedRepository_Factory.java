package com.aisc.ngalo.completed;

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
public final class CompletedRepository_Factory implements Factory<CompletedRepository> {
  @Override
  public CompletedRepository get() {
    return newInstance();
  }

  public static CompletedRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CompletedRepository newInstance() {
    return new CompletedRepository();
  }

  private static final class InstanceHolder {
    private static final CompletedRepository_Factory INSTANCE = new CompletedRepository_Factory();
  }
}
