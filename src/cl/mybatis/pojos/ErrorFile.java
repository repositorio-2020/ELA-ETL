/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.mybatis.pojos;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author David Lara 
 * @version 20191030
 * @see pojo para el almacenamiento de Calendario
 * 
 */

public class ErrorFile implements Serializable  {

    
    private String erf_secue;
    private String erf_namefile;
    private String erf_linea;
    private String erf_nrolinea;    
    private String erf_error;
    private Date erf_fchhora;
    
   
    public ErrorFile() {
    }

    public String getErf_secue() {
        return erf_secue;
    }

    public void setErf_secue(String erf_secue) {
        this.erf_secue = erf_secue;
    }

    public String getErf_namefile() {
        return erf_namefile;
    }

    public void setErf_namefile(String erf_namefile) {
        this.erf_namefile = erf_namefile;
    }

    public String getErf_linea() {
        return erf_linea;
    }

    public void setErf_linea(String erf_linea) {
        this.erf_linea = erf_linea;
    }

    public String getErf_nrolinea() {
        return erf_nrolinea;
    }

    public void setErf_nrolinea(String erf_nrolinea) {
        this.erf_nrolinea = erf_nrolinea;
    }

    public String getErf_error() {
        return erf_error;
    }

    public void setErf_error(String erf_error) {
        this.erf_error = erf_error;
    }

    public Date getErf_fchhora() {
        return erf_fchhora;
    }

    public void setErf_fchhora(Date erf_fchhora) {
        this.erf_fchhora = erf_fchhora;
    }

    
    
    
    
    
    public void limpiar() {

        
    this.erf_secue = null;
    this.erf_namefile = null;
    this.erf_linea = null;
    this.erf_nrolinea = null;    
    this.erf_error = null;
    this.erf_fchhora = null;
        

        
    }   
    
   
    
    
    
}
