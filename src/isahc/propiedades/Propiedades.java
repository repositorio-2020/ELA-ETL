/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isahc.propiedades;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author user1
 */
public class Propiedades {
private Properties properties;

public Properties getProperties(){
      properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/Configuracion.properties"));
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
		return properties;
	}	
    
    
    
}
