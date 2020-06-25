package qo.control.general;


import java.sql.*;
import java.io.*;


import java.util.*;
import java.sql.*;
// Proyecto Generador Codigo --- < DARIL-GENERATOR V 1.0_0421 > --- .



/**
 * Implementa las operaciones basicas CRUD Mantenimiento de una tabla.
 *
 *
 * @author David Ricardo Lara Amaya <DARIL>
 * @version < DARIL-GENERATOR V 1.0003 > Ano 2004 - Abril 1 2004
 */
// David Ricardo Lara Amaya - 
// Ultima Modificacion Abril 15 - 2004. 12:30 P.M. - Asignacion FK en en el manejo maestro/detalle.
// Ultima Modificacion Abril 14 - 2004. 05:20 P.M.
// Ultima Modificacion Abril 10 - 2004. 11:30 P.M.

// Inicio Proyecto Noviembre 2003. Hora 10:00 P.M.
// Proyecto Generador Codigo --- < DARIL-GENERATOR V 1.0003 > --- .



public class tiempo  {

private Conexion con;
private boolean inicilizado = false;
private String Version = "V 1.1.";
private String nameConexion = "";


/**
 * @param mapping The ActionMapping used to select this instance
 * @param actionForm The optional ActionForm bean for this request (if any)
 * @param request The HTTP request we are processing
 * @param response The HTTP response we are creating
 *
 * @exception IOException if an input/output error occurs
 * @exception ServletException if a servlet exception occurs
 */

public tiempo( String conexion ) {
	
	this.nameConexion = conexion;

}

 /**
  * fecha:
  * Retorna la fecha de la base de datos.
  */

    @SuppressWarnings("empty-statement")
public String getFecha( ) {

   Conexion c1 = null;

   ResultSet rs = null;
   boolean result = false;
   String fecha = "";
   String sqlca = "";
   

    try {

           c1  = new Conexion(this.nameConexion);

          // Verifica si existe alguno, Independiente del estado
		  sqlca   = "  SELECT count(*), ";  
		  sqlca  += "         now()   ";
		  sqlca  += "  FROM acc_usu_usuario  ";

		  rs = c1.consultar(sqlca);
   		   
   		  if ( rs.next()  )
		  {
		    fecha = rs.getString(2).trim();	
		    fecha = fecha.substring(0,10);

		  };
          


        } 
	catch(Exception e)
	{
		 	System.out.print("Ocurrio el siguiente error:"+e.toString());
		 	e.printStackTrace();
	}

	finally
	{
            if ( c1 != null ) c1.cerrar();

            return fecha;
            
	}
  }


 /**
  * fecha:
  * Retorna la fecha de la base de datos.
  */

    @SuppressWarnings("empty-statement")
public String getHora( ) {

   Conexion c1 = null;

   ResultSet rs = null;
   boolean result = false;
   String fecha = "";
   String sqlca = "";

    try {

           c1  = new Conexion(this.nameConexion);

          // Verifica si existe alguno, Independiente del estado
		  sqlca   = "  SELECT count(*), ";  
		  sqlca  += "         now()   ";
		  sqlca  += "  FROM acc_usu_usuario  ";

		  rs = c1.consultar(sqlca);
   		   
   		  if ( rs.next()  )
		  {
		    fecha = rs.getString(2).trim();	
		    fecha = fecha.substring(11,18);
   		 	System.out.print("\n\n\n\n -----------Fecha Capturada   "+fecha);

		  };
          


        } 
	catch(Exception e)
	{
		 	System.out.print("Ocurrio el siguiente error:"+e.toString());
		 	e.printStackTrace();
	}

	finally
	{
            if ( c1 != null ) c1.cerrar();

            return fecha;
            
	}
  }



public static void main(String arg[]) {


           Vector xx = new Vector();


           int tipo = 0;
           System.out.println("Ingreso sistema ........ ");


           String str_cuenta = "";
           long double_vlr;
           long double_cuenta;
           
           tiempo Tiempo = new tiempo("con_cuf");
           

           
           System.out.println("Fecha...... "+Tiempo.getFecha());
           
           
           System.out.println("Final Proceso ........ ");
           
           
   }







}


