package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlCreateBufferPool extends TbMsg {
  public int readBufferCount;
  
  public int transformBufferCount;
  
  public int readBufferSize;
  
  public int transformBufferSize;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.readBufferCount = paramTbStreamDataReader.readInt32();
    this.transformBufferCount = paramTbStreamDataReader.readInt32();
    this.readBufferSize = paramTbStreamDataReader.readInt32();
    this.transformBufferSize = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlCreateBufferPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */