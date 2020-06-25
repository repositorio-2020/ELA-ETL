/*
 * DBQuery.java
 *
 * Created on 25 de enero de 2002, 12:03
 */

package qo.control.general;

import java.sql.*;

/**
 *
 * @author  Omar Rojas - 25/01/2002
 * @version 1.0
 */
public class DBQuery 
{

  private DBConnectionManager iConManager;  // Manejador del pool de comexiones
  private Connection          iConnection;  // Comexi�n
  private String              isConnection; // Nombre de la comexi�n
  private Statement           iStatement;     // Sentencia

  /** Crea un nuevo DBQuery a partir de un nombre de comexi�n */
  public DBQuery(String sConnection) throws java.sql.SQLException
  {
    iConManager  = DBConnectionManager.getInstance(); 
    isConnection = sConnection;
    iConnection  = iConManager.getConnection(isConnection);
    iStatement   = iConnection.createStatement();
  }
  
  /**
   * Retorna la conexi�n para que pueda ser utilizada fuera de la
   * clase y as� no haya necesidad de solicitar una nueva
   */
  public Connection getConnection()
  {
    return iConnection;  
  }
  
  /**
   * Ejecuta la sentencia SQL que se le env�a, retornando un ResultSet con
   * los resultados. La sentencia debe ser una consulta
   */
  public ResultSet executeQuery(String sQuery) throws java.sql.SQLException
  {
    return iStatement.executeQuery(sQuery);
  }
  
  /**
   * Ejecuta la sentencia SQL que se le env�a, retornando un boolean con
   * el resultado. La sentencia debe ser una sentencia de manipulaci�n de
   * datos
   */
  public boolean execute(String sQuery) throws java.sql.SQLException
  {
    return iStatement.execute(sQuery);
  }

  public void closeAll() throws java.sql.SQLException
  {
    if (iStatement != null)
      iStatement.close();
    if (iConnection != null)
      iConManager.freeConnection(isConnection, iConnection);
  }
}
