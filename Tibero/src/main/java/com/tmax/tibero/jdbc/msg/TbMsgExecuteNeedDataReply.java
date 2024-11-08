package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgExecuteNeedDataReply extends TbMsg {
  public int paramIndex;
  
  public int csrId;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.paramIndex = paramTbStreamDataReader.readInt32();
    this.csrId = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgExecuteNeedDataReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */