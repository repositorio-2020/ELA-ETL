package qo.control.general;
import java.util.*;
import java.sql.*;
import java.lang.Math.*;

/**
 *	Esta clase se adapta a cualquier tabla, ofreciendo una funcionalidad gen�rica para
 *	cualquier base de datos en ORACLE.
 *	Permite generar clases que se encargen del manejo de la comunicaci�n desde y hacia la
 *	una tabla en particular de la base de datos.
 *
 *	@author Varios
 *
*/
public class Persistente
{

   	/**
   	 *	Objeto encargado de la conexi�n a la base de datos
   	 *  @see control.General.Conexion
   	 */
	protected Conexion con;

   	/**
   	 *	Objeto encargado de la manejar los resultados de las peticiones a la base de datos
   	 *  @see java.util.*
   	 */
	ResultSet	rs;

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
   	 *	Vector que almacena el tipo de datos para cada campo en la tabla, en el
   	 *	mismo orden que tiene el vector campos
   	 */
   	protected Vector tipos=new Vector();

   	/**
   	 *	Vector que almacena los campos que son llaves primarias de la tabla
   	 */
   	protected Vector llavePrimaria=new Vector();

   	/**
   	 *	Vector que almacena las llaves for�neas de la tabla
   	 */
   	protected Vector llavesForaneas=new Vector();

   	/**
   	 *	Vector que almacena el nombre de las tablas de las cuales hereda la
   	 *	tabla
   	 */
   	protected Vector tablasPadre=new Vector();

   	/**
   	 *	Vector que almacena los campos que la tabla hereda de las tablas padre.
   	 */
   	protected Vector camposTablasPadre=new Vector();

   	/**
   	 *	Vector que guarda la informaci�n sobre los campos obligatorios que
   	 *	tiene la tabla
   	 */
	protected Vector obligatorios=new Vector();

	/**
	 *	String donde se almacena el nombre de la tabla que se est� trabajando
	 *	en el momento.
	 */
	protected String tabla;

	/**
	 * String donde se almacena el nombre de la secuencia que se debe utilizar para calcular
	 * los identificadores.
	 *
	 *
	 */
	protected String nombreSecuencia;
	/**
	 *	Entero que lleva la cuenta de los campos que tiene la tabla
	 */
	protected int numCampos=0;

	/**
	 *	Entero que lleva la cuenta de los campos que son llaves primarias
	 *	en la tabla
	 */
	protected int numCamposPK=0;

	/**
	 *	Indica si una tabla ha sido cargada en memoria o no.
	 */
	protected boolean cargada=false;

	/**
	 *	Objeto que maneja la creaci�n de contenido HTML
	 *	@see control.Artesanias.GeneradorHTML
	 */
	GeneradorHTML gen=new GeneradorHTML();

   	protected Vector mensajeValidacion=new Vector();

	public Persistente (){}
	/**
	 *	Este constructor crea un ejemplar de la clase con el nombre de una tabla
	 *	y una conexi�n, luego carga la informaci�n de esa tabla.
	  *	@param c Objeto de tipo Conexion que se va a utilizar para conectar con
	 *	la base de datos.
	 */
	public Persistente(Conexion c)
	{
		con=c;
		tabla="";
		nombreSecuencia="";
	}
	/**
	 *	Este constructor crea un ejemplar de la clase con el nombre de una tabla
	 *	y una conexi�n, luego carga la informaci�n de esa tabla.
	 *	@param tbl String que contiene el nombre de la tabla
	 *	@param c Objeto de tipo Conexion que se va a utilizar para conectar con
	 *	la base de datos.
	 */
	public Persistente(String tbl,Conexion c)
	{
		con=c;
		tabla=tbl;
		nombreSecuencia="";
		cargarInformacion();
	}

	/**
	 *	Este constructor crea un ejemplar de la clase con el nombre de una tabla
	 *	y una conexi�n, luego carga la informaci�n de esa tabla.
	 *	@param tbl String que contiene el nombre de la tabla
	 *	@param nombreSec String que tiene el nombre de la secuencia;
	 *	@param c Objeto de tipo Conexion que se va a utilizar para conectar con
	 *	la base de datos.
	 */
	public Persistente(String tbl,String nombreSec,Conexion c)
	{
		con=c;
		tabla=tbl;
		nombreSecuencia=nombreSec;
		if(!tabla.equals(""))
		cargarInformacion();
	}

	/**
	 *	Carga la informaci�n de la tabla en los vectores correspondientes,
	 *	encuentre el nombre de los campos, sus valores, el tipo de datos para
	 *	cada campo, adem�s identifica las las llaves primarias y for�neas.
	 *	@param ninguno
	 *	@return nada
	 */
	private  void cargarInformacion()
	{
		try
		{

			//CONSULTA QUE LOCALIZA LOS PK DENTRO DE LOS CAMPOS
			String consulta="SELECT e.CONSTRAINT_NAME, o.INDEX_NAME, o.COLUMN_NAME ";
			consulta+="FROM user_constraints e,user_ind_columns o ";
			consulta+="WHERE e.CONSTRAINT_NAME = o.INDEX_NAME AND ";
			consulta+="e.TABLE_NAME ='"+tabla+"' AND e.CONSTRAINT_TYPE='P'";
			rs=con.consultar("select COLUMN_NAME from user_tab_columns where table_name='"+tabla+"' ORDER BY COLUMN_ID");//OJO SE ADICIONO ORDER BY COLUMN_ID
		    while(rs.next())
		    {
				campos.addElement(new String(rs.getString("COLUMN_NAME")));
				valores.addElement(new String(""));
			}
			rs=con.consultar(consulta);
			while(rs.next())
			{
				llavePrimaria.addElement(new String(rs.getString("COLUMN_NAME")));
			}
			numCampos=campos.size();
			numCamposPK=llavePrimaria.size();
	/*	if(rs!=null)
			rs.close();*/
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		identificarTipos();
	//	cargarLlaves();
	//	identificarObligatorios();

	}

	/**
	 *	Carga los campos que son llaves primarias en el vector llaveprimaria.
	 *	@param ninguno
	 *	@return nada
	 */
	private void cargarLlaves()
	{
		try
		{
			String consulta= new String("");
			consulta="SELECT E.COLUMN_NAME,E.TABLE_NAME ";
			consulta+="FROM USER_CONS_COLUMNS E, USER_CONSTRAINTS B ";
			consulta+="WHERE B.R_CONSTRAINT_NAME =E.CONSTRAINT_NAME ";
			consulta+="AND B.TABLE_NAME='"+tabla+"'";
		    rs=con.consultar(consulta);
		    while(rs.next())
		    {
		    	llavesForaneas.addElement(new String(rs.getString("COLUMN_NAME")));
		    	tablasPadre.addElement(new String(rs.getString("TABLE_NAME")));
		    }
		  /*  if(rs!=null)
			rs.close();*/

		}
		catch(Exception e)
		{
			System.out.println("Ocurrio este error"+e.toString());
		}finally{

		}
	}

	/**
	 *	Identifica el tipo de datos de cada campo en la tabla. Si es un n�mero,
	 *	en el vector tipos coloca un string vacio (""), si es otro tipo de
	 *	datos (VARCHAR, DATE, etc.) coloca una comilla (').
	 *	@param ninguno
	 *	@return nada
	 */
	private void identificarTipos()
	{
		rs=con.consultar("SELECT DATA_TYPE FROM user_tab_columns WHERE table_name='"+tabla+"' ORDER BY COLUMN_ID" );
		try
		{
			while(rs.next())

				tipos.addElement((rs.getString("DATA_TYPE").equals("NUMBER"))||(rs.getString("DATA_TYPE").equals("FLOAT"))?"":"'");

	/*	if(rs!=null)
			rs.close();*/
			}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}finally{

		}
	}

	/**
	 *	Identifica los campos que son obligatorios en la tabla
	 *	@param ninguno
	 *	@return nada
	 */
	public void identificarObligatorios()
	{
		rs=con.consultar("SELECT COLUMN_NAME,NULLABLE FROM user_tab_columns WHERE table_name='"+tabla+"' ORDER BY COLUMN_ID");
		try
		{
			while(rs.next())
			{
				if(rs.getString(2).equals("N"))
					obligatorios.addElement(rs.getString(1));
			}
	/*	if(rs!=null)
			rs.close();*/	}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}finally{

		}
	}

	/**
	 *	Este m�todo valida que el vector de campos tenga informaci�n para los
	 *	campos que son obligatorios en la tabla.
	 *	@param ninguno
	 *	@return nada
	 */
	public boolean validarCamposObligatorios()
	{
		boolean completos=true;
		for(int i=0;i<obligatorios.size() && completos;i++)
		{
			if(valores.elementAt(campos.indexOf(obligatorios.elementAt(i))).equals(""))
			{
				completos=false;
				mensajeValidacion.addElement("Debe llenar los datos completos.");
			}
		}
		return completos;
	}

	/**
	 *	Este m�todo selecciona toda la informaci�n disponible del registro identificado
	 *	con la llave primaria que esta cargada en el objeto
	 *	@param ninguno
	 *	@return ResultSet
	 */
	private ResultSet consultarRegistro()
	{
		String qry = new String("");
		qry="SELECT * FROM "+tabla+" WHERE ";
		for(int i=0;i<numCamposPK;i++)
		{
			qry+=llavePrimaria.elementAt(i)+" = ";
			qry+=tipos.elementAt(campos.indexOf(llavePrimaria.elementAt(i)));
			qry+=valores.elementAt(campos.indexOf(llavePrimaria.elementAt(i)));
			qry+=tipos.elementAt(campos.indexOf(llavePrimaria.elementAt(i)));
			if(i<numCamposPK-1)
				qry+=" and ";
		}
		//System.out.println(qry);
		return con.consultar(qry);
	}


	/**
	 *	Carga a los vectores toda la informaci�n disponible del registro identificado con
	 *	la llave primaria que esta cargada en memoria.
	 *	@param ninguno
	 *	@return boolean
	 */
	public boolean cargar()
	{
		boolean bien=true;
		try
		{
			rs=consultarRegistro();
			if(rs.next())
			{
				for(int i=1;i<=numCampos;i++)
					valores.set(i-1,rs.getString(i));
				cargada=true;
			}
			else
			{
				bien=false;
			}
		/*	if(rs!=null)
			rs.close();*/
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			bien=false;
		}
		finally{

		}
		return bien;
  }

	/**
	 *	Comprueba que exista el registro en la tabla
	 *	@param ninguno
	 *	@return boolean
	 */
	public boolean existeRegistro()
	{
		boolean bien=true;
		try
		{
			rs=consultarRegistro();
			bien=rs.next();
		/*if(rs!=null)
			rs.close();*/
			}
		catch(Exception e)
		{
			System.out.println(e.toString());
			bien=false;
		}finally{

		}
		return bien;
	}

	/**
	 *	Almacena la informaci�n que esta almacenada en el objeto actual en la
	 *	base de datos. Si el registro existe lo actualiza, si no existe lo crea
	 *	@param ninguno
	 *	@return boolean
	 */
	public boolean guardar() throws SQLException
	{
  		String qry="";
		if(existeRegistro())
		{
			qry=crearUpdate();
		}
		else
		{
				qry=crearInsert();
		}

	//System.out.println("Sentecia: "+ qry);
		return con.actualizar(qry)>=1?true:false;
	}


	/**
	 *	Almacena la informaci�n que esta almacenada en el objeto actual en la
	 *	base de datos. Si el registro existe lo actualiza, si no existe lo crea
	 *	@param ninguno
	 *	@return boolean
	 */
	public boolean guardarNuevo() throws SQLException
	{
  		String qry=crearInsert();


		//System.out.println("Sentecia: "+ qry);
		return con.actualizar(qry)>=1?true:false;
	}

	/**
	 *	Almacena la informaci�n que esta almacenada en el objeto actual en la
	 *	base de datos. Si el registro existe lo actualiza, si no existe lo crea
	 *	@param ninguno
	 *	@return boolean
	 */
	public boolean guardarActualizacion() throws SQLException
	{	String qry=	"";
		if(existeRegistro())
		{
  		 qry=	crearUpdate();
		}


	//System.out.println("Sentecia: "+ qry);
		return con.actualizar(qry)>=1?true:false;
	}



	/**
	 *	Elimina un registro de la tabla actual
	 *	@param campo indica los campo que se deben comparar
	 *	@param val es el vector con los valores de cada campo
	 *	@return boolean
	 */
	 public boolean borrar(String[] campo, String[] val) throws SQLException
	 {
		String qry;
		boolean ret=false;



		// Verificar si la longitud del vector de campos es
		// igual a la longitud del vector de valores
		if(campo.length==val.length)
		{
			qry="DELETE FROM "+tabla+" WHERE ";
			for(int i=0;i<campo.length;i++)
			{
       			qry+=campo[i]+"="+tipos.elementAt(campos.indexOf(campo[i])).toString()+val[i]+tipos.elementAt(campos.indexOf(campo[i])).toString();
				if(i<campo.length-1)
					qry=qry+",";

			}
//			System.out.println("CONSULTA: "+qry);
			ret= con.actualizar(qry)>0?true:false;
		}

		return ret;
	}

	/**
	 *	Elimina el registro correspondiente al representado por el
	 *	objeto actual.
	 *	@param ninguno
	 *	@return boolean
	 */

	public boolean eliminar() throws SQLException
	{
		String qry;
		boolean ret=false;
		if(existeRegistro())
		{
			qry="DELETE FROM "+tabla+" WHERE ";
			for(int i=0;i<numCamposPK;i++)
			{
				qry+=llavePrimaria.elementAt(i)+"="+tipos.elementAt(campos.indexOf(llavePrimaria.elementAt(i)))+valores.elementAt(campos.indexOf(llavePrimaria.elementAt(i)))+tipos.elementAt(campos.indexOf(llavePrimaria.elementAt(i)));
				if(i<numCamposPK-1)
					qry+=" and ";
			}

			ret= con.actualizar(qry)>0?true:false;
		}
		return ret;
  }

	/**
	 *	Fija los valores de un registro en los vectores.
	 *	@param val[] Arreglo String con la informaci�n del registro debe ir en el
	 *	mismo orden que el vector campos.
	 *	@return nada
	 */
	boolean fijarValores(String val[])
	{
  		if(val.length != numCampos)
			return false;
		for(int i=0;i<numCampos;i++)
			valores.set(i,val[i]);
		return true;
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
	 *	Genera el c�digo HTML para un objeto de tipo Lista
	 *	@param val String  que indica el campo que se usar� para identificar
	 *	los elementos de la lista
	 *	@param nom String que indica el campo que se usara para obtener los
	 *	valores de los elementos que aparecen en la lista.
	 *	@return String con el c�digo HTML del cuadro de lista.
	 */
  	public String obtenerOpcionesHTML(String val,String nom,String sel)
  	{
		GeneradorHTML GenInt=new GeneradorHTML();
		return GenInt.generarOpciones(obtenerValoresDeCampoEnOrden(val,nom,true),obtenerValoresDeCampoEnOrden(nom,nom,true),sel);
	}

	/**
	 *	Devuelve el n�mero de campos de la tabla
	 *	@return int con el n�mero de campos
	 */
	int obtenerNumCampos()
	{
		return numCampos;
	}

	/**
	 *	Devuelve el n�mero de campos que conforman la llave primaria de la tabla
	 *	@return int con el n�mero de campos que son llave primaria
	 */
	int obtenerNumCamposPK()
	{
		return numCamposPK;
	}

	/**
	 *	Devuelve el nombre de los campos de la tabla
	 *	@return String[] con el nombre de cada campo de la tabla
	 *
	 */
	String[] obtenerCampos()
	{
		return vectorAArregloString(campos);
	}

	/**
	 *	Devuelve los valores de los campos de la tabla para un registro
	 *	@return String[] con el valor de cada campo de la tabla para un registro
	 *
	 */
	public String[] obtenerValores()
	{
  		return vectorAArregloString(valores);
	}

	/**
	 *	Devuelve el valor de un campo de la tabla sobre el registro actual.
	 *	@param campo el campo que se desea obtener
	 *	@return String con el valor del campo
	 *
	 */
	public String obtenerValorCampo(String campo)
	{
  		String strCampo=new String("");
  		if(!existeCampo(campo))
			return null;
		try
		{
			strCampo= valores.elementAt(campos.indexOf(campo)).toString()==null?new String(""):valores.elementAt(campos.indexOf(campo)).toString();//ojo se modifico
		}
		catch(Exception e)
		{
			//System.out.println("Ocurrio este error en el campo "+campo +e.toString());
		}
		return strCampo;
	}

	/**
	 *	Devuelve el nombre de los campos de la tabla
	 *	@return String[] con el nombre de cada campo de la tabla
	 *
	 */
	public String[] obtenerValoresDeCampo(String col)
	{
		if(!existeCampo(col))
			return null;
		Vector t=new Vector();
		try
		{
			rs=con.consultar("SELECT "+ col +" FROM "+tabla);
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
	 *	Obtiene TODOS los valores de un campo en la tabla ordenados por otro.
	 *	@param col campo sobre el cual se desea obtener todos los valores
	 *	@param colOrd campo que se usa para ordenar el resultado
	 *	@param org boolean que indica si el orden es ascendente o descendente.
	 *	@return String [] con todos los datos del campo en la tala.
	 */
	public String[] obtenerValoresDeCampoEnOrden(String col,String colOrd,boolean ord)
	{
		if(!existeCampo(col))
			return null;
		Vector t=new Vector();
		try
		{
			if(ord)
				rs=con.consultar("SELECT "+ col +" FROM "+tabla+" ORDER BY "+colOrd+" ASC");
			else
				rs=con.consultar("SELECT "+ col + " FROM "+tabla+" ORDER BY "+colOrd+" DESC");
			while(rs.next())
			{
				t.addElement(rs.getString(col));
			}
		/*		if(rs!=null)
			rs.close();*/
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}finally{

		}
		return vectorAArregloString(t);
	}

	/**
	 *	Fija el nombre de la tabla
	 *	@param tbl Nombre de la tabla
	 *	@return boolean indicando si se realiz� correctamente la operaci�n
	*/
	boolean fijarNomTabla(String tbl)
	{
  		tabla=tbl.equals("")?tabla:tbl;
		return tbl.equals("")?false:true;
	}

	/**
	 *	Comprueba si existe un campo en la tabla cargada en memoria
	 *	@param campo el campo que se desa verificar.
	 *	@return boolean indicando si existe o no el campo.
	 */
	public boolean existeCampo(String campo)
	{
		return campos.indexOf(campo)>=0?true:false;
	}

	/**
	 *	Fija para el registro actual el valor de un campo
	 *	@param campo el campo al cual se le va a fijar el valor
	 *	@param valor el valor del campo.
	 *	@return true si el valor se pudo almacenar, falso en otro caso
	 */
  public boolean fijarValor(String campo, String valor)
  {
		if(!existeCampo(campo))
			return false;
		valores.set(campos.indexOf(campo),valor.replace('\'', ' '));
		return true;
  }
	/**
	 *	Verifica que una cadena sea numerica
	 *	@param cad cadena que se desea verificar
	 *	@return true si es numerica, falso si no lo es.
	 */
	public boolean esCadenaNumerica(String cad)
	{
		boolean bien=true;
		cad=cad.replace(',','.');
		try
		{
			Double.parseDouble(cad);
    	}
    	catch(Exception e)
    	{
			bien=false;
    	}
		return bien;
	}


	/**
	 *	Verifica que un caracter alfab�tico este en minuscula
	 *	@param c caracter que se desea verificar
	 *	@return true si esta en  minuscula, falso si no lo es.
	 */
	boolean esMinuscula(char c)
	{
		int x=(int)c;
		return ((x>96 && x<123) || c=='�' || c=='�' || c=='�' || c=='�' || c=='�' || c=='�' || c=='�')?true:false;
	}

	/**
	 *	Verifica que un caracter alfab�tico este en may�scula
	 *	@param c caracter que se desea verificar
	 *	@return true si esta en  may�scula, falso si no lo es.
	 */
	boolean esMayuscula(char c)
	{
		int x=(int)c;
		return ((x>64 && x<91) || c=='�' || c=='�' || c=='�' || c=='�' || c=='�' || c=='�' || c=='�')?true:false;
	}


	/**
	 *	Verifica que una cadena sea  alfab�tica
	 *	@param cad cadena que se desea verificar
	 *	@return true si es alfabetica, falso si no lo es.
	 */
	public boolean esCadenaAlfabetica(String cad)
	{
		boolean bien=true;
		for(int i=0;i<cad.length();i++)
		{
			//System.out.println("\nchar: "+cad.charAt(i)+" int: "+(int)cad.charAt(i)+" min: "+esMinuscula(cad.charAt(i))+" may: "+esMayuscula(cad.charAt(i)));
			if(!esMinuscula(cad.charAt(i)) && !esMayuscula(cad.charAt(i)) && cad.charAt(i)!=' ')
    			bien=false;
    	}
		return bien;
	}


	/**
	 *	Calcula el identificador que debe tener un registro nuevo
	 *	@param tbl la tabla sobre la que se desea calcular el identificador
	 *	@param idT el nombre del campo sobre el que desea calcularse el identificador
	 *	@return String con el identificador que se puede usar en la tabla
	 */
	public String calcularIdentificador(String tbl,String idT)
	{
		ResultSet rsInt=null;//con.consultar("SELECT * FROM "+tbl);
		String id="1";
		String sql="";
		try
		{
			
				if(nombreSecuencia.equals(""))
					sql="SELECT MAX("+idT+") FROM "+tbl;
				else
					sql="SELECT "+nombreSecuencia+".NEXTVAL FROM DUAL";
			//	System.out.println(sql);
				rsInt=con.consultar(sql);
				if(rsInt.next())
				   id=Integer.toString( Integer.parseInt(rsInt.getString(1))+1 );
					
		}catch(NumberFormatException e)
		{
			return id;
		}
		catch(SQLException e)
		{
			System.out.println(sql);
			System.out.println(e.toString());
			return null;
		}finally{

		}
		return id;
	}




	/**
	 *	Calcula el identificador que debe tener un registro nuevo
	 *	@return String con el identificador que se puede usar en la tabla
	 */
	public String calcularIdentificador()
	{
		return null;
	}










	/**
	 *	Genera un mensje de validaci�n en HTML
	public String mensajesValidacionHTML(int w,int b, int cs, int cp)
	{
		String msj[]=vectorAArregloString(mensajeValidacion);
		return gen.generarTabla(null,msj,"","",w,b,cs,cp);
	}
	 */


	/**
	 *	Cuenta el n�mero de campos vacios en un arreglo String
	 *	@param strCampos[] Arreglo string con los campos que se desean validad
	 *	@return entero con la cuenta de campos vacios del string.
	 */
	public int validarCamposVacios(String strCampos[])
	{
		if(strCampos.length==0)
	 		return -1;
	 	else
	 	{
	 		int intCampos = 0;
	 		for(int i=0; i<strCampos.length;i++)
	 		{
		 		if(strCampos[i].equals(""))
		 			intCampos++;
		 	}
		 	return intCampos;
		 }
	}


	/**
	 *	Verifica si existe un campo con un valor dado
	 *	@param campo el campo que se desea verificar
	 *	@param Valor el valor que se desea comprobar
	 *	@return true si existe el valor en el campo, false en caso contrario
	 */
	public boolean validarCampoDuplicado(String campo, String Valor)
	{
		boolean existe=false;
		String camposInt[] = obtenerValoresDeCampo(campo);
		if (camposInt!=null)
		for(int i=0; i<camposInt.length; i++)
		{
			if(camposInt[i].equals(Valor))
			{
				existe=true;
				break;
		    }
		}
		return existe;
	}

	/**
	 *	Devuelve los registros del objeto relacionados con la tabla padre utilizando
	 *	el identificador (llave primaria) del objeto actualmente referenciado.
	 *	@param tablaPadre el nombre de la tabla padre de la que se desea buscar el
	 *	elemento
	 *	@param valor el valor que se desea hallar.
	 */
/*	public ResultSet obtenerReferenciantes(String tablaPadre, String valor)
	{
		ResultSet rs;
		int i=0,j=0;
		i=tablasPadre.indexOf(tablaPadre);
		if(i==-1)
			return null;
		else
		{
			String consulta=new String("");
			consulta="SELECT  * FROM " +tabla +" ";
			consulta+="WHERE "+llavesForaneas.elementAt(i) +"="+valor;
			try
			{
				rs=con.consultar(consulta);
}
			catch(Exception e)
			{
			//	if(rs!=null)
			//	rs.close();
				System.out.println(e.toString());
				return null;
			}
		}
		return rs;
	}




*/
/*	public int obtenerNumCampoReferenciantes(ResultSet rs)
	{
		int i=0;
		try
		{
			while(rs.next())
			{
				i++;
			}
		} catch(Exception e)
		{
			System.out.println("Error al leer el ResultSet" +e.toString());
			return 0;
		}
		return i;
	}
*/
/*

	public String[] obtenerCampoReferenciantes(String tablaPadre, String valor, String Campo)
	{
		String[] resultado;
		int i=0,j=0;
		i=tablasPadre.indexOf(tablaPadre);
		if(i==-1)
			return null;

		else
		{
			String consulta=new String("");
			consulta="SELECT  "+ Campo +" FROM " +tabla +" ";
			consulta+="WHERE "+llavesForaneas.elementAt(i) +"="+valor;
			try
			{
				ResultSet rs=con.consultar(consulta);
				int camposref=obtenerNumCampoReferenciantes(rs);
				if(camposref<=0)
					resultado=new String[0];
				else
					resultado=new String[camposref];
				rs=con.consultar(consulta);
				while(rs.next())
				{
					resultado[j]=rs.getString(Campo);
					j++;
				}

			}
			catch(Exception e)
			{
				System.out.println(e.toString());
				return null;
			}finally{

		}
		}

		return resultado;
	}

*/
/*	public String[] obtenerCampoReferenciantesOrd(String tablaPadre, String valor, String Campo, String CampoOrd)
	{
		String[] resultado;
		int i=0,j=0;
		i=tablasPadre.indexOf(tablaPadre);
		if(i==-1)
			return null;

		else
		{
			String consulta=new String("");
			consulta="SELECT  "+ Campo +" FROM " +tabla +" ";
			consulta+="WHERE "+llavesForaneas.elementAt(i) +"="+valor;
			consulta+=" ORDER BY "+ CampoOrd;
			try
			{
				ResultSet rs=con.consultar(consulta);
				int camposref=obtenerNumCampoReferenciantes(rs);
				if(camposref<=0)
					resultado=new String[0];
				else
					resultado=new String[camposref];
				rs=con.consultar(consulta);
				while(rs.next())
				{
					resultado[j]=rs.getString(Campo);
					j++;
				}

			}
			catch(Exception e)
			{
				System.out.println(e.toString());
				return null;
			}finally{

		}
		}

		return resultado;
	}
*/
	/** Actualiza el campo que se pasa en el primer parametro
	  * con los valores recibidos en el segundo, el tercer par�metro
	  * guarda una comilla simple si el campo es un caracter o nada
	  * si es un campo numerico
	  * @param nombreCampo el campo que se desea actualizar
	  *	@param valorCampo valor del campo
	  *	@param tipoCampo el tipo del campo (comilla simple ' o nada)
	  *	@return true si la actualizacion fue satisfactoria, false en cualquier otro
	  *	caso
	 */

	public boolean actualizarCampo(String nombreCampo, String valorCampo, String tipoCampo) throws SQLException
	{
		String qry = "UPDATE "+tabla+ "SET"+ nombreCampo + "=" + tipoCampo+ valorCampo+ tipoCampo;
		return con.actualizar(qry)>=1?true:false;
	}




	public void reemplazarTipo(String campo, String tipo)
	{
	tipos.remove(campos.indexOf(campo));
	tipos.add(campos.indexOf(campo), tipo);
	}
/*	public Persistente(String tbl,Conexion c)
	{
		con=c;
		tabla=tbl;
		cargarInformacion();
	}*/


		/**
	 *	Almacena la informaci�n que esta almacenada en el objeto actual en la
	 *	base de datos para ser guardado en batch .
	 *	@param ninguno
	 *	@return boolean
	 */
	public void guardarEnBatch() throws SQLException
	{
  		String qry="";
		qry=crearInsert();
	//System.out.println("Sentecia: "+ qry);
		try{
		con.adicionarBatch(qry);
		}catch(Exception e)
		{
			System.out.println(e.toString()+" Error en la adicion en el Batch..");
			throw new SQLException (e.getMessage());

		}
	}

	public int[] ejecutarBatch() throws SQLException
	{
		try{
		return con.ejecutarBatch();
		}catch(Exception e)
		{
			System.out.println(e.toString()+" Error en la adicion en el Batch..");
			throw new SQLException (e.getMessage());

		}
	}

	public String crearUpdate()
	{
		String qry="";

			//Crea sentencia para actualizar un registro existente

			qry+="UPDATE "+tabla+" SET ";
			for(int i=0;i<numCampos;i++)
			{
				if(valores.elementAt(i) != null)
					qry+=campos.elementAt(i)+"="+tipos.elementAt(i)+valores.elementAt(i)+tipos.elementAt(i);
				else
					qry+=campos.elementAt(i)+"="+tipos.elementAt(i)+tipos.elementAt(i);
				if(i<numCampos-1)
					qry+=",";
			}
			qry+=" WHERE ";
			for(int i=0;i<numCamposPK;i++)
			{
				qry+=llavePrimaria.elementAt(i)+"="+tipos.elementAt(campos.indexOf(llavePrimaria.elementAt(i)))+valores.elementAt(campos.indexOf(llavePrimaria.elementAt(i)))+tipos.elementAt(campos.indexOf(llavePrimaria.elementAt(i)));
				if(i<numCamposPK-1)
					qry+=" and ";
			}
			return qry;
	}

	public String  crearInsert()
	{
		String qry="";
			//Crea sentencia para insertar un nuevo registro
			qry="INSERT INTO "+tabla+" (";
			for(int i=0;i<numCampos;i++)
			{
				qry+=campos.elementAt(i);
				if(i<numCampos-1)
					qry+=",";
			}
			qry+=") VALUES(";
			for(int i=0;i<numCampos;i++)
			{
				qry+=tipos.elementAt(i).toString()+valores.elementAt(i)+tipos.elementAt(i).toString();
				if(i<numCampos-1)
					qry+=",";
			}
			qry+=")";
			return qry;
	}

	protected void finalize()
	{
	try{
		if(rs!=null)
		 rs.close();

		}catch(Exception e)
		{

		}
	}
	
	public Conexion getCon()
	{
		return con;
	}
}
