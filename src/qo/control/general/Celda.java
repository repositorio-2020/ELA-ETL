/* Clase Celda
 * David Ricardo Lara Amaya 
 * Cel 310 862 9671
 */

package qo.control.general; 

public class Celda {
	int fila;
	int columna;
        int hoja;
	String tipoDato;   // N - Numero, S - String, D Date Fecha
	String valor;
	String formato = "";
        String nomPestana = "";
        int codTipoDato = 601;  // Por defecto cadena de caracteres.
        
	
	public void add (int p_fila,int p_columna,String p_valor){
		this.fila=p_fila;
	    this.columna=p_columna;
	    this.valor=p_valor;
	    this.tipoDato = "S";
	    this.formato = "";
            this.hoja = 0;
            this.nomPestana = "";
	}
	

	public void add (int p_fila,int p_columna,String p_valor, String p_tipoDato){
		this.fila=p_fila;
	    this.columna=p_columna;
	    this.valor=p_valor;
	    this.tipoDato = p_tipoDato.trim();
            this.hoja = 0;
            this.nomPestana = "";
	}


	public void add (int p_hoja,int p_fila,int p_columna,String p_valor, int p_tipoDato){
            this.fila       = p_fila;
	    this.columna    = p_columna;
	    this.valor      = p_valor;
	    this.tipoDato   = "S";
            this.hoja       = p_hoja;
            this.codTipoDato = p_tipoDato;
            this.nomPestana = "";

	}

        
	public void add (String p_pestana,int p_fila,int p_columna,String p_valor, int p_tipoDato){
            this.fila       = p_fila;
	    this.columna    = p_columna;
	    this.valor      = p_valor;
	    this.tipoDato   = "S";
            this.hoja       = 0;
            this.codTipoDato = p_tipoDato;
            this.nomPestana = p_pestana;

	}
        
        
        
	public int getFila(){
		return this.fila;
	}
	
	public int getColumna(){
		return this.columna;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	public String getTipoDato(){
		return this.tipoDato;
	}

    public int getCodTipoDato() {
        return codTipoDato;
    }

    public void setCodTipoDato(int codTipoDato) {
        this.codTipoDato = codTipoDato;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getHoja() {
        return hoja;
    }

    public void setHoja(int hoja) {
        this.hoja = hoja;
    }

    public String getNomPestana() {
        return nomPestana;
    }

    public void setNomPestana(String nomPestana) {
        this.nomPestana = nomPestana;
    }
	
	
        
        
        
        
        
        
}