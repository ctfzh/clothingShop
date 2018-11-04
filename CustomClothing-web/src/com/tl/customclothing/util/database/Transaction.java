package com.tl.customclothing.util.database;

public abstract interface Transaction
{
  public abstract void begin();

  public abstract void commit();

  public abstract void rollback();
}
