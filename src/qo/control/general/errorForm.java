package qo.control.general;

import java.util.*;


public class errorForm {

  private String codigo     = "";  // Numero error retornado
  private String tipo       = "";  // SQLExeption, etc.
  private String mensaje    = "";  // Mensaje original de la exeption
  private String traduccion = "";  // Mensaje traducido
  

  public errorForm() {
  }


  public String getCodigo() {
    return this.codigo;
  }

  public String getTipo() {
    return this.tipo;
  }

  public String getMensaje() {
    return this.mensaje;
  }

  public String getTraduccion() {
    return this.traduccion;
  }



  public void setCodigo( String vlr) {
    this.codigo = vlr;
  }
  
  public void setTipo( String vlr) {
    this.tipo = vlr;
  }
  
  public void setMensaje( String vlr) {
    this.mensaje = vlr;
  }
  
  public void setTraduccion( String vlr) {
    this.traduccion = vlr;
  }
 
}
