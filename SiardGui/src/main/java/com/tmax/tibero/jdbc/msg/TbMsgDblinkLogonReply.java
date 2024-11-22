package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgDblinkLogonReply extends TbMsg {
  public int gwPid;
  
  public String gwProcname;
  
  public int gwTid;
  
  public String remoteSessInfo;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.gwPid = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.gwProcname = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.gwTid = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    this.remoteSessInfo = paramTbStreamDataReader.readDBDecodedPadString(j);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgDblinkLogonReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */