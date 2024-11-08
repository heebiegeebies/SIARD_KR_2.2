package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbArrayDescriptor;
import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.TbClob;
import com.tmax.tibero.jdbc.TbDatabaseMetaData;
import com.tmax.tibero.jdbc.TbNClob;
import com.tmax.tibero.jdbc.TbSQLInfo;
import com.tmax.tibero.jdbc.TbSQLInfo2;
import com.tmax.tibero.jdbc.TbSQLXML;
import com.tmax.tibero.jdbc.TbStruct;
import com.tmax.tibero.jdbc.TbStructDescriptor;
import com.tmax.tibero.jdbc.TbTypeDescriptor;
import com.tmax.tibero.jdbc.comm.TbBlobAccessor;
import com.tmax.tibero.jdbc.comm.TbClobAccessor;
import com.tmax.tibero.jdbc.comm.TbComm;
import com.tmax.tibero.jdbc.comm.TbCommType4;
import com.tmax.tibero.jdbc.comm.TbXAComm;
import com.tmax.tibero.jdbc.comm.TbXACommType4;
import com.tmax.tibero.jdbc.data.ConnectionInfo;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.ExtFeatureInfo;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.data.ServerInfo;
import com.tmax.tibero.jdbc.dpl.TbDirPathMetaData;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.ext.TbStatementCache;
import com.tmax.tibero.jdbc.util.TbDTFormat;
import com.tmax.tibero.jdbc.util.TbDTFormatParser;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class TbConnection extends com.tmax.tibero.jdbc.TbConnection {
  final String _DESC_TOBJ_ID_PREFIX = "/T";

  final String _DESC_VERSION_NO_PREFIX = "/V";

  public static final String CLIENT_INFO_IDENTIFIER = "client_identifier";

  public static final String[] CLIENT_INFO_KEYS = new String[] { "client_identifier" };

  private TbComm dbComm;

  public ConnectionInfo info;

  protected boolean isPooledConnection;

  public ServerInfo serverInfo;

  private String sessKey;

  protected DataTypeConverter typeConverter;

  protected SQLWarning warnings;

  protected boolean autoCommit;

  protected boolean connClosed;

  protected boolean sessionClosed;

  protected TbDatabaseMetaData dbMetaData = null;

  protected Map<String, Class<?>> typeMap;

  protected Map<String, TbTypeDescriptor> descriptorMap;

  private String nlsDate = null;

  private String nlsTimestamp = null;

  private String nlsCalendar = "GREGORIAN";

  private boolean nlsWarning = false;

  private int sessionId;

  private int serialNo;

  private int mthrPid = -1;

  private int txnMode;

  private boolean readOnly;

  protected int isolationLevel = 2;

  private TbTimeout timeout;

  private int preFetchSize = 64000;

  private TbStatementCache stmtCache = null;

  public String[] clientInfo;

  private boolean middleOfFailover;

  private boolean reconnected;

  private long waitingTime;

  private boolean checkWaitingTimer = false;

  private int maxDFRCharCount = 0;

  private int maxDFRNCharCount = 0;

  private ExtFeatureInfo efInfo;

  private TbDTFormat parsedNlsDateFormat = null;

  private TbDTFormat parsedNlsTimestampFormat = null;

  private LinkedList<TbResultSetBase> foActiveRsets;

  public void activateTimer() {
    this.checkWaitingTimer = true;
  }

  public boolean addFOActiveResultSet(TbResultSetBase paramTbResultSetBase) {
    return this.foActiveRsets.add(paramTbResultSetBase);
  }

  public void addWaitingTime(long paramLong) {
    this.waitingTime += paramLong;
  }

  public void addWarning(SQLWarning paramSQLWarning) {
    if (this.warnings != null) {
      this.warnings.setNextWarning(paramSQLWarning);
    } else {
      this.warnings = paramSQLWarning;
    }
  }

  public void clearWarnings() throws SQLException {
    this.warnings = null;
  }

  public synchronized void close() throws SQLException {
    if (!this.connClosed)
      try {
        if (this.timeout != null)
          this.timeout.close();
        if (this.dbComm != null) {
          this.dbComm.close();
          this.dbComm = null;
        }
        this.connClosed = true;
      } finally {
        reset();
      }
  }

  public synchronized void closeCursor(TbResultSet paramTbResultSet, int paramInt) throws SQLException {
    this.dbComm.closeCursor(paramTbResultSet, paramInt);
  }

  @Deprecated
  public synchronized void closeSession() throws SQLException {
    throw TbError.newSQLException(-90201);
  }

  public synchronized void commit() throws SQLException {
    if (this.connClosed || this.dbComm.getStream() == null)
      throw TbError.newSQLException(-90603);
    this.dbComm.commit();
  }

  public Array createArrayOf(String paramString, Object[] paramArrayOfObject) throws SQLException {
    TbArray tbArrayNull = null;
    String str1 = this.info.getUser();
    String str2 = ".";
    String[] arrayOfString = paramString.split("[delim]{1}");
    if (arrayOfString.length != 1)
      if (arrayOfString.length == 2) {
        str1 = arrayOfString[0];
        paramString = arrayOfString[1];
      } else {
        throw TbError.newSQLException(-90649);
      }
    String str3 = str1 + str2 + paramString;
    TbArrayDescriptor tbArrayDescriptor = TbArrayDescriptor.lookupUdtMeta(str1, paramString, this);
    return (Array)new TbArray(tbArrayDescriptor, this, paramArrayOfObject);
  }

  public Blob createBlob() throws SQLException {
    return (Blob)createTbBlob();
  }

  public TbBlob createTbBlob() throws SQLException {
    byte[] arrayOfByte = getBlobAccessor().createTemporaryBlob();
    return new TbBlob(this, arrayOfByte, false);
  }

  public Clob createClob() throws SQLException {
    return (Clob)createTbClob();
  }

  public TbClob createTbClob() throws SQLException {
    byte[] arrayOfByte = getClobAccessor().createTemporaryClob();
    return new TbClob(this, arrayOfByte, false);
  }

  public TbDirPathStream createDirPathStream(TbDirPathMetaData paramTbDirPathMetaData) throws SQLException {
    return new TbDirPathStream(this, paramTbDirPathMetaData);
  }

  public NClob createNClob() throws SQLException {
    return (NClob)createTbNClob();
  }

  public TbNClob createTbNClob() throws SQLException {
    byte[] arrayOfByte = getClobAccessor().createTemporaryNClob();
    return new TbNClob(this, arrayOfByte, false);
  }

  public SQLXML createSQLXML() throws SQLException {
    return (SQLXML)new TbSQLXML(this);
  }

  public synchronized Statement createStatement() throws SQLException {
    return this.createStatement(1003, 1007);
  }

  public synchronized Statement createStatement(int paramInt1, int paramInt2) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (this.stmtCache != null) {
      com.tmax.tibero.jdbc.TbStatement tbStatement = this.stmtCache.get(null, 0, RsetType.getRsetType(paramInt1, paramInt2));
      if (tbStatement != null)
        return tbStatement;
    }
    return new TbStatement(this, paramInt1, paramInt2, this.preFetchSize);
  }

  public Statement createStatement(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (paramInt3 != getHoldability())
      addWarning(TbError.newSQLWarning(-90660));
    if (this.stmtCache != null) {
      com.tmax.tibero.jdbc.TbStatement tbStatement = this.stmtCache.get(null, 0, RsetType.getRsetType(paramInt1, paramInt2));
      if (tbStatement != null)
        return (Statement)tbStatement;
    }
    return new TbStatement(this, paramInt1, paramInt2, this.preFetchSize);
  }

  public Struct createStruct(String paramString, Object[] paramArrayOfObject) throws SQLException {
    String str1 = this.info.getUser();
    String str2 = ".";
    String[] arrayOfString = paramString.split("[" + str2 + "]{1}");
    if (arrayOfString.length != 1)
      if (arrayOfString.length == 2) {
        str1 = arrayOfString[0];
        paramString = arrayOfString[1];
      } else {
        throw TbError.newSQLException(-90649);
      }
    TbStructDescriptor tbStructDescriptor = TbStructDescriptor.lookupUdtMeta(str1, paramString, this);
    return (Struct)new TbStruct(tbStructDescriptor, this, paramArrayOfObject);
  }

  @Override
  public void setSchema(String schema) throws SQLException {

  }

  @Override
  public String getSchema() throws SQLException {
    return "";
  }

  @Override
  public void abort(Executor executor) throws SQLException {

  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return 0;
  }

  public void disableStmtCache() {
    this.info.setStmtCache(false);
  }

  void disallowGlobalTxnMode(int paramInt) throws SQLException {
    if (this.txnMode == 2)
      throw TbError.newSQLException(paramInt);
  }

  public void enableStmtCache() {
    this.info.setStmtCache(true);
  }

  public boolean getAutoCommit() throws SQLException {
    return this.autoCommit;
  }

  public TbBlobAccessor getBlobAccessor() {
    return (TbBlobAccessor)this.dbComm;
  }

  public String getCatalog() throws SQLException {
    return null;
  }

  public boolean getIncludeSynonyms() {
    return this.info.getIncludeSynonyms();
  }

  public boolean getMapDateToTimestamp() {
    return this.info.getMapDateToTimestamp();
  }

  public void setIncludeSynonyms(boolean paramBoolean) {
    this.info.setIncludeSynonyms(paramBoolean);
  }

  public Properties getClientInfo() throws SQLException {
    return new Properties();
  }

  public String getClientInfo(String paramString) throws SQLException {
    if (this.clientInfo == null)
      return null;
    for (byte b = 0; b < CLIENT_INFO_KEYS.length; b++) {
      if (CLIENT_INFO_KEYS[b].equals(paramString))
        return this.clientInfo[b];
    }
    return null;
  }

  public TbClobAccessor getClobAccessor() {
    return (TbClobAccessor)this.dbComm;
  }

  public int getDefaultRowPrefetch() {
    return this.preFetchSize;
  }

  public List<TbResultSetBase> getFOActiveRSetList() {
    return this.foActiveRsets;
  }

  public int getHoldability() throws SQLException {
    return 1;
  }

  public TbSQLInfo getLastExecutedSqlinfo() throws SQLException {
    return this.dbComm.getLastExecutedSqlinfo();
  }

  public TbSQLInfo2 getLastExecutedSqlinfo2() throws SQLException {
    return this.dbComm.getLastExecutedSqlinfo2();
  }

  public int getMaxDFRCharCount() {
    return this.maxDFRCharCount;
  }

  public int getMaxDFRNCharCount() {
    return this.maxDFRNCharCount;
  }

  public DatabaseMetaData getMetaData() throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    this.dbMetaData = new TbDatabaseMetaData(this);
    return (DatabaseMetaData)this.dbMetaData;
  }

  public int getMthrPid() {
    return this.mthrPid;
  }

  public String getNlsCalendar() {
    return this.nlsCalendar;
  }

  public String getNlsDate() {
    return this.nlsDate;
  }

  public String getNlsTimestamp() {
    return this.nlsTimestamp;
  }

  public int getSerialNo() {
    return this.serialNo;
  }

  public int getServerCharSet() {
    return this.serverInfo.getServerCharSet();
  }

  public ServerInfo getServerInfo() {
    return this.serverInfo;
  }

  public String getSessKey() {
    return this.sessKey;
  }

  public int getServerNCharSet() {
    return this.serverInfo.getServerNCharSet();
  }

  public int getSessionId() {
    return this.sessionId;
  }

  public TbStatementCache getStmtCache() {
    return this.stmtCache;
  }

  public TbComm getTbComm() {
    return this.dbComm;
  }

  public TbXAComm getTbXAComm() {
    return (TbXAComm)this.dbComm;
  }

  public TbTimeout getTimeout() throws SQLException {
    if (this.timeout == null)
      this.timeout = TbTimeout.newTimeout();
    return this.timeout;
  }

  public synchronized int getTransactionIsolation() throws SQLException {
    return this.isolationLevel;
  }

  public int getTxnMode() {
    return this.txnMode;
  }

  public DataTypeConverter getTypeConverter() {
    return this.typeConverter;
  }

  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return this.typeMap;
  }

  public long getWaitingTime() {
    return this.waitingTime;
  }

  public SQLWarning getWarnings() throws SQLException {
    return this.warnings;
  }

  public boolean isActivatedTimer() {
    return this.checkWaitingTimer;
  }

  public boolean isClosed() {
    return this.connClosed;
  }

  public boolean isMiddleOfFailover() {
    return this.middleOfFailover;
  }

  public boolean isPooledConnection() {
    return this.isPooledConnection;
  }

  public boolean isReadOnly() throws SQLException {
    return this.readOnly;
  }

  public boolean isReconnected() {
    return this.reconnected;
  }

  public boolean isSessionClosed() {
    return this.sessionClosed;
  }

  public boolean isValid(int paramInt) throws SQLException {
    if (isClosed() || this.dbComm == null)
      return false;
    if (paramInt == 0) {
      try {
        this.dbComm.ping();
      } catch (SQLException sQLException) {
        return false;
      }
      return true;
    }
    Statement statement = null;
    statement = createStatement();
    ((TbStatement)statement).setQueryTimeout(paramInt);
    try {
      statement.execute("SELECT 1 FROM DUAL");
    } catch (SQLException sQLException) {
      return false;
    } finally {
      if (statement != null)
        statement.close();
    }
    return true;
  }

  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }

  public String nativeSQL(String paramString) throws SQLException {
    return paramString;
  }

  public void openConnection(ConnectionInfo paramConnectionInfo, boolean paramBoolean) throws SQLException {
    reuse();
    if (!this.middleOfFailover) {
      this.typeMap = new HashMap<String, Class<?>>();
      this.descriptorMap = new HashMap<String, TbTypeDescriptor>();
      TbTypeDescriptor.genPredefUDTDescs(this);
      this.foActiveRsets = new LinkedList<TbResultSetBase>();
    }
    this.info = (ConnectionInfo)paramConnectionInfo.clone();
    this.typeConverter = new DataTypeConverter(this);
    if (this.info.isStmtCache())
      this.stmtCache = new TbStatementCache(this.info.getStmtCacheMaxSize());
    String str = this.info.getDriverType();
    if (str.equals("thin")) {
      if (this.info.isXA()) {
        this.dbComm = (TbComm)new TbXACommType4(this);
      } else {
        this.dbComm = (TbComm)new TbCommType4(this);
      }
    } else {
      throw TbError.newSQLException(-590723, str);
    }
    this.dbComm.createStream();
    this.dbComm.getStream().setSelfKeepAliveEnabled(false);
    if (this.info.isInternal()) {
      this.dbComm.describeConnectInfo();
      this.dbComm.describeSessInfo();
      setAutoCommit(false);
    } else {
      try {
        this.dbComm.logon(paramBoolean);
      } catch (SQLException sQLException) {
        if (!this.sessionClosed && !this.connClosed)
          return;
        throw sQLException;
      }
    }
    this.dbComm.getStream().setSelfKeepAliveEnabled(this.info.useSelfKeepAlive());
    this.dbComm.getStream().setSoTimeout(this.info.getReadTimeout());
    this.sessionClosed = false;
    this.connClosed = false;
    this.efInfo = new ExtFeatureInfo(this);
    if (this.middleOfFailover)
      this.reconnected = true;
  }

  @Deprecated
  public void openSession() throws SQLException {
    throw TbError.newSQLException(-90201);
  }

  public synchronized CallableStatement prepareCall(String paramString) throws SQLException {
    return prepareCall(paramString, 1003, 1007);
  }

  public synchronized CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (this.stmtCache != null) {
      com.tmax.tibero.jdbc.TbStatement tbStatement = this.stmtCache.get(paramString, 2, RsetType.getRsetType(paramInt1, paramInt2));
      if (tbStatement instanceof TbCallableStatement)
        return (CallableStatement)tbStatement;
    }
    return (CallableStatement)new TbCallableStatement(this, paramString, paramInt1, paramInt2, this.preFetchSize);
  }

  public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (this.stmtCache != null) {
      com.tmax.tibero.jdbc.TbStatement tbStatement = this.stmtCache.get(paramString, 2, RsetType.getRsetType(paramInt1, paramInt2));
      if (tbStatement instanceof TbCallableStatement)
        return (CallableStatement)tbStatement;
    }
    if (paramInt3 != getHoldability())
      addWarning(TbError.newSQLWarning(-90660));
    return (CallableStatement)new TbCallableStatement(this, paramString, paramInt1, paramInt2, this.preFetchSize);
  }

  public synchronized PreparedStatement prepareStatement(String paramString) throws SQLException {
    return prepareStatement(paramString, 1003, 1007, false);
  }

  public synchronized PreparedStatement prepareStatement(String paramString, boolean paramBoolean) throws SQLException {
    return prepareStatement(paramString, 1003, 1007, paramBoolean);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt) throws SQLException {
    TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)prepareStatement(paramString);
    if (36 == tbPreparedStatement.getSqlType())
      tbPreparedStatement.setReturnAutoGeneratedKeys((paramInt == 1));
    return (PreparedStatement)tbPreparedStatement;
  }

  public synchronized PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2) throws SQLException {
    return prepareStatement(paramString, paramInt1, paramInt2, false);
  }

  public synchronized PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (this.stmtCache != null) {
      com.tmax.tibero.jdbc.TbStatement tbStatement = this.stmtCache.get(paramString, 1, RsetType.getRsetType(paramInt1, paramInt2));
      if (tbStatement instanceof TbPreparedStatement)
        return (PreparedStatement)tbStatement;
    }
    return (PreparedStatement)new TbPreparedStatement(this, paramString, paramInt1, paramInt2, this.preFetchSize, paramBoolean);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (this.stmtCache != null) {
      com.tmax.tibero.jdbc.TbStatement tbStatement = this.stmtCache.get(paramString, 1, RsetType.getRsetType(paramInt1, paramInt2));
      if (tbStatement instanceof TbPreparedStatement)
        return (PreparedStatement)tbStatement;
    }
    if (paramInt3 != getHoldability())
      addWarning(TbError.newSQLWarning(-90660));
    return (PreparedStatement)new TbPreparedStatement(this, paramString, paramInt1, paramInt2, this.preFetchSize, false);
  }

  public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfint) throws SQLException {
    if (paramArrayOfint == null || paramArrayOfint.length == 0)
      TbError.newSQLException(-590732);
    TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)prepareStatement(paramString);
    if (36 == tbPreparedStatement.getSqlType()) {
      tbPreparedStatement.setReturnAutoGeneratedKeys(true);
      tbPreparedStatement.setAutoGenKeyArr(paramArrayOfint);
    }
    return (PreparedStatement)tbPreparedStatement;
  }

  public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString) throws SQLException {
    if (paramArrayOfString == null || paramArrayOfString.length == 0)
      TbError.newSQLException(-590732);
    TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)prepareStatement(paramString);
    if (36 == tbPreparedStatement.getSqlType()) {
      tbPreparedStatement.setReturnAutoGeneratedKeys(true);
      tbPreparedStatement.setAutoGenKeyArr(paramArrayOfString);
    }
    return (PreparedStatement)tbPreparedStatement;
  }

  public void reconnect(boolean paramBoolean) {
    if (this.info.isFailoverSessionEnabled()) {
      boolean bool = true;
      ConnectionInfo connectionInfo = this.info;
      if (!paramBoolean)
        setMiddleOfFailover(true);
      try {
        bool = getAutoCommit();
        close();
      } catch (SQLException sQLException) {}
      try {
        openConnection(connectionInfo, false);
        setAutoCommit(bool);
      } catch (SQLException sQLException) {
        if (!isReconnected()) {
          try {
            close();
          } catch (SQLException sQLException1) {}
        } else {
          try {
            setAutoCommit(bool);
          } catch (SQLException sQLException1) {}
          setReconnected(false);
        }
      }
      setMiddleOfFailover(paramBoolean);
    } else {
      try {
        close();
      } catch (SQLException sQLException) {}
    }
  }

  public synchronized void releaseSavepoint(Savepoint paramSavepoint) throws SQLException {
    throw TbError.newSQLException(-90201);
  }

  public boolean removeFOActiveResultSet(TbResultSetBase paramTbResultSetBase) {
    return this.foActiveRsets.remove(paramTbResultSetBase);
  }

  public void reset() {
    this.info = null;
    this.serverInfo = null;
    this.sessKey = null;
    this.warnings = null;
    this.dbMetaData = null;
    this.autoCommit = true;
    this.sessionClosed = true;
    this.txnMode = 0;
    this.readOnly = false;
    this.isolationLevel = 2;
    this.nlsDate = null;
    this.nlsTimestamp = null;
    this.preFetchSize = 64000;
    this.clientInfo = null;
    this.reconnected = false;
    this.efInfo = null;
    if (this.typeConverter != null) {
      this.typeConverter.reset();
      this.typeConverter = null;
    }
    if (this.stmtCache != null) {
      this.stmtCache.clear();
      this.stmtCache = null;
    }
    if (!isMiddleOfFailover()) {
      this.typeMap = null;
      this.descriptorMap = null;
    }
  }

  public synchronized void resetSession() throws SQLException {
    if (this.serverInfo != null) {
      int i = this.serverInfo.getProtocolMajorVersion();
      int j = this.serverInfo.getProtocolMinorVersion();
      if (i > 2 || (i == 2 && j > 1))
        this.dbComm.resetSession();
    }
  }

  public void reuse() {
    this.warnings = null;
    this.autoCommit = true;
    this.sessionClosed = true;
    this.txnMode = 0;
    this.readOnly = false;
    this.isolationLevel = 2;
    this.preFetchSize = 64000;
    this.clientInfo = null;
    this.reconnected = false;
    if (this.typeMap != null)
      this.typeMap.clear();
    if (this.descriptorMap != null)
      this.descriptorMap.clear();
  }

  public synchronized void rollback() throws SQLException {
    if (this.connClosed || this.dbComm.getStream() == null)
      throw TbError.newSQLException(-90603);
    this.dbComm.rollback();
  }

  public synchronized void rollback(Savepoint paramSavepoint) throws SQLException {
    if (this.connClosed || this.dbComm.getStream() == null)
      throw TbError.newSQLException(-90603);
    if (this.autoCommit)
      throw TbError.newSQLException(-90601);
    this.dbComm.rollback((TbSavepoint)paramSavepoint);
  }

  public synchronized void setAutoCommit(boolean paramBoolean) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    if (this.info.isInternal()) {
      this.autoCommit = false;
    } else {
      this.autoCommit = paramBoolean;
    }
  }

  public void setCatalog(String paramString) throws SQLException {}

  public synchronized void setClientInfo(Properties paramProperties) throws SQLClientInfoException {
    if (this.clientInfo == null)
      this.clientInfo = new String[CLIENT_INFO_KEYS.length];
    for (byte b = 0; b < CLIENT_INFO_KEYS.length; b++)
      this.clientInfo[b] = (String)paramProperties.get(CLIENT_INFO_KEYS[b]);
    this.dbComm.setClientInfo(this.clientInfo);
  }

  public synchronized void setClientInfo(String paramString1, String paramString2) throws SQLClientInfoException {
    if (this.clientInfo == null)
      this.clientInfo = new String[CLIENT_INFO_KEYS.length];
    for (byte b = 0; b < CLIENT_INFO_KEYS.length; b++) {
      if (CLIENT_INFO_KEYS[b].equals(paramString1))
        this.clientInfo[b] = paramString2;
    }
    this.dbComm.setClientInfo(this.clientInfo);
  }

  public void setClosed(boolean paramBoolean) {
    this.connClosed = paramBoolean;
  }

  public void setDefaultRowPrefetch(int paramInt) {
    this.preFetchSize = paramInt;
  }

  public void setHoldability(int paramInt) throws SQLException {
    switch (paramInt) {
      case 1:
        return;
      case 2:
        throw TbError.newSQLException(-90660);
    }
    throw TbError.newSQLException(-90608);
  }

  public void setMaxDFRCharCount() {
    int i = this.typeConverter.getMaxBytesPerChar();
    if (i == 1)
      i = 2;
    this.maxDFRCharCount = 65532 / i;
    int j = this.typeConverter.getMaxBytesPerNChar();
    if (j == 1)
      j = 2;
    this.maxDFRNCharCount = 65532 / j;
  }

  public void setMiddleOfFailover(boolean paramBoolean) {
    this.middleOfFailover = paramBoolean;
  }

  public void setMthrPid(int paramInt) {
    this.mthrPid = paramInt;
  }

  public void setNLSDate(String paramString) {
    this.nlsDate = paramString;
    try {
      this.parsedNlsDateFormat = TbDTFormatParser.parse(this.nlsDate);
    } catch (SQLWarning sQLWarning) {
      addWarning(sQLWarning);
      this.parsedNlsDateFormat = null;
    }
  }

  public void setNLSTimestamp(String paramString) {
    this.nlsTimestamp = paramString;
    try {
      this.parsedNlsTimestampFormat = TbDTFormatParser.parse(this.nlsTimestamp);
    } catch (SQLWarning sQLWarning) {
      addWarning(sQLWarning);
      this.parsedNlsTimestampFormat = null;
    }
  }

  public void setNLSCalandar(String paramString) {
    this.nlsCalendar = paramString;
  }

  public boolean isNLSWarning() {
    return this.nlsWarning;
  }

  public void setNLSWarning(boolean paramBoolean) {
    this.nlsWarning = paramBoolean;
  }

  public void setPooledConnection(boolean paramBoolean) {
    this.isPooledConnection = paramBoolean;
  }

  public void setReadOnly(boolean paramBoolean) throws SQLException {
    this.readOnly = paramBoolean;
  }

  public void setReconnected(boolean paramBoolean) {
    this.reconnected = paramBoolean;
  }

  public synchronized Savepoint setSavepoint() throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    TbSavepoint tbSavepoint = new TbSavepoint();
    this.dbComm.setSavePoint(tbSavepoint);
    return tbSavepoint;
  }

  public synchronized Savepoint setSavepoint(String paramString) throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    TbSavepoint tbSavepoint = new TbSavepoint(paramString);
    this.dbComm.setSavePoint(tbSavepoint);
    return tbSavepoint;
  }

  public void setSerialNo(int paramInt) {
    this.serialNo = paramInt;
  }

  public void setServerInfo(ServerInfo paramServerInfo) {
    this.serverInfo = paramServerInfo;
  }

  public void setSessKey(String paramString) {
    this.sessKey = paramString;
  }

  public void setSessionId(int paramInt) {
    this.sessionId = paramInt;
  }

  public synchronized void setTransactionIsolation(int paramInt) throws SQLException {
    if (paramInt != 2 && paramInt != 8)
      throw TbError.newSQLException(-590722);
    Statement statement = null;
    try {
      statement = createStatement();
      if (paramInt == 8) {
        statement.execute("alter session set isolation_level=serializable");
      } else {
        statement.execute("alter session set isolation_level=read committed");
      }
    } finally {
      if (statement != null)
        statement.close();
    }
    this.isolationLevel = paramInt;
  }

  public void setTxnMode(int paramInt) {
    this.txnMode = paramInt;
  }

  public synchronized void setTypeMap(Map<String, Class<?>> paramMap) throws SQLException {
    this.typeMap = paramMap;
  }

  public void switchTxnMode(int paramInt1, int paramInt2) {
    if (this.txnMode == paramInt1)
      this.txnMode = paramInt2;
  }

  public synchronized void putDescriptor(String paramString, TbTypeDescriptor paramTbTypeDescriptor) throws SQLException {
    if (paramString == null || paramTbTypeDescriptor == null)
      throw TbError.newSQLException(-590713);
    if (this.descriptorMap == null)
      this.descriptorMap = new HashMap<String, TbTypeDescriptor>();
    int i = paramTbTypeDescriptor.getTobjID();
    int j = paramTbTypeDescriptor.getVersionNo();
    String str = paramString + "/T" + i + "/V" + j;
    this.descriptorMap.put(str, paramTbTypeDescriptor);
    if (TbTypeDescriptor.preDefinedOIDList.contains(paramString) || TbTypeDescriptor.preDefinedTypeNameList.contains(paramString))
      this.descriptorMap.put(paramString, paramTbTypeDescriptor);
  }

  public synchronized Object getDescriptor(String paramString) throws SQLException {
    if (paramString == null)
      throw TbError.newSQLException(-590713);
    return (this.descriptorMap == null) ? null : this.descriptorMap.get(paramString);
  }

  public ExtFeatureInfo getExtFeatureInfo() throws SQLException {
    if (this.connClosed)
      throw TbError.newSQLException(-90603);
    return this.efInfo;
  }

  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    }
  }

  public TbDTFormat getParsedNlsDateFormat() {
    return this.parsedNlsDateFormat;
  }

  public TbDTFormat getParsedNlsTimestampFormat() {
    return this.parsedNlsTimestampFormat;
  }
}