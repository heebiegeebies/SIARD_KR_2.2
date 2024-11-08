package com.tmax.tibero.jdbc.data.charset;

public class ISO8859P7 {
  protected static final String b2cTable = " ‘’£€₯¦§¨©ͺ«¬­�―°±²³΄΅Ά·ΈΉΊ»Ό½ΎΏΐΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡ�ΣΤΥΦΧΨΩΪΫάέήίΰαβγδεζηθικλμνξοπρςστυφχψωϊϋόύώ�\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
  
  protected static final int c2bMask1 = 65280;
  
  protected static final int c2bMask2 = 255;
  
  protected static final int c2bShift = 8;
  
  protected static final short[] c2bIndex1 = new short[] { 
      0, 190, 190, 324, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 559, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 
      190, 190, 190, 190, 190, 190 };
  
  protected static final String c2bIndex2 = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ \000\000£\000\000¦§¨©\000«¬­\000\000°±²³\000\000\000·\000\000\000»\000½\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000ª\000\000\000\000\000\000\000\000\000´µ¶\000¸¹º\000¼\000¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑ\000ÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþ\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000¯\000\000¡¢\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000¤\000\000¥\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\charset\ISO8859P7.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */