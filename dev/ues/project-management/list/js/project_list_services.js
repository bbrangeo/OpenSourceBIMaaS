//creating the CORS requests
$('#success').delay(5000).fadeOut('slow');
//$('.hf').html("<img src='https:\/\/demo.bimaas.uk:9453\/project-management\/list\/fileuploading.gif'></img>");

var createCORSRequest = function(
      method, url)
   {
      var xhr = new XMLHttpRequest();
      if ("withCredentials" in
         xhr)
      {
         // Most browsers.
         xhr.open(method, url,
            true);
      }
      else if (typeof XDomainRequest !=
         "undefined")
      {
         // IE8 & IE9
         xhr = new XDomainRequest();
         xhr.open(method, url);
      }
      else
      {
         // CORS not supported.
         xhr = null;
      }
	  xhr.setRequestHeader("Content-Type", "application/json");
      return xhr;
   };
   
var createMutipartFormDataCORSRequest = function(
      method, url)
   {
      var xhr = new XMLHttpRequest();
      if ("withCredentials" in
         xhr)
      {
         // Most browsers.
         xhr.open(method, url,
            true);
      }
      else if (typeof XDomainRequest !=
         "undefined")
      {
         // IE8 & IE9
         xhr = new XDomainRequest();
         xhr.open(method, url);
      }
      else
      {
         // CORS not supported.
         xhr = null;
      }
	  xhr.setRequestHeader("Content-Type", "multipart/form-data");
      return xhr;
   };
   

 
	//request to get all project details and load at the startup
   var getAllProjectRequest = '{"request": {"method": "getAllProjects" }}';
   var url = 'https://demo.bimaas.uk:8247/bimaas-project/GetAllProjects';
   var method = 'POST';
	$('.p').html("<img src='https:\/\/demo.bimaas.uk:9453\/project-management\/list\/loading.gif'></img>");

   var xhr = createCORSRequest(method, url,true);

	xhr.send(getAllProjectRequest);
   xhr.onload = function()
   {
	$('.p').html('');
      // Success code goes here
      var jsn = JSON.parse(xhr.response);
      var template = $('#project_row_template')
         .html();

      $('#project-items-container')
         .html('')

      $.each(jsn.response.result,
         function(i, v)
         {
            // template = template_ori;
			var parentId = '';
			var parentName = '';
			if(v.parentId == '-1'){
				parentId = '-1';
				parentName = '-';
				console.log('no parent');
			}else{
				parentId = v.parentId;
				parentName = v.parentId;
				console.log('has a parent');
			}
            x = template.replace(/{project_name}/g, v.name)
               .replace(/{project_type}/g, v.bimaasDetails.type)
               .replace(/{project_id}/g, v.id)
			   .replace(/{poid}/g, v.oid)
			   .replace(/{parent_id}/g,v.parentId)
			   .replace(/{parent_name}/g,v.parentId == "-1" ? "-" : v.bimaasDetails.parentName)
			   .replace(/{description}/g,v.description)
			   .replace(/{latitude}/g,v.bimaasDetails.latitude)
			   .replace(/{longitude}/g,v.bimaasDetails.longitude);

            // console.log(x);
            //if(i == 2 ) { 
            //   }
            $('#project-items-container')
               .append(x);
         });

      <!-- Icheck for the check box. -->
      $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red')
         .iCheck(
         {
            checkboxClass: 'icheckbox_minimal-red',
            radioClass: 'iradio_minimal-red'
         });

      //Flat red color scheme for iCheck
      $('input[type="checkbox"].flat-red, input[type="radio"].flat-red')
         .iCheck(
         {
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
         });
   };

   xhr.onerror = function()
   {
      // Error code goes here.
   };
   

	//reading the check in IFC file and creating the check in request
   
	var checkInRequest = "";
	var isFileAvailable = false;
	console.log('file: '+isFileAvailable);
	
	var handleFileSelect = function(evt) {
		var files = evt.target.files;
		var file = files[0];
		var fileName = file.name;
		var fileSize = file.size;
		var fileData;
		
		$('.hf').html("<img src='https:\/\/demo.bimaas.uk:9453\/project-management\/list\/fileuploading.gif'></img>");


		if (files && file) {
		
        var reader = new FileReader();

        reader.onload = function(readerEvt) {
            		
			isFileAvailable = true;
			console.log('file: '+isFileAvailable);
			var binaryString = readerEvt.target.result;
            //document.getElementById("base64textarea").value = btoa(binaryString);
			fileData = btoa(binaryString);
			checkInRequest = '{"request":{"method":"checkInIFCStepFile",'
			+'"projectId": "'+$('#checkinPoid').val()+'","comment": "","fileSize": "'+fileSize+'","fileName": "'+fileName+'","data":"'+fileData+'"}}';
			console.log('new'+checkInRequest);
				};

				reader.readAsBinaryString(file);
			
				var mvdModelCheckerUrl = 'https://demo.bimaas.uk:9451/services-mvdchecker-as-1.0.0/mvd-validate/validate';
				var method = 'POST';
				var mvdModelCheckerUrlXhr = createMutipartFormDataCORSRequest(method, mvdModelCheckerUrl);
				
				//var mvdModelCheckerRequest = '';
				var fd = new FormData();
				fd.append("attachment", file);
				mvdModelCheckerUrlXhr.send(fd);	
				
				mvdModelCheckerUrlXhr.onload = function(){
					$('.hf').html(' ');
					alert(mvdModelCheckerUrlXhr.response);
				
				}
				mvdModelCheckerUrlXhr.onerror = function(){
					$('.hf').html(' ');

					alert('Error in validating the xml');
				
				}
			}
			console.log(checkInRequest);
		};

	if (window.File && window.FileReader && window.FileList && window.Blob) {
		document.getElementById('ifcInputFile').addEventListener('change', handleFileSelect, false);
	} else {
		alert('The File APIs are not fully supported in this browser.');
	}


