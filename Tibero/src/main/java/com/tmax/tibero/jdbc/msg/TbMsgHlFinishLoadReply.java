package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlFinishLoadReply extends TbMsg {
  public int totalRequestedCnt;
  
  public int totalHitCnt;
  
  public int totalFailedCnt;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.totalRequestedCnt = paramTbStreamDataReader.readInt32();
    this.totalHitCnt = paramTbStreamDataReader.readInt32();
    this.totalFailedCnt = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlFinishLoadReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */