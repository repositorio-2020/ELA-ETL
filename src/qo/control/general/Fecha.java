package qo.control.general;
import java.util.*;
import java.text.*;

import org.apache.log4j.Logger;



public class Fecha {


/**
 *  Variable statica solicitada por el log4j.
 *
 **/
static Logger logger = Logger.getLogger(Fecha.class);


	Date hoy=new Date();
        
	GregorianCalendar gc = new GregorianCalendar();

	public Fecha(){
		gc.setTime(hoy);
	}
        
public void setFecha(){
            
                hoy=new Date();
		gc.setTime(hoy);
                
	}        

	/**
	 *	Devuelve el nombre de acuerdo al n�mero del mes en el a�o
	 *	@param numMes = numero del mes (1=enero, 12= diciembre)
	 *	@return nombre corto del mes (ene, feb, mar, Abr, Jun, etc)
	 */
	public String devolverMes(String numMes)
	{

	//	String meses[] = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dec"};
		String meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

		int i = Integer.parseInt(numMes);

		if(i>0 && i<=12)
			return meses[i-1];
		else
			return null;
	}


	/**
	 *	Devuelve el nombre del mes en espa�ol en 3 letras  de acuerdo al su n�mero a�o
	 *	@param numMes = numero del mes (1=enero, 12= diciembre)
	 *	@return nombre corto del mes (ene, feb, mar, Abr, May, etc)
	 */
	public String devolverMesEsp(String numMes)
	{


		String meses[] = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

		int i = Integer.parseInt(numMes);

		if(i>0 && i<=12)
			return meses[i-1];
		else
			return null;
	}

		/**
	 *	Devuelve el nombre del mes en espa�ol en 3 letras  de acuerdo al su n�mero a�o
	 *	@param numMes = numero del mes (1=enero, 12= diciembre)
	 *	@return nombre corto del mes (ene, feb, mar, Abr, May, etc)
	 */
	public String devolverMesCompletoEsp(String numMes)
	{


		String meses[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};

		int i = Integer.parseInt(numMes);

		if(i>0 && i<=12)
			return meses[i-1];
		else
			return null;
	}

	/**
	 *	Devuelve el nombre del mes en ingles en 3 letras  de acuerdo al su n�mero a�o
	 *	@param numMes = numero del mes (1=enero, 12= diciembre)
	 *	@return nombre corto del mes (jan, feb, mar, Apr, may, etc)
	 */
	public String devolverMesIng(String numMes)
	{

		String meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


		int i = Integer.parseInt(numMes);

		if(i>0 && i<=12)
			return meses[i-1];
		else
			return null;
	}


	public String hoyMesEnLetras(){
		gc.setTime(hoy);
		return gc.get(Calendar.DAY_OF_MONTH) +"-"+ devolverMesEsp(Integer.toString(gc.get(Calendar.MONTH)+1)) +"-"+ gc.get(Calendar.YEAR) ;
	}
	public String hoyMesEnLetrasIngles(){
		gc.setTime(hoy);
		return gc.get(Calendar.DAY_OF_MONTH) +"-"+ devolverMesIng(Integer.toString(gc.get(Calendar.MONTH)+1)) +"-"+ gc.get(Calendar.YEAR) ;
	}
	public String hoy(){
		gc.setTime(hoy);
		return gc.get(Calendar.DAY_OF_MONTH) +"-"+ (gc.get(Calendar.MONTH)+1) +"-"+ gc.get(Calendar.YEAR);
	}

	public String getAno(){
		gc.setTime(hoy);
		return "" + gc.get(Calendar.YEAR);
	}

	public String getMes(){
		gc.setTime(hoy);
		return "" + (gc.get(Calendar.MONTH)+1);
	}

	public String getDia(){
		gc.setTime(hoy);
		return "" + (gc.get(Calendar.DAY_OF_MONTH));
	}

	public String consultaFecha(int campo, int valor){
		gc.setTime(hoy);
		int mes = gc.get(Calendar.MONTH);
		gc.roll(campo, valor);
		if (gc.get(Calendar.MONTH)>mes)
			gc.set(Calendar.YEAR,gc.get(Calendar.YEAR)-1);
		return gc.get(Calendar.DAY_OF_MONTH) +"-"+ (gc.get(Calendar.MONTH)+1) +"-"+ gc.get(Calendar.YEAR);
	}

	public String unDiaAntes(){
		return consultaFecha(Calendar.DAY_OF_YEAR,-1);
	}

	public String unaSemanaAntes(){
		return consultaFecha(Calendar.WEEK_OF_YEAR,-1);
	}

	public String unMesAntes(){
		return consultaFecha(Calendar.MONTH,-1);
	}

	public String unSemestreAntes(){
		return consultaFecha(Calendar.MONTH,-6);
	}


	public String xDiasDespues(int xDias ){
		return consultaFecha(Calendar.DAY_OF_YEAR,xDias);
	}


	/**
	 *	Toma una fecha en el formato DD:MM:AA HH:MM:SS.xx y lo convierte al formato
	 *	DD:MM:AA
	 *	@param strFecha la fecha que se desea convertir
	 *	@return String con la fecha en forma DD:MM:AA
	 */
	public String formatearFecha(String strFecha)
	{

		java.util.Date fecha = new java.util.Date();
		int ind=strFecha.indexOf(" ");
		String f="";
		f=(ind>=0?strFecha.substring(0,ind):strFecha);
		fecha = java.sql.Date.valueOf(f);
		return fecha.toString();

	}

		/**
	 *	Este m�todo descompone la fecha devuelta por
	 *	el sistema en un arreglo que tiene el a�o, mes
	 *	y dia
	 *	@param strFecha Sring con la fecha en formato AA:MM:DD HH:MM:SS.xx
	 *	@return Arreglo string con el siguiente orden
	 *	String[0] = a�o
	 *	String[1] = mes
	 *	String[2] = dia
	 */
	public String[] descomponerFecha(String strFecha)
	{
		int ind=strFecha.indexOf(" ");
		String f="";
		f=(ind>=0?strFecha.substring(0,ind):strFecha);
		StringTokenizer temp = new StringTokenizer(f, "-");
		int i = temp.countTokens();
		String strRet[] = new String[i];
		for(int j= 0; j<i;j++)
			strRet[j] = temp.nextToken();
		return strRet;
	}


		/**
	 *	Genera un arreglo con el n�mero de dias del mes, tiene en cuenta si el a�o
	 *	es bisiesto o no.
	 *	@param mes n�mero del mes del que se desea generar los dias
	 *	@param bisiesto boolean que indica si el a�o es bisiesto
	 *	@return arreglo string cuyo tama�o es el n�mero de dias del mes
	 */
	public String[] generarDias(String mes, boolean bisiesto)
	{
		String dias[] = {""};
		int i, diasMes;

		// se buscan los meses que tienen 31 dias

		if(mes.equals("01") || mes.equals("03") || mes.equals("05") ||mes.equals("07") ||
			mes.equals("08") || mes.equals("10") ||mes.equals("12"))
		{
			dias = new String[31];
			for(i = 0; i<31; i++)
			{
				if(i<9)
					dias[i] = "0"+Integer.toString(i+1);
				else
					dias[i] = Integer.toString(i+1);
			}
		}

		// Ahora se buscan los meses de 30 d�as
		else if(mes.equals("04") || mes.equals("06") || mes.equals("09") ||
			mes.equals("11"))
		{
			dias = new String[30];
			for(i = 0; i<30; i++)
			{
				if(i<9)
					dias[i] = "0"+Integer.toString(i+1);
				else
					dias[i] = Integer.toString(i+1);
			}
		}

		// mes de febrero cuando el a�o es bisiesto
		else if (mes.equals("02") && bisiesto)
		{
			dias = new String[29];
			for(i = 0; i<29; i++)
			{
				if(i<9)
					dias[i] = "0"+Integer.toString(i+1);
				else
					dias[i] = Integer.toString(i+1);
			}
		}

		// mes de febrero cuando el a�o no es bisiesto
		else if (mes.equals("02") && !bisiesto)
		{
			dias = new String[28];
			for(i = 0; i<28; i++)
			{
				if(i<9)
					dias[i] = "0"+Integer.toString(i+1);
				else
					dias[i] = Integer.toString(i+1);
			}
		}

		return dias;
	}

	// m�todo que verifica si la fecha de incio es anterior a la fecha de finalizacion
	/**
	 *	Verifica si una fecha es anterior a otra.
	 *	@param strFechaIni String que tiene la primera fecha que se desea comprobar
	 *	@param strFechaFin String que tiene la segunda fecha que se desea comprobar
	 *	@return true si strFechaIni es anterior a strFechaFin.
	 */
	public boolean verificarFechas(String strFechaIni, String strFechaFin)
	{

		boolean esCorrecto=false;
		java.util.Date fechaIni = new java.util.Date();
		java.util.Date fechaFin = new java.util.Date();

		fechaIni = java.sql.Date.valueOf(strFechaIni);
		fechaFin = java.sql.Date.valueOf(strFechaFin);
		esCorrecto = fechaIni.before(fechaFin);
		if(!esCorrecto)
			if(!fechaIni.after(fechaFin))
				esCorrecto = true;
		return esCorrecto;
	}

	/**
	 *	Este m�todo calcula si un a�o es bisiesto o no.
	 *	@param strAnio String que tiene el valor del a�o
	 *	@return true si el a�o es bisiesto, false en caso contrario,
	 */
	public boolean esBisiesto(String strAnio)
	{
		int anio = Integer.parseInt(strAnio);


		// comparar si el a�o es inicio de siglo
		if (java.lang.Math.IEEEremainder((double)anio, (double)100) != 0)
		{
			// si el a�o es divisible por 4, entonces es
			// bisiesto
			if (java.lang.Math.IEEEremainder((double)anio, (double)4) == 0)
				return true;
			return false;
		}
		else
		{
			// si es inicio de siglo, debe ser divisible
			// por 400 para que sea bisiesto
			// por ejemplo 1900 no fue bisiesto aunque
			// 1896 y 1904 lo fueron
			if (java.lang.Math.IEEEremainder((double)anio, (double)400) == 0)
				return true;
			return false;
		}

	}


	/**
	 *	Genera una lista de a�os entre dos a�os determinados
	 *	@param strAnioIni el a�o de inicio de la lista
	 *	@param strAnioFin el a�o de finalizaci�n de la lista
	 *	@return arreglo String con la lista de a�os entre strAnioIni y strAnioFin
	 */
	public String[] generarAnios(String strAnioIni, String strAnioFin)
	{
		String[] resultado = null;
		int anioIni= Integer.parseInt(strAnioIni), anioFin = Integer.parseInt(strAnioFin);
		int numanios = anioFin - anioIni;
		resultado = new String[numanios+1];
		for(int i = anioIni; i<=anioFin; i++)
			resultado[i-anioIni] = Integer.toString(i);
		return resultado;

	}
	
	public static boolean comprobarFechas(String fecha1, String fecha2)
	{
		try{
			SimpleDateFormat formato=  new SimpleDateFormat("yyyy-MM-dd") 
;
		//	formato.setLenient(false);
			Date inicio= formato.parse(fecha1);
			Date fin= formato.parse(fecha2);
			if(inicio.compareTo(fin)<=0)
			 return true;
				
		}catch(Exception e)
		{
			
			logger.error("Error "+e.toString());
			e.printStackTrace();
		}
		return false;
	}



	public static Date crearFecha(String agno, String mes, String dia)
	{
		
		String fecha = agno +"-" + mes + "-" + dia;
		Date inicio= null;
		
		try{
			SimpleDateFormat formato=  new SimpleDateFormat("yyyy-MM-dd") ;

	        inicio= formato.parse(fecha);
			
		}catch(Exception e)
		{
			
			logger.error("Error "+e.toString());
			e.printStackTrace();
		}
		return inicio;
	}


	public String diasDespuesFecha(Date fecha, int valor){
		gc.setTime(fecha);
		int mes = gc.get(Calendar.MONTH);
		gc.roll(Calendar.DAY_OF_YEAR, valor);
		if (gc.get(Calendar.MONTH)>mes)
			gc.set(Calendar.YEAR,gc.get(Calendar.YEAR));
//		return gc.get(Calendar.DAY_OF_MONTH) +"-"+ (gc.get(Calendar.MONTH)+1) +"-"+ gc.get(Calendar.YEAR);
		return gc.get(Calendar.YEAR) +"-"+ (gc.get(Calendar.MONTH)) +"-"+ gc.get(Calendar.DAY_OF_MONTH);


	}


	public String despuesDe(int campo, int valor){
		gc.setTime(hoy);
		int mes = gc.get(Calendar.MONTH);
		System.out.println( gc.get(Calendar.DAY_OF_MONTH) +"-"+ (gc.get(Calendar.MONTH)) +"-"+ gc.get(Calendar.YEAR)) ;
		gc.roll(campo, valor);
		return gc.get(Calendar.DAY_OF_MONTH) +"-"+ (gc.get(Calendar.MONTH)) +"-"+ gc.get(Calendar.YEAR);
	}


/////////////////////


/**
 * calculaFecha
 * Calcula la fecha al a�o, mes, dia le incrementa sumaMes al mes y retorna 
 * la fecha en el formato requerido de acuerdo a la base de datos 
 * correspondiente
 * 
 * String agno: A�o de la fecha
 * String mes: Mes de la fecha 
 * String dia: Dia de la fecha 
 * int sumaMes: Numero de meses a sumar al mes. 0, no suma nada.
 * String p_bd: Determina la BD para efectos de definir el formato.
 *              ORACLE
 *              SQLANY : Sql Anyware
 *
 *
 */


	public static String calculaFecha(String p_agno, String p_mes, String p_dia, int sumaMes, String p_bd)
	{
		
		String fecha = "";
		Date inicio= null;
		int mesInt = 0;		
		int agnoInt = 0;		
		int diaInt  =0;
		String fchSalida = "";
		
		try{
			
			
		  diaInt  =	Integer.valueOf(p_dia).intValue();	
		  mesInt  = Integer.valueOf(p_mes).intValue();	
		  agnoInt = Integer.valueOf(p_agno).intValue();	
		  
		  mesInt  += sumaMes;
		  
          // Sumar el mes 
          if ( mesInt > 12 ) 
          {
          	mesInt = mesInt - 12;   // Retorna el mes del proximo a�o
          	agnoInt++;              // Incremento en uno el a�o
          }	
          
          
          p_mes  = mesInt + "";
          p_dia  = diaInt + "";
          p_agno = agnoInt + "";
          
          // Pasar mes a string
          if ( mesInt < 10)  p_mes = "0" + mesInt;
          
          // Pasar dia a string
          if ( diaInt < 10)  p_dia = "0" + diaInt;
          
          
         	
							
		  if ( p_bd.equals("ORACLE") ) 
		  {
		  	
		  	System.out.println("Sacar fecha en formato Oracle ");
		  	fchSalida =  p_dia + "-" + p_mes + "-" + p_agno;
		  	
		  	
		  }		
		  else
		  {
		  	System.out.println("Sacar fecha en Otro formato  ");
		  	
		  	fchSalida = p_agno +"-" + p_mes + "-" + p_dia;
		  			  	
		  }
			
		}catch(Exception e)
		{
			
			System.out.print("Error "+e.toString());
			e.printStackTrace();
		}
		return fchSalida;
	}




/////////////////////////////////////////////

/**
 * formatoFecha
 * Calcula la fecha al a�o, mes, dia le incrementa sumaMes al mes y retorna 
 * la fecha en el formato requerido de acuerdo a la base de datos 
 * correspondiente
 * 
 * String agno: A�o de la fecha
 * String mes: Mes de la fecha 
 * String dia: Dia de la fecha 
 * int sumaMes: Numero de meses a sumar al mes. 0, no suma nada.
 * String p_bd: Determina la BD para efectos de definir el formato.
 *              ORACLE
 *              SQLANY : Sql Anyware
 *
 *
 */


	public static String formatoFecha(String p_agno, String p_mes, String p_dia, String p_formato )
	{
		
		String fecha = "";
		Date inicio= null;
		String fchSalida = "";
		
		try{

           
           p_mes  = p_mes.trim();
           p_dia  = p_dia.trim();
           p_agno = p_agno.trim();
           
           
         if ( p_mes.length() == 1 )  p_mes = "0" + p_mes;			

         if ( p_dia.length() == 1 )  p_dia = "0" + p_dia;			
         
			
		  if ( p_formato.equals("YYYY/MM/DD") ) 
		  {
		  	
		  	fchSalida =  p_agno + "/" + p_mes + "/" + p_dia;
   		    return fchSalida;
		  	
		  }		

           
							
		  if ( p_formato.equals("DD/MM/YYYY") ) 
		  {
		  	
		  	fchSalida =  p_dia + "/" + p_mes + "/" + p_agno;
   		    return fchSalida;
		  	
		  }		

		  if ( p_formato.equals("DD-MM-YYYY") ) 
		  {
		  	
		  	fchSalida =  p_dia + "-" + p_mes + "-" + p_agno;
		  	return fchSalida;
		  }		

		  
		  if ( p_formato.equals("YYYY-MM-DD") ) 
		  {
		  	
		  	fchSalida =  p_agno +"-" + p_mes + "-" + p_dia;
		  	return fchSalida;
		  	
		  }		
		  

		  if ( p_formato.equals("YYYY/MM/DD") ) 
		  {
		  	
		  	fchSalida =  p_agno +"/" + p_mes + "/" + p_dia;
		  	return fchSalida;
		  	
		  }		

  
  
     	fchSalida =  p_agno +"/" + p_mes + "/" + p_dia;
      
			
		}catch(Exception e)
		{
			
			System.out.print("Error "+e.toString());
			e.printStackTrace();
		}
		return fchSalida;
	}



	public String getHora(){
		gc.setTime(hoy);
		return "" + gc.get(Calendar.HOUR);
	}

	public String getMinuto(){
		gc.setTime(hoy);
		return "" + gc.get(Calendar.MINUTE);
	}

	public String getSegundo(){
		gc.setTime(hoy);
		return "" + gc.get(Calendar.SECOND);
	}

	public static String formatoHora(String p_hor, String p_min, String p_seg, String p_formato )
	{
		
		String hora = "";
		String horaSalida = "";
		
		try{

           
           p_hor  = p_hor.trim();
           p_min  = p_min.trim();
           p_seg = p_seg.trim();
           
           
         if ( p_hor.length() == 1 )  p_hor = "0" + p_hor;			

         if ( p_min.length() == 1 )  p_min = "0" + p_min;			
         
         if ( p_seg.length() == 1 )  p_seg = "0" + p_seg;			
			
		  if ( p_formato.equals("HH:MM:SS") ) 
		  {
		  	
		  	horaSalida =  p_hor + ":" + p_min + ":" + p_seg;
   		    return horaSalida;
		  	
		  }		

		}catch(Exception e)
		{
			
			System.out.print("Error "+e.toString());
			e.printStackTrace();
		}
		return horaSalida;
	}
        
        
        
        
///////////////////





	public static void main(String arg[]) {
		Fecha f=new Fecha();
		Date fecha = null;
		String strFecha = "";
		String formatoFecha[] = {"","",""};

/*		
		System.out.println("\nHoy: "+f.hoy());
		System.out.println("\nAyer: "+f.unDiaAntes());
		System.out.println("\nSemana Pasada: "+f.unaSemanaAntes());
		System.out.println("\nHace un Mes: "+f.unMesAntes());
		System.out.println("\nHace un Semestre: "+f.unSemestreAntes());
		System.out.println("\ncon: "+Fecha.comprobarFechas("2006-12-31","1995-01-01"));
		System.out.println("\n: "+Fecha.comprobarFechas("2006-12-31","1995-01-01"));
		System.out.println("\n:----------------------------------------------- ");
		System.out.println("\n:Fecha  "+f.hoy);
		System.out.println("\n:Ano ----   "+f.getAno());
		System.out.println("\n:Ano ----   "+f.getMes());
		System.out.println("\nTRes dias despues: "+f.xDiasDespues(3));
         fecha = f.crearFecha("1968","10","28");		
         System.out.println(" Fecha armada  "+fecha);         
         strFecha = f.diasDespuesFecha(fecha, 30);	
         System.out.println(" Fecha armada 30 dias despuest "+strFecha);         
*/		

//      System.out.println(" Un dias despues "+f.despuesDe(Calendar.DAY_OF_YEAR,20));


//		System.out.println("\nHoy: "+f.hoy());
				

//        System.out.println(" 0 Mes despues " + f.calculaFecha("2007", "04", "10", 1, "ORACLE"));
//        System.out.println(" Dia " + f.getDia()+" Mes  "+f.getMes()+" Agno "+f.getAno());
        System.out.println(" Fecha - 1 "+f.formatoFecha(f.getAno(), f.getMes(), f.getDia(), "DD/MM/YYYY HH:MM"));
        System.out.println(" Fecha - 2 "+f.formatoFecha(f.getAno(), f.getMes(), f.getDia(), "YYYY-MM-DD"));
        System.out.println(" Fecha - Hora "+f.formatoHora(f.getHora(), f.getMinuto(), f.getSegundo(), "HH:MM:SS"));
        
        System.out.println(" Hora "+f.getHora());        
        System.out.println(" MInuto "+f.getMinuto());        
        System.out.println(" Fecha DD/MM/YYYY "+f.formatoFecha(f.getAno(), f.getMes(), f.getDia(), "DD/MM/YYYY"));

        
        
         
         	
	}



}
