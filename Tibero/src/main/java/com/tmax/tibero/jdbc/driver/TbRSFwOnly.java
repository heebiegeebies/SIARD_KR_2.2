package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.BindData;
import com.tmax.tibero.jdbc.data.Row;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbSQLTypeScanner;
import java.sql.SQLException;

public class TbRSFwOnly extends TbResultSetBase {
  private Row row;
  
  private int chunkOffset;
  
  protected int lastFetchedCnt;
  
  protected TbRSFwOnly(TbStatement paramTbStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) throws SQLException {
    super(paramTbStatement, paramInt1, paramInt2, paramInt3, paramArrayOfbyte);
    paramTbStatement.conn.addFOActiveResultSet(this);
  }
  
  public void buildRowTable(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    this.chunkOffset = 1;
    if (this.rowsFetchedCnt + paramInt < 0)
      throw TbError.newSQLException(-90613); 
    this.lastFetchedCnt = this.rowsFetchedCnt;
    this.rowsFetchedCnt += paramInt;
    this.currentFetchCount = paramInt;
    if (this.row == null)
      this.row = new Row(this.columnCount); 
    this.rowChunk = paramArrayOfbyte;
  }
  
  private void checkRowIndex(int paramInt) throws SQLException {
    if (paramInt < 0)
      throw TbError.newSQLException(-90635); 
    if ((this.stmt.getMaxRows() != 0 && paramInt >= this.stmt.getMaxRows()) || (paramInt >= this.rowsFetchedCnt && this.fetchComplete))
      throw TbError.newSQLException(-90647); 
    if (paramInt < this.lastFetchedCnt || (this.stmt.getMaxRows() != 0 && paramInt >= this.stmt.getMaxRows()) || paramInt >= this.rowsFetchedCnt)
      throw TbError.newSQLException(-90624); 
  }
  
  protected Row getCurrentRow() throws SQLException {
    checkRowIndex(this.currentRowIndex);
    return this.row;
  }
  
  public synchronized boolean next() throws SQLException {
    if (!super.next()) {
      closeCursor();
      return false;
    } 
    this.chunkOffset += this.row.buildRowData(this.rowChunk, this.chunkOffset, this.cols);
    return true;
  }
  
  protected void removeCurrentRow() throws SQLException {}
  
  protected void recover() throws SQLException {
    setFOECode(0);
    if (this.stmt == null)
      throw TbError.newSQLException(-90702, "stmt=null"); 
    if (!TbSQLTypeScanner.isQueryStmt(this.stmt.getSqlType()))
      throw TbError.newSQLException(-90702, "sqlType=" + this.stmt.getSqlType()); 
    if (this.stmt.getRealRsetType().isSensitive() || this.stmt.getRealRsetType().isUpdatable())
      throw TbError.newSQLException(-90702, "realRsetType=" + this.stmt.getRealRsetType()); 
    if (this.stmt.isReturnAutoGeneratedKeys())
      throw TbError.newSQLException(-90702, "autoGenKeys=true"); 
    if (this.stmt instanceof TbPreparedStatementImpl) {
      TbPreparedStatementImpl tbPreparedStatementImpl = (TbPreparedStatementImpl)this.stmt;
      if (tbPreparedStatementImpl.getBatchRowCount() > 0)
        throw TbError.newSQLException(-90702, "BatchRowCnt=" + tbPreparedStatementImpl.getBatchRowCount()); 
      BindData bindData = tbPreparedStatementImpl.getBindData();
      if (bindData.getDFRParameterCnt() > 0)
        throw TbError.newSQLException(-90702, "DFRParamCnt=" + bindData.getDFRParameterCnt()); 
      tbPreparedStatementImpl.setPPID((byte[])null);
      try {
        tbPreparedStatementImpl.conn.getTbComm().prepareExecute(tbPreparedStatementImpl, tbPreparedStatementImpl.getOriginalSql(), 0);
      } catch (Exception exception) {
        throw TbError.newSQLException(-90701, exception);
      } 
    } else {
      try {
        this.stmt.conn.getTbComm().executeDirect(this.stmt, this.stmt.getOriginalSql());
      } catch (Exception exception) {
        throw TbError.newSQLException(-90701, exception);
      } 
    } 
    if (this.stmt.currentRs == null)
      throw TbError.newSQLException(-90702, "failoverRset=null"); 
    if (!(this.stmt.currentRs instanceof TbRSFwOnly))
      throw TbError.newSQLException(-90702, "failoverRsetClass=" + this.stmt.currentRs.getClass().getName()); 
    int i = 1;
    for (byte b = 0; b < this.currentFetchCount; b++)
      i += this.row.buildRowData(this.rowChunk, i, this.cols); 
    long l1 = getCurrentChunkCRC(this.rowChunk, i, 0L);
    TbRSFwOnly tbRSFwOnly = (TbRSFwOnly)this.stmt.currentRs;
    while (tbRSFwOnly.currentRowIndex < this.lastFetchedCnt)
      tbRSFwOnly.next(); 
    long l2 = -1L;
    if (tbRSFwOnly.rowChunk.length > i)
      l2 = getCurrentChunkCRC(tbRSFwOnly.rowChunk, i, l1); 
    if (l2 != 0L)
      throw TbError.newSQLException(-90702, "failoverRset invalid."); 
    while (tbRSFwOnly.currentRowIndex < this.currentRowIndex - 1)
      tbRSFwOnly.next(); 
    this.csrID = tbRSFwOnly.csrID;
    this.fetchComplete = tbRSFwOnly.fetchComplete;
    this.stmt.setResultSet(this);
    this.typeConverter = this.stmt.conn.getTypeConverter();
    tbRSFwOnly.reset();
  }
  
  protected void reset() {
    super.reset();
    if (this.row != null) {
      this.row.close();
      this.row = null;
    } 
  }
  
  public boolean absolute(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90620);
  }
  
  public void afterLast() throws SQLException {
    throw TbError.newSQLException(-90620);
  }
  
  public void beforeFirst() throws SQLException {
    throw TbError.newSQLException(-90620);
  }
  
  public boolean first() throws SQLException {
    throw TbError.newSQLException(-90620);
  }
  
  public boolean isLast() throws SQLException {
    throw TbError.newSQLException(-90620);
  }
  
  public boolean last() throws SQLException {
    throw TbError.newSQLException(-90620);
  }
  
  public boolean previous() throws SQLException {
    throw TbError.newSQLException(-90620);
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return null;
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return null;
  }

  public boolean relative(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90620);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbRSFwOnly.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */