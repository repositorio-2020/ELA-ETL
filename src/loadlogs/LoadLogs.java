/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadlogs;

import java.sql.SQLException;
import qo.control.general.ArchivoPlano;
import qo.control.general.Fecha;
import qo.control.general.cConnection;



/**
 *
 * @author user1
 */
public class LoadLogs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
     long existeError = 0;   
      cConnection conx = new cConnection();
        
     System.out.println("--------------------------- Inicio Load Logs Version 20200520 --------------------- ");
        
     ArchivoPlano archivoCargue=new ArchivoPlano();
     
     String lFile = "ANT16042018.csv";
     String lPath = "C:\\CarguePLano\\";
     String lSeparador = "|";
     
     // Validar archivo cargue
     existeError = conx.leerCarpeta();
     
     if ( existeError < 1 ) return;
     
     System.out.println("---------- Inicio de la validacion validarTablaLogs ---------------- nro de Errores "+existeError);
     
      existeError = 0;
      
      //// 1. Validar la estructura del archivo
      // existeError = archivoCargue.validarTablaLogs(conx.nameFile, conx.directorio, lSeparador );

     System.out.println("---------- Fin de la validacion validarTablaLogs ---------------- nro de Errores "+existeError);
      

      
      if ( existeError == 0 ) 
      {
          //// 2. Inicializar Base datos Limpia la tabla de logs en la base de datos e inicializa la secuencia
          //conx.ejecutarProcedimiento("inicializa_BD()");

          //// 3. Cargar Archivo a la Base de datos
          System.out.println("---------- 3. Cargar Archivo a la Base de datos ----------------  ");
          //archivoCargue.cargarTablaLogs( conx.nameFile, conx.directorio, lSeparador , conx);          

          
          System.out.println("---------- 4. Crear parametros curso, estudiantes , programa ----------------  ");

          //// 4. Crear parametros curso, estudiantes , programa
          //conx.ejecutarProcedimiento("crea_parametros()");

          System.out.println("---------- 5. Crear procesos para ejecucion ----------------  ");
          //// 5. Crear procesos para ejecucion
          //conx.ejecutarProcedimiento("crear_procesos_ejecuta()");
          
          
          System.out.println("---------- 6. Calcular los indicadores  ----------------  ");
          // 6. Calcular los indicadores 
          conx.ejecutarProcedimiento("ind_proceso()");
          
          // 7. Dispone de manera horizontal los indicadores
          //conx.ejecutarProcedimiento("consolida_fila()");

          
          
          
          
      }
      
      else {
         System.out.println("Errores Generados - Numero de errores  "+existeError);          
      }
        
    }
    
}
