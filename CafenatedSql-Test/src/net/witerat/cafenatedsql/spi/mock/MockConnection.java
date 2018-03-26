package net.witerat.cafenatedsql.spi.mock;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MockConnection implements Connection {

  String method;
  private String schema;
  Properties properties;
  private String url;
  private boolean closed=false;
  private boolean readonly=false;

  public MockConnection(final Map<String, Object> properties, final String method) {
    this.method = method;
    this.properties = new Properties();
    this.properties.putAll(properties);
    this.url = (String) properties.get("connection.url");
  }

  public MockConnection(String url, Properties properties2) {
    this.url = url;
    this.properties = new Properties(properties2);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    try {
      return iface.cast(this);
    } catch (ClassCastException cce) {
      throw new SQLException(cce);
    }
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isAssignableFrom(this.getClass());
  }

  @Override
  public Statement createStatement() throws SQLException {
    return new MockStatement(this);
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return new MockPreparedStatment(this, sql);
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SQLException {
    return new MockCallableStatement(this, sql);
  }

  @Override
  public String nativeSQL(final String sql) throws SQLException {
    if(isClosed()) throw new SQLException("connection is closed");
    return sql;
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void commit() throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public void rollback() throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() throws SQLException {
    closed=true;

  }

  @Override
  public boolean isClosed() throws SQLException {
    // TODO Auto-generated method stub
    return closed;
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {
    readonly=readOnly;

  }

  @Override
  public boolean isReadOnly() throws SQLException {
    // TODO Auto-generated method stub
    return readonly;
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public String getCatalog() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    // TODO Auto-generated method stub
    return TRANSACTION_NONE;
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void clearWarnings() throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setHoldability(int holdability) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public int getHoldability() throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return new MockStatement(this, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return new MockPreparedStatment(this, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return new MockCallableStatement(this, sql, resultSetType, resultSetConcurrency,resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Clob createClob() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Blob createBlob() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NClob createNClob() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    // TODO Auto-generated method stub

  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setSchema(String schema) throws SQLException {
    this.schema = schema;

  }

  @Override
  public String getSchema() throws SQLException {
    // TODO Auto-generated method stub
    return schema;
  }

  @Override
  public void abort(Executor executor) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  public String toString() {
    return this.getClass().getName() + "@" + System.identityHashCode(this) + " \"" + method + "\" "
        + url;
  }
}
