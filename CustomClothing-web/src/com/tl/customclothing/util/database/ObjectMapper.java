package com.tl.customclothing.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface ObjectMapper
{
  public abstract Object rowMapper(ResultSet rs)
    throws SQLException;
}
