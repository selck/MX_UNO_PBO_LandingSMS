package mx.com.amx.unotv.landing.portlet;

import java.io.*;

import javax.portlet.*;

import org.apache.log4j.Logger;

/**
 * A sample portlet
 */
public class MX_UNO_PBO_LandingSMSPortlet extends javax.portlet.GenericPortlet {
private Logger log=Logger.getLogger(MX_UNO_PBO_LandingSMSPortlet.class);
	
	public void init() throws PortletException{
		super.init();
	}
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		log.debug("=====doView=====");
		String redirect="/resources/jsp/landingSMS-4.jsp";
		try {
			response.setContentType(request.getResponseContentType());
			PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(redirect);
			rd.include(request,response);
		} catch (Exception e) {
			log.error("Error doView: ",e);
		}
	}
	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, java.io.IOException {
		log.debug("=====processAction=====");
		try {
			String accion=request.getParameter("hdAccion")==null?"":request.getParameter("hdAccion");
			log.debug("Accion processAction:"+accion);
		} catch (Exception e) {
			log.error("Error processAction: ",e);
		}
	}

}
