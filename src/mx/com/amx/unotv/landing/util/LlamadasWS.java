package mx.com.amx.unotv.landing.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.com.amx.unotv.landing.dto.NotaDTO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LlamadasWS {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private String URL_WS_BASE="";
	private HttpHeaders headers = new HttpHeaders();
	
	public LlamadasWS(String urlWS) {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

	        if ( factory instanceof SimpleClientHttpRequestFactory)
	        {
	            ((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000 );
	            ((SimpleClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000 );
	            logger.info("Inicializando rest template");
	        }
	        else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
	        {
	            ((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000);
	            ((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000);
	            logger.info("Inicializando rest template");
	        }
	        restTemplate.setRequestFactory( factory );
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
			URL_WS_BASE = urlWS;
	}
	
	
	public List<NotaDTO> getNotasInfiniteLanding(String idContenidos) {
		List<NotaDTO> listNotasRecibidas=new ArrayList<NotaDTO>();
		String metodo="getNotasInfiniteLanding";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<String> entity = new HttpEntity<String>( idContenidos );
			//Problema con el Cast
			//listNotasRecibidas= Arrays.asList(restTemplate.postForObject(URL_WS, parts, NotaDTO[].class));
			
			//Se resulve problema del cast
			NotaDTO[] arrayNotasRecibidas = restTemplate.postForObject(URL_WS,entity, NotaDTO[].class);
			listNotasRecibidas=new ArrayList<NotaDTO>(Arrays.asList(arrayNotasRecibidas));
			
		} catch(Exception e) {
			logger.error("Error getNotasInfiniteLanding [LlamadasWS]: ",e);
		}		
		return listNotasRecibidas;	
	}
	
	public List<NotaDTO> getNotesPublished(String idMagazine) {
		List<NotaDTO> listNotasRecibidas=new ArrayList<NotaDTO>();
		String metodo="getNotesPublished";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			logger.info("URL_WS: "+URL_WS);
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idMagazine", idMagazine);
			//Problema con el Cast
			//listNotasRecibidas= Arrays.asList(restTemplate.postForObject(URL_WS, parts, NotaDTO[].class));
			
			//Se resulve problema del cast
			NotaDTO[] arrayNotasRecibidas = restTemplate.postForObject(URL_WS,parts, NotaDTO[].class);
			listNotasRecibidas=new ArrayList<NotaDTO>(Arrays.asList(arrayNotasRecibidas));
			
		} catch(Exception e) {
			logger.error("Error getNotesPublished [LlamadasWS]: ",e);
		}		
		return listNotasRecibidas;	
	}
}
