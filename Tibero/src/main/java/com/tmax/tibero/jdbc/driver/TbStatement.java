package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.BigLiteral;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import com.tmax.tibero.jdbc.util.TbSQLParser;
import com.tmax.tibero.jdbc.util.TbSQLTypeScanner;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class TbStatement extends com.tmax.tibero.jdbc.TbStatement {
  protected int queryTimeout;
  protected int fetchDirection;
  protected int maxRow;
  protected int sqlType;
  protected TbConnection conn;
  protected DataTypeConverter typeConverter;
  protected RsetType userRsetType;
  protected RsetType realRsetType;
  protected String originalSql;
  protected String sqlWithRowId;
  protected boolean rsetTypeDowngraded;
  protected SQLWarning warnings;
  protected TbSQLTypeScanner sqlTypeScanner;
  protected boolean enableEscapeProcessing;
  private boolean closed;
  private boolean closeOnCompletion;
  protected boolean isExecuting;
  protected List<String> batchStmts;
  protected int[] batchCounts;
  protected TbResultSet currentRs;
  protected ArrayList<TbResultSet> subResultSets;
  protected long rowsUpdated;
  protected boolean noMoreUpdateCount;
  protected int curCsrId;
  protected int maxFieldSize;
  protected int preFetchSize;
  protected int fetchSize;
  protected TbPivotInfo[] pivotInfo;
  protected Vector<byte[]> pivotData;
  protected boolean returnAutoGeneratedKeys;
  protected boolean poolable;
  protected boolean defaultNChar;
  private TbSQLParser sqlParser;

  public TbStatement(TbConnection var1) {
    this(var1, 1003, 1007, 64000);
  }

  public TbStatement(TbConnection var1, int var2, int var3, int var4) {
    this.queryTimeout = 0;
    this.fetchDirection = 1000;
    this.maxRow = 0;
    this.sqlType = 0;
    this.enableEscapeProcessing = true;
    this.rowsUpdated = -1L;
    this.curCsrId = -1;
    this.maxFieldSize = 65535;
    this.preFetchSize = 64000;
    this.fetchSize = this.preFetchSize;
    this.pivotData = new Vector();
    this.returnAutoGeneratedKeys = false;
    this.defaultNChar = false;
    this.conn = var1;
    this.typeConverter = var1.typeConverter;
    this.userRsetType = RsetType.getRsetType(var2, var3);
    this.preFetchSize = var4;
    this.fetchSize = var4;
    this.closed = false;
    this.isExecuting = false;
    this.subResultSets = new ArrayList();
    this.poolable = var1.info.isStmtCache();
    this.rsetTypeDowngraded = false;
    this.defaultNChar = this.conn.info.getDefaultNChar();
    this.sqlParser = new TbSQLParser();
  }

  public synchronized void addBatch(String var1) throws SQLException {
    if (this.batchStmts == null) {
      this.batchStmts = new ArrayList();
    }

    this.batchStmts.add(var1);
  }

  public void addPivotData(byte[] var1) {
    this.pivotData.add(var1);
  }

  public void addWarning(SQLWarning var1) {
    if (this.warnings != null) {
      this.warnings.setNextWarning(var1);
    } else {
      this.warnings = var1;
    }

  }

  public void cancel() throws SQLException {
    if (!this.closed && this.conn != null && !this.conn.isClosed()) {
      if (this.isExecuting) {
        this.conn.getTbComm().cancelStatement();
      }

    }
  }

  protected void checkBatchStmtRemained() throws SQLException {
    if (this.batchStmts.size() > 0) {
      throw TbError.newSQLException(-90606);
    }
  }

  protected void checkConnectionOpen() throws SQLException {
    if (this.conn == null || this.conn.isClosed()) {
      throw TbError.newSQLException(-90603);
    }
  }

  public synchronized void clearBatch() throws SQLException {
    if (this.batchStmts != null) {
      this.batchStmts.clear();
      this.batchStmts = null;
    }

  }

  public void clearWarnings() throws SQLException {
    this.warnings = null;
  }

  public synchronized void close() throws SQLException {
    if (this.conn == null || this.conn.getStmtCache() == null || !this.conn.getStmtCache().add(this, 0)) {
      this.closeInternal();
    }
  }

  public synchronized void closeInternal() throws SQLException {
    try {
      this.closeResultSets();
    } catch (SQLException var5) {
    } finally {
      this.reset();
    }

  }

  private void closeResultSets() throws SQLException {
    if (this.subResultSets.size() > 0) {
      Iterator var1 = this.subResultSets.iterator();

      while(var1.hasNext()) {
        ResultSet var2 = (ResultSet)var1.next();

        try {
          var2.close();
        } catch (SQLException var4) {
        }
      }

      this.subResultSets.removeAll(this.subResultSets);
    }

    if (this.currentRs != null) {
      this.currentRs.close();
      this.currentRs = null;
    }

  }

  public synchronized boolean execute(String var1) throws SQLException {
    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    this.returnAutoGeneratedKeys = false;
    if (this.queryTimeout != 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    try {
      this.isExecuting = true;
      this.executeInternal(var1);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    return this.currentRs != null;
  }

  public boolean execute(String var1, int var2) throws SQLException {
    if (var2 == 2) {
      return this.execute(var1);
    } else {
      if (var2 != 1) {
        TbError.newSQLException(-590733);
      }

      this.checkConnectionOpen();
      if (this.batchStmts != null) {
        this.checkBatchStmtRemained();
        this.initBatchStmts();
      }

      this.initBeforeExecute();
      this.returnAutoGeneratedKeys = true;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
      }

      try {
        this.isExecuting = true;
        this.executeInternal(var1);
      } finally {
        this.isExecuting = false;
        if (this.queryTimeout != 0) {
          this.conn.getTimeout().cancelTimeout();
        }

      }

      return false;
    }
  }

  public boolean execute(String var1, int[] var2) throws SQLException {
    if (var2 == null || var2.length == 0) {
      TbError.newSQLException(-590732);
    }

    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    if (this.queryTimeout != 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    try {
      this.isExecuting = true;
      this.executeInternal(var1, var2);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    return false;
  }

  public boolean execute(String var1, String[] var2) throws SQLException {
    if (var2 == null || var2.length == 0) {
      TbError.newSQLException(-590734);
    }

    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    if (this.queryTimeout != 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    try {
      this.isExecuting = true;
      this.executeInternal(var1, var2);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    return false;
  }

  boolean isInsertStmt(String var1) {
    if (var1 != null) {
      return this.sqlType == 36;
    } else {
      return false;
    }
  }

  public synchronized int[] executeBatch() throws SQLException {
    this.checkConnectionOpen();
    this.initBeforeExecute();
    if (this.batchStmts == null) {
      return new int[0];
    } else {
      int var1 = this.batchStmts.size();
      if (var1 <= 0) {
        return new int[0];
      } else {
        this.batchCounts = new int[var1];
        byte var2 = 0;

        try {
          if (this.queryTimeout != 0) {
            this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
          }

          this.isExecuting = true;

          for(int var10 = 0; var10 < var1; ++var10) {
            this.batchCounts[var10] = this.executeInternal((String)this.batchStmts.get(var10));
            if (this.currentRs != null) {
              throw TbError.newSQLException(-90630);
            }
          }

          int[] var3 = this.batchCounts;
          return var3;
        } catch (SQLException var8) {
          int[] var4 = new int[var2];
          System.arraycopy(this.batchCounts, 0, var4, 0, var2);
          this.batchCounts = var4;
          throw new BatchUpdateException(var8.getMessage(), var8.getSQLState(), var8.getErrorCode(), this.batchCounts);
        } finally {
          this.isExecuting = false;
          if (this.queryTimeout != 0) {
            this.conn.getTimeout().cancelTimeout();
          }

          this.clearBatch();
        }
      }
    }
  }

  private synchronized int executeInternal(String var1) throws SQLException {
    return this.executeInternal(var1, (Object)null);
  }

  private synchronized int executeInternal(String var1, Object var2) throws SQLException {
    this.initSql(var1);
    if (!this.isInsertStmt(var1)) {
      this.returnAutoGeneratedKeys = false;
    }

    int var3 = this.conn.typeConverter.getMaxBytesPerChar();
    int var6;
    if (this.originalSql.length() > '\uffff' / var3) {
      ArrayList var4 = new ArrayList();
      String var5 = TbSQLParser.getBigLiteral(this.originalSql, var3, var4);
      var6 = var4.size();
      if (var6 > 0) {
        TbPreparedStatement var7 = (TbPreparedStatement)this.conn.prepareStatement(var5);

        for(int var8 = 0; var8 < var6; ++var8) {
          BigLiteral var9 = (BigLiteral)var4.get(var8);
          var7.setString(var8 + 1, var9.getLiteralValue());
        }

        var4.clear();
        return var7.impl().executeInternal(var5);
      }
    }

    if (TbSQLTypeScanner.isQueryStmt(this.sqlType)) {
      if (this.userRsetType.getType() != 1005 && this.userRsetType.getConcurrency() != 1008) {
        try {
          this.realRsetType = this.userRsetType;
          synchronized(this.conn) {
            return this.conn.getTbComm().executeDirect(this, this.originalSql);
          }
        } catch (SQLException var23) {
          SQLException var25 = var23;
          if (!this.conn.isClosed() && this.conn.info.isFailoverCursorEnabled() && var23.getErrorCode() == -90700) {
            synchronized(this.conn) {
              var6 = this.conn.getTbComm().executeDirect(this, this.originalSql);
              this.addWarning(TbError.newSQLWarning(-90700, (Throwable)var25));
              return var6;
            }
          } else {
            throw var23;
          }
        }
      } else {
        this.sqlWithRowId = this.getQueryWithRowId(this.originalSql);

        try {
          this.realRsetType = this.userRsetType;
          synchronized(this.conn) {
            return this.conn.getTbComm().executeDirect(this, this.sqlWithRowId);
          }
        } catch (SQLException var20) {
          this.realRsetType = RsetType.getDownGradedRsetType(this.userRsetType.getRank());
          synchronized(this.conn) {
            return this.conn.getTbComm().executeDirect(this, this.originalSql);
          }
        }
      }
    } else if (!TbSQLTypeScanner.isDMLStmt(this.sqlType) && (TbSQLTypeScanner.isQueryStmt(this.sqlType) || TbSQLTypeScanner.isPSMStmt(this.sqlType))) {
      this.realRsetType = this.userRsetType;
      synchronized(this.conn) {
        return this.conn.getTbComm().executeDirect(this, this.originalSql);
      }
    } else {
      this.realRsetType = this.userRsetType;
      String var24 = this.originalSql;
      if (this.returnAutoGeneratedKeys) {
        if (this.realRsetType.isSensitive() || this.realRsetType.isUpdatable()) {
          this.realRsetType = RsetType.SIRD;
        }

        if (var2 == null) {
          var24 = this.getAutoGenSql(this.originalSql);
        } else if (var2 instanceof int[]) {
          var24 = this.getAutoGenSql(this.originalSql, (int[])((int[])var2));
        } else if (var2 instanceof String[]) {
          var24 = this.getAutoGenSql(this.originalSql, (String[])((String[])var2));
        }
      }

      synchronized(this.conn) {
        this.rowsUpdated = (long)this.conn.getTbComm().executeDirect(this, var24);
      }

      return (int)this.rowsUpdated;
    }
  }

  protected String getAutoGenSql(String var1) {
    StringBuffer var2 = new StringBuffer();
    var2.append(var1).append(" RETURNING ROWID INTO GENKEY0");
    return var2.toString();
  }

  protected String getAutoGenSql(String var1, String[] var2) {
    StringBuffer var3 = new StringBuffer();
    var3.append(var1).append(" RETURNING ");
    int var4 = var2.length;

    int var5;
    for(var5 = 0; var5 < var4; ++var5) {
      if (var5 > 0) {
        var3.append(", ");
      }

      var3.append(var2[var5]);
    }

    var3.append(" INTO ");

    for(var5 = 0; var5 < var4; ++var5) {
      if (var5 > 0) {
        var3.append(", ");
      }

      var3.append("GENKEY").append(var5);
    }

    return var3.toString();
  }

  protected String getAutoGenSql(String var1, int[] var2) throws SQLException {
    StringBuffer var3 = new StringBuffer();
    var3.append(var1).append(" RETURNING ");
    int var4 = var2.length;
    int var5 = var1.toUpperCase().indexOf("INTO", this.sqlTypeScanner.getCurrentIndex());
    if (var5 < 0) {
      TbError.newSQLException(-90608);
    }

    int var6 = var1.length();

    int var7;
    for(var7 = var5 + 5; var7 < var6 && var1.charAt(var7) == ' '; ++var7) {
    }

    if (var7 >= var6) {
      TbError.newSQLException(-90608);
    }

    int var8;
    for(var8 = var7 + 1; var8 < var6 && var1.charAt(var8) != ' ' && var1.charAt(var8) != '('; ++var8) {
    }

    String var9 = var1.substring(var7, var8);
    Statement var10 = null;

    String var23;
    try {
      var10 = this.conn.createStatement();
      ResultSet var11 = var10.executeQuery("SELECT * FROM " + var9);
      ResultSetMetaData var12 = var11.getMetaData();
      int var13 = var12.getColumnCount();

      int var14;
      for(var14 = 0; var14 < var4; ++var14) {
        if (var2[var14] < 1 || var2[var14] > var13) {
          TbError.newSQLException(-90608);
        }

        if (var14 > 0) {
          var3.append(", ");
        }

        var3.append(var12.getColumnName(var2[var14]));
      }

      var3.append(" INTO ");

      for(var14 = 0; var14 < var4; ++var14) {
        if (var14 > 0) {
          var3.append(", ");
        }

        var3.append("GENKEY").append(var14);
      }

      var23 = var3.toString();
    } finally {
      if (var10 != null) {
        try {
          var10.close();
        } catch (Exception var21) {
        }
      }

    }

    return var23;
  }

  public synchronized ResultSet executeQuery(String var1) throws SQLException {
    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    if (this.queryTimeout > 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    try {
      this.isExecuting = true;
      this.executeInternal(var1);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout > 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    if (this.currentRs == null) {
      this.currentRs = TbResultSetFactory.buildResultSet(this, -1, 0, 0, (byte[])null);
    }

    return (ResultSet) this.currentRs;
  }

  public synchronized int executeUpdate(String var1) throws SQLException {
    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    this.returnAutoGeneratedKeys = false;
    if (this.queryTimeout != 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    int var2;
    try {
      this.isExecuting = true;
      var2 = this.executeInternal(var1);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    return var2;
  }

  public int executeUpdate(String var1, int var2) throws SQLException {
    if (var2 == 2) {
      return this.executeUpdate(var1);
    } else {
      if (var2 != 1) {
        TbError.newSQLException(-590733);
      }

      this.checkConnectionOpen();
      if (this.batchStmts != null) {
        this.checkBatchStmtRemained();
        this.initBatchStmts();
      }

      this.initBeforeExecute();
      this.returnAutoGeneratedKeys = true;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
      }

      int var3;
      try {
        this.isExecuting = true;
        var3 = this.executeInternal(var1);
      } finally {
        this.isExecuting = false;
        if (this.queryTimeout != 0) {
          this.conn.getTimeout().cancelTimeout();
        }

      }

      return var3;
    }
  }

  public int executeUpdate(String var1, int[] var2) throws SQLException {
    if (var2 == null || var2.length == 0) {
      TbError.newSQLException(-590732);
    }

    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    if (this.queryTimeout != 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    int var3;
    try {
      this.isExecuting = true;
      var3 = this.executeInternal(var1, var2);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    return var3;
  }

  public int executeUpdate(String var1, String[] var2) throws SQLException {
    if (var2 == null || var2.length == 0) {
      TbError.newSQLException(-590732);
    }

    this.checkConnectionOpen();
    if (this.batchStmts != null) {
      this.checkBatchStmtRemained();
      this.initBatchStmts();
    }

    this.initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    if (this.queryTimeout != 0) {
      this.conn.getTimeout().setTimeout((long)(this.queryTimeout * 1000), this);
    }

    int var3;
    try {
      this.isExecuting = true;
      var3 = this.executeInternal(var1, var2);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0) {
        this.conn.getTimeout().cancelTimeout();
      }

    }

    return var3;
  }

  public synchronized void fetch(TbResultSetBase var1) throws SQLException {
    synchronized(this.conn) {
      this.conn.getTbComm().fetch(this, var1);
    }
  }

  public Connection getConnection() throws SQLException {
    this.checkConnectionOpen();
    return this.conn;
  }

  public int getCurCsrId() {
    return this.curCsrId;
  }

  public int getFetchDirection() throws SQLException {
    return this.fetchDirection;
  }

  public int getFetchSize() throws SQLException {
    return this.fetchSize;
  }

  public ResultSet getGeneratedKeys() throws SQLException {
    if (this.returnAutoGeneratedKeys) {
      return (ResultSet) this.currentRs;
    } else {
      throw TbError.newSQLException(-90600);
    }
  }

  public int getMaxFieldSize() throws SQLException {
    return this.maxFieldSize;
  }

  public int getMaxRows() throws SQLException {
    return this.maxRow;
  }

  public synchronized boolean getMoreResults() throws SQLException {
    return false;
  }

  public boolean getMoreResults(int var1) throws SQLException {
    throw TbError.newSQLException(-90201);
  }

  public String getOriginalSql() {
    return this.originalSql;
  }

  protected void initSql(String var1) throws SQLException {
    if (var1 != null && var1.length() > 0) {
      String var2 = null;
      if (this.conn.serverInfo.getServerCharSet() == 3) {
        var2 = TbSQLParser.replaceIDEOGraphicSpace(var1);
      } else {
        var2 = var1;
      }

      this.sqlTypeScanner = new TbSQLTypeScanner();
      var2 = this.sqlTypeScanner.callDeclareSyntax(var2);
      this.sqlType = this.sqlTypeScanner.getSQLType(var2);
      if (136 != this.sqlType) {
        if (this.enableEscapeProcessing && var2.indexOf(123) >= 0) {
          var2 = this.sqlParser.parse(var2);
        }

        if (var2.indexOf(63) > 0) {
          var2 = TbSQLParser.replace(var2);
        }
      }

      this.originalSql = var2;
    } else {
      throw TbError.newSQLException(-590737);
    }
  }

  public Vector<byte[]> getPivotData() {
    return this.pivotData;
  }

  public TbPivotInfo[] getPivotInfo() {
    return this.pivotInfo;
  }

  public int getPreFetchSize() {
    return TbSQLTypeScanner.isQueryStmt(this.getSqlType()) && this.getRealRsetType().getType() != 1005 ? this.preFetchSize : 0;
  }

  public int getQueryTimeout() throws SQLException {
    return this.queryTimeout;
  }

  protected String getQueryWithRowId(String var1) {
    StringBuffer var2 = new StringBuffer(100);
    String var3 = var1.toUpperCase();
    int var4 = var3.lastIndexOf("FOR");
    int var5 = var3.lastIndexOf("UPDATE");
    if (var3.lastIndexOf("\"") < var4 && var4 < var5) {
      var2.append(var1).append(") ROWID_ALIAS0");
      var2.delete(var4, var4 + 3);
      var2.delete(var5 - 3, var5 + 3);
      var2.insert(0, "select ROWIDTOCHAR(ROWID) _ROWID_CHAR0_, ROWID_ALIAS0.* from (");
      var2.append(" FOR UPDATE");
    } else {
      var2.append("select ROWIDTOCHAR(ROWID) _ROWID_CHAR0_, ROWID_ALIAS0.* from (").append(var1).append(") ROWID_ALIAS0");
    }

    return var2.toString();
  }

  public RsetType getUserRsetType() {
    return this.userRsetType;
  }

  public RsetType getRealRsetType() {
    return this.realRsetType;
  }

  public ResultSet getResultSet() throws SQLException {
    return this.returnAutoGeneratedKeys ? null : (ResultSet) this.currentRs;
  }

  public int getResultSetConcurrency() throws SQLException {
    return this.userRsetType.getConcurrency();
  }

  public int getResultSetHoldability() throws SQLException {
    return this.userRsetType.getHoldability();
  }

  public int getResultSetType() throws SQLException {
    return this.userRsetType.getType();
  }

  public int getSqlType() {
    return this.sqlType;
  }

  public String getSqlWithRowId() {
    return this.sqlWithRowId;
  }

  public int getUpdateCount() throws SQLException {
    if (this.batchCounts == null) {
      if (!this.noMoreUpdateCount) {
        this.noMoreUpdateCount = true;
        return (int)this.rowsUpdated;
      } else {
        return -1;
      }
    } else {
      int var1 = 0;

      for(int var2 = 0; var2 < this.batchCounts.length; ++var2) {
        var1 += this.batchCounts[var2];
      }

      return var1;
    }
  }

  public SQLWarning getWarnings() throws SQLException {
    return this.warnings;
  }

  protected void initBatchStmts() {
    if (this.batchStmts != null) {
      this.batchStmts.clear();
      this.batchStmts = null;
    }

  }

  protected void initBeforeExecute() throws SQLException {
    this.batchCounts = null;
    this.rowsUpdated = -1L;
    this.noMoreUpdateCount = false;
    if (this.currentRs != null) {
      this.currentRs.close();
      this.currentRs = null;
    }

  }

  public boolean isClosed() throws SQLException {
    return this.closed;
  }

  public boolean isPoolable() throws SQLException {
    if (this.isClosed()) {
      throw TbError.newSQLException(-90659);
    } else {
      return this.poolable;
    }
  }

  @Override
  public void closeOnCompletion() throws SQLException {

  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return false;
  }

  public boolean isWrapperFor(Class<?> var1) throws SQLException {
    return var1.isInstance(this);
  }

  protected void reset() {
    this.closed = true;
    this.closeOnCompletion = false;
    this.warnings = null;
    this.conn = null;
    this.typeConverter = null;
    this.userRsetType = null;
    this.noMoreUpdateCount = false;
    this.returnAutoGeneratedKeys = false;
    this.currentRs = null;
    this.batchCounts = null;
    this.maxRow = 0;
    this.rsetTypeDowngraded = false;
    this.subResultSets.removeAll(this.subResultSets);
    if (this.batchStmts != null) {
      this.batchStmts.clear();
      this.batchStmts = null;
    }

  }

  public void resetForCache() {
    this.currentRs = null;
    this.batchCounts = null;
    this.warnings = null;
    this.subResultSets.removeAll(this.subResultSets);
    if (this.batchStmts != null) {
      this.batchStmts.clear();
      this.batchStmts = null;
    }

  }

  public void setCurCsrId(int var1) {
    this.curCsrId = var1;
  }

  public void setCursorName(String var1) throws SQLException {
  }

  public synchronized void setEscapeProcessing(boolean var1) throws SQLException {
    this.enableEscapeProcessing = var1;
  }

  public synchronized void setFetchDirection(int var1) throws SQLException {
    switch(var1) {
      case 1000:
      case 1002:
        this.fetchDirection = 1000;
        return;
      case 1001:
        this.fetchDirection = 1001;
      default:
        throw TbError.newSQLException(-590738);
    }
  }

  public synchronized void setFetchSize(int var1) throws SQLException {
    if (var1 == 0) {
      this.fetchSize = 50;
    } else {
      if (var1 <= 0) {
        throw TbError.newSQLException(-590735);
      }

      this.fetchSize = var1;
    }

  }

  public synchronized void setPreparedFetchSize(int var1) throws SQLException {
    if (var1 == 0) {
      this.fetchSize = 50;
    } else {
      if (var1 <= 0) {
        throw TbError.newSQLException(-590735);
      }

      this.fetchSize = var1 > 64 ? -64 : var1 * -1;
    }

  }

  public void setMaxFieldSize(int var1) throws SQLException {
    if (var1 < 0) {
      throw TbError.newSQLException(-590739);
    } else {
      this.maxFieldSize = var1;
    }
  }

  public synchronized void setMaxRows(int var1) throws SQLException {
    if (var1 < 0) {
      throw TbError.newSQLException(-590740);
    } else {
      this.maxRow = var1;
    }
  }

  public void setPivotInfo(TbPivotInfo[] var1) {
    this.pivotInfo = var1;
  }

  public void setPoolable(boolean var1) throws SQLException {
    this.poolable = var1;
  }

  public synchronized void setQueryTimeout(int var1) throws SQLException {
    if (var1 < 0) {
      throw TbError.newSQLException(-590741);
    } else {
      this.queryTimeout = var1;
    }
  }

  public void setResultSet(TbResultSet var1) {
    this.currentRs = var1;
  }

  public void addSubResultSet(TbResultSet var1) {
    this.subResultSets.add(var1);
  }

  public synchronized void setRowPreFetch(int var1) throws SQLException {
    if (var1 < 0) {
      throw TbError.newSQLException(-590735);
    } else {
      this.preFetchSize = var1;
      if (var1 == 0) {
        this.setFetchSize(50);
      } else {
        this.setFetchSize(var1);
      }

    }
  }

  public <T> T unwrap(Class<T> var1) throws SQLException {
    try {
      return var1.cast(this);
    } catch (ClassCastException var3) {
      throw TbError.newSQLException(-90657);
    }
  }

  boolean isReturnAutoGeneratedKeys() {
    return this.returnAutoGeneratedKeys;
  }

  protected void setReturnAutoGeneratedKeys(boolean var1) {
    this.returnAutoGeneratedKeys = var1;
  }
}