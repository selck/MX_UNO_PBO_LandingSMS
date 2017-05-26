
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>

<%@ page import="java.util.*" %>
<portlet-client-model:init>
	<portlet-client-model:require module="ibm.portal.xml.*"/>
	<portlet-client-model:require module="ibm.portal.portlet.*"/>   
</portlet-client-model:init>

<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/jquery-latest.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/bootpag.min.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/uno_magazine_llamadasWS.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/general.js'></script>
<link href="<%= request.getContextPath() %>/resources/css/paginacion.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript">
var generaHtml = "<%=response.encodeURL(request.getContextPath())%>" + "/GeneraHtmlServlet";
</script>
<script type="text/javascript">
var vistaPrevia = "<%=response.encodeURL(request.getContextPath())%>" + "/VistaPreviaServlet";
</script>
<style>
	.content { 
			padding: 10px 10px 10px 10px;
			border : 1px solid #ccc;
	}
</style>
<div id="allContent">
<div id="wait" style="display: none; width: 69px; height: 89px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
	<img src="<%= request.getContextPath() %>/resources/images/loader.gif" width="64" height="64">
	<br>Loading..
</div>
<div class="content">	
	<h3 style="text-align: center">Elementos del Componente</h3>
	<div id="elementos_magazine">			

	</div>
</div>
	
<!-- Ini Secciones -->
<div class="content">
	<h3>Numero de Notas Landing</h3>
	<input type="radio" name="num_notas_landing" value="5" checked onclick="javascript:setMaxElements(this.value);"> Solo Notas Principales [5]<br>
  <br>
  	<input type="radio" name="num_notas_landing" value="15" onclick="javascript:setMaxElements(this.value);"> Todas Las Notas [M&aacute;x 15]<br>
</div>	
<div class="content">
<h3>Notas Uno Noticias</h3>
	<strong>Tipo Secci&oacute;n </strong>
	<select name="tipo_seccion" id="tipo_seccion">
		<option value="-1">Seleccione</option>
	</select>
<br>
<br>
	<strong>Secci&oacute;n </strong>
	<select name="seccion" id="seccion">
		<option value="-1">Seleccione</option>
	</select>
<br>
<br>
	<strong>Categor&iacute;a</strong>
	<select name="categoria" id="categoria">
		<option value="-1">Seleccione</option>
	</select>
</div>
<br>
<!-- Secciones para preOlimpicos-->
<div class="content">
<h3>Notas Pre Olimpicos</h3>
Seleccione una Secci&oacute;n:
	<select name="secciones-pre-olimpicos" id="secciones-pre-olimpicos">
		<option value="-1">Seleccione</option>
		<option value="NOT">Notas</option>
		<option value="PRO">Programas</option>
		<option value="CAP">Capsulas</option>
	</select>
<br>
<br>
Seleccione una Categor&iacute;a
	<select name="categorias-pre-olimpicos" id="categorias-pre-olimpicos">
		<option value="-1">Seleccione</option>
	</select>	
</div>
<!-- Fin Secciones -->
<br>
<div class="content">
<h3>Selecci&oacute;n de Estado y N&uacute;mero de Landing</h3>
	Seleccione Estado:
	<select id="estado" class="medium">
		<option value="-1">Selecciona una Opción</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=Nacional">Nacional</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=DF">DF</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=aguascalientes">AGUASCALIENTES</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=baja_california">BAJA CALIFORNIA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=baja_california_sur">BAJA CALIFORNIA SUR</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=campeche">CAMPECHE</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=chiapas">CHIAPAS</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=chihuahua">CHIHUAHUA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=coahuila">COAHUILA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=colima">COLIMA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=durango">DURANGO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=estado_de_mexico">ESTADO DE MÉXICO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=guanajuato">GUANAJUATO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=guerrero">GUERRERO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=hidalgo">HIDALGO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=jalisco">JALISCO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=michoacan">MICHOACÁN</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=morelos">MORELOS</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=nayarit">NAYARIT</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=nuevo_leon">NUEVO LEÓN</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=oaxaca">OAXACA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=puebla">PUEBLA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=queretaro">QUERÉTARO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=quintana_roo">QUINTANA ROO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=san_luis_potosi">SAN LUIS POTOSÍ</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=sinaloa">SINALOA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=sonora">SONORA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=tabasco">TABASCO</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=tamaulipas">TAMAULIPAS</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=tlaxcala">TLAXCALA</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=veracruz">VERACRUZ</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=yucatan">YUCATÁN</option>
		<option value="?utm_source=sms&utm_medium=link&utm_campaign=zacatecas">ZACATECAS</option>
   	</select>
	<br>
	<br>
	Seleccione N&uacute;mero de landing :
	<select name="numLanding" id="numLanding">
		<option value="-1">Seleccione</option>
		<option value="1">Lunes</option>
		<option value="2">Martes</option>
		<option value="3">Mi&eacute;rcoles</option>
		<option value="4">Jueves</option>
		<option value="5">Viernes</option>
		<option value="6">S&aacute;bado</option>
		<option value="7">Domingo</option>
	</select>
</div>

</br>
<div class="content">
	<input type="button" value="Agregar seleccion a magazine" id="agregar_noticias"/>
	<span id="error_seleccion">&nbsp;</span>
</div>

<div id="div_generar_html" class="content" style="padding-top: 30px; padding-bottom: 30px;">
	<input type="button" value="Generar HTML" id="generar_html" onclick="createHTML();" disabled="disabled">
	<input style="display: none" type="button" value="Vista Previa" id="vista_previa" onclick="javascript:showVistaPrevia();" disabled="disabled">
	<span id="error_generar_html">&nbsp;</span>
</div>  
<div id="loading" style="height:50px;display:none;">
Procesando ...
<img src="<%=request.getContextPath() %>/resources/images/loader.gif" style="vertical-align:middle; width:96px; height:25px"/>
</div> 
<div class="content">
	<p><h2><span id="titulo_seccion"></span></h2></p>
	<div id="noticias" >
		<!-- Noticias -->
	</div>
	<div id="page-selection" class="pagination bootpag" style="text-align:right;" ></div>
</div>
</div>
<div class="content" id="muestraUrlMobile" style="display: none">
<form name="formFin" method="post" action="<portlet:actionURL/>">			
	<h4>URL Mobile</h4>
	<input name="url-mobile" id="url-mobile" type="text" placeholder="URL Mobile">
	<h4 style="display:none">>Short URL</h4>
	<input name="url-mobile-recortada" id="url-mobile-recortada" type="text" placeholder="URL final" style="display:none">
	<input type="button" value="Regresar" id="terminar" onclick="javascript:finCreateHTML(document.formFin);"/>
	<span id="error_terminar_html">&nbsp;</span>
	<input type="hidden" id="hdAccion" name="hdAccion" value="terminar" />
</form>
</div>
<!-- end Noticias -->

<div id="previa" style="display:none">

 
</div>	


