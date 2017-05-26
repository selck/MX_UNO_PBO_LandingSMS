package mx.com.amx.unotv.landing.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import mx.com.amx.unotv.landing.dto.NotaDTO;
import mx.com.amx.unotv.landing.dto.ParametrosDTO;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OperacionesPrerender {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private final int numMagazine=5;
	/**
	 * Obtiene los datos del archivo de propiedades externo
	 * @param String, Archivo de propiedades a obtener del server
	 * @return ParametrosDTO, DTO con los datos obtenido
	 * */
	public ParametrosDTO obtenerPropiedades() {
		ParametrosDTO parametrosDTO = new ParametrosDTO();		 
		try {	    		
			Properties props = new Properties();
		    props.load(this.getClass().getResourceAsStream( "/general.properties" ));				
		    parametrosDTO.setPathFiles(props.getProperty("pathFiles"));
			parametrosDTO.setPathShell(props.getProperty("pathShell"));
			parametrosDTO.setPathShellElimina(props.getProperty("pathShellElimina"));
			parametrosDTO.setPathRemote(props.getProperty("pathRemote"));
			parametrosDTO.setNameHTML(props.getProperty("nameHTML"));
			parametrosDTO.setJsoupConnectLandingSMS(props.getProperty("JsoupConnectLandingSMS"));
			parametrosDTO.setBaseTheme(props.getProperty("baseTheme"));
			parametrosDTO.setCarpetaResources(props.getProperty("carpetaResources"));
			parametrosDTO.setBasePagesPortal(props.getProperty("basePagesPortal"));
			parametrosDTO.setAmbiente(props.getProperty("ambiente"));
			parametrosDTO.setURL_WS_BASE(props.getProperty("URL_WS_BASE"));
			parametrosDTO.setIdMagazineHome(props.getProperty("idMagazineHome"));
			parametrosDTO.setBaseURL(props.getProperty("baseURL"));
			parametrosDTO.setDominioPreOlimpicos(props.getProperty("dominioPreOlimpicos"));
		} catch (Exception ex) {
			parametrosDTO = new ParametrosDTO();
			logger.error("No se encontro el Archivo de propiedades: ", ex);			
		}
		return parametrosDTO;
    }
	public String getHTMLPlantilla(ParametrosDTO parametrosDTO,ArrayList<NotaDTO> listNoticias) 
	{
		logger.debug("Inicia getHTMLPlantilla.");
		Document doc = null;		
		String HTML="", htmlComponente="";
		StringBuffer HTMLFULL=new StringBuffer();
		
		try
		{
			logger.debug("Conectandose a: "+parametrosDTO.getJsoupConnectLandingSMS());
			doc = Jsoup.connect(parametrosDTO.getJsoupConnectLandingSMS()).timeout(120000).get();
			
			Elements componentes = doc.getElementsByClass("magazine");
			htmlComponente=componentes.html();
			StringBuffer htmlBuffer=new StringBuffer();
			htmlBuffer.append("<div class=\"magazine\"> \n");
			htmlBuffer.append(htmlComponente); 
			htmlBuffer.append("</div> \n");
			HTML = htmlBuffer.toString();
			//HTML=doc.html();
			HTML = reemplazaPlantillaDetalleSMS(HTML,parametrosDTO, listNoticias,"");	
			try {
				String comscore="landing.sms.vistaPrevia";
				HTML = HTML.replace("$WCM_NAVEGACION_COMSCORE$",comscore);
			} catch (Exception e) {
				HTML = HTML.replace("$WCM_NAVEGACION_COMSCORE$", "");
				logger.error("Error al remplazar $WCM_NAVEGACION_COMSCORE$: ",e);
			}
			try {
				String title_contenido="Landing SMS Vista Previa";
				HTML = HTML.replace("$WCM_TITLE_CONTENIDO$",title_contenido);
			} catch (Exception e) {
				HTML = HTML.replace("$WCM_TITLE_CONTENIDO$", "");
				logger.error("Error al remplazar $WCM_TITLE_CONTENIDO$: ",e);
			}
			
			HTML = HTML.replace(parametrosDTO.getBaseTheme(),parametrosDTO.getCarpetaResources());
			HTML = HTML.replace(parametrosDTO.getBasePagesPortal(), "");
			
			HTML =cambiaCaracteres(HTML);
			//HTMLFULL.append(" <link href=\"/wps/MX_UNO_LandingSMS4_P/resources/css/styles.css\" rel=\"stylesheet\" type=\"text/css\">  \n");
			HTMLFULL.append(HTML);
		}catch (Exception e) {
			logger.error("Exception getHTMLPlantilla: ",e);
			return "error";
		}
		return HTMLFULL.toString();
	}
	/**
	 * Metodo para crear los folder en el servidor que seran movidos
	 * @param carpetaContenido, Ruta de las carpetas
	 * @return boolean
	 * */
	public boolean createFolders(String carpetaContenido) {
		boolean success = false;
		try {						
			File carpetas = new File(carpetaContenido) ;
			if(!carpetas.exists()) {   
				success = carpetas.mkdirs();					
			} else 
				success = true;							
		} catch (Exception e) {
			success = false;
			logger.error("Ocurrio error al crear las carpetas: ", e);
		} 
		return success;
	}
	
	public boolean writeHTML(String rutaHMTL, String HTML) {
		boolean success = false;
		try {
			FileWriter fichero = null;
	        PrintWriter pw = null;
	        try {
				fichero = new FileWriter(rutaHMTL);				
				pw = new PrintWriter(fichero);							
				pw.println(HTML);
				pw.close();
				success = true;
			} catch(Exception e){			
				logger.error("Error al obtener la plantilla " + rutaHMTL + ": ", e);
				success = false;
			}finally{
				try{                    			              
					if(null!= fichero)
						fichero.close();
				}catch (Exception e2){
					success = false;
					logger.error("Error al cerrar el file: ", e2);
				}
			}	
		} catch(Exception e) {
			success = false;
			logger.error("Fallo al crear la plantilla: ", e);
		}		
		return success;
	}
	
	
	/**
	 * Metodo que genera la pagina de detalle Normal en Programas
	 * @param parametrosDTO
	 * @param contentDTO 
	 * @param secuencia
	 * @param perfilDTO 
	 *  
	 * */
	public boolean createPlantilla(ParametrosDTO parametrosDTO,ArrayList<NotaDTO> listNoticias,String estadoSeleccionado,String paramEstadoSeleccionado,String nombreArchivo,String landingSeleccionado) 
	{
		logger.debug("Inicia createPlantillaSMSLanding.");
		boolean success = false;
		Document doc = null;		
		
		String rutaHTML = "";
		
		try
		{
			String conectar=estadoSeleccionado.equalsIgnoreCase("nacional")?parametrosDTO.getJsoupConnectLandingSMS()+"nacional":parametrosDTO.getJsoupConnectLandingSMS()+"default";
			logger.debug("Conectandose a: "+conectar);
			doc = Jsoup.connect(conectar).timeout(120000).get();
								 
			//rutaHTML = parametrosDTO.getPathFiles() +"/"+estadoSeleccionado+ "/" + parametrosDTO.getNameHTML();
			rutaHTML = parametrosDTO.getPathFiles()+estadoSeleccionado+ "/" + nombreArchivo;
				
			logger.debug("rutaHTML: " +rutaHTML);
			String HTML = doc.html();
			HTML = reemplazaPlantillaDetalleSMS(HTML,parametrosDTO, listNoticias,paramEstadoSeleccionado);
			try {
				String comscore="landing.sms."+estadoSeleccionado+landingSeleccionado;
				HTML = HTML.replace("$WCM_NAVEGACION_COMSCORE$",comscore);
			} catch (Exception e) {
				HTML = HTML.replace("$WCM_NAVEGACION_COMSCORE$", "");
				logger.error("Error al remplazar $WCM_NAVEGACION_COMSCORE$: ",e);
			}
			try {
				String descripcion_contenido="Las noticias relevantes de "+estadoSeleccionado.toUpperCase();
				HTML = HTML.replace("$WCM_DESCRIPCION_CONTENIDO$",descripcion_contenido+" | Uno Tv Noticias");
			} catch (Exception e) {
				HTML = HTML.replace("$WCM_DESCRIPCION_CONTENIDO$", "");
				logger.error("Error al remplazar $WCM_DESCRIPCION_CONTENIDO$: ",e);
			}
			
			try {
				String title_contenido="SMS "+estadoSeleccionado.toUpperCase();
				HTML = HTML.replace("$WCM_TITLE_CONTENIDO$",title_contenido+" | Uno Tv Noticias");
			} catch (Exception e) {
				HTML = HTML.replace("$WCM_TITLE_CONTENIDO$", "");
				logger.error("Error al remplazar $WCM_TITLE_CONTENIDO$: ",e);
			}
			
			//</title>
			try {
				String metaIndex="<meta name=\"robots\" content=\"INDEX, FOLLOW\" />";
				String metaNoIndex="<meta name=\"robots\" content=\"NOINDEX, FOLLOW\" />";
				HTML = HTML.replace(metaIndex,metaNoIndex);
			} catch (Exception e) {
				HTML = HTML.replace("<meta name=\"robots\" content=\"INDEX, FOLLOW\" />","");
				logger.error("Error al remplazar meta de No Indexar: ",e);
			}
			
			
			HTML = HTML.replace(parametrosDTO.getBaseTheme(), "/" + parametrosDTO.getCarpetaResources() + "/");
			//HTML = HTML.replace(parametrosDTO.getBasePagesPortal(), "");
			//HTML = reemplazaURLPages(HTML, "/wps/portal/unotv/unotv/");
			HTML = reemplazaURLPages(HTML,parametrosDTO.getBasePagesPortal());
			success = writeHTML(rutaHTML, HTML);			
			if(success && parametrosDTO.getAmbiente().equalsIgnoreCase("desarrollo")) {					
				transfiereWebServer(parametrosDTO);
			}			
		}catch (Exception e) {
			logger.error("Exception en createPlantillaDetalleNormal: ",e);
			success = false;
		}
		return success;
	}
	
	private String reemplazaURLPages(String HTML,  String basePortal) {
		try {
			HTML = HTML.replace(basePortal, "");
		} catch (Exception e) {
			logger.error("Ocurrio error al modificar URL de las paginas");
		}
		return HTML;		
	}
	public static String removeTilde(String cadena){
		String cad=cadena;
			cad=cad.replace("á","a");
			cad=cad.replace("é","e");
			cad=cad.replace("í","i");
			cad=cad.replace("ó","o");
			cad=cad.replace("ú","u");
        return cad;				  
	}
	
	public static void main(String [] args){
		String url_detalle="http://www.unotv.com/noticias/portal/nacional/detalle/alerta-flujo-ninos-migrantes-aumenta-eu-869308/";
			url_detalle=url_detalle.replace("http://www.unotv.com/", "portal/unotv/").replace("especialess", "especiales");
			System.out.println("2.-"+url_detalle);
		
		
	}
	private StringBuffer getItemsInifniteMagazine(ArrayList<NotaDTO> listNotasMagazineLanding, ParametrosDTO parametrosDTO, String paramEstadoSeleccionado){
		StringBuffer items=new StringBuffer();
		StringBuffer item=new StringBuffer();
		String url_detalle;
		String idContenidosNoRepetidos="";
		try {
			logger.info("listNotasMagazineLanding.size [Minimo "+numMagazine+" o superior]: "+listNotasMagazineLanding.size());
			LlamadasWS llamadasWS=new LlamadasWS(parametrosDTO.getURL_WS_BASE());
			
			for (NotaDTO notaDTO : listNotasMagazineLanding) {
				idContenidosNoRepetidos+="'"+notaDTO.getFcIdContenido()+"',";
			}
			
			if(listNotasMagazineLanding.size() == numMagazine){
				ArrayList<NotaDTO> listNotasExtra5Magazine=(ArrayList<NotaDTO>) llamadasWS.getNotesPublished("magazine-infinite-landing");
				 logger.info("Dentro de listNotasMagazineLanding.size() == "+numMagazine+": "+listNotasExtra5Magazine.size());
				if(listNotasExtra5Magazine!=null && listNotasExtra5Magazine.size()>0){
					logger.info("listNotasExtra5Magazine.size: "+listNotasExtra5Magazine.size());
					for (int i = 0; i < listNotasExtra5Magazine.size(); i++) {
						NotaDTO notaExtra5Magazine=listNotasExtra5Magazine.get(i);
						for (int j = 0; j < listNotasMagazineLanding.size(); j++) {
							NotaDTO notaMagazinePrincipal=listNotasMagazineLanding.get(j);
							if(notaExtra5Magazine.getFcIdContenido().equals(notaMagazinePrincipal.getFcIdContenido())){
								listNotasExtra5Magazine.remove(i);
							}
						}
					}
					logger.info("listNotasExtra5Magazine.size despues de quitar repetidos: "+listNotasExtra5Magazine.size());
					for (NotaDTO notaDTO : listNotasExtra5Magazine) {
						idContenidosNoRepetidos+="'"+notaDTO.getFcIdContenido()+"',";
					}
					
					for (int i = 0; i < listNotasExtra5Magazine.size(); i++) {
						item=new StringBuffer();
						url_detalle="";
						//url_detalle=listNotasExtra5Magazine.get(i).getFcNombre().contains("http://")||listNotasExtra5Magazine.get(i).getFcNombre().contains("https://")?listNotasExtra5Magazine.get(i).getFcNombre():listNotasExtra5Magazine.get(i).getFcLinkDetalle().replaceAll("http://www.unotv.com", "").replace("especialess", "especiales");
						if(listNotasExtra5Magazine.get(i).getFcNombre().toLowerCase().contains("http://") || listNotasExtra5Magazine.get(i).getFcNombre().toLowerCase().contains("https://"))
							url_detalle=listNotasExtra5Magazine.get(i).getFcNombre();
						else
							url_detalle=listNotasExtra5Magazine.get(i).getFcLinkDetalle().replace("http://www.unotv.com/", "").replace("especialess", "especiales")+"/"+paramEstadoSeleccionado;
						
						
						//item.append(" <a href=\""+listNotasExtra5Magazine.get(i).getFcLinkDetalle().replaceAll("http://www.unotv.com", "").replace("especialess", "especiales")+"\" class=\"item-note\"> \n");
						item.append(" <a href=\""+url_detalle+"\" class=\"item-note\"> \n");
						item.append(" <div class=\"thumb\"> \n");
						//if(listNotasExtra5Magazine.get(i).getFcImgPrincipal().contains("olimpicos_pre")){
						if(listNotasExtra5Magazine.get(i).getFcLinkDetalle().contains("olimpicos.clarosports.com")){
							item.append(" <img src=\"utils/img/blank.gif\" data-echo=\""+parametrosDTO.getDominioPreOlimpicos()+listNotasExtra5Magazine.get(i).getFcImgPrincipal()+"\"></div> \n");
						}else
							item.append(" <img src=\"utils/img/blank.gif\" data-echo=\""+listNotasExtra5Magazine.get(i).getFcImgPrincipal()+"\"></div> \n");
						
						item.append(" <h3> \n");
						item.append(" 	<span class=\"title\"> \n");
						if(listNotasExtra5Magazine.get(i).getFcIdTipoNota().equalsIgnoreCase("video"))
							item.append(" 		<i class=\"fa fa fa-play\"></i> \n");
						else if(listNotasExtra5Magazine.get(i).getFcIdTipoNota().equalsIgnoreCase("galeria"))
							item.append(" 		<i class=\"fa fa fa-camera-retro\"></i> \n");
						else if(listNotasExtra5Magazine.get(i).getFcIdTipoNota().equals("infografia"))
							item.append("<i class=\"fa fa-file-picture-o\"></i>");
						else if(listNotasExtra5Magazine.get(i).getFcIdTipoNota().equals("multimedia")){
							item.append("<i class=\"fa fa fa-play\"></i> \n");
							item.append("<i class=\"fa fa fa-camera-retro\"></i> \n");
						}
						item.append(StringEscapeUtils.escapeHtml(listNotasExtra5Magazine.get(i).getFcTitulo())+" \n");
						item.append(" 	</span> \n");
						//if(listNotasExtra5Magazine.get(i).getFcImgPrincipal().contains("olimpicos_pre")){
						if(listNotasExtra5Magazine.get(i).getFcLinkDetalle().contains("olimpicos.clarosports.com")){
							item.append("	  <span class=\"rio-2016 tag\">"+StringEscapeUtils.escapeHtml("Rio 2016")+"</span> \n");
						}else
							item.append(" 	<span class=\""+listNotasExtra5Magazine.get(i).getFcIdCategoria()+" tag\">"+StringEscapeUtils.escapeHtml(listNotasExtra5Magazine.get(i).getFcDescripcionCategoria())+"</span> \n");
						
						item.append(" </h3> \n");
						item.append(" </a> \n");
						items.append(item);
						logger.info("Detalle Nota ----->"+url_detalle);
					}
				}
			}else if(listNotasMagazineLanding.size() > numMagazine){
				for (int i = numMagazine; i < listNotasMagazineLanding.size(); i++) {
					item=new StringBuffer();
					url_detalle="";
					//url_detalle=listNotasExtra5Magazine.get(i).getFcNombre().contains("http://")||listNotasExtra5Magazine.get(i).getFcNombre().contains("https://")?listNotasExtra5Magazine.get(i).getFcNombre():listNotasExtra5Magazine.get(i).getFcLinkDetalle().replaceAll("http://www.unotv.com", "").replace("especialess", "especiales");
					if(listNotasMagazineLanding.get(i).getFcNombre().toLowerCase().contains("http://") || listNotasMagazineLanding.get(i).getFcNombre().toLowerCase().contains("https://"))
						url_detalle=listNotasMagazineLanding.get(i).getFcNombre();
					else
						url_detalle=listNotasMagazineLanding.get(i).getFcLinkDetalle().replaceAll("http://www.unotv.com/", "").replace("especialess", "especiales")+"/"+paramEstadoSeleccionado;
						
					item.append(" <a href=\""+url_detalle+"\" class=\"item-note\"> \n");
					item.append(" <div class=\"thumb\"> \n");
					//if(listNotasMagazineLanding.get(i).getFcImgPrincipal().contains("olimpicos_pre")){
					if(listNotasMagazineLanding.get(i).getFcLinkDetalle().contains("olimpicos.clarosports.com")){
						item.append(" <img src=\"utils/img/blank.gif\" data-echo=\""+parametrosDTO.getDominioPreOlimpicos()+listNotasMagazineLanding.get(i).getFcImgPrincipal()+"\"></div> \n");
					}else
						item.append(" <img src=\"utils/img/blank.gif\" data-echo=\""+listNotasMagazineLanding.get(i).getFcImgPrincipal()+"\"></div> \n");
					
					item.append(" <h3> \n");
					item.append(" 	<span class=\"title\"> \n");
					if(listNotasMagazineLanding.get(i).getFcIdTipoNota().equalsIgnoreCase("video"))
						item.append(" 		<i class=\"fa fa fa-play\"></i> \n");
					else if(listNotasMagazineLanding.get(i).getFcIdTipoNota().equalsIgnoreCase("galeria"))
						item.append(" 		<i class=\"fa fa fa-camera-retro\"></i> \n");
					else if(listNotasMagazineLanding.get(i).getFcIdTipoNota().equals("infografia"))
						item.append("<i class=\"fa fa-file-picture-o\"></i>");
					else if(listNotasMagazineLanding.get(i).getFcIdTipoNota().equals("multimedia")){
						item.append("<i class=\"fa fa fa-play\"></i> \n");
						item.append("<i class=\"fa fa fa-camera-retro\"></i> \n");
					}
					item.append(StringEscapeUtils.escapeHtml(listNotasMagazineLanding.get(i).getFcTitulo())+" \n");
					item.append(" 	</span> \n");
					//if(listNotasMagazineLanding.get(i).getFcImgPrincipal().contains("olimpicos_pre")){
					if(listNotasMagazineLanding.get(i).getFcLinkDetalle().contains("olimpicos.clarosports.com")){
						item.append("	  <span class=\"rio-2016 tag\">"+StringEscapeUtils.escapeHtml("Rio 2016")+"</span> \n");
					}else
						item.append(" 	<span class=\""+listNotasMagazineLanding.get(i).getFcIdCategoria()+" tag\">"+StringEscapeUtils.escapeHtml(listNotasMagazineLanding.get(i).getFcDescripcionCategoria())+"</span> \n");
					item.append(" </h3> \n");
					item.append(" </a> \n");
					items.append(item);
					logger.info("Detalle Nota ----->"+url_detalle);
				}
			}
		
			idContenidosNoRepetidos=idContenidosNoRepetidos.substring(0, idContenidosNoRepetidos.length()-1);
			logger.info("idContenidosNoRepetidos: "+idContenidosNoRepetidos);
			logger.info("idContenidosNoRepetidos.Split, supuestamente 9 al menos que haya repetidos: "+idContenidosNoRepetidos.split("\\,").length);
			
			ArrayList<NotaDTO> listNotasMagazineHome=(ArrayList<NotaDTO>) llamadasWS.getNotasInfiniteLanding(idContenidosNoRepetidos);
			logger.info("listNotasMagazineHome.size()"+listNotasMagazineHome.size());
			
			if(listNotasMagazineHome!= null && listNotasMagazineHome.size()>0){
				
				for (int i = 0; i < listNotasMagazineHome.size(); i++) {
					item=new StringBuffer();
					item.append(" <a href=\""+listNotasMagazineHome.get(i).getFcLinkDetalle().replaceAll("http://www.unotv.com", "").replace("especialess", "especiales")+"\" class=\"item-note\"> \n");
					item.append(" <div class=\"thumb\"> \n");
					item.append(" <img src=\"utils/img/blank.gif\" data-echo=\""+listNotasMagazineHome.get(i).getFcImgPrincipal()+"\"></div> \n");
					item.append(" <h3> \n");
					item.append(" 	<span class=\"title\"> \n");
					if(listNotasMagazineHome.get(i).getFcIdTipoNota().equalsIgnoreCase("video"))
						item.append(" 		<i class=\"fa fa fa-play\"></i> \n");
					else if(listNotasMagazineHome.get(i).getFcIdTipoNota().equalsIgnoreCase("galeria"))
						item.append(" 		<i class=\"fa fa fa-camera-retro\"></i> \n");
					else if(listNotasMagazineHome.get(i).getFcIdTipoNota().equals("infografia"))
						item.append("<i class=\"fa fa-file-picture-o\"></i>");
					else if(listNotasMagazineHome.get(i).getFcIdTipoNota().equals("multimedia")){
						item.append("<i class=\"fa fa fa-play\"></i> \n");
						item.append("<i class=\"fa fa fa-camera-retro\"></i> \n");
					}
					item.append(" "+ StringEscapeUtils.escapeHtml(listNotasMagazineHome.get(i).getFcTitulo())+" \n");
					item.append(" 	</span> \n");
					item.append(" 	<span class=\""+listNotasMagazineHome.get(i).getFcIdCategoria()+" tag\">"+StringEscapeUtils.escapeHtml(listNotasMagazineHome.get(i).getFcDescripcionCategoria())+"</span> \n");
					item.append(" </h3> \n");
					item.append(" </a> \n");
					items.append(item);
				}
			}
		} catch (Exception e) {
			logger.error("Error getItemsInifnite: ",e);
		}
		return items;
	}
	/**
	 * Metodo que genera la pagina para Mobile
	 * @param parametrosDTO
	 * @param contentDTO 
	 * @param secuencia
	 * @param perfilDTO 
	 *  
	 * */
	public String reemplazaPlantillaDetalleSMS(String HTML, ParametrosDTO parametrosDTO, ArrayList<NotaDTO> listNoticias, String paramEstadoSeleccionado)
	{
		logger.debug("Inicia reemplazaPlantillaDetalleSMS.. ");
		try {		
			StringBuffer sbFor= new StringBuffer();
			StringBuffer sbInifnite=new StringBuffer();
			try {
				for (int i = 0; i < numMagazine; i++) {
					String urlDetalle="";
					if(listNoticias.get(i).getFcNombre().toLowerCase().contains("http://") || listNoticias.get(i).getFcNombre().toLowerCase().contains("https://"))
						urlDetalle=listNoticias.get(i).getFcNombre();
					else
						urlDetalle=listNoticias.get(i).getFcLinkDetalle().replaceAll("http://www.unotv.com/", "").replace("especialess", "especiales")+"/"+paramEstadoSeleccionado;
					
					logger.info("Detalle Nota ----->"+urlDetalle);
					sbFor.append(" <a href=\""+urlDetalle+"\" class=\"featured\"> \n");
					sbFor.append(" <div class=\"thumb\"> \n");
					//if(listNoticias.get(i).getFcImgPrincipal().contains("olimpicos_pre")){
					if(listNoticias.get(i).getFcLinkDetalle().contains("olimpicos.clarosports.com")){
						sbFor.append("	<img src=\""+parametrosDTO.getDominioPreOlimpicos()+listNoticias.get(i).getFcImgPrincipal()+"\"> \n");
					}else
						sbFor.append("	<img src=\""+listNoticias.get(i).getFcImgPrincipal()+"\"> \n");
					
					if(listNoticias.get(i).getFcIdTipoNota().equals("video"))
						sbFor.append("<i class=\"fa fa fa-play\"></i> \n");
					else if(listNoticias.get(i).getFcIdTipoNota().equals("galeria"))
						sbFor.append("<i class=\"fa fa fa-camera-retro\"></i> \n");
					else if(listNoticias.get(i).getFcIdTipoNota().equals("infografia"))
						sbFor.append("<i class=\"fa fa-file-picture-o\"></i>");
					else if(listNoticias.get(i).getFcIdTipoNota().equals("multimedia")){
						sbFor.append("<i class=\"fa fa fa-play\"></i> \n");
						sbFor.append("<i class=\"fa fa fa-camera-retro\"></i> \n");
					}
					sbFor.append(" </div> \n");
					sbFor.append(" <div class=\"highlight\"> \n");
					sbFor.append("  <h3> \n");
					//if(listNoticias.get(i).getFcImgPrincipal().contains("olimpicos_pre")){
					if(listNoticias.get(i).getFcLinkDetalle().contains("olimpicos.clarosports.com")){
						sbFor.append("	  <span class=\"rio-2016 tag\">"+StringEscapeUtils.escapeHtml("Rio 2016")+"</span> \n");
					}else
						sbFor.append("	  <span class=\""+listNoticias.get(i).getFcIdCategoria()+" tag\">"+StringEscapeUtils.escapeHtml(listNoticias.get(i).getFcDescripcionCategoria())+"</span> \n");
					
					sbFor.append("	  <span class=\"title\">"+StringEscapeUtils.escapeHtml(listNoticias.get(i).getFcTitulo())+"</span> \n");
					sbFor.append("  </h3> \n");
					sbFor.append(" </div> \n");
					
					if(listNoticias.get(i).getFiBanPatrocinio().equals("1"))
						sbFor.append("<div class=\"label\"><span>Patrocinado</span></div>");
					
					sbFor.append(" </a> \n");
				}
				HTML = HTML.replace("$WCM_LIST_NOTAS$",sbFor);
				
				sbInifnite.append("<div id=\"carousel-landing\" class=\"special-carousel owl-carousel carousel-noticias\"></div> \n");
				sbInifnite.append("  \n <!--vicunaj ini--> \n ");
				sbInifnite.append(" <div class=\"infinite\"> \n");
				/*sbInifnite.append(" <script> \n");
				sbInifnite.append(" if( !!('ontouchstart' in window) ){ \n");
				sbInifnite.append(" $(document).bind(\"touchmove MSPointerMove pointermove\", this.touchRetieveContent); \n");
				sbInifnite.append(" } else { \n");
				sbInifnite.append("    $(window).bind(\"scroll\", this.scrollRetieveContent); \n");
				sbInifnite.append("   } \n");
				sbInifnite.append(" </script> \n");*/
				sbInifnite.append(" <div id=\"grid\"> \n");
				sbInifnite.append(getItemsInifniteMagazine(listNoticias, parametrosDTO,paramEstadoSeleccionado));
				sbInifnite.append(" </div> <!--grid--> \n");
				sbInifnite.append(" </div> <!--infinite--> \n");
				sbInifnite.append(" <!--vicunaj fin--> \n ");
				HTML = HTML.replace("$WCM_INFINITE_NOTAS$",sbInifnite);
				
			} catch (Exception e) {
				logger.error("Error al remplazar $WCM_LIST_NOTAS$: ",e);
			}		
			
			try {
				logger.debug("Cambiando base URL...");
				//<base href="/wps/portal/unotv/unotv/noticias/portal/nacional/detalle-prerender/" target="_self" />
				String valorBase [] = HTML.split("<base");
				valorBase[0] = valorBase[1].substring(0, valorBase[1].indexOf("/>"));
				String tmp [] = valorBase[0].split("href=\"");
				String base = tmp[1].substring(0, tmp[1].indexOf("\""));
				logger.debug("Base URL: "+base);
				HTML = HTML.replace(base, parametrosDTO.getBaseURL());			
				//HTML = HTML.replace(base, "/");
				
			} catch (Exception e) {
				logger.debug("No tiene base URL");
			}
							
		return HTML;

			
			
		} catch (Exception e) {
			logger.error("Exception en reemplazaPlantillaDetalleNormal: ",e);
		}		
	return HTML;		
	}

	
	/**
	 * Metodo que ejecuta el shell para tranferir archivos
	 * @param  ParametrosDTO, DTO con los parametros
	 * @param secuencia
	 * */
	public boolean transfiereWebServer(ParametrosDTO parametros) {
		boolean success = false;	
		
		String local = parametros.getPathFiles() + "*";
		String remote = parametros.getPathRemote();
		String comando = parametros.getPathShell() + " " + local + " " + remote;
		//String comandoElimina = parametros.getPathShellElimina() + " " + parametros.getPathFiles() + "/*";
		
		logger.debug("comado: "+comando);
		//logger.debug("comadoElimina: "+comandoElimina);
		
		try {								
			Runtime r = Runtime.getRuntime();
			r.exec(comando).waitFor();
			//r.exec(comandoElimina).waitFor();			
			success = true;
		} catch(Exception e) {
			success = false;
			logger.error("Ocurrio un error al ejecutar el Shell " + comando + ": ", e);
		}		
		return success;
	}
	
	public String eliminaComillas(String texto){
		texto=texto.replaceAll("\\'", "");
		texto=texto.replaceAll("\"", "");
		return texto;
	}
	/**
	 * Metodo para la codificacion
	 * @param String, string a revisar
	 * */
	private String cambiaCaracteres(String texto) {
		texto = texto.replaceAll("á", "&#225;");
        texto = texto.replaceAll("é", "&#233;");
        texto = texto.replaceAll("í", "&#237;");
        texto = texto.replaceAll("ó", "&#243;");
        texto = texto.replaceAll("ú", "&#250;");  
        texto = texto.replaceAll("Á", "&#193;");
        texto = texto.replaceAll("É", "&#201;");
        texto = texto.replaceAll("Í", "&#205;");
        texto = texto.replaceAll("Ó", "&#211;");
        texto = texto.replaceAll("Ú", "&#218;");
        texto = texto.replaceAll("Ñ", "&#209;");
        texto = texto.replaceAll("ñ", "&#241;");        
        texto = texto.replaceAll("ª", "&#170;");          
        texto = texto.replaceAll("ä", "&#228;");
        texto = texto.replaceAll("ë", "&#235;");
        texto = texto.replaceAll("ï", "&#239;");
        texto = texto.replaceAll("ö", "&#246;");
        texto = texto.replaceAll("ü", "&#252;");    
        texto = texto.replaceAll("Ä", "&#196;");
        texto = texto.replaceAll("Ë", "&#203;");
        texto = texto.replaceAll("Ï", "&#207;");
        texto = texto.replaceAll("Ö", "&#214;");
        texto = texto.replaceAll("Ü", "&#220;");
        texto = texto.replaceAll("¿", "&#191;");
        texto = texto.replaceAll("“", "&#8220;");        
        texto = texto.replaceAll("”", "&#8221;");
        texto = texto.replaceAll("‘", "&#8216;");
        texto = texto.replaceAll("’", "&#8217;");
        texto = texto.replaceAll("…", "...");
        texto = texto.replaceAll("¡", "&#161;");
        texto = texto.replaceAll("¿", "&#191;");
        texto = texto.replaceAll("°", "&#176;");
        
        texto = texto.replaceAll("–", "&#8211;");
        texto = texto.replaceAll("—", "&#8212;");
        //texto = texto.replaceAll("\"", "&#34;");
		return texto;
	}

	public static String capitalize(String line)
	{
	  return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
	

}
