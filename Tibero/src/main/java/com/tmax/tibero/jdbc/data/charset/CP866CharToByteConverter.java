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
  
  private static final String INDEX_2 = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ÿ\000\000\000ý\000\000\000\000\000\000\000\000\000\000\000ø\000\000\000\000\000\000ú\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ð\000\000ò\000\000ô\000\000\000\000\000\000ö\000 ¡¢£¤¥¦§¨©ª«¬­®¯àáâãäåæçèéêëìíîï\000ñ\000\000ó\000\000õ\000\000\000\000\000\000÷\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ü\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ùû\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000Ä\000³\000\000\000\000\000\000\000\000\000Ú\000\000\000¿\000\000\000À\000\000\000Ù\000\000\000Ã\000\000\000\000\000\000\000´\000\000\000\000\000\000\000Â\000\000\000\000\000\000\000Á\000\000\000\000\000\000\000Å\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ÍºÕÖÉ¸·»ÔÓÈ¾½¼ÆÇÌµ¶¹ÑÒËÏÐÊØ×Î\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ß\000\000\000Ü\000\000\000Û\000\000\000Ý\000\000\000Þ°±²\000\000\000\000\000\000\000\000\000\000\000\000þ\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
  
  public CP866CharToByteConverter() {
    this.mask1 = 65280;
    this.mask2 = 255;
    this.shift = 8;
    this.index1 = INDEX_1;
    this.index2 = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ÿ\000\000\000ý\000\000\000\000\000\000\000\000\000\000\000ø\000\000\000\000\000\000ú\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ð\000\000ò\000\000ô\000\000\000\000\000\000ö\000 ¡¢£¤¥¦§¨©ª«¬­®¯àáâãäåæçèéêëìíîï\000ñ\000\000ó\000\000õ\000\000\000\000\000\000÷\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ü\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ùû\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000Ä\000³\000\000\000\000\000\000\000\000\000Ú\000\000\000¿\000\000\000À\000\000\000Ù\000\000\000Ã\000\000\000\000\000\000\000´\000\000\000\000\000\000\000Â\000\000\000\000\000\000\000Á\000\000\000\000\000\000\000Å\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ÍºÕÖÉ¸·»ÔÓÈ¾½¼ÆÇÌµ¶¹ÑÒËÏÐÊØ×Î\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ß\000\000\000Ü\000\000\000Û\000\000\000Ý\000\000\000Þ°±²\000\000\000\000\000\000\000\000\000\000\000\000þ\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\CP866CharToByteConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */