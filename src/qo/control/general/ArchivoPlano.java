/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qo.control.general;

import cl.beans.ErrorFileBean;
import cl.beans.MdlLogStoreBean;
import cl.mybatis.pojos.ErrorFile;
import cl.mybatis.pojos.MdlLogStore;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.*;


/**
 *
 * @author David Lara
 */
public class ArchivoPlano {

   String nameFile;
   String namePath;
   String nameCompleto;
   String linea;
   String separador = Pattern.quote("X");    // Dejarlo como parametro  -- Pattern.quote("|")  - https://www.cdmon.com/es/conversor-timestamp
   
   MdlLogStore dataRegistro = new MdlLogStore();
   List dataRegistroLista = new ArrayList();
   
   MdlLogStoreBean modeloLogStore = new MdlLogStoreBean();
   
   
   
   long charLeidos;
   File archivo = null;
   FileReader fr = null;
   BufferedReader br = null;
   String[] partes = null;

   FileWriter fichero = null;
   PrintWriter pw = null;
   
   
   
   
    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = Pattern.quote(separador);
    }

    public long getCharLeidos() {
        return charLeidos;
    }

    public void setCharLeidos(long charLeidos) {
        this.charLeidos = charLeidos;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public FileReader getFr() {
        return fr;
    }

    public void setFr(FileReader fr) {
        this.fr = fr;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public String getNameCompleto() {
        return nameCompleto;
    }

    public void setNameCompleto( ) {
        this.nameCompleto = this.getNamePath() + this.getNameFile();
    }
   
   
     public int leerArchivo( ) {

         this.setNameFile("test02.csv");
         this.setNameFile("ANT16042018.csv");
         this.setNameFile("ANT16012019.csv");  // Cargue Plano Sergio
         
         
         this.setNamePath("C:\\Bak\\UNAD\\CarquePLano\\");
         this.setNamePath("C:\\DRLA\\Fuentes\\BAK\\CarquePLano\\");
         this.setNameCompleto();
         int numFila = 0;
         
         
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (this.getNameCompleto());   
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null && numFila < 500)
         {
            numFila++; 
            System.out.println("NUmero fila "+numFila + " - "+linea);
         }    
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    return 0;
   }

     // Carga el archivo CSV de logs y realiza las siguientes actividades
    /* 
    •	Leer el archivo y capturar cada uno de los campos separados por el separador.
    •	Validar cada uno de los campos y en caso de nulos colocar valor por defecto.
    •	Calcular la fecha y tiempo trasformando el formato de Linux.
    •	Capturar cada uno de los campos y colocar en las variables 
    •	Guardar en la base de datos cada uno de los registros.
    •	Parametrizar nombre del archivo, path del archivo, separador.
    */
     public int cargarArchivoLogs( ) {

         this.setNameFile("DATA-2019.csv");  // Cargue Plano Sergio

         
         this.setNamePath("C:\\DRLA\\Fuentes\\BAK\\CarquePLano\\"); // Path donde se encuentra el archivo.

         this.setNameCompleto();
         long numFila = 0;
         
         // this.AbrirArchivoGuardar("C:\\Bak\\UNAD\\CarquePLano\\cargue78910.csv");
         this.AbrirArchivoGuardar(this.getNameCompleto());

         try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (this.getNameCompleto());   
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null && (numFila < 10000000 ) )
         {
            if ( (numFila >= 1 && numFila < 2000 ) )
            {        
             System.out.println("NUmero fila "+numFila + " - "+linea);
             this.separaLinea(linea);
            
             this.SaveLinea(linea);
             
            }            
            System.out.println("NUmero fila "+numFila + " - "+linea);
            numFila++; 

            
         }    
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
               this.CerrarArchivo();
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    return 0;
   }

     // Valida la informacion del archivo que va cargarse
    /*   1. Abre el archivo a cargar
         2. Valida la estructura de cada una de las filas
         2.2. Eliminar los errores del log de errores
         3. Registra cada uno de los errores log de errores
         3.1. Longitud del registro  (ERROR - 001 )
         3.2. Verifica el numero de columnas (ERROR - 002 )
    */
     public long validarTablaLogs( String pFile, String pPath, String pSeparador  ) {

         ErrorFile mdlErrorFile = new ErrorFile();   // Pojo
  
         ErrorFileBean mdlErrorFileBean = new ErrorFileBean();  // Bean Modelo
         
         int existeError = 0;
         long totErrores = 0;
         int longitud = 0;
         
         this.setSeparador(pSeparador);
         
//         this.setNameFile("ANT16042018_PTE01.csv");  // Cargue Plano Base del 2019
         this.setNameFile(pFile);  // Cargue Plano Base del 2019
         
//         this.setNamePath("C:\\CarguePLano\\"); // Path donde se encuentra el archivo.
         this.setNamePath(pPath); // Path donde se encuentra el archivo.

         this.setNameCompleto();
         long numFila = 0;

         System.out.println(" ----------------------------- validarTablaLogs - Valida la tabla antes de cargar ------------------- ");
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (this.getNameCompleto());   
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero - Maximo Cargue de archivos de 2 millones
         String linea;
         mdlErrorFileBean.delete();  // Eliminar errores tablas anteriores.
         while((linea=br.readLine())!=null && (numFila <= 10000000 ) )
         {
             
             existeError = 0;
             
                     
             if ( numFila >= 1 && linea.length() > 10  ) {
             //this.separaLinea(linea); 
             this.separaLineaValida(linea);
             existeError = this.validaLineaTabla();
             System.out.println(" ============= > validarTablaLogs - Numero de Filas  "+numFila);
             if ( existeError > 0 ) {
           
                 mdlErrorFile.limpiar();
                 
                 longitud = linea.length();
                 if ( longitud >= 500 ) longitud = 490; 
                 
                 mdlErrorFile.setErf_linea("Datos "+linea.substring(0, longitud)   );   // Registra la linea maximo 
                 mdlErrorFile.setErf_namefile(this.getNameCompleto());
                 mdlErrorFile.setErf_nrolinea(""+numFila);
                        
                 if ( existeError == 1 ) {
                   mdlErrorFile.setErf_error("ERROR Estructura Numero Columnas <> 21.");
                 }
                 
                 if ( existeError == 2 ) {
                   mdlErrorFile.setErf_error("ERROR Fallo en Fecha-Hora-LINUX 10 numeros.");
                   
                 }
                 
                 System.out.println(" ============= > Error Generado -----------  "+numFila);
                 
                 totErrores++;
                 mdlErrorFileBean.adicionar(mdlErrorFile);

             }
            }; 
             
            numFila++; 
            
         }         
        System.out.println(" validarTablaLogs - Numero de Filas  "+numFila+" Numero de errores "+totErrores);

      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
               this.CerrarArchivo();
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    return totErrores;
   }

     
     
     
     // Carga el archivo CSV de logs y realiza las siguientes actividades
    /*  ALMACENA la informacion en la tabla de la base de datos mdl_logstore_standard_log
    •	Leer el archivo y capturar cada uno de los campos separados por el separador.
    •	Validar cada uno de los campos y en caso de nulos colocar valor por defecto.
    •	Calcular la fecha y tiempo trasformando el formato de Linux.
    •	Capturar cada uno de los campos y colocar en las variables 
    •	Guardar en la base de datos cada uno de los registros.
    •	Parametrizar nombre del archivo, path del archivo, separador.
    */
     public int cargarTablaLogs( String pFile, String pPath, String pSeparador, cConnection conBD  ) {

         int i = 0;
         int j = 0;
         
         this.setNameFile(pFile);  // Cargue Plano Base del 2019
         
         this.setNamePath(pPath); // Path donde se encuentra el archivo.
         
         this.setSeparador(pSeparador);
         
         this.setNameCompleto();
         long numFila = 0;
          
         
         
         System.out.println(" ====> Inicia Proceso Cargar Tabla de logs a la base de datos --- ");             

         
        // this.AbrirArchivoGuardar(this.getNameCompleto());

         try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
        // archivo = new File (this.getNameCompleto());   
        
        
         fr = new FileReader (this.getNameCompleto());
         br = new BufferedReader(fr);

         
         // Lectura del fichero - Maximo Cargue de archivos de 2 millones
         String linea = br.readLine();
         while((linea = br.readLine())!=null && (numFila <= 12000000 ) )
             
                         
         {
             

             this.separaLinea(linea);            
             this.SaveLineaTablaLista();

             
             i++;
             j++;
             if ( i > 8000 ) {
               System.out.println(" ====> cargarTablaLogs --- NUmero fila "+numFila + " - "+linea);             
               modeloLogStore.adicionarLista(dataRegistroLista);
               dataRegistroLista.clear();
              // modeloLogStore.limpiaConectBD();
               i = 0;  
             }
             
             
             if ( j > 50000) 
             {                 
               conBD.ejecutarProcedimiento("purge_idle_connections()");
               j = 0;
             }
             
             
             numFila++; 
                          
           
            
         }   
        modeloLogStore.adicionarLista(dataRegistroLista);
        dataRegistroLista.clear();

      
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
               this.CerrarArchivo();
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    return 0;
   }




     // Separa en campos la linea
    public int separaLinea( String linea ) {
        
     int i = 0;
     
     partes = linea.split(this.getSeparador());
     
         while( i < partes.length )
         {
            if ( i == 6 )  partes[i] = this.cambioStr(partes[i],"","\\N");
            if ( i == 7 )  partes[i] = this.cambioStr(partes[i],"0","\\N");
            if ( i == 15 ) partes[i] = this.cambioStr(partes[i],"0","\\N");
            if ( i == 17 ) partes[i] = this.procesaFecha(partes[i]);
            if ( i == 20 ) partes[i] = this.cambioStr(partes[i],"","\\N");
            
            // Valida los numero si es vacio los inicializa en cero
            if (( i == 7 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14  || i ==15 || i == 16 || i == 20 ) && ( partes[i] == null || partes[i].length() == 0  ) ) {
              partes[i] = "0";  
            }
                
            // System.out.println(" ====> SeparaLinea -- Campo "+i + " - "+partes[i]);
            i++;
            
         }    

    return i;
   }

 
    // Separa en campos la linea
    public int separaLineaValida( String linea ) {
        
     int i = 0;
     
     partes = linea.split(this.getSeparador());
     
         while( i < partes.length )
         {
            if ( i == 6 )  partes[i] = this.cambioStr(partes[i],"","\\N");
            if ( i == 7 )  partes[i] = this.cambioStr(partes[i],"0","\\N");
            if ( i == 15 ) partes[i] = this.cambioStr(partes[i],"0","\\N");
            if ( i == 17 ) partes[i] = partes[i];
            if ( i == 20 ) partes[i] = this.cambioStr(partes[i],"","\\N");
            
            // Valida los numero si es vacio los inicializa en cero
            if (( i == 7 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14  || i ==15 || i == 16 || i == 20 ) && ( partes[i] == null || partes[i].length() == 0  ) ) {
              partes[i] = "0";  
            }
                
            // System.out.println(" ====> SeparaLinea -- Campo "+i + " - "+partes[i]);
            i++;
            
         }    

    return i;
   }

    
     // Toma la fecha en milisegundos 
    public String procesaFecha( String fchHoraMil ) {
        
               long milisegundos = Long.parseLong(fchHoraMil);
               Date date = new Date( milisegundos*1000L );
               SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
               jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
               String java_date = jdf.format(date);
               // System.out.println("Nro. Columna "+i+" Fecha Java "+java_date);
    return java_date;
   }

     // Cambiar string \N por otro 
    public String cambioStr( String strOriginal, String strCambio, String strCompara ) {
       String strResultado = "";        
       
       if ( strOriginal.equals(strCompara) ) strResultado = strCambio;
       
    return strResultado;
   }
     
     // Abre el archivo para guardar la informacion de la linea
     public int AbrirArchivoGuardar( String nameFileGuardar ) {


        try
        {
            fichero = new FileWriter(nameFileGuardar);
            pw = new PrintWriter(fichero);

        } catch (Exception e) {
            e.printStackTrace();
        }        
      return 0;   
   }


     // Salvar La linea
     public int SaveLinea( String linea ) {

         int i = 0;
         linea = "";
         try
        {
                        
         while( i < partes.length )
         {
            if ( i <= 16)  linea = linea + partes[i]+ ";";
            else 
            {
              if ( i == 17 ) linea = linea + " other;";   
              
              linea = linea + partes[i];
              if ( i < 20 ) linea = linea + ";";
              
            }    
            
            i++;            
         }    
         pw.println(linea);
  
           
           

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
      return 0;   
   }

     // Salvar La linea en la tabla hacer el insert en la tabla.
     public int SaveLineaTabla(  ) {

         int i = 0;
         Date fecha;
         SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         try
        {
          //  System.out.println("==============================> SaveLineaTabla <================================ partes.length -> "+ partes.length );
            
        this.dataRegistro.limpiar();
         
            
        if ( partes.length == 21 ) {
            
            //System.out.println("Nro. Columna "+i+" Valor Columna "+partes[i]);
            
            // Crear funcion Convierta de String a NUmero
            // Crear funcion Convierta de String a Fecha
            
            
            this.dataRegistro.setProgram(partes[0]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> Programa  partes[0] "+partes[0] );

            this.dataRegistro.setId(Long.parseLong(partes[1]));  // Long
            // System.out.println(" ** SaveLineaTabla  ==============================> Id  partes[1] "+partes[1] + " Long "+ Long.parseLong(partes[1]) );

            this.dataRegistro.setEventname(partes[2]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> EventName  partes[2] "+partes[2] );

            this.dataRegistro.setComponent(partes[3]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> Component  partes[3] "+partes[3] );

            this.dataRegistro.setLogaction(partes[4]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> LogAction  partes[4] "+partes[4] );

            this.dataRegistro.setTarget(partes[5]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> Target  partes[5] "+partes[5] );

            this.dataRegistro.setObjecttable(partes[6]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> ObjectTable  partes[6] "+partes[6] );

            this.dataRegistro.setObjectid(Long.parseLong(partes[7]));  // Long
            // System.out.println("**** SaveLineaTabla  ==============================> ObjectId  partes[7] "+partes[7] + " Long ");

            this.dataRegistro.setCrud(partes[8]);  // String 8
            // System.out.println(" SaveLineaTabla  ==============================> Crud  partes[8] "+partes[8] );

            this.dataRegistro.setEdulevel(Long.parseLong(partes[9]));  // Long 9
            // System.out.println(" SaveLineaTabla  ==============================> EduLevel  partes[9] "+partes[9] );

            this.dataRegistro.setContextid(Long.parseLong(partes[10]));  // Long 10
            // System.out.println(" SaveLineaTabla  ==============================> ContextId  partes[10] "+partes[10]+ " Long "+ Long.parseLong(partes[10]) );

            this.dataRegistro.setContextlevel(Long.parseLong(partes[11]));  // Long 11
            // System.out.println(" SaveLineaTabla  ==============================> ContextLevel  partes[11] "+partes[11] + " Long "+ Long.parseLong(partes[11]) );

            this.dataRegistro.setContextinstanceid(Long.parseLong(partes[12]));  // Long 12
            // System.out.println(" SaveLineaTabla  ==============================> Contextinstanceid  partes[12] "+partes[12] + " Long "+ Long.parseLong(partes[12]) );

            this.dataRegistro.setUserid(Long.parseLong(partes[13]));  // Long  13
            // System.out.println(" SaveLineaTabla  ==============================> UserId  partes[13] "+partes[13] + " Long "+ Long.parseLong(partes[13]));

            this.dataRegistro.setCourseid(Long.parseLong(partes[14]));  // Long 14
            // System.out.println(" SaveLineaTabla  ==============================> CourseId  partes[14] "+partes[14] + " Long "+ Long.parseLong(partes[14]));

            this.dataRegistro.setRelateduserid(0);  // String 15
            // System.out.println(" SaveLineaTabla  ==============================> Relateduserid  partes[15] "+partes[15] );

            this.dataRegistro.setAnonymous(0);  // String 16
            // System.out.println(" SaveLineaTabla  ==============================> Anonymous  partes[16] "+partes[16] );

            this.dataRegistro.setOther("other");  // String XX - Other NO APLICA

            // System.out.println(" SaveLineaTabla  ==============================> Timecreated partes[17] "+partes[17] );
            fecha = formatoDelTexto.parse(partes[17]);            
            this.dataRegistro.setTimecreated(fecha);  // DateTime  18
           // System.out.println(" ** SaveLineaTabla  ==============================> Timecreated partes[17] "+partes[17]+ " Fecha registro "+this.dataRegistro.getTimecreated() );

            
            this.dataRegistro.setOrigin(partes[18]);  // String  18
            // System.out.println(" SaveLineaTabla  ==============================> Origin partes[18] "+partes[18] );

            
            this.dataRegistro.setIp(partes[19]);  // String 19            
           // System.out.println(" SaveLineaTabla  ==============================> IP partes[19] "+partes[19] );

            this.dataRegistro.setRelateduserid(Long.parseLong(partes[20]));  // Long 20
            // System.out.println(" SaveLineaTabla  ==============================> Relateduserid partes[20] "+partes[20]+ " Long "+ Long.parseLong(partes[20]) );

            
            
           // System.out.println("Fecha  ====> "+fecha );

            
            modeloLogStore.adicionar(this.dataRegistro);
            
        }
            
           

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
      return 0;   
   }


     // Salvar La linea en la tabla hacer el insert en la tabla.
     public int SaveLineaTablaLista(  ) {

         int i = 0;
         Date fecha;
         SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         try
        {
          //  System.out.println("==============================> SaveLineaTabla <================================ partes.length -> "+ partes.length );
            
        // this.dataRegistro.limpiar();
        
        this.dataRegistro = new MdlLogStore();
         
            
        if ( partes.length == 21 ) {
            
            //System.out.println("Nro. Columna "+i+" Valor Columna "+partes[i]);
            
            // Crear funcion Convierta de String a NUmero
            // Crear funcion Convierta de String a Fecha
            
            
            this.dataRegistro.setProgram(partes[0]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> Programa  partes[0] "+partes[0] );

            this.dataRegistro.setId(Long.parseLong(partes[1]));  // Long
            // System.out.println(" ** SaveLineaTabla  ==============================> Id  partes[1] "+partes[1] + " Long "+ Long.parseLong(partes[1]) );

            this.dataRegistro.setEventname(partes[2]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> EventName  partes[2] "+partes[2] );

            this.dataRegistro.setComponent(partes[3]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> Component  partes[3] "+partes[3] );

            this.dataRegistro.setLogaction(partes[4]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> LogAction  partes[4] "+partes[4] );

            this.dataRegistro.setTarget(partes[5]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> Target  partes[5] "+partes[5] );

            this.dataRegistro.setObjecttable(partes[6]);  // String 
            // System.out.println(" SaveLineaTabla  ==============================> ObjectTable  partes[6] "+partes[6] );

            this.dataRegistro.setObjectid(Long.parseLong(partes[7]));  // Long
            // System.out.println("**** SaveLineaTabla  ==============================> ObjectId  partes[7] "+partes[7] + " Long ");

            this.dataRegistro.setCrud(partes[8]);  // String 8
            // System.out.println(" SaveLineaTabla  ==============================> Crud  partes[8] "+partes[8] );

            this.dataRegistro.setEdulevel(Long.parseLong(partes[9]));  // Long 9
            // System.out.println(" SaveLineaTabla  ==============================> EduLevel  partes[9] "+partes[9] );

            this.dataRegistro.setContextid(Long.parseLong(partes[10]));  // Long 10
            // System.out.println(" SaveLineaTabla  ==============================> ContextId  partes[10] "+partes[10]+ " Long "+ Long.parseLong(partes[10]) );

            this.dataRegistro.setContextlevel(Long.parseLong(partes[11]));  // Long 11
            // System.out.println(" SaveLineaTabla  ==============================> ContextLevel  partes[11] "+partes[11] + " Long "+ Long.parseLong(partes[11]) );

            this.dataRegistro.setContextinstanceid(Long.parseLong(partes[12]));  // Long 12
            // System.out.println(" SaveLineaTabla  ==============================> Contextinstanceid  partes[12] "+partes[12] + " Long "+ Long.parseLong(partes[12]) );

            this.dataRegistro.setUserid(Long.parseLong(partes[13]));  // Long  13
            // System.out.println(" SaveLineaTabla  ==============================> UserId  partes[13] "+partes[13] + " Long "+ Long.parseLong(partes[13]));

            this.dataRegistro.setCourseid(Long.parseLong(partes[14]));  // Long 14
            // System.out.println(" SaveLineaTabla  ==============================> CourseId  partes[14] "+partes[14] + " Long "+ Long.parseLong(partes[14]));

            this.dataRegistro.setRelateduserid(0);  // String 15
            // System.out.println(" SaveLineaTabla  ==============================> Relateduserid  partes[15] "+partes[15] );

            this.dataRegistro.setAnonymous(0);  // String 16
            // System.out.println(" SaveLineaTabla  ==============================> Anonymous  partes[16] "+partes[16] );

            this.dataRegistro.setOther("other");  // String XX - Other NO APLICA

            // System.out.println(" SaveLineaTabla  ==============================> Timecreated partes[17] "+partes[17] );
            fecha = formatoDelTexto.parse(partes[17]);            
            this.dataRegistro.setTimecreated(fecha);  // DateTime  18
           // System.out.println(" ** SaveLineaTabla  ==============================> Timecreated partes[17] "+partes[17]+ " Fecha registro "+this.dataRegistro.getTimecreated() );

            
            this.dataRegistro.setOrigin(partes[18]);  // String  18
            // System.out.println(" SaveLineaTabla  ==============================> Origin partes[18] "+partes[18] );

            
            this.dataRegistro.setIp(partes[19]);  // String 19            
           // System.out.println(" SaveLineaTabla  ==============================> IP partes[19] "+partes[19] );

            this.dataRegistro.setRelateduserid(Long.parseLong(partes[20]));  // Long 20
            // System.out.println(" SaveLineaTabla  ==============================> Relateduserid partes[20] "+partes[20]+ " Long "+ Long.parseLong(partes[20]) );

            
            
           // System.out.println("Fecha  ====> "+fecha );

            
            // modeloLogStore.adicionar(this.dataRegistro);
            
           // System.out.println("Data adicionado hora fecha "+dataRegistro.getTimecreated() );
            
           // Adicional a la lista  
           this.dataRegistroLista.add(dataRegistro);
            
            
        }
            
           

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
      return 0;   
   }
     
     
     
     
     // validaLineaTabla
     // Realiza las siguientes actividades
     // Valida la longitud de la linea 21 columnas.
     // Valida longitud de la fecha para evitar errores.     
     // Codigo Error 1 - Formato no valido en la fecha longitud diferente de 10
     // Codigo Error 2 - Error en el numero de columnas diferente de 21.
     public int validaLineaTabla(  ) {
         
         int i = 0;
         SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         
         try
        {
                    
        if ( partes.length == 21 ) {
            

            // System.out.println(" SaveLineaTabla  ==============================> Timecreated partes[17] "+partes[17] );
            
            if ( partes[17].length() != 10 ) {
               // System.out.println(" validaLineaTabla ==> Timecreated partes[17] "+partes[17]+" Longitud "+partes[17].length() );
                return 2;
            }
            
        }
        else
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
      return 0;   
   }

     
     
     // Cerrar el archivo 
     public int CerrarArchivo(  ) {

           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
      return 0;   
   }

     // Carga el archivo CSV de logs y realiza las siguientes actividades
    /*  ALMACENA la informacion en la tabla de la base de datos mdl_logstore_standard_log
    */
     public int calculaIndicadores(   ) {

         int i = 0;
         System.out.println(" ====> Inicia Proceso Cargar Tabla de logs a la base de datos --- ");             

         

         try {
         System.out.println(" ====> cargarTablaLogs --- Proceso de Borrado Tabla logs Name Completo "+ this.getNameCompleto());             
         modeloLogStore.calculaIndices();
      
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         }
    return 0;
   }



     
}

