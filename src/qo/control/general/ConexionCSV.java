package qo.control.general;
import java.util.*;
import java.sql.*;

//ESTA CLASE CUBRE LA FUNCIONALIDAD NECESARIA PARA MANEJAR UNA CONEXI�N A BASE DE DATOS,
//INCLUYENDO CONSULTAS Y ACTUALIZACIONES. SE BASA EN PARAMETROS QUE SE PASAN POR CONSTRUCTOR
//O POR LOS M�TODOS IMPLEMENTADOS PARA FIJAR VALORES A LAS VARIABLES.

public class ConexionCSV {
  
   String controlador;
   String base;
   String usuario;
   String contrasena;
   Connection con;
   Statement stm;
   ResultSet rs;
  
  public ConexionCSV(String drv,String bd) {
		try{
			con=null;
			Class.forName(drv).newInstance();
    	con = DriverManager.getConnection(bd);
			stm = con.createStatement();
	  }
    catch(SQLException e)
    {
     	System.out.println(" Error de conexion..."+e.toString());	
     	System.out.println(" Error de conexion..."+e.getErrorCode());	
       	
    }
    catch(Exception e)
    {
     	System.out.println(" Error de conexion..."+e.toString());	       	
    }

	}
	
	
	  
  public ConexionCSV(String drv,String bd, Properties props ) {
		try{
			con=null;
			Class.forName(drv).newInstance();
    	con = DriverManager.getConnection(bd,props);
			stm = con.createStatement();
	  }
    catch(SQLException e)
    {
     	System.out.println(" Error de conexion..."+e.toString());	
     	System.out.println(" Error de conexion..."+e.getErrorCode());	
       	
    }
    catch(Exception e)
    {
     	System.out.println(" Error de conexion..."+e.toString());	       	
    }

	}

 public ConexionCSV(Connection cone ){
		try{
			con = cone ;
			stm = con.createStatement();
	  }
    catch(SQLException e)
    {
     	System.out.println(" Error de conexion..."+e.toString());	
     	System.out.println(" Error de conexion..."+e.getErrorCode());	
       	
    }
    catch(Exception e)
    {
     	System.out.println(" Error de conexion..."+e.toString());	       	
    }

	}
	
	//CIERRA LA CONEXI�N A LA BASE DE DATOS
 public void cerrar() {
  	try{
  		if(stm!=null)
  		stm.close();
  		if(con!=null)
  		con.close();
  	}
  	catch(Exception e){
  		System.out.println(e.toString()+" Error al cerrar...");
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
  public ResultSet consultar(String consulta)throws SQLException {
  	ResultSet r=null;
  	try{
  		r=stm.executeQuery(consulta);
  	/*while(r.next()){
  		System.out.println("resulset:" + rs.getString(1));
  	}*/
  	}
  	catch(SQLException e){
  		//System.out.println(e.toString()+" Error en la consulta..." + consulta);
  		throw new SQLException("Error en la consulta sobre el archivo: "+e.getMessage());
  	}
  	return r;
  }
  
	//ACTUALIZA LA BASE DE DATOS CON LA SENTENCIA RECIBIDA
	public int actualizar(String consulta) {
  	int x=-1;
  	try{
	  	
	  	x=stm.executeUpdate(consulta);
  	}
  	catch(Exception e){
  		System.out.println(e.toString()+" Error en la actualizacion...");
  	}
  	return x;
  }
  //ACTUALIZA LA BASE DE DATOS CON LA SENTENCIA RECIBIDA
	public boolean ejecutar(String consulta) {
  	boolean resultado=true;
  	try{
	  	
	  	resultado=stm.execute(consulta);
  	}
  	catch(Exception e){
  		System.out.println(e.toString()+" Error en la ejecucion...");
  	return false;
  	}
  	
  	return resultado;
  	
  }
  
 
  public Connection retornarConexion()			
  {
  return con;
  	}
  	
  
} 
