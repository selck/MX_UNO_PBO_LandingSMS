package mx.com.amx.unotv.landing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.amx.unotv.landing.dto.ParametrosDTO;
import mx.com.amx.unotv.landing.dto.NotaDTO;
import mx.com.amx.unotv.landing.util.OperacionesPrerender;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


public class VistaPreviaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log=Logger.getLogger(VistaPreviaServlet.class);
    
    public VistaPreviaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Peticion GET a VistaPreviaServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			log.debug("====== VistaPreviaServlet =====");
			String respuesta="";
			OperacionesPrerender prerender = new OperacionesPrerender();
			String listNotas=request.getParameter("listNotas")==null?"":request.getParameter("listNotas");
			ParametrosDTO parametrosDTO = prerender.obtenerPropiedades();
			ArrayList<NotaDTO> listNoticias=null;
			
			boolean success = true;
			log.debug("listNotas: "+listNotas);
			
			if(success){
				JSONArray listJson = new JSONArray(listNotas);
				if(listJson!=null && listJson.length()>0){
					
					listNoticias=new ArrayList<NotaDTO>();
					log.debug("Numero de Notas: "+listJson.length());

				    for (int i = 0; i < listJson.length(); i++) {
				    	JSONObject object = listJson.getJSONObject(i);
				    	NotaDTO noti=new NotaDTO();
				    	noti.setFcIdContenido(object.getString("fcIdContenido"));
				    	noti.setFcIdCategoria(object.getString("fcIdCategoria"));
				    	noti.setFcNombre(object.getString("fcNombre"));
				    	noti.setFcImgPrincipal(object.getString("fcImgPrincipal").replace("-Principal", "-Miniatura"));
				    	noti.setFiBanPatrocinio(object.getString("fiBanPatrocinio"));
				    	noti.setFcIdTipoNota(object.getString("fcIdTipoNota"));
				    	noti.setFcDescripcionCategoria(object.getString("fcDescripcionCategoria"));
				    	noti.setFcLinkDetalle(object.getString("fcLinkDetalle").replaceAll("bites-lab", "mito-y-realidad"));
				    	listNoticias.add(noti);
				   }
				}
				
				respuesta=prerender.getHTMLPlantilla(parametrosDTO, listNoticias);
				String auxi=respuesta.equalsIgnoreCase("error")?"Error al generar la vista previa":"Se genero el html de la vista Previa exitosamente";
				log.debug("Respuesta: "+auxi);
				response.setContentType("html");  
				PrintWriter out = response.getWriter();  
				out.println(respuesta);  
				log.debug("======End VistaPreviaServlet =====");
			}
			
		} catch (Exception e) {
			log.error("Error VistaPreviaServlet: ",e);
		}

	}

}
