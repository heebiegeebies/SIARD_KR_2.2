package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgExecuteUdtPsmReply extends TbMsg {
  public byte[] ppid = new byte[8];
  
  public int paramDataArrayCnt;
  
  public TbOutParamUdt[] paramData;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    paramTbStreamDataReader.readBytes(this.ppid, 0, 8);
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.paramData = new TbOutParamUdt[i];
      for (byte b = 0; b < i; b++) {
        this.paramData[b] = new TbOutParamUdt();
        this.paramData[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.paramData = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgExecuteUdtPsmReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */