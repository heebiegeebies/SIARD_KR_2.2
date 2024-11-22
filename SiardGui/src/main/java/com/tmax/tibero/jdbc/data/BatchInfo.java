package com.tmax.tibero.jdbc.data;

public class BatchInfo {
  private int currentRowIndex = 0;
  
  private BindData bindData;
  
  public BatchInfo() {}
  
  public BatchInfo(BindData paramBindData, int paramInt) {
    this.bindData = paramBindData;
    this.currentRowIndex = paramInt;
  }
  
  public int getCurrentRowIndex() {
    return this.currentRowIndex;
  }
  
  public BindData getBindData() {
    return this.bindData;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\BatchInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */