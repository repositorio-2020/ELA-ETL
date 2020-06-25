package qo.control.general;



public class GeneradorHTML{
     String atr="<tr>",
            ctr="</tr>",
            atd="<td>",
            ctd="</td>",
            ap="<p>",
            cp="</p>",
            ab="<blockquote>",
            cb="</blockquote>",
            ct="</table>",
            ca="</a>",
						co="</option>";

     public GeneradorHTML(){

     }

     String at(String w,int b, int cs, int cp, String clase){
             return "<table width=\""+w+"\" border=\""+b+"\" cellspacing=\""+cs+"\" cellpadding=\""+cp+"\" class=\""+clase+"\">";
     }

     String aa(String enlace,String parametro, String id,String clase,String target){
             return "<a href=\""+enlace+"?"+parametro+"="+id+"\" class=\""+clase+"\" target=\""+target+"\">";
     }

  //Funcion desactivada temporlamente
     public String generarTabla(String ids[],String nombres[], String enlace,String parametro, String w,int b, int cs, int cp, String clase, String claseLink,String targeLink){
		String cad="",cadTotal="";
		for(int i=0;i<nombres.length;i++){
			if((enlace.equals("") || enlace==null) && (parametro.equals("") || parametro==null))
				cad=atr+atd+nombres[i]+ctd+ctr;
			else
				cad=atr+atd+aa(enlace,parametro,ids[i],claseLink,targeLink)+nombres[i]+ca+ctd+ctr;
			cadTotal+=cad;
		}
		return at(w,b,cs,cp, clase)+cadTotal+ct;
     }


		String ao(String id,boolean sel){

			return sel?"<option value=\""+id+"\" selected>":"<option value=\""+id+"\">";
		}


		public String generarOpciones(String val[],String nom[],String sel){
			String cad="",cadTotal="";
			for(int i=0;i<val.length;i++){

				if(val[i].equals(sel))
					cad=ao(val[i],true)+nom[i]+co;
				else
					cad=ao(val[i],false)+nom[i]+co;
				cadTotal+=cad;
			}
			return cadTotal;
		}

		public String retornoDeCarroABR(String tex){
			tex=tex.replace('\"','`');
			StringBuffer sb = new StringBuffer(tex);
			int ind =0;
	    while (ind!=-1){
	    ind = tex.indexOf ("\r\n");
			if (ind!=-1)
	    	tex= sb.replace (ind,ind+1,"<br>").toString ();
			}
			return tex;
		}
	public boolean buscarEnArrayString (String val, String valores[])
	{

		if(	valores!=null &&valores.length>0)
		for(int i=0; i<valores.length ;i++)
		{
			if(valores[i].equals(val))
		 		return true;
		}

		return false;
	}
	public String cajaDeChequeo(String nombre, String valor,String etiqueta, String sel)
	{
		return  "<td width=\"5%\">"+ " <input type=\"checkbox\""+ " name=\""+ nombre +"\" value=\""+valor+"\" "+ sel +" > <br>"+"</td>" + "<td width=\"95%\">"+etiqueta+"</td>";
	}

	public String generarCajasDeChequeo(String nombre, String val[], String nom[], String sel[])
	{
		StringBuffer sb=new StringBuffer();
		String checked="checked";
		String n="";

		if(val!=null && nom!=null && val.length>0 &&nom.length>0 &&val.length==nom.length)
		for(int j=0; j<val.length;j++)
		{
			sb.append ("<tr bgcolor=\"#FFFFFF\" bordercolor=\"#FFFFFF\">");
			if(buscarEnArrayString (val[j], sel))
			sb.append(cajaDeChequeo(nombre, val[j], nom[j], checked));
			else
			sb.append(cajaDeChequeo(nombre, val[j], nom[j], n));
			sb.append("</tr>");
		}

		return sb.toString();
	}

		public String radioButton(String nombre, String valor,String etiqueta, String sel)
	{
		return  "<td width=\"5%\">"+ " <input type=\"radio\""+ " name=\""+ nombre +"\" value=\""+valor+"\" "+ sel +" > <br>"+"</td>" + "<td width=\"95%\">"+etiqueta+"</td>";
	}

	public String generarRadioButtons(String nombre, String val[], String nom[], String sel)
	{
		StringBuffer sb=new StringBuffer();
		String checked="checked";
		String n="";

		if(val!=null && nom!=null && val.length>0 &&nom.length>0 &&val.length==nom.length&&sel!=null )
		for(int j=0; j<val.length;j++)
		{
			sb.append ("<tr bgcolor=\"#FFFFFF\" bordercolor=\"#FFFFFF\">");
			if(val[j].equals(sel))
			sb.append(cajaDeChequeo(nombre, val[j], nom[j], checked));
			else
			sb.append(cajaDeChequeo(nombre, val[j], nom[j], n));
			sb.append("</tr>");
		}

		return sb.toString();
	}
     public static void main(String[] args){
            GeneradorHTML gen=new GeneradorHTML();
            String id[]={"0","1","2"};
            String nom[]={"a","b","c"};
            System.out.println(gen.generarTabla(id,nom,"pag.jsp","id","300",5,5,5,"aa","bb","ta"));
						System.out.println(gen.generarOpciones(id,nom,"1"));
     }
}
