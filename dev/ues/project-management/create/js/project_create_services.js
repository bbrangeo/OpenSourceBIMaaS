






var createCORSRequest = function(method, url) {
  var xhr = new XMLHttpRequest();

/* added >> var xhr = new XMLHttpRequest({mozSystem: true}); here */


  if ("withCredentials" in xhr) {
    // Most browsers.
    xhr.open(method, url, true);
  } else if (typeof XDomainRequest != "undefined") {
    // IE8 & IE9
    xhr = new XDomainRequest();
    xhr.open(method, url);
  } else {
    // CORS not supported.
    xhr = null;
  }
  
  xhr.setRequestHeader("Content-Type", 'application/json');
  return xhr;
};

//request to get all project details and load at the startup
   var getAllProjectRequest = '{"request": {"method": "getAllProjects" }}';
   var url = 'https://demo.bimaas.uk:8247/bimaas-project/GetAllProjects';
   var method = 'POST';
   var xhr = createCORSRequest(method, url);
	
	xhr.send(getAllProjectRequest);
   xhr.onload = function()
   {
      // Success code goes here
    var jsn = JSON.parse(xhr.response);
	
	var template = $('#parent_project_list_template').html();

	$('#parent_projects_container').html('').append("<option value='none'>None</option>");

	$.each(jsn.response.result, function(i, v) {
		  
		  x = template.replace(/{project_name}/g, v.name).replace(
			 /{project_id}/g, v.oid);
		  $('#parent_projects_container').append(x);
	   })
	};

   xhr.onerror = function()
   {
      // Error code goes here.
   }
$( "#projectAddButton" ).click(function() {

	
	
	var selectedParentProjectOption = $('#parent_projects_container option:selected').val();
	var createMethod = '';
	var parentId = '-1';
	console.log(selectedParentProjectOption);
	if(selectedParentProjectOption !== 'none'){
		console.log('Has parent project');
		createMethod = 'createSubProject';
		parentId	 = selectedParentProjectOption;
	}else{
		console.log('No parent project');
		createMethod = 'createProject';
	}
	console.log('create Method'+createMethod);
	console.log('parent Id'+parentId);
  var addProjectRequest = '{"request": {"method": "'+createMethod+'","projectName":"'+$('#projectName').val()+'","parentId":"'+parentId+'","schema":"ifc2x3tc1",'
	+'"projectLatitude":"'+$('#latitude').val()+'","projectLongitude":"'+$('#longitude').val()+'", "geoFence":[{"lat":"7.111", "lon":"8.111"},'
	+'{"lat":"7.112", "lon":"8.112"},{"lat":"7.113", "lon":"8.113"}]}}';
	
	console.log(addProjectRequest);
	 
	var url = 'https://demo.bimaas.uk:8247/bimaas-project/CreateProject';
	var method = 'POST';
	var xhr = createCORSRequest(method, url, false);
	

	xhr.send(addProjectRequest);
	

	xhr.onload = function() {
	console.log("loaded");
	
	var jsn = JSON.parse(xhr.response);
	//console.log(xhr.response);
	if(jsn.REQUEST_STATUS=='SUCCESSFUL'){
		//alert("successfully created the project");
		$('#contwrap').load('https://demo.bimaas.uk:9453/project-management/list/');
	}else{
		//alert("Request Failed");
		$('#failedtext').html(' Project Creation Failed ..');
		$('#fail').delay(10000).fadeOut('slow');
	}
	console.debug("Success");


	};

/* if failed  */
/*
$('#failedtext').html(' Project Creation Failed ..');
$('#fail').delay(10000).fadeOut('slow');
*/

/* if success  */
//$('#contwrap').load('https://demo.bimaas.uk:9453/project-management/list/');

	
	



	xhr.onerror = function() {
	  // Error code goes here.
	};

	
});
