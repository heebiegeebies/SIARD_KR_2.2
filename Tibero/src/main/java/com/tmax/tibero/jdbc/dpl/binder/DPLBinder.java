package com.tmax.tibero.jdbc.dpl.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;

public abstract class DPLBinder {
  public abstract void bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2) throws SQLException;
  
  public abstract int bind(TbConnection paramTbConnection, TbDirPathStream paramTbDirPathStream, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException;
  
  public void init() {}
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\dpl\binder\DPLBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */