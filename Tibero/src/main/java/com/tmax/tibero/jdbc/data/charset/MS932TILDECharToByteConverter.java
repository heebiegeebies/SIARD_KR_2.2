package com.tmax.tibero.jdbc.data.charset;

public class MS932TILDECharToByteConverter extends MS932DBCharToByteConverter {
  private JIS0201CharToByteConverter JIS0201cb = new JIS0201CharToByteConverter();
  
  protected int convSingleByte(char paramChar, byte[] paramArrayOfbyte) {
    if ((paramChar & 0xFF80) == 0) {
      paramArrayOfbyte[0] = (byte)paramChar;
      return 1;
    } 
    byte b;
    if ((b = this.JIS0201cb.getNative(paramChar)) == 0)
      return 0; 
    paramArrayOfbyte[0] = b;
    return 1;
  }
  
  protected int convDoubleByte(char paramChar) {
    if (paramChar == '〜' || paramChar == '～') {
      char c = '';
      byte b = 96;
      return c << 8 | b;
    } 
    int i = index1[(paramChar & 0xFF00) >> 8] << 8;
    return index2[i >> 12].charAt((i & 0xFFF) + (paramChar & 0xFF));
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\MS932TILDECharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */