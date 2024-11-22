package com.tmax.tibero.jdbc.data.charset;

public class CP866CharToByteConverter extends CharToByteSingleByte {
  private static final short[] INDEX_1 = new short[] { 
      0, 184, 184, 184, 439, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 673, 904, 184, 184, 1160, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184, 184, 184, 184, 184, 
      184, 184, 184, 184, 184, 184 };
  
  private static final String INDEX_2 = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00FF\000\000\000\u00FD\000\000\000\000\000\000\000\000\000\000\000\u00F8\000\000\000\000\000\000\u00FA\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00F0\000\000\u00F2\000\000\u00F4\000\000\000\000\000\000\u00F6\000\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F\u00A0\u00A1\u00A2\u00A3\u00A4\u00A5\u00A6\u00A7\u00A8\u00A9\u00AA\u00AB\u00AC\u00AD\u00AE\u00AF\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\000\u00F1\000\000\u00F3\000\000\u00F5\000\000\000\000\000\000\u00F7\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00FC\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00F9\u00FB\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00C4\000\u00B3\000\000\000\000\000\000\000\000\000\u00DA\000\000\000\u00BF\000\000\000\u00C0\000\000\000\u00D9\000\000\000\u00C3\000\000\000\000\000\000\000\u00B4\000\000\000\000\000\000\000\u00C2\000\000\000\000\000\000\000\u00C1\000\000\000\000\000\000\000\u00C5\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00CD\u00BA\u00D5\u00D6\u00C9\u00B8\u00B7\u00BB\u00D4\u00D3\u00C8\u00BE\u00BD\u00BC\u00C6\u00C7\u00CC\u00B5\u00B6\u00B9\u00D1\u00D2\u00CB\u00CF\u00D0\u00CA\u00D8\u00D7\u00CE\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00DF\000\000\000\u00DC\000\000\000\u00DB\000\000\000\u00DD\000\000\000\u00DE\u00B0\u00B1\u00B2\000\000\000\000\000\000\000\000\000\000\000\000\u00FE\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
  
  public CP866CharToByteConverter() {
    this.mask1 = 65280;
    this.mask2 = 255;
    this.shift = 8;
    this.index1 = INDEX_1;
    this.index2 = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00FF\000\000\000\u00FD\000\000\000\000\000\000\000\000\000\000\000\u00F8\000\000\000\000\000\000\u00FA\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00F0\000\000\u00F2\000\000\u00F4\000\000\000\000\000\000\u00F6\000\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F\u00A0\u00A1\u00A2\u00A3\u00A4\u00A5\u00A6\u00A7\u00A8\u00A9\u00AA\u00AB\u00AC\u00AD\u00AE\u00AF\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\000\u00F1\000\000\u00F3\000\000\u00F5\000\000\000\000\000\000\u00F7\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00FC\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00F9\u00FB\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00C4\000\u00B3\000\000\000\000\000\000\000\000\000\u00DA\000\000\000\u00BF\000\000\000\u00C0\000\000\000\u00D9\000\000\000\u00C3\000\000\000\000\000\000\000\u00B4\000\000\000\000\000\000\000\u00C2\000\000\000\000\000\000\000\u00C1\000\000\000\000\000\000\000\u00C5\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00CD\u00BA\u00D5\u00D6\u00C9\u00B8\u00B7\u00BB\u00D4\u00D3\u00C8\u00BE\u00BD\u00BC\u00C6\u00C7\u00CC\u00B5\u00B6\u00B9\u00D1\u00D2\u00CB\u00CF\u00D0\u00CA\u00D8\u00D7\u00CE\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00DF\000\000\000\u00DC\000\000\000\u00DB\000\000\000\u00DD\000\000\000\u00DE\u00B0\u00B1\u00B2\000\000\000\000\000\000\000\000\000\000\000\000\u00FE\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\CP866CharToByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */