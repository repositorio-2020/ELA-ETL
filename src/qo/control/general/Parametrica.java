package qo.control.general;
import java.util.*;
import java.sql.*;

public class Parametrica extends Persistente {
  	String tablaParametrica;
	String tablaCompleja;
	String idTablaParametrica;
	String idTablaCompleja;
	String idRefTablaCompleja;
	
  public Parametrica(String tblP,String idTblP,Conexion c){
		super(tblP,c);
		tablaParametrica=tblP;
		idTablaParametrica=idTblP;
	}
	
	public void fijarTablaCompleja(String tblC,String idTblC,String idRefTblC){
		tablaCompleja=tblC;
		idTablaCompleja=idTblC;
		idRefTablaCompleja=idRefTblC;
	}

					
	
	//RETORNA EL NuMERO DE REGISTROS DE LA TABLA COMPLEJA QUE EST�N REFERENCIADOS 
	//POR EL IDENTIFICADOR CARGADO EN MEMORIA PAR LA TABLA PARAM�TRICA
	public int obtenerNumReferenciantes() {
		int x=0;
		if(obtenerValorCampo(idTablaParametrica)==null)
			return -1;
		ResultSet rs=con.consultar("SELECT COUNT("+idTablaCompleja+") FROM "+tablaCompleja+" WHERE "+idRefTablaCompleja+"="+obtenerValorCampo(idTablaParametrica)+"");//se quitaron las comillas!!!
		try{
			if(rs.next())
				x=Integer.parseInt(rs.getString(1));
		}
		catch(Exception e){
			System.out.println(e.toString());
			return -1;
		}
		return x;
  }
  
	//RETORNA UN ARREGLO QUE CONTIENE LOS IDENTIFICADORES DE LOS REGISTROS QUE SON REFEENCIADOS
	//POR EL IDENTIFICADOR DE LA TABLA PARaMETRICA QUE ESTA CARGADO EN MEMORIA.
	public String[] referenciantes() {
  		if(obtenerNumReferenciantes()<0)
  			return null;
  		String usr[]=new String[obtenerNumReferenciantes()];
		int i=0;
		ResultSet rs=con.consultar("SELECT "+idTablaCompleja+" FROM "+tablaCompleja+" WHERE "+idRefTablaCompleja+"="+obtenerValorCampo(idTablaParametrica)+"");
		try{
			while(rs.next()){
				usr[i]=rs.getString(idTablaCompleja);
				i++;
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
			return null;
		}	
		return usr;
  }
	
	
	
	
	public String obtenerOpcionesHTML(String val,String nom,String sel){
		GeneradorHTML gen=new GeneradorHTML();
		return gen.generarOpciones(obtenerValoresDeCampo(val),obtenerValoresDeCampo(nom),sel);
	}

	public static void main(String[] args){
		
	
		
		
		////String a="sun.jdbc.odbc.JdbcOdbcDriver",b="jdbc:odbc:transmilenio",c="sa",d="";
		//Conexion cone=new Conexion(a,b,c,d);
		/*
		Parametrica e=new Parametrica("ESTADO_USUARIO","id_estado_usuario",cone);
		//e.imprimirCampos();
		e.fijarTablaCompleja("USUARIO","id_usuario","id_estado_usuario");
		System.out.println("Fija: "+e.fijarValor("id_estado_usuario","1"));
		System.out.println("Valor: "+e.obtenerValorCampo("id_estado_usuario"));
		System.out.println("Cuenta: "+e.obtenerNumReferenciantes());
    String u[]=e.referenciantes();		
		for(int i=0;i<u.length;i++)
			System.out.println("Usuario: "+u[i]+"\n");	
		*/
		//Parametrica p=new Parametrica("PERFIL","id_perfil",cone);
		//System.out.println(p.obtenerOpcionesHTML("id_perfil","nombre_perfil","1"));
	}

} 
