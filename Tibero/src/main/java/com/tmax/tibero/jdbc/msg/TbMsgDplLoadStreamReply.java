package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsgError;
import java.sql.SQLException;

public class TbMsgDplLoadStreamReply extends TbMsgError {
  public int rowCnt;
  
  public int returnCode;
  
  public int dummy;
  
  public byte[] errorStack;
  
  public int errorStackLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.rowCnt = paramTbStreamDataReader.readInt32();
    this.returnCode = paramTbStreamDataReader.readInt32();
    this.dummy = paramTbStreamDataReader.readInt32();
    readErrorStackInfo(paramTbStreamDataReader);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgDplLoadStreamReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */