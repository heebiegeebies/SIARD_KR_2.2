package com.tmax.tibero.pivot;

public class TbPivotColMeta implements Comparable {
  int oldIdx;
  
  String name;
  
  int type;
  
  public TbPivotColMeta(int paramInt1, TbPivotColMeta paramString, int paramInt2) {
    this.oldIdx = paramInt1;
    this.name = String.valueOf(paramString);
    this.type = paramInt2;
  }
  
  public int compareTo(Object paramObject) {
    return this.name.compareTo(((TbPivotColMeta)paramObject).name);
  }
  
  public int getOldIdx() {
    return this.oldIdx;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getType() {
    return this.type;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\pivot\TbPivotColMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */