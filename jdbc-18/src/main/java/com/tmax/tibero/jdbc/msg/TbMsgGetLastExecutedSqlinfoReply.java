package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgGetLastExecutedSqlinfoReply extends TbMsg {
  public String sqlid;
  
  public String hashval;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.sqlid = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.hashval = paramTbStreamDataReader.readDBDecodedPadString(j);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgGetLastExecutedSqlinfoReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */