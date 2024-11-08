package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class TimestampBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Timestamp timestamp = paramParamContainer.getParamTimestamp(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(timestamp);
    paramTbStreamDataWriter.makeBufferAvailable(13);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int j = dataTypeConverter.fromTimestamp(arrayOfByte, i + 1, calendar, timestamp.getNanos());
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    paramTbStreamDataWriter.writePadding(j + 1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    Timestamp timestamp = paramParamContainer.getParamTimestamp(paramInt1, paramInt2);
    int i = paramTbStreamDataWriter.getBufferedDataSize();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(timestamp);
    paramTbStreamDataWriter.makeBufferAvailable(13);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int j = dataTypeConverter.fromTimestamp(arrayOfByte, i + 1, calendar, timestamp.getNanos());
    arrayOfByte[i] = (byte)j;
    paramTbStreamDataWriter.moveOffset(j + 1);
    if (paramBoolean)
      paramTbStreamDataWriter.writePadding(j + 1); 
  }
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {}
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\TimestampBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */