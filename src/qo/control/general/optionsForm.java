 package qo.control.general;
 
 import java.sql.*; 
 // Version 1.0 - 2004 Marzo 23  -------------------- acc_mdl_modulo
 // Autor: David R Lara Amaya. Cel: 310 8629671 acc_mdl_modulo


  public class optionsForm  { 

// Definicion Atributos de la Forma.
   private String valor = null;  
   private String option = null;  



// Definicion Set and Get .


   public void setValor(String text) { 
     this.valor = text; 
   } 

   public String getValor( ) { 
     return this.valor ; 
   } 


   public void setOption(String text) { 
     this.option = text; 
   } 

   public String getOption( ) { 
     return this.option ; 
   } 


 } // Fin Class  optionsForm

