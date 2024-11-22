package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class KOI8RByteToCharConverter {
  protected boolean subMode = true;
  
  protected char[] subChars = new char[] { '?' };
  
  private static final char[] KOI8R_TO_UNICODE_PAGE = new char[] { 
      '\u2500', '\u2502', '\u250C', '\u2510', '\u2514', '\u2518', '\u251C', '\u2524', '\u252C', '\u2534', 
      '\u253C', '\u2580', '\u2584', '\u2588', '\u258C', '\u2590', '\u2591', '\u2592', '\u2593', '\u2320', 
      '\u25A0', '\u2219', '\u221A', '\u2248', '\u2264', '\u2265', '\u00A0', '\u2321', '\u00B0', '\u00B2', 
      '\u00B7', '\u00F7', '\u2550', '\u2551', '\u2552', '\u0451', '\u2553', '\u2554', '\u2555', '\u2556', 
      '\u2557', '\u2558', '\u2559', '\u255A', '\u255B', '\u255C', '\u255D', '\u255E', '\u255F', '\u2560', 
      '\u2561', '\u0401', '\u2562', '\u2563', '\u2564', '\u2565', '\u2566', '\u2567', '\u2568', '\u2569', 
      '\u256A', '\u256B', '\u256C', '\u00A9', '\u044E', '\u0430', '\u0431', '\u0446', '\u0434', '\u0435', 
      '\u0444', '\u0433', '\u0445', '\u0438', '\u0439', '\u043A', '\u043B', '\u043C', '\u043D', '\u043E', 
      '\u043F', '\u044F', '\u0440', '\u0441', '\u0442', '\u0443', '\u0436', '\u0432', '\u044C', '\u044B', 
      '\u0437', '\u0448', '\u044D', '\u0449', '\u0447', '\u044A', '\u042E', '\u0410', '\u0411', '\u0426', 
      '\u0414', '\u0415', '\u0424', '\u0413', '\u0425', '\u0418', '\u0419', '\u041A', '\u041B', '\u041C', 
      '\u041D', '\u041E', '\u041F', '\u042F', '\u0420', '\u0421', '\u0422', '\u0423', '\u0416', '\u0412', 
      '\u042C', '\u042B', '\u0417', '\u0428', '\u042D', '\u0429', '\u0427', '\u042A' };
  
  private int decodeFromUcs(byte paramByte1, byte paramByte2) {
    return (paramByte1 << 8) + (paramByte2 & 0xFF);
  }
  
  public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2) {
      int k = decodeFromUcs((byte)0, paramArrayOfbyte[i]);
      if (k < 128) {
        paramArrayOfchar[j++] = (char)k;
        i++;
        continue;
      } 
      k = KOI8R_TO_UNICODE_PAGE[k - 128];
      paramArrayOfchar[j++] = (char)k;
      i++;
    } 
    return j - paramInt3;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\KOI8RByteToCharConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */