/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.mybatis.myBatisUtil;
import cl.mybatis.pojos.MdlLogStore;
import org.apache.ibatis.session.SqlSession;


import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.util.List;
import javax.annotation.PostConstruct;


/**
 *
 * @author user1
 */

public class MdlLogStoreBean implements Serializable  {

    private MdlLogStore mdlLogStore;
    private List<MdlLogStore> listMdlLogStore; 
    private MdlLogStore mdlLogStoreSelected; 
    
    
    public void init() {
        System.out.println(" INIT ----------------------------------------------------------------------");
         mdlLogStore = new MdlLogStore();
         mdlLogStoreSelected = new MdlLogStore();
        
     }
     
       
    /**
     * Creates a new instance of UsuarioBean
     */
    
    public MdlLogStoreBean() {
        
    }

    
    
    
    public MdlLogStore getMdlLogStore() {
        return this.mdlLogStore;
    }

        
    public void setMdlLogStore(MdlLogStore mdlLogStore) {
        this.mdlLogStore = mdlLogStore;
    }

    public MdlLogStore getMdlLogStoreSelected() {
        return mdlLogStoreSelected;
    }

    public void setMdlLogStoreSelected(MdlLogStore mdlLogStoreSelected) {
        System.out.println("----------------------------------------------------------------------");
        
        this.mdlLogStoreSelected = mdlLogStoreSelected;
    }

    
    
    
    
    public List<MdlLogStore> getListMdlLogStore() {
        return listMdlLogStore;
    }

    
    
    public void setListMdlLogStore(List<MdlLogStore> listMdlLogStore) {
        this.listMdlLogStore = listMdlLogStore;
    }
    
    
    public void selectAll(){
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("Ingreso selet all MdlLogStore  ");
        
        if ( session != null ){
            try {
                
                listMdlLogStore = session.selectList("MdlLogStore.selectAll");     
                this.mdlLogStoreSelected = listMdlLogStore.get(0);
                System.out.println("Ver 02 - Tamaño de la lista "+listMdlLogStore.size()+ " Nombre 1 "+ listMdlLogStore.get(1).getEventname() );
                
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }

    
    public void selectFilter(MdlLogStore pMdlLogStore) throws Exception {
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Ingreso Select ID");
        
        if ( session != null ){
            try {
                
                listMdlLogStore = session.selectList("MdlLogStore.selectFilter", pMdlLogStore);
                if ( listMdlLogStore.size() > 0 ) {
                  System.out.println(" ------ > selectFilter - Tamaño de la lista "+listMdlLogStore.size()+ " Nombre 1 "+ listMdlLogStore.get(0).getEventname()+" Log Action "+ listMdlLogStore.get(0).getLogaction() );                    
                }
                
                this.mdlLogStore = listMdlLogStore.get(0);
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }
    
    
    public String adicionar( MdlLogStore pMdlLogStore ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                session.insert("MdlLogStore.insert", pMdlLogStore);
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
    

    
    public String adicionarLista( List<MdlLogStore> pMdlLogStore ) throws InterruptedException{
        SqlSession session = new myBatisUtil().getSessionBatch();
    //    SqlSession session = new myBatisUtil().getSession();
        
        int ind = 0;
        
        System.out.println("----------------------- Tamaño de la lista que va ha ser insertada ."+pMdlLogStore.size());


        for ( ind = 0;   ind < pMdlLogStore.size() ; ind++ ) 
        {
            
          if ( session != null ){
            try {
             // System.out.println("----------------------- Tamaño de la lista que va ha ser insertada ."+pMdlLogStore.get(ind).getTimecreated() +"  ind "+ind );

                session.insert("MdlLogStore.insert", pMdlLogStore.get(ind));
            } catch(Exception e) {
                            System.out.println("Error al crear la sesion."+e.getMessage());
         }finally {
               // session.close();
            }
          }
           else {
            System.out.println("Error al crear la sesion.");
          }
        }
        
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Agenda Creado"));
        session.commit();
        session.close();
        
       // sleep(5000);

        return "index";
    }



    
    public String delete(  ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                session.delete("MdlLogStore.delete");
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
    
    
    public String actualizar( MdlLogStore pMdlLogStore ){
        SqlSession session = new myBatisUtil().getSession();
        if ( session != null ){
            try {
                session.update("MdlLogStore.update", pMdlLogStore);
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
 
    

    public void inicializaBD(){
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("Invoca al procedimiento que borra la informacion de la BD  ");
        
        if ( session != null ){
            try {
                
                listMdlLogStore = session.selectList("MdlLogStore.selectParametro");     
                this.mdlLogStoreSelected = listMdlLogStore.get(0);
                System.out.println("Salio de la ejecucion ----> "+listMdlLogStore.size() );
                
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }
    
    
    public void limpiaConectBD(){
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("Invoca al procedimiento que borra la informacion de la BD  ");
        
        if ( session != null ){
            try {
                
                listMdlLogStore = session.selectList("MdlLogStore.selectConect");     
                this.mdlLogStoreSelected = listMdlLogStore.get(0);
                System.out.println("Salio de la ejecucion ----> "+listMdlLogStore.size() );
                
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }
    
    public void calculaIndices(){
        SqlSession session = new myBatisUtil().getSession();
        System.out.println("Invoca al procedimiento que borra la informacion de la BD  ");
        
        if ( session != null ){
            try {
                
                listMdlLogStore = session.selectList("MdlLogStore.calculaIndices");     
                this.mdlLogStoreSelected = listMdlLogStore.get(0);
                System.out.println("Salio de la ejecucion ----> "+listMdlLogStore.size() );
                
                
            } finally {
                session.close();
            }
        }
        else {
            System.out.println("Error al crear la sesion.");
        }
        
    }
    
public static void main(String arg[]) throws Exception {
  
    
  System.out.println("Modelo Perfil - TEST ");
  
  MdlLogStore mdlLogStore = new MdlLogStore();   // Pojo
  
  MdlLogStoreBean mdlLogStoreBean = new MdlLogStoreBean();  // Bean Modelo
  
  
  // agenda.setId("2");
  
  
  mdlLogStoreBean.selectFilter(mdlLogStore);
  
  
 mdlLogStoreBean.selectAll();
 //List<MdlLogStore> list = mdlLogStoreBean.getListMdlLogStore();

  mdlLogStoreBean.inicializaBD();

 
// System.out.print("Tamaño de la lista xx "+list.size());
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
