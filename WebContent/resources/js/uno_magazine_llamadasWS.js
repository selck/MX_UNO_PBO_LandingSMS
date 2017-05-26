	
	var URL_BASE_IMAGENES = 'http://www.unotv.com';
	var objWCM_JSON_FIELD='';
	var titulo_tmp = '';
	
	var urlJSON = '/MX_UNO_WSD_BackOffice/rest/backOfficeController/getNotasByCategoria/';
	var urlJSONOLIMPICOS = '/MX_PRE_OLI_WSD_BackOffice/rest/backOfficeController/getNotasByCategoria/';
	var arrayMagazine = new Array();
	var maxElementos = 5;
	var estadoSeleccionado = ''
	var paramEstadoSeleccionado = '';
	var ArrayJSON = '';
	
	$(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
    
	$( document ).ready(function() {
		
		$.getJSON( "/MX_UNO_WSD_BackOffice/rest/backOfficeController/getTipoSecciones", function( data ) {		
		  $.each( data, function( index , val ) {			
			$('#tipo_seccion').append(new Option( val.fcIdTipoSeccion , val.fcIdTipoSeccion , true, true));						  		   
		  });  
		  $('#tipo_seccion').val( '-1' );		  
		});
		
		$('#tipo_seccion').change( function (){
			$('#seccion').html( '<option value="-1">Seleccione</option>' );
			$('#seccion').val( '-1' );
			var tipoSeccion = $("#tipo_seccion").val();
			$.getJSON( "/MX_UNO_WSD_BackOffice/rest/backOfficeController/getSecciones/"+tipoSeccion, function( data ) {
			if(data.length > 0){
				$.each( data, function( index , val ) {			
					$('#seccion').append(new Option( val.fcIdSeccion , val.fcIdSeccion , true, true));						  		   
				  });  
				  $('#seccion').val( '-1' );	
			}else{
				$('#seccion').html( '<option value="-1">Seleccione</option>' );
				$('#seccion').val( '-1' );
			}
				  	  
			});
		});
		
		$('#seccion').change( function (){
			$('#categoria').html( '<option value="-1">Seleccione</option>' );
			$('#categoria').val( '-1' );
			var seccion = $("#seccion").val();
			$.getJSON( "/MX_UNO_WSD_BackOffice/rest/backOfficeController/getCategorias/"+seccion, function( data ) {
			if(data.length > 0){
				$.each( data, function( index , val ) {			
					$('#categoria').append(new Option( val.fcDescripcion , val.fcIdCategoria , true, true));						  		   
				  });  
				  $('#categoria').val( '-1' );	
			}else{
				$('#categoria').html( '<option value="-1">Seleccione</option>' );
				$('#categoria').val( '-1' );
			}
				  	  
			});
		});
		
		//On change de las secciones
		$('#categoria').change( function (){
			var categoria = $( "#categoria option:selected" ).val();
			var html = '<div><span></span></div>';
			$('#titulo_seccion').html ( 'Notas ' + categoria );
			$('#noticias').html ( '' );
			$.ajax({
				type: "get",
				dataType: "json",
				url: urlJSON + categoria +'/0',
				cache:false,
				success: function(data){
					ArrayJSON = data;
					$.each( data, function( index , val ) {
						html += '<tr><td><input type="checkbox" index="'+index+'" class="cbxnoticia" value="'+ val.fcNombre +'" /></td><td>'+ val.fcTitulo +'</td></tr>';
					});
					$('#noticias').append ( '<table>' + html +'</table>' );
				}, complete: function (xhrObj, status , jqXHR ) {
					var numPaginas = xhrObj.getResponseHeader("total");
					if ( numPaginas > 1 ){
						$('#page-selection').bootpag({
							total: numPaginas
						}).on("page", function(event, num){
							/* Paginador */
							var categoriatmp = $( "#categoria option:selected" ).val();
							$.ajax({
								type: "get",
								dataType: "json",
								url: urlJSON + categoriatmp+'/'+ num ,
								cache:false,
								success: function(data){
									html = '';
									ArrayJSON = data;
									$.each( data, function( index , val ) {
										html += '<tr><td><input type="checkbox" index="'+index+'" class="cbxnoticia" value="'+ val.fcNombre +'" /></td><td>'+ val.fcTitulo +'</td></tr>';
									}); // end ajax
									$('#noticias').html ( '<table>' + html +'</table>' );
								}
							});
							/* END paginador*/
						});
					}	//end validacion paginas > 1
				}
			});//End ajax
		});
		//End Onchange de las secciones
		
/*Lo de PreOlimpicos [INI]*/
		
		$('#secciones-pre-olimpicos').change( function (){
			$('#categorias-pre-olimpicos').html( '<option value="-1">Seleccione</option>' );
			$('#categorias-pre-olimpicos').val( '-1' );
			var seccion = $("#secciones-pre-olimpicos").val();
			
			$.getJSON( "/MX_PRE_OLI_WSD_BackOffice/rest/backOfficeController/getCategorias/"+seccion, function( data ) {
			if(data.length > 0){
				$.each( data, function( index , val ) {			
					$('#categorias-pre-olimpicos').append(new Option( val.fcDescripcionCategoria , val.fcIdCategoria , true, true));						  		   
				  });  
				  $('#categorias-pre-olimpicos').val( '-1' );	
			}else{
				$('#categorias-pre-olimpicos').html( '<option value="-1">Seleccione</option>' );
				$('#categorias-pre-olimpicos').val( '-1' );
			}
				  	  
			});
		});
		
		$('#categorias-pre-olimpicos').change( function (){		
			var categoria = $( "#categorias-pre-olimpicos option:selected" ).val();
			var html = '<div><span></span></div>';
			$('#titulo_seccion').html ( $( "#categorias-pre-olimpicos option:selected" ).text() +' PreoLimpicos');
			$('#noticias').html ( '' );
			
			$.ajax({
				type: "get",
				dataType: "json",			
				url: urlJSONOLIMPICOS + categoria +'/0',
				cache:false,
				success: function(data) 
				{
					    ArrayJSON = replaceArrayPreOlimpicos(data);			
						$.each( replaceArrayPreOlimpicos(data), function( index , val ) {
							html += '<tr><td><input type="checkbox" index="'+index+'" class="cbxnoticia" value="'+ val.fcNombre +'" /></td><td>'+ val.fcNombre +'</td></tr>'																	  		   
						});					  		  
						
						$('#noticias').append ( '<table>' + html +'</table>' );		  		
					
				}, complete: function (xhrObj, status , jqXHR ) {
					
					var numPaginas = xhrObj.getResponseHeader("total");
					
					if ( numPaginas > 1 ){

					$('#page-selection').bootpag({
							total: numPaginas
						}).on("page", function(event, num){
					
						// Paginador 	
							  
							var categoriatmp = $( "#categorias-pre-olimpicos option:selected" ).val();
							$.ajax({
							type: "get",
							dataType: "json",			
							url: urlJSONOLIMPICOS + categoriatmp +"/"+ num ,
							cache:false,
							success: function(data) 
							{
								html = '';
									ArrayJSON = replaceArrayPreOlimpicos(data);			
									$.each( replaceArrayPreOlimpicos(data), function( index , val ) {
									html += '<tr><td><input type="checkbox" index="'+index+'" class="cbxnoticia" value="'+ val.fcNombre +'" /></td><td>'+ val.fcNombre +'</td></tr>'																	  		   
							}); // end ajax					  		  
						
							$('#noticias').html ( '<table>' + html +'</table>' );		  		
					
							}
						});
						// END paginador
						});
					}	//end validacion paginas > 1
				}
			});//End ajax
		}); //End Change PreOlimpicos
		/*
		/*Lo de PreOlimpicos [END]*/
		
		//add noticias
		$('#agregar_noticias').click ( function (){
			
			var totalElementos = 0;
			
			$('#error_seleccion').html('Debe seleccionar una noticia');
			
			//[INI] Tratamiento elementos permitidos
			$( ".cbxnoticia" ).each(function( index ) {
				if($( this ).is(':checked')) {
					totalElementos++;
				}
			});
			totalElementos += arrayMagazine.length;
			//[FIN] Tratamiento elementos permitidos
			
			if(totalElementos<= maxElementos){
				$( ".cbxnoticia" ).each(function( index ) {
					var agregar = true;
					if($( this ).is(':checked')) {
						$('#error_seleccion').html('');
						for ( var xx =0; xx < arrayMagazine.length ; xx ++ ){
							if ( arrayMagazine [ xx ].fcIdContenido == ArrayJSON [ $( this ).attr( 'index' ) ].fcIdContenido  ){
								agregar = false;
								break;
							}
						}
						if ( agregar ){
							arrayMagazine [ arrayMagazine.length ] = ArrayJSON [ $( this ).attr( 'index' ) ];
						}else{
							alert("La nota a agregar ya se encuentra en el magazine");
						}
					}
					
				});
			}else{
				alert("Se permite seleccionar hasta " + maxElementos + " elementos incluyendo los ya seleccionados");
			}
			valida_elementos();
			$("input.cbxnoticia:checkbox").removeAttr("checked");
		});
		$('#noticias').find(':checked').each(function() {
			   $(this).removeAttr('checked');
		});
	
	});// end document ready
	
	function replaceAll( text, busca, reemplaza ){
		if(text!=null){
			while (text.toString().indexOf(busca) != -1)
			text = text.toString().replace(busca,reemplaza);
			return text;
		}else
			return "";
	}
	
	function insertCaracterEscape(cadena){
		var s=replaceAll(cadena,'"','&x22;');
		s=replaceAll(s,"'",'&x27;');
		return s;
	}
	
	function btnDisabled(id, bandera) {
		var idBtn = '#' + id;
	    if (bandera) {
	        $(idBtn).attr('disabled', true);
	    } else {
	        $(idBtn).removeAttr('disabled');
	    }
	}
	
	function valida_elementos(){
		
		if(arrayMagazine.length < 5){
			imprime_elementos();
			btnDisabled('agregar_noticias', false);
			btnDisabled('generar_html', true);
			btnDisabled('vista_previa', true);
		}else if(arrayMagazine.length == maxElementos){
			imprime_elementos();
			btnDisabled('agregar_noticias', true);
			btnDisabled('generar_html', false);
			btnDisabled('vista_previa', false);
		}else if(arrayMagazine.length >= 5 && arrayMagazine.length <= maxElementos){
			imprime_elementos();
			//btnDisabled('agregar_noticias', true);
			btnDisabled('agregar_noticias', false);
			btnDisabled('generar_html', false);
			btnDisabled('vista_previa', false);
		}else{
			alert("Se permite seleccionar hasta " + maxElementos + " elementos incluyendo los ya seleccionados");
		}
		
	}
	function setMaxElements(maxElement){
		if(maxElementos == 15 && maxElement == 5 && arrayMagazine.length > 5){
			 var respuesta = confirm("Se van a eliminar contenidos del magazine para que quede en 5");
		    if (respuesta == true) {
				arrayMagazine.splice(5, arrayMagazine.length);
				imprime_elementos();
				$("input.cbxnoticia:checkbox").removeAttr("checked");
		    }
		}else{
			maxElementos=maxElement;
		}
			valida_elementos();
		};
	function imprime_elementos(){
		$('#elementos_magazine').html ( '' );
		$('#JSONDATA').html ( '' );
		var noticias = '';
		var elemento_magazine='<tr><td width="30px">&nbsp;&nbsp;%index%</td><td width="100px"><img src="'+ URL_BASE_IMAGENES +'%imagen%" width="70px" height="40px" /></td><td width="500px"><input type="text" name="linkNoticia%index%" id="linkNoticia%index%" value="%nombre%" disabled size="60"></td>%controlesTD%%controlesCheckBox%<td width="100px">%controles%</td></tr>';
		var controlTD='<td id="tdLinkNoticia%index%" width="30px"><a href="javascript:editarLink(%index%)">Editar Link</a></td>';
		var controlCheckBox='<td style="display:none;" id="tdPatrocinado%index%" width="30px">Patrocinada<input name="checkbox" id="checkbox%index%" type="checkbox" value="1" onclick="insertPatrocinado('+'%index%'+');">';
		var control_elemento='<table width="100%"><tr><td><input type="button" value="^" onclick="up(%index%)"/></td><td><input onclick="down(%index%)" type="button" id="%index%" value="v" /></td><td><input type="button" id="%index%" value="X" onclick="del(%index%)"/></td></tr></table>';
		var contenido = '';
		var tmp = '';
		$('#JSONDATA').append ( '[' );
		for ( var i = 0; i < arrayMagazine.length ; i++ ){
		$('#JSONDATA').append ( '{' );	
			var j =0;
			tmp = elemento_magazine;
			$.each( arrayMagazine [ i ], function( key, val ) {
				if ( j > 0 )
					$('#JSONDATA').append ( ',' );
				    
					$('#JSONDATA').append ( '"'+ key  + '": "'+ insertCaracterEscape ( val ) + '"' );
					j++;
				tmp = tmp.replace("%index%", i +1 );
				if ( key == 'fcImgPrincipal' )
					tmp = tmp.replace("%imagen%", val );
				
				if ( key == 'fcNombre' )
					tmp = tmp.replace("%nombre%", val );
				
				/*if ( key == 'fc_ID_PROGRAMA' )
					tmp = tmp.replace("%seccion%", val );*/
				
				tmp = tmp.replace("%controles%", control_elemento.replace(/%index%/g, i) );
				tmp = tmp.replace("%controlesTD%", controlTD.replace(/%index%/g, i) );
				tmp = tmp.replace("%controlesCheckBox%", controlCheckBox.replace(/%index%/g, i) );
				
			});
			
			$('#elementos_magazine').append ( '<table width="100%">' + tmp + '</table>');
			$('#JSONDATA').append ( '},' );						
		}
		
		/*Aki pintamos los checkbox*/
		for ( var i = 0; i < arrayMagazine.length ; i++ ){
			$.each( arrayMagazine [ i ], function( key, val ) {
				if ( key == 'fiBanPatrocinio' )
					if ( val == '1' ){
						setCheckedPatrocinado(i,'true');
					}
					
				
			});
		}
		
	}
	function setCheckedPatrocinado(index,checado){
	 if(checado == "true")   
		$("#checkbox"+index+"").prop('checked', true);
       else
        $("#checkbox"+index+"").prop('checked', false);
	}
	function up( id ){
		var elemento_tmp ='';				
		if ( id > 0 ){
			elemento_tmp = arrayMagazine [ id - 1 ];
			arrayMagazine [ id - 1 ] = arrayMagazine [ id ];
			arrayMagazine [ id ] = elemento_tmp;
			
			valida_elementos();
		}
	};
	
	function down( id ){
		var elemento_tmp ='';
		if ( id < arrayMagazine.length -1  ){
			elemento_tmp = arrayMagazine [ id + 1 ];
			arrayMagazine [ id + 1 ] = arrayMagazine [ id ];
			arrayMagazine [ id ] = elemento_tmp;
			valida_elementos();
		}
	};
	
	function del( id ){
		var elemento_tmp ='';
		var index_tmp = 0;
		var array_tmp = new Array();
		for ( var yy=0; yy < arrayMagazine.length ; yy++ ){
			elemento_tmp = arrayMagazine [ id + 1 ];
			if ( yy != id ){
				array_tmp [ index_tmp++ ] = arrayMagazine [ yy ];
			}
		}
		arrayMagazine = array_tmp;
		valida_elementos();
		//valida_elementos_elimina();
	};
	
	
	