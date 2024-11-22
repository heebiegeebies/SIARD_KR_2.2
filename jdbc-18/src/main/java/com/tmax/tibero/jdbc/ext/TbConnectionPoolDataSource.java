package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

public class TbConnectionPoolDataSource extends TbDataSource implements ConnectionPoolDataSource {
  private static final long serialVersionUID = 672395909270677554L;
  
  public PooledConnection getPooledConnection() throws SQLException {
    return getPooledConnection(null, null);
  }
  
  public PooledConnection getPooledConnection(String paramString1, String paramString2) throws SQLException {
    Connection connection = getConnection(paramString1, paramString2);
    ((TbConnection)connection).setPooledConnection(true);
    return new TbPooledConnection((TbConnection)connection);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\ext\TbConnectionPoolDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */