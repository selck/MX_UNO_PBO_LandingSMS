package mx.com.amx.unotv.landing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

public class GeneraHtmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log=Logger.getLogger(GeneraHtmlServlet.class);
	
    public GeneraHtmlServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Peticion GET a GeneraHtmlServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			log.debug("====== GeneraHtmlServlet =====");
			String respuesta="";
			OperacionesPrerender prerender = new OperacionesPrerender();
			String estadoSeleccionado=request.getParameter("estadoSeleccionado")==null?"":request.getParameter("estadoSeleccionado").toLowerCase();
			String paramEstadoSeleccionado=request.getParameter("paramEstadoSeleccionado")==null?"":request.getParameter("paramEstadoSeleccionado");
			String landingSeleccionado=request.getParameter("landingSeleccionado")==null?"":request.getParameter("landingSeleccionado");
			String listNotas=request.getParameter("listNotas")==null?"":request.getParameter("listNotas");
			ParametrosDTO parametrosDTO = prerender.obtenerPropiedades();
			ArrayList<NotaDTO> listNoticias=null;
			
			boolean success = false;
			String nombre = "";
			String nombreArchivo = "";
			String carpeta = "";
			if(estadoSeleccionado.equalsIgnoreCase("Selecciona una Opción")){
				estadoSeleccionado="default";
				paramEstadoSeleccionado="";
				landingSeleccionado="";
			}
			estadoSeleccionado=cambiaAcentos(estadoSeleccionado);
			log.debug("estadoSeleccionado: "+estadoSeleccionado);
			log.debug("landingSeleccionado: "+landingSeleccionado);
			log.debug("listNotas: "+listNotas);
			
			//Asigna el nombre del archivo
			nombre = estadoSeleccionado;//parametrosDTO.getNameHTML();
			//int indNombre = nombre.indexOf(".html");
			if(landingSeleccionado != null && !landingSeleccionado.equals("")){
				nombreArchivo = estadoSeleccionado +  "-" + landingSeleccionado + ".html";
			}
			else{
				nombreArchivo = nombre;
			}
			
			carpeta = parametrosDTO.getPathFiles()+estadoSeleccionado;
			log.debug("carpeta: " +carpeta);
			success = prerender.createFolders(carpeta);
			if(success){
				JSONArray listJson = new JSONArray(listNotas);
				if(listJson!=null && listJson.length()>0){
					listNoticias=new ArrayList<NotaDTO>();
				    for (int i = 0; i < listJson.length(); i++) {
				    	JSONObject object = listJson.getJSONObject(i);
				    	NotaDTO noti=new NotaDTO();
				    	noti.setFcIdContenido(object.getString("fcIdContenido")!= null || object.getString("fcIdContenido").length()>0?object.getString("fcIdContenido"):"");
				    	noti.setFcIdCategoria(object.getString("fcIdCategoria")!= null || object.getString("fcIdCategoria").length()>0?object.getString("fcIdCategoria"):"");
				    	noti.setFcNombre(object.getString("fcNombre")!= null || object.getString("fcNombre").length()>0?object.getString("fcNombre"):"");
				    	noti.setFcTitulo(object.getString("fcTitulo")!= null || object.getString("fcTitulo").length()>0?object.getString("fcTitulo"):"");
				    	noti.setFcImgPrincipal(object.getString("fcImgPrincipal")!= null || object.getString("fcImgPrincipal").length()>0?object.getString("fcImgPrincipal"):"");
				    	noti.setFiBanPatrocinio(object.getString("fiBanPatrocinio")!= null || object.getString("fiBanPatrocinio").length()>0?object.getString("fiBanPatrocinio"):"");
				    	noti.setFcIdTipoNota(object.getString("fcIdTipoNota")!= null || object.getString("fcIdTipoNota").length()>0?object.getString("fcIdTipoNota"):"");
				    	noti.setFcDescripcionCategoria(object.getString("fcDescripcionCategoria")!= null || object.getString("fcDescripcionCategoria").length()>0?object.getString("fcDescripcionCategoria"):"");		
				    	//noti.setFcLinkDetalle(object.getString("fcLinkDetalle").replaceAll("bites-lab", "mito-y-realidad"));
				    	noti.setFcLinkDetalle(object.getString("fcLinkDetalle")!= null || object.getString("fcLinkDetalle").length()>0?object.getString("fcLinkDetalle").replaceAll("bites-lab", "mito-y-realidad"):"");
				    	listNoticias.add(noti);
				   }
				}
				Date date = new Date();
				
				String fecha = new SimpleDateFormat("dd-MM-yyyy").format(date);
				String utm_fecha="&utm_content="+fecha;
				String utm_campaign="&"+paramEstadoSeleccionado.split("&")[2];
				paramEstadoSeleccionado=paramEstadoSeleccionado.substring(0,paramEstadoSeleccionado.indexOf("&utm_campaign"));
				String paramSinFecha=paramEstadoSeleccionado+utm_campaign;
				paramEstadoSeleccionado=paramEstadoSeleccionado+utm_fecha+utm_campaign;
				
				
				log.debug("paramSinFecha: "+paramSinFecha);
				log.debug("paramEstadoSeleccionado: "+paramEstadoSeleccionado);
				if(prerender.createPlantilla(parametrosDTO,listNoticias,estadoSeleccionado,paramEstadoSeleccionado,nombreArchivo,landingSeleccionado)){
					
					if(parametrosDTO.getAmbiente().equalsIgnoreCase("desarrollo"))
						//respuesta="http://dev-unotv.tmx-internacional.net/portal/unotv/landing-sms/"+estadoSeleccionado+ "/"+nombreArchivo+paramEstadoSeleccionado+utm_fecha+utm_campaign;
					respuesta="http://dev-unotv.tmx-internacional.net/portal/unotv/landing-sms/"+estadoSeleccionado+ "/"+nombreArchivo+paramEstadoSeleccionado;
					else if(parametrosDTO.getAmbiente().equalsIgnoreCase("produccion"))
						//respuesta="http://www.unotv.com/landing-sms/"+estadoSeleccionado+ "/"+nombreArchivo+paramEstadoSeleccionado+utm_fecha+utm_campaign;
						respuesta="http://www.unotv.com/landing-sms/"+estadoSeleccionado+ "/"+nombreArchivo+paramSinFecha;
				}else{
					respuesta="error";
				}
				log.debug("Respuesta: "+respuesta);
				response.setContentType("text/html");  
				PrintWriter out = response.getWriter();  
				out.println(respuesta);  
			}
			
		} catch (Exception e) {
			log.error("Error GeneraHtmlServlet: ",e);
		}

	}
	
	public static void main(String[] args){
		try {
			Date date = new Date();
			String fecha = new SimpleDateFormat("dd-MM-yyyy").format(date);
			String salida="www.unotv.com/landing-sms/nacional/nacional-6.html";
			String utm_fecha="&utm_content="+fecha;
			String paramEstadoSeleccionado="?utm_source=sms&utm_medium=link&utm_campaign=Nacional";
			String utm_campaign="&"+paramEstadoSeleccionado.split("&")[2];
			paramEstadoSeleccionado=paramEstadoSeleccionado.substring(0,paramEstadoSeleccionado.indexOf("&utm_campaign"));
			
			salida=salida+paramEstadoSeleccionado+utm_fecha+utm_campaign;
			System.out.println(salida);
			
			
		} catch (Exception e) {
			System.out.println("Error main: "+e.getMessage());
		}
	}
	public  String cambiaAcentos(String cadena){
		try {
			cadena=cadena.replaceAll("á", "a");
			cadena=cadena.replaceAll("é", "e");
			cadena=cadena.replaceAll("í", "i");
			cadena=cadena.replaceAll("ó", "o");
			cadena=cadena.replaceAll("ú", "u");
			cadena=cadena.replaceAll(" ", "-");
		} catch (Exception e) {
			log.error("Error cambiaAcentos: ",e);
		}
		return cadena;
	}
}
