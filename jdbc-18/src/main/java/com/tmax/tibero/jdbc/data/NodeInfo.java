package com.tmax.tibero.jdbc.data;

public class NodeInfo {
  private String address = "localhost";
  
  private int portNumber = 8629;
  
  private int nodeType = 0;
  
  public NodeInfo() {}
  
  public NodeInfo(String paramString, int paramInt) {}
  
  public NodeInfo(String paramString, int paramInt1, int paramInt2) {}
  
  public String getAddress() {
    return this.address;
  }
  
  public int getNodeType() {
    return this.nodeType;
  }
  
  public int getPort() {
    return this.portNumber;
  }
  
  public void setAddress(String paramString) {
    this.address = paramString;
  }
  
  public void setNodeType(int paramInt) {
    this.nodeType = paramInt;
  }
  
  public void setPort(int paramInt) {
    this.portNumber = paramInt;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\data\NodeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */