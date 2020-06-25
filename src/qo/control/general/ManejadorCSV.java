package qo.control.general;

import java.util.*;
import java.sql.*;
/** Clase que permite gestionmar archivos CSV. */
public class ManejadorCSV {

	  	/**
   	 *	Objeto encargado de la conexion a la base de datos
   	 *  @see control.General.Conexion
   	 */
	protected ConexionCSV con;

   	/**
   	 *	Objeto encargado de la manejar los resultados de las peticiones a la base de datos
   	 *  @see java.util.*
   	 */
	protected	ResultSet	rs;

	/*
	 *	Vector que almacena el nombre de los campos de una tabla
	 */
   	protected Vector campos=new Vector();

   	/**
   	 *	Vector que almacena los valores que tienen los campos de la tabla, en el mismo orden
   	 *	en que aparecen en el Vectro campos.
   	 *
   	 */
   	protected Vector valores=new Vector();


	/**
	 *	String donde se almacena el nombre de la tabla que se est� trabajando
	 *	en el momento.
	 */
	protected String archivo;


	/**
	 *	Entero que lleva la cuenta de los campos que tiene la tabla
	 */
	int numCampos=0;




   	protected Vector mensajeValidacion=new Vector();


	public ManejadorCSV(String arc,ConexionCSV c)
	{
		con=c;
		archivo=arc;

	}

		/**
	 *	Convierte un vector en un arreglo de tipo String
	 *	@param v Vector que de desea convertir
	 *	@return Arreglo String con la informacion del vector
	 */
	public String[] vectorAArregloString(Vector v)
	{
  		String[] temp=new String[v.size()];
		int i=0;
		for (Enumeration e = v.elements() ; e.hasMoreElements() ;i++)
		{
			temp[i]=e.nextElement().toString();
    	}
		return temp;
	}

		/**
	 *	Obtiene TODOS los valores de un campo en la tabla ordenados por otro.
	 *	@param col campo sobre el cual se desea obtener todos los valores
	 *	@param colOrd campo que se usa para ordenar el resultado
	 *	@param org boolean que indica si el orden es ascendente o descendente.
	 *	@return String [] con todos los datos del campo en la tala.
	 */
	public String[] obtenerValoresDeCampoEnOrden(String col,String colOrd,boolean ord)
	{

		Vector t=new Vector();
		try
		{
			if(ord)
				rs=con.consultar("SELECT "+ col +" FROM "+archivo);
			else
				rs=con.consultar("SELECT "+ col + " FROM "+archivo);
			while(rs.next())
			{
				t.addElement(rs.getString(col));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return vectorAArregloString(t);
	}
	/**
	 *Realiza una consulta de la informacion almacenada en un archivo viendo a este como una tabla de BD
 	**/
	public boolean obtenerValores() throws SQLException
	{
		try
		{

			rs=con.consultar("SELECT * FROM "+archivo);

			return true;
		}
		catch(SQLException e)
		{

			throw new SQLException("Error en el proceso de subir la informacion del archivo a la BD "+ e.getMessage());

		}

	}

}
