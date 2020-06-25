package qo.control.general;
import sun.net.smtp.SmtpClient;
import java.io.*;

public class Correo
{
	String servidor,de,para,mensaje,asunto;
	SmtpClient cliente;
	PrintStream correo;
	
	public Correo(String serv){
		servidor=serv;
		try{
			cliente=new SmtpClient(serv);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void fijarParametros(String d,String p){
		de=d;
		para=p;
	}
	
	public void fijarMensaje(String m){
		mensaje=m;
	}
	
	public void fijarAsunto(String a){
		asunto=a;
	}

	public boolean enviar(){
		try
		{
    	cliente.from(de);
   		cliente.to(para);
	    correo = cliente.startMessage();
     	correo.println("to: " + para);
     	correo.println("subject: " + asunto);
     	correo.println();
     	correo.println();
     	correo.println(mensaje);
     	correo.println();
     	correo.println(de);
     	correo.println();
     	correo.println();
     	cliente.closeServer();
  	}
  	catch (IOException e){	
    	System.out.println(e.toString());
			return false;
  	}
		return true;
	}


	public static void main(String args[]){
		Correo c=new Correo("192.168.1.13");
		c.fijarParametros("kcastano@dnet2.com","ricardocastano79@yahoo.com");
		c.fijarAsunto("Una prueba....");
		c.fijarMensaje("ddd");
		c.enviar();
	}
}

