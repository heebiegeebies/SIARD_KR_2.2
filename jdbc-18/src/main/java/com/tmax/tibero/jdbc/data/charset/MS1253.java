package com.tmax.tibero.jdbc.data.charset;

public class MS1253 {
  protected static final String b2cTable = "\u20AC\uFFFD\u201A\u0192\u201E\u2026\u2020\u2021\uFFFD\u2030\uFFFD\u2039\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\u2018\u2019\u201C\u201D\u2022\u2013\u2014\uFFFD\u2122\uFFFD\u203A\uFFFD\uFFFD\uFFFD\uFFFD\u00A0\u0385\u0386\u00A3\u00A4\u00A5\u00A6\u00A7\u00A8\u00A9\uFFFD\u00AB\u00AC\u00AD\u00AE\u2015\u00B0\u00B1\u00B2\u00B3\u0384\u00B5\u00B6\u00B7\u0388\u0389\u038A\u00BB\u038C\u00BD\u038E\u038F\u0390\u0391\u0392\u0393\u0394\u0395\u0396\u0397\u0398\u0399\u039A\u039B\u039C\u039D\u039E\u039F\u03A0\u03A1\uFFFD\u03A3\u03A4\u03A5\u03A6\u03A7\u03A8\u03A9\u03AA\u03AB\u03AC\u03AD\u03AE\u03AF\u03B0\u03B1\u03B2\u03B3\u03B4\u03B5\u03B6\u03B7\u03B8\u03B9\u03BA\u03BB\u03BC\u03BD\u03BE\u03BF\u03C0\u03C1\u03C2\u03C3\u03C4\u03C5\u03C6\u03C7\u03C8\u03C9\u03CA\u03CB\u03CC\u03CD\u03CE\uFFFD\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
  
  protected static final int c2bMask1 = 65280;
  
  protected static final int c2bMask2 = 255;
  
  protected static final int c2bShift = 8;
  
  protected static final short[] c2bIndex1 = new short[] { 
      0, 190, 337, 461, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 698, 920, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337, 337, 337, 337, 337, 
      337, 337, 337, 337, 337, 337 };
  
  protected static final String c2bIndex2 = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00A0\000\000\u00A3\u00A4\u00A5\u00A6\u00A7\u00A8\u00A9\000\u00AB\u00AC\u00AD\u00AE\000\u00B0\u00B1\u00B2\u00B3\000\u00B5\u00B6\u00B7\000\000\000\u00BB\000\u00BD\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u0083\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u00B4\u00A1\u00A2\000\u00B8\u00B9\u00BA\000\u00BC\000\u00BE\u00BF\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C6\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D0\u00D1\000\u00D3\u00D4\u00D5\u00D6\u00D7\u00D8\u00D9\u00DA\u00DB\u00DC\u00DD\u00DE\u00DF\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F7\u00F8\u00F9\u00FA\u00FB\u00FC\u00FD\u00FE\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u0096\u0097\u00AF\000\000\u0091\u0092\u0082\000\u0093\u0094\u0084\000\u0086\u0087\u0095\000\000\000\u0085\000\000\000\000\000\000\000\000\000\u0089\000\000\000\000\000\000\000\000\u008B\u009B\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u0080\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\u0099\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\charset\MS1253.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */