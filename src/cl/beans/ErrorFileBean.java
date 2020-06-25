/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.mybatis.myBatisUtil;
import cl.mybatis.pojos.ErrorFile;
import org.apache.ibatis.session.SqlSession;


import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;


/**
 *
 * @author user1
 */

public class ErrorFileBean implements Serializable  {

    private ErrorFile errorFile;
    private List<ErrorFile> listMdlErrorFile; 
    private ErrorFile errorFileSelected; 
    
    
    
    
    
    public void init() {
        System.out.println(" INIT ----------------------------------------------------------------------");
         errorFile = new ErrorFile();
         errorFileSelected = new ErrorFile();
        
     }
     
       
    /**
     * Creates a new instance of UsuarioBean
     */
    
    public ErrorFileBean() {
        
    }

    
    
    
    public ErrorFile getMdlErrorFile() {
        return this.errorFile;
    }

        
    public void setMdlErrorFile(ErrorFile mdlErrorFile) {
        this.errorFile = mdlErrorFile;
    }

    public ErrorFile getMdlErrorFileSelected() {
        return errorFileSelected;
    }

    public void setMdlErrorFileSelected(ErrorFile mdlErrorFileSelected) {
        System.out.println("----------------------------------------------------------------------");
        
        this.errorFileSelected = mdlErrorFileSelected;
    }

    
    
    
    
    public List<ErrorFile> getListMdlErrorFile() {
        return listMdlErrorFile;
    }

    
    
    public void setListMdlErrorFile(List<ErrorFile> listMdlErrorFile) {
        this.listMdlErrorFile = listMdlErrorFile;
    }
    
    
    public void selectAll(){
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("Ingreso selet all MdlErrorFile  ");
        
        if ( session != null ){
            try {
                
                listMdlErrorFile = session.selectList("ErrorFile.selectAll");     
                this.errorFileSelected = listMdlErrorFile.get(0);
                System.out.println("Ver 02 - Tamaño de la lista "+listMdlErrorFile.size()+ " Nombre 1 "+ listMdlErrorFile.get(1).getErf_linea() );
                
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }

    
    public void selectFilter(ErrorFile pMdlErrorFile) throws Exception {
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Ingreso Select ID");
        
        if ( session != null ){
            try {
                
                listMdlErrorFile = session.selectList("ErrorFile.selectFilter", pMdlErrorFile);
                if ( listMdlErrorFile.size() > 0 ) {
                  System.out.println(" ------ > selectFilter - Tamaño de la lista "+listMdlErrorFile.size()+ " Nombre 1 "+ listMdlErrorFile.get(0).getErf_linea()+" Log Action "+ listMdlErrorFile.get(0).getErf_linea() );                    
                }
                
                this.errorFile = listMdlErrorFile.get(0);
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }
    
    
    public String adicionar( ErrorFile pMdlErrorFile ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                session.insert("ErrorFile.insert", pMdlErrorFile);
                session.commit();
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Agenda Creado"));
        return "index";
    }
    

    public String delete(  ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                session.delete("ErrorFile.delete");
                session.commit();
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Agenda Creado"));
        return "index";
    }


    
      public String commit(  ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                 session.commit();
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Agenda Creado"));
        return "index";
    }
    
    
    public String actualizar( ErrorFile pMdlErrorFile ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                session.update("ErrorFile.update", pMdlErrorFile);
                session.commit();
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Agenda Creado"));
        return "index";
    }
 
    
    
public static void main(String arg[]) throws Exception {
  
    
  System.out.println("Modelo Perfil - TEST ");
  
  ErrorFile mdlErrorFile = new ErrorFile();   // Pojo
  
  ErrorFileBean mdlErrorFileBean = new ErrorFileBean();  // Bean Modelo
  
  
  // agenda.setId("2");
  
  
  // mdlErrorFileBean.selectFilter(mdlErrorFile);
  
  
 //mdlErrorFileBean.selectAll();
 //List<ErrorFile> list = mdlErrorFileBean.getListMdlErrorFile();

// System.out.print("Tamaño de la lista xx "+list.size());

System.out.print("Error file guardar informacion -------------- ");


mdlErrorFile.setErf_error("Definicion del error Columnas diferenste 21");
mdlErrorFile.setErf_linea("Linea de datos validada");
mdlErrorFile.setErf_namefile("Nombre del archivo ");
mdlErrorFile.setErf_nrolinea("681028");


mdlErrorFileBean.adicionar(mdlErrorFile);

//System.out.print("Direccion item - "+list.get(0).getDireccion());
  



/*
  
  for ( int i = 0; i < list.size() ; i++  ) {
      
      System.out.println("Item "+ i+"  Nro Item "+list.get(i).getId()+" Nombre "+list.get(i).getUa());
      
  }
  list.get(2).setEstado("AGENDADO");
  list.get(2).setFchVisita("2016-03-04");

  agendaBean.actualizar(list.get(2));
  
  System.out.print("Fin del proceso......... ");
  
*/

}

   
    
}