//prepeare the check in modal and show

var loadCheckinParameters = function(){

	$('#checkinPoid').val('');
    var $row = $('.selectedForCheckin').closest('tr').filter(":first");
    var $nameCell = $row.find('td').filter(":first");
	$('#checkinPoid').val($nameCell.children().val());
    console.log($('#checkinPoid').val());
	$('.selectedForCheckin').removeClass('selectedForCheckin');
	$('.checkIn-revision-details').remove();
	var projectName = $nameCell.contents().filter(function(){return this.nodeType == 3;})[0].nodeValue;
	console.log(projectName);
	
	//var projectName = $nameCell.contents().filter(function(){return this.nodeType == 3;})[0].nodeValue;
	var getAllRevisionsRequest = '{"request": { "method": "getAllRevisions","projectId":"'+$('#checkinPoid').val()+'"}}';
    var url = 'https://demo.bimaas.uk:8247/bimaas-model/GetAllRevisions';
    var method = 'POST';
    var xhr = createCORSRequest(method, url);
	var tableRow = '';
	
	xhr.send(getAllRevisionsRequest);
	
	xhr.onload = function()
   {
      // Success code goes here
	  
      var jsn = JSON.parse(xhr.response);
	  var revisionJson = null;
	  tableRow = '';
	  if(jsn.revisions.revision.length !== undefined){
		revisionJson = jsn.revisions.revision;
	  }else{
		revisionJson = jsn.revisions;
	  }
	  if( revisionJson !== null){
		$.each(revisionJson,
         function(i, v)
         {
			
			var checkInDate = new Date(v.lastUpdatedTimestamp);
			console.log(checkInDate.toLocaleString());
			tableRow = '<tr class="checkIn-revision-details"><td>'+ v.fileName + '</td>'
					 + '<td>'+checkInDate.toGMTString()+ '</td>'
					 + '<td>'+v.revisionId + '</td>'
					 + '<td><input class="minimal-red" type="radio" value="'+v.revisionId + '" name="activeRevision"></td>'
					 + '<td>'+v.status+'</td></tr>';
			$('#checkInRevisionTableBody').append(tableRow);
         });
	  }else{
		
	  }
	  
	var getActiveRevisionRequest = '{"request": { "method": "getActiveRevision","projectId": "'+$('#checkinPoid').val()+'"}}';
	var getActiveRevisionUrl = 'https://demo.bimaas.uk:8247/bimaas-model/GetActiveRevision';
   var method = 'POST';
   var getActiveRevisionXhr = createCORSRequest(method, getActiveRevisionUrl);
   getActiveRevisionXhr.send(getActiveRevisionRequest);
	  getActiveRevisionXhr.onload = function()
	   {
		  // Success code goes here
		  
		  var jsn = JSON.parse(getActiveRevisionXhr.response);
		  $.each($("input[type='radio'][name='activeRevision']"),
         function(i, v)
         {
			console.log('i:'+i+', v:'+v.value);
			if(v.value == jsn.revisionIds.revisionId.activeRevisionId){
				v.checked = true;
			}else{
				v.checked = false;
			}
         });
		  
	   };
   };

   xhr.onerror = function()
   {
      // Error code goes here.
   };
   
	//$('#checkInRevisionTableBody').append(tableRow);
	
	$('#popup-proj1-checkin').show();
}

var loadDetails = function(){

	$('#viewProjectPoid').val('');
	$('#viewProjectName').val('');
	$('#viewProjectDescription').val('');
	$('#viewProjectParentName').val('');
	$('#viewProjectLongitude').val('');
	$('#viewProjectLatitude').val('');
	$('#viewProjectType').val('');
	$('.project-details-revision-details').remove();
	
    var $row = $('.selectedForViewDetails').closest('tr').filter(":first");
	
    var $nameCell = $row.find('td').filter(":first");
	var $parentCell = $row.find('td').filter('.fa-parent');
	var $typeCell = $row.find('td').filter('.fa-type'); 
	
	$('#viewProjectPoid').val($nameCell.children().val());
	var projectName = $nameCell.contents().filter(function(){return this.nodeType == 3;})[0].nodeValue;
	var parentName = $parentCell.find('a').contents().filter(function(){return this.nodeType == 3;})[0].nodeValue;
	var typeName = $typeCell.text();
	
	$('#viewProjectName').html(projectName);
	$('#viewProjectParentName').html(parentName);
	$('#viewProjectType').html(typeName);
	$('#viewProjectDescription').html($nameCell.find('#description').val());
	$('#viewProjectLongitude').html('Longitude: ' + $nameCell.find('#longitude').val());
	$('#viewProjectLatitude').html('Latitude : ' + $nameCell.find('#latitude').val());
	
	$('.selectedForViewDetails').removeClass('selectedForViewDetails');
	$('#popup-proj1-details').show();
	
	var getAllRevisionsForDetailsRequest = '{"request": { "method": "getAllRevisions","projectId":"'+$('#viewProjectPoid').val()+'"}}';
    var url = 'https://demo.bimaas.uk:8247/bimaas-model/GetAllRevisions';
    var method = 'POST';
    var getAllRevisionsForDetailsXhr = createCORSRequest(method, url);
	var tableRow = '';
	
	getAllRevisionsForDetailsXhr.send(getAllRevisionsForDetailsRequest);
	
	getAllRevisionsForDetailsXhr.onload = function()
   {
      // Success code goes here
	  
      var jsn = JSON.parse(getAllRevisionsForDetailsXhr.response);
	  var revisionJson = null;
	  tableRow = '';
	  if(jsn.revisions.revision.length !== undefined){
		revisionJson = jsn.revisions.revision;
	  }else{
		revisionJson = jsn.revisions;
	  }
	  if( revisionJson !== null){
		$.each(revisionJson,
         function(i, v)
         {
			
			var checkInDate = new Date(v.lastUpdatedTimestamp);
			console.log(checkInDate.toLocaleString());
			tableRow = '<tr class="project-details-revision-details"><td>'+ v.fileName + '</td>'
					 + '<td>'+checkInDate.toGMTString()+ '</td>'
					 + '<td>'+v.revisionId + '</td>'
					 + '<td><input class="minimal-red" type="radio" value="'+v.revisionId + '" name="activeRevision"></td>'
					 + '<td>'+v.status+'</td></tr>';
			$('#viewDetailsTableBody').append(tableRow);
         });
	  }else{
		
	  }
	  
	var getActiveRevisionForDetailsRequest = '{"request": { "method": "getActiveRevision","projectId": "'+$('#viewProjectPoid').val()+'"}}';
	var getActiveRevisionUrl = 'https://demo.bimaas.uk:8247/bimaas-model/GetActiveRevision';
   var method = 'POST';
   var getActiveRevisionForDetailsXhr = createCORSRequest(method, getActiveRevisionUrl);
   getActiveRevisionForDetailsXhr.send(getActiveRevisionForDetailsRequest);
	  getActiveRevisionForDetailsXhr.onload = function()
	   {
		  // Success code goes here
		  
		  var jsn = JSON.parse(getActiveRevisionForDetailsXhr.response);
		  $.each($("input[type='radio'][name='activeRevision']"),
         function(i, v)
         {
			console.log('i:'+i+', v:'+v.value);
			v.disabled = true;
			if(v.value == jsn.revisionIds.revisionId.activeRevisionId){
				v.checked = true;
			}else{
				v.checked = false;
			}
			//v.disabled = true;
         });
		  
	   };
	   
	   getActiveRevisionForDetailsXhr.onerror = function()
	   {
		  // Error code goes here.
	   };

   };

   getAllRevisionsForDetailsXhr.onerror = function()
   {
      // Error code goes here.
   };

}

//sending the check in request
$('#saveChangesToIfcFilesButton').click(function(){

	if(isFileAvailable){
	
    
		console.log(checkInRequest);
	   var checkInUrl = 'https://demo.bimaas.uk:8247/bimaas-model/CheckInIFCStepFile';
	   var method = 'POST';
	   var checkInXhr = createCORSRequest(method, checkInUrl);
		
		checkInXhr.send(checkInRequest);
	   checkInXhr.onload = function()
	   {
		  // Success code goes here
		  var jsn = JSON.parse(checkInXhr.response);
		  console.log(checkInXhr.response);
		  $('#checkinPoid').val('');
		  $('#ifcInputFile').wrap('<form>').closest('form').get(0).reset();
		  $('#ifcInputFile').unwrap();
		  alert('Successfully checked in the IFC file.\nReference ID:'+jsn.response.referenceId);
		  isFileAvailable = false;
	   };

	   checkInXhr.onerror = function()
	   {
		  // Error code goes here.
		  console.log("request failed");
	   };
	}

		var selectedVal = "";
		var selected = $("input[type='radio'][name='activeRevision']:checked");
		if (selected.length > 0) {
			selectedVal = selected.val();
			console.log(selectedVal);
		}

		if(selectedVal !== ""){
			var setActiveRevisionRequest = '{"request": { "method": "setActiveRevision","projectId": "'+$('#checkinPoid').val()+'", "revisionId": "'+selectedVal+'"}}'
			var url = 'https://demo.bimaas.uk:8247/bimaas-model/SetActiveRevision';
		   var method = 'POST';
		   var xhr = createCORSRequest(method, url);
		   xhr.send(setActiveRevisionRequest);
		   xhr.onload = function()
		   {
			  // Success code goes here
			  var jsn = JSON.parse(xhr.response);
			  console.log(xhr.response);
			  
		   };

		   xhr.onerror = function()
		   {
			  // Error code goes here.
			  console.log("request failed");
		   };
		}
			$('#checkinPoid').val('');
			  $('#ifcInputFile').wrap('<form>').closest('form').get(0).reset();
			  $('#ifcInputFile').unwrap();
			  $('.modal-plane').hide();
});
