package qo.control.general;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;
//INCLUYENDO CONSULTAS Y ACTUALIZACIONES. SE BASA EN PARAMETROS QUE SE PASAN POR CONSTRUCTOR
//O POR LOS M�TODOS IMPLEMENTADOS PARA FIJAR VALORES A LAS VARIABLES.

public class Conexion {

   String controlador;
   String base;
   String usuario;
   String contrasena;
public   Connection con;
   Statement stm;
   Statement stmScroll;
 	/**Variable para el manejo de actualizacion en batch*/
   Statement stmBatch;
   ResultSet rs;
   /**
    *  Variable statica solicitada por el log4j.
    *
    **/
   static Logger logger = Logger.getLogger(Conexion.class);

   
  DBQuery dbq;
  boolean conPool;
  public Conexion(String drv,String bd,String usr, String cta) {
		try{
			con=null;
			Class.forName(drv).newInstance();
    	con = DriverManager.getConnection(bd,usr,cta);
			stm = con.createStatement();
			stmScroll = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			                                ResultSet.CONCUR_READ_ONLY);
			stmBatch=con.createStatement();
			conPool=false;
	  }
    catch(SQLException e)
    {
     	logger.error(" ERROR CONEXION : "+e.toString());
     	logger.error(" ERROR CONEXION : "+e.getErrorCode());

    }
    catch(Exception e)
    {
     	logger.error(" ERROR CONEXION : "+e.toString());
    }

	}

	public Conexion (String nombre) throws Exception
	{
		try{

    		dbq = new DBQuery(nombre);
    		con =dbq.getConnection();
			if(con==null)
   		    	throw new SQLException(" La conexion es nula");
   		   	stm = con.createStatement();
            stmScroll = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			                                ResultSet.CONCUR_READ_ONLY);

		  	stmBatch=con.createStatement();
			conPool=true;
   		 
	 	 }
    	catch(SQLException e)
    	{
        	logger.error(" ERROR CONEXION : "+e.toString());

			throw new SQLException(" Error de conexion..."+e.toString()+e.getErrorCode());
 		   }
  	  catch(Exception e)
 	   {
         	logger.error(" Error de conexion..."+e.toString());
   		  	throw new SQLException(" Error de conexion... \n No se logro conseguir una conexi�n a la BD, por favor intente de nuevo.\n"+e.getMessage()+e.toString());
 		   
   		 }

	}
 public Conexion(Connection cone ){
		try{
			con = cone ;
			stm = con.createStatement();
			stmScroll = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			                                ResultSet.CONCUR_READ_ONLY);
			
			stmBatch=con.createStatement();
			conPool=false;
	  }
    catch(SQLException e)
    {
     	logger.error(" Error de conexion..."+e.toString());
     	logger.error(" Error de conexion..."+e.getErrorCode());

    }
    catch(Exception e)
    {
     	logger.error(" Error de conexion..."+e.toString());
    }

	}

	//CIERRA LA CONEXI�N A LA BASE DE DATOS
 public void cerrar() {
  	try{
  	   if (rs != null)
  	     rs.close();
  	   if(stm!=null)
  	   	stm.close();
  	   if(stmScroll!=null)
  	   	stmScroll.close();
  	   	if(stmBatch!=null)
  	   	stmBatch.close();
    	if(conPool)
    	  dbq.closeAll();
    	  else
    	  {
    	  	if(con!=null)
    	  		con.close();

    	  }
  	}
  	catch(Exception e){
  		logger.error(e.toString()+" Error al cerrar...");
  	}
  }

	//RETORNA EL CAMINO AL CONTROLADOR UTILIZADO PARA LA CONEXI�N A LA BASE DE DATOS
  String obtenerControlador() {
  	return controlador;
  }

	//RETORNA EL URL DE LA BASE DE DATOS A CONECTAR
  String obtenerBase() {
  	return base;
  }

	//RETORNA EL NOMBRE DE USUARIO CON EL QUE SE CONECTAR� A LA BASE DE DATOS
  String obtenerUsuario() {
  	return usuario;
  }

	//RETORNA LA CONREASE�A DEL USUARIO CON EL QUE SE CONECTAR� A LA BASE DE DATOS
  String obtenerContrasena() {
  	return contrasena;
  }

	//FIJA EL CAMINO AL CONTROLADOR UTILIZADO PARA LA CONEXI�N A LA BASE DE DATOS
  boolean fijarControlador(String cnt) {
  	boolean bien=true;
  	if(cnt.equals(""))
  		bien =false;
  	else
  		controlador=cnt;
  	return bien;
  }

	//FIJA EL URL DE LA BASE DE DATOS A CONECTAR
  boolean fijarBase(String bd) {
  	boolean bien=true;
  	if(bd.equals(""))
  		bien =false;
  	else
  		base=bd;
  	return bien;
  }

	//FIJA EL NOMBRE DE USUARIO CON EL QUE SE CONECTAR� A LA BASE DE DATOS
  boolean fijarUsuario(String usr) {
  	boolean bien=true;
  	if(usr.equals(""))
  		bien =false;
  	else
  		usuario=usr;
  	return bien;
  }

	//FIJA LA CONREASE�A DEL USUARIO CON EL QUE SE CONECTAR� A LA BASE DE DATOS
	boolean fijarContrasena(String cta) {
  	boolean bien=true;
  	if(cta.equals(""))
  		bien =false;
  	else
  		contrasena=cta;
  	return bien;
  }

	//RETORNA UNA TABLA CON EL RESULTADO DE UNA CONSULTA
  public ResultSet consultar(String consulta) {
  	ResultSet r=null;
  	try{
  		r=stm.executeQuery(consulta);
  	/*while(r.next()){
  		System.out.println("resulset:" + rs.getString(1));
  	}*/
  		/*if(stm!=null)
			stm.close();*/
  	}
  	catch(Exception e){
  		logger.error(e.toString()+" Error en la consulta..." + consulta);
  	}finally{

		}
  	return r;
  }

	//RETORNA UNA TABLA CON EL RESULTADO DE UNA CONSULTA,
	// Pero una hoja de resultados Scrollable.
  public ResultSet consultarScroll(String consulta) {
  	ResultSet r=null;
  	try{
  		r=stmScroll.executeQuery(consulta);
  	/*while(r.next()){
  		System.out.println("resulset:" + rs.getString(1));
  	}*/
  		/*if(stm!=null)
			stm.close();*/
  	}
  	catch(Exception e){
  		logger.error(e.toString()+" Error en la consulta..." + consulta);
  	}finally{

		}
  	return r;
  }


	//ACTUALIZA LA BASE DE DATOS CON LA SENTENCIA RECIBIDA
	public int actualizar(String sqlca) throws SQLException {
  	int x=-1;

	  	x=stm.executeUpdate(sqlca);
	  	/*	if(stm!=null)
			stm.close();*/

  	return x;

  }


	//Ejecuta el borrado de la base de datos.
   public int borrado(String sqlca) throws SQLException {
  	int x=-1;

     	logger.debug(" SQL Eliminar a EXE "+sqlca);
 	  	x=stm.executeUpdate(sqlca);
	  	/*	if(stm!=null)
			stm.close();*/
   return x;

  }

  
  //ACTUALIZA LA BASE DE DATOS CON LA SENTENCIA RECIBIDA
	public boolean ejecutar(String sqlca) {
  	boolean resultado=true;
  	try{

	  	resultado=stm.execute(sqlca);
	  	/*	if(stm!=null)
			stm.close();*/
  	}
  	catch(Exception e){
  		logger.error(e.toString()+" Error en la ejecucion... SQL "+sqlca);
  	    return false;
  	}finally{

   	  return resultado;

    }


  }


  public Connection retornarConexion()
  {
  return con;
  	}
  	/**Metodo que adiciona uns sentencia SQL para ejecutarse en batch*/

  	public void adicionarBatch(String consulta) throws SQLException
  	{
   	try{

	  stmBatch.addBatch(consulta);
  	}
  	catch(Exception e){
  		logger.error(e.toString()+" Error ADICION en Batch.."+consulta);
	throw new SQLException (e.getMessage());
  	}

  }

  /** Metodo que ejecuta el batch */
	public int[] ejecutarBatch() throws SQLException
  	{

   	try{

	  int i[]= stmBatch.executeBatch();
	  stmBatch.clearBatch();
//	  stmBatch = con.createStatement();
	  return i;
  	}
  	catch(Exception e){
  		logger.error(e.toString()+" Error en la adicion en el Batch..");
	throw new SQLException (e.getMessage());
  	}finally{
		/*	if(stmBatch!=null)
			stmBatch.close(); */
		}

  }

protected void finalize()
{
	try{
		if(rs!=null)
		 rs.close();
		if(stm!=null)
			stm.close();
		if(stmScroll!=null)
			stmScroll.close();
		if(stmBatch!=null)
			stmBatch.close();

		}catch(Exception e)
		{

		}
}
}
