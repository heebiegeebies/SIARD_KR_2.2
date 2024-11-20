package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.TbClobBase;
import java.sql.SQLException;

public interface TbClobAccessor extends TbLobAccessor {
  byte[] createTemporaryClob() throws SQLException;
  
  byte[] createTemporaryNClob() throws SQLException;
  
  long position(TbClobBase paramTbClobBase, char[] paramArrayOfchar, long paramLong) throws SQLException;
  
  long read(TbClobBase paramTbClobBase, long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException;
  
  long write(TbClobBase paramTbClobBase, long paramLong1, char[] paramArrayOfchar, long paramLong2, long paramLong3) throws SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\comm\TbClobAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */