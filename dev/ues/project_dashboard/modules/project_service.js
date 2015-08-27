
  var request = '{"request": {"method": "getAllProjects" }}';

  var createCORSRequest = function(method, url)
   {
      var xhr = new XMLHttpRequest();
      if ("withCredentials" in xhr)
  {
     // Most browsers.
     xhr.open(method, url,
        true);
  }
  else if (typeof XDomainRequest != "undefined")
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
      
     // xhr.setRequestHeader("Authorization", "Bearer fbea5fbd3fa947f887257fb7532372");
      xhr.setRequestHeader("Content-Type", "application/json");
      return xhr;
   };

  // var url = 'http://52.74.66.119:8280/project/1.0.0/GetAllProjects';
   var url = 'http://52.74.66.119:8284/bimaas-project/GetAllProjects';
  
   var method = 'POST';
   var xhr = createCORSRequest(method, url);
   var name1;
   var name2;

   xhr.onload = function()
   {
      // Success code goes here
  var jsn = JSON.parse(xhr.response);
  var template = $('#project_row_template')
     .html();

  $('#product-list-in-box)
 .html('')

  $.each(jsn.result,
     function(i, v)
     {
        // template = template_ori;
    x = template.replace(/{product_title}/g, v.name)
       .replace(/{product_description}/g, v.description);

    // console.log(x);
    //if(i == 2 ) { 
    //   }
    $('#product-list-in-box')
           .append(x);
     });
 
   };

   xhr.onerror = function()
   {
      // Error code goes here.
   };
   xhr.send(request);
   
   