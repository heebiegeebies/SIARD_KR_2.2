package com.tmax.tibero.jdbc.data.binder;

import com.tmax.tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.TbStruct;
import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.BindItem;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.Connection;
import java.sql.SQLData;
import java.sql.SQLException;
import java.util.LinkedList;

public class StructInBinder extends Binder {
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    DataTypeConverter dataTypeConverter = paramTbConnection.getTypeConverter();
    BindItem bindItem = paramParamContainer.getBindData().getBindItem(paramInt2);
    TbStruct tbStruct = null;
    TbStructDescriptor tbStructDescriptor = (TbStructDescriptor)bindItem.getTypeDescriptor();
    String str = tbStructDescriptor.getOID();
    int i = tbStructDescriptor.getTobjID();
    int j = tbStructDescriptor.getVersionNo();
    int k = paramTbStreamDataWriter.getBufferedDataSize();
    paramTbStreamDataWriter.makeBufferAvailable(43);
    byte[] arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
    int n = 40;
    arrayOfByte[k] = (byte)n;
    byte b1 = 1;
    int m = dataTypeConverter.fromString(arrayOfByte, k + b1, str);
    paramTbStreamDataWriter.moveOffset(m + b1);
    paramTbStreamDataWriter.writeInt(i, 4);
    paramTbStreamDataWriter.writeInt(j, 4);
    tbStruct = (TbStruct)paramParamContainer.getParamStruct(paramInt1, paramInt2);
    int i1 = 0;
    int i2 = paramTbStreamDataWriter.getBufferedDataSize();
    arrayOfByte[i2] = (byte)i1;
    byte b2 = 1;
    paramTbStreamDataWriter.moveOffset(b2);
    paramTbStreamDataWriter.writeByte((byte)-124);
    paramTbStreamDataWriter.writeByte((byte)1);
    if (!tbStructDescriptor.isFinal())
      paramTbStreamDataWriter.writeBytes(DataTypeConverter.RPCOL_UDT_NOT_FINAL); 
    int i3 = paramTbStreamDataWriter.getBufferedDataSize();
    int i4 = 0;
    paramTbStreamDataWriter.writeByte((byte)i4);
    byte b3 = 0;
    LinkedList linkedList = new LinkedList();
    ObjUdtNode objUdtNode1 = new ObjUdtNode(tbStruct);
    linkedList.addFirst(objUdtNode1);
    ObjUdtNode objUdtNode2 = objUdtNode1;
    if (paramTbConnection.serverInfo.getProtocolMajorVersion() >= 2 && paramTbConnection.serverInfo.getProtocolMinorVersion() >= 16) {
      DataTypeConverter.RPCOL_NULLOBJ = -5;
      DataTypeConverter.RPCOL_5BYTE = -3;
    } else {
      DataTypeConverter.RPCOL_NULLOBJ = -3;
      DataTypeConverter.RPCOL_5BYTE = -5;
    } 
    while (!linkedList.isEmpty()) {
      UdtNode udtNode = null;
      int i7;
      ParamContainer paramContainer;
      Binder binder;
      Object[] arrayOfObject = objUdtNode2.getAttributes();
      if (arrayOfObject == null) {
        if (b3 != 0) {
          paramTbStreamDataWriter.writeByte(DataTypeConverter.RPCOL_NULLOBJ);
        } else {
          paramTbStreamDataWriter.writeByte((byte)-4);
          paramTbStreamDataWriter.writeByte((byte)b3);
        } 
        b3 = 0;
        linkedList.remove(0);
        if (linkedList.isEmpty())
          continue; 
        udtNode = (UdtNode) linkedList.getFirst();
        continue;
      } 
      int i5 = udtNode.attrProcessed;
      udtNode.attrProcessed++;
      Object object = arrayOfObject[i5];
      int i6 = 0;
      if (udtNode instanceof ObjUdtNode) {
        i6 = ((TbStructDescriptor)udtNode.getDescriptor()).getAttributeTypes()[i5];
      } else if (udtNode instanceof ArrayUdtNode) {
        i6 = ((TbArrayDescriptor)udtNode.getDescriptor()).getElementType();
      } else {
        throw TbError.newSQLException(-90664);
      } 
      switch (i6) {
        case 28:
        case 32:
          if (object instanceof TbStruct || object instanceof SQLData) {
            ObjUdtNode objUdtNode;
            if (object instanceof SQLData) {
              TbStruct tbStruct1 = (TbStruct)TbStruct.toStruct(object, (Connection)paramTbConnection);
              objUdtNode = new ObjUdtNode(tbStruct1);
            } else {
              objUdtNode = new ObjUdtNode((TbStruct)object);
            } 
            if (!((TbStructDescriptor)objUdtNode.getDescriptor()).isFinal()) {
              int[] arrayOfInt = ObjUdtNode.writeNotFinalObject(paramTbStreamDataWriter, (TbStructDescriptor)objUdtNode.desc);
              linkedList.addFirst(objUdtNode);
              udtNode = objUdtNode;
              udtNode.setOffset(arrayOfInt);
              continue;
            } 
            linkedList.addFirst(objUdtNode);
            udtNode = objUdtNode;
            b3++;
            continue;
          } 
          if (b3 == 0) {
            paramTbStreamDataWriter.writeByte(DataTypeConverter.RPCOL_NULLOBJ);
            break;
          } 
          paramTbStreamDataWriter.writeByte((byte)-4);
          paramTbStreamDataWriter.writeByte((byte)b3);
          b3 = 0;
          break;
        case 29:
        case 30:
          if (object instanceof TbArray) {
            ArrayUdtNode arrayUdtNode = new ArrayUdtNode((TbArray)object);
            int i8 = arrayUdtNode.getNumOfFields();
            int[] arrayOfInt = ArrayUdtNode.writeArrayMeta(paramTbStreamDataWriter, (TbArrayDescriptor)arrayUdtNode.desc, i8);
            linkedList.addFirst(arrayUdtNode);
            udtNode = arrayUdtNode;
            udtNode.setOffset(arrayOfInt);
            continue;
          } 
          if (b3 == 0) {
            paramTbStreamDataWriter.writeByte(DataTypeConverter.RPCOL_NULLOBJ);
            break;
          } 
          paramTbStreamDataWriter.writeByte((byte)-4);
          paramTbStreamDataWriter.writeByte((byte)b3);
          b3 = 0;
          break;
        default:
          i7 = paramTbStreamDataWriter.getBufferedDataSize();
          paramContainer = udtNode.getSubParams();
          binder = paramContainer.getBinder(-1, i5);
          binder.bind(paramTbConnection, paramContainer, paramTbStreamDataWriter, -1, i5, paramContainer.getBindData().getBindItem(i5).getLength(), false);
          arrayOfByte = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
          if (paramTbStreamDataWriter.getBufferedDataSize() - i7 == 2 && arrayOfByte[i7] == 0)
            paramTbStreamDataWriter.setCurDataSize(i7 + 1); 
          b3 = 0;
          break;
      } 
      while (udtNode.attrProcessed >= udtNode.getNumOfFields()) {
        if ((udtNode instanceof ArrayUdtNode || !udtNode.isFinal()) && linkedList.size() != 1)
          udtNode.rewriteRpLen(paramTbStreamDataWriter); 
        linkedList.remove(0);
        if (linkedList.isEmpty())
          break; 
        udtNode = (UdtNode) linkedList.getFirst();
      } 
    } 
    paramTbStreamDataWriter.makeBufferAvailable(12);
    i4 = paramTbStreamDataWriter.getBufferedDataSize() - k - 40 - b1 - b2;
    if (i4 <= 250) {
      paramTbStreamDataWriter.reWriteInt(i3, i4, 1);
    } else if (i4 <= 65535) {
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, i3 + 1, arrayOfByte1, i3 + 3, i4);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(i3, 254, 1);
      paramTbStreamDataWriter.reWriteInt(i3 + 1, i4 + 2, 2);
    } else {
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, i3 + 1, arrayOfByte1, i3 + 5, i4);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(i3, 253, 1);
      paramTbStreamDataWriter.reWriteInt(i3 + 1, i4 + 4, 4);
    } 
    i1 = paramTbStreamDataWriter.getBufferedDataSize() - k - b2;
    if (i1 <= 250) {
      b2 = 1;
      paramTbStreamDataWriter.reWriteInt(i2, i1, 1);
    } else if (i1 <= 65535) {
      b2 = 3;
      i1 += 2;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, i2 + 1, arrayOfByte1, i2 + 3, i1);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(i2, 254, 1);
      paramTbStreamDataWriter.reWriteInt(i2 + 1, i1, 2);
    } else {
      b2 = 5;
      i1 += 4;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, i2 + 1, arrayOfByte1, i2 + 5, i1);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(i2, 253, 1);
      paramTbStreamDataWriter.reWriteInt(i2 + 1, i1, 4);
    } 
    n = i1;
    if (n <= 250) {
      b1 = 1;
      paramTbStreamDataWriter.reWriteInt(k, n, 1);
    } else if (n <= 65535) {
      b1 = 3;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, k + 1, arrayOfByte1, k + 3, n);
      paramTbStreamDataWriter.moveOffset(2);
      paramTbStreamDataWriter.reWriteInt(k, 254, 1);
      paramTbStreamDataWriter.reWriteInt(k + 1, n, 2);
    } else {
      b1 = 5;
      byte[] arrayOfByte1 = paramTbStreamDataWriter.getStreamBuf().getRawBytes();
      System.arraycopy(arrayOfByte1, k + 1, arrayOfByte1, k + 5, n);
      paramTbStreamDataWriter.moveOffset(4);
      paramTbStreamDataWriter.reWriteInt(k, 253, 1);
      paramTbStreamDataWriter.reWriteInt(k + 1, n, 4);
    } 
    paramTbStreamDataWriter.writePadding(n + b1);
  }
  
  public void bind(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {}
  
  public void bindDFR(TbConnection paramTbConnection, ParamContainer paramParamContainer, TbStreamDataWriter paramTbStreamDataWriter, int paramInt1, int paramInt2, long paramLong) throws SQLException {
    throw TbError.newSQLException(-590705);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\binder\StructInBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */