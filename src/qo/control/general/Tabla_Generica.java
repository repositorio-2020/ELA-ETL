
package qo.control.general;
import java.util.*;
import java.sql.*;

public class Tabla_Generica extends Persistente
{
	public Tabla_Generica(){}
	public Tabla_Generica(Conexion c){
		super(c);
	}
	public Tabla_Generica(String nombreTabla, Conexion c){
		super(nombreTabla,c);
	}

	public Tabla_Generica(String nombreTabla,String nombreSec, Conexion c){
		super(nombreTabla,nombreSec,c);
	}
		/** Los resultados son discriminados por 2 campos perteneciente a la misma tabla
 *	@param col Campo en la tabla que se quiere hacer el listado.
 *	@param colOrd Campo en la tabla para realizar el ordenamiento.
 *  @param campo[] Los resultados seran discriminados por estos campos.
 *	@param valor[] Valores que debe tener el parametro campo en la sentencia SQL.
 * *	@return String[] Devuelve el arreglo con los registros obtenidos de la consulta
 **/

	public String[] obtenerValoresDeCampoEnOrdenDis(String col,String colOrd, String campos[], String valores[])
	{
		ResultSet	rs=null;
		boolean informacionCompleta=true;
		String consulta= new String("");
		if(!existeCampo(col))
			return null;
		Vector t=new Vector();
		try{
	    		if(campos!=null&&valores!=null&&(campos.length==valores.length)&&campos.length>0)
				{
		    		if(valores[0].equals(""))
		    		informacionCompleta=false;
		    		consulta="SELECT DISTINCT *  FROM "+ tabla  + " WHERE "+campos[0]+ "="+valores[0];
		    		for(int i=1; i<campos.length;i++)
		    		{
		    			consulta=consulta + " AND " + campos[i] + "=" +valores[i];
		    			if(informacionCompleta==true && valores[i].equals(""))
		    				informacionCompleta=false;
		    		}
		    	//	System.out.println("consulta:"+consulta);
		    		if(informacionCompleta)
		    		rs=con.consultar(consulta);
	    			while(rs.next())
					{
						t.addElement(rs.getString(col));
					}

				}
				if(rs!=null)
	  	 			rs.close();


	   }
	   catch(Exception e)
	   {
			System.out.println(e.toString());
	   }

	   return vectorAArregloString(t);
	}

	public String[] obtenerValoresDeCampoEnOrdenDis(String col,String colOrd,String campo, String valor, boolean ord)
	{
        StringBuffer sb=new StringBuffer("");
        	ResultSet	rs=null;
	        if(!existeCampo(col))
                	return null;
               Vector t=new Vector();
                try
                {
	         if(!valor.equals("")){
   	          if(ord)
	            sb.append("SELECT DISTINCT * FROM "+ tabla + " WHERE "+ campo + "=" + valor + " ORDER BY "+colOrd+" ASC");
              else
				 sb.append("SELECT DISTINCT * FROM "+ tabla + " WHERE "+ campo + "=" + valor + " ORDER BY "+colOrd+"  DESC");
		  	  // System.out.println(sb.toString());
		  	   rs=con.consultar(sb.toString());
			}
				
		
                 while(rs.next())
                 {
                  t.addElement(rs.getString(col));
                 // System.out.println(rs.getString(col));
				 }
		 if(rs!=null)
	  	 rs.close();
                }
		catch(Exception e){
			System.out.println("tabla "+tabla + " col " + col+sb.toString());
			System.out.println(e.toString());
		}

		return vectorAArregloString(t);
	}



}