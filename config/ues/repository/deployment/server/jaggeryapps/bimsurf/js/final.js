
$(function()
{
	var o = this;


	var objCount = 0;
	var totObjects = 0;
    var selectedNode = null;

	var runOnce=true;


	o.server = null;
	o.viewer = null;
	o.bimServerApi = null;

	SceneJS.configure({ pluginPath: "lib/scenejs/plugins" });

	/* Reset the credentials with the server credentials */
	var serverUrl = "http://127.0.0.1:8080/";
	var username = "admin@bimserver.org";
	var pass = "admin";

	connect(serverUrl,username,pass);
	
	function showSelectProject() {
		//$(this.window).resize(function(e) {
		//	o.viewer.resize($('div#viewport').width(), $('div#viewport').height());
		//});

		o.bimServerApi.call("Bimsie1ServiceInterface", "getAllProjects", {onlyActive: true, onlyTopLevel: false}, function(projects){
			projects.forEach(function(project){
				if(project.lastRevisionId != -1)
				{
					var identifier = $(this).parent().data('project');
					var parentId = project.parentId;
					/* If parent id is -1 its a main project so replace with # for the js tree */
					if(parentId == -1){
						parentId = "#";
					};

					/* Set the project structure to the json tree */
					jsonTree['core']['data'].push({'id': project.oid, 'parent' : parentId, "text":project.name,'data':project})
				}
			});

            /* Initiate the json tree drawing */
			$('#treeViewDiv').jstree(jsonTree);
			
		});
	}

    /* If the tree node is selected */
	$('#treeViewDiv').on("changed.jstree", function (e, data) {
	      // console.log(data.selected);

        /* Find the node of the project selected */
        for(var i = 0; i < jsonTree['core']['data'].length; i++) {
            var obj = jsonTree['core']['data'][i];
            if(data.selected == obj.id){
                loadProject(jsonTree['core']['data'][i]['data']);
            }
        }
	});

	/* Refresh the tree */
	function refreshTree(){
		$('#treeViewDiv').jstree(true).refresh();
	}
	
	function connect(server, email, password) {
		loadBimServerApi(server, null, function(bimServerApi){
			o.bimServerApi = bimServerApi;
			o.bimServerApi.login(email, password, false, function(){
				// $(dialog).dialog('close');
				o.viewer = new BIMSURFER.Viewer(o.bimServerApi, 'viewport');
				resize();

				o.viewer.loadScene(function(){
					var clickSelect = o.viewer.getControl("BIMSURFER.Control.ClickSelect");
					clickSelect.activate();
					clickSelect.events.register('select', o.nodeSelected);
					clickSelect.events.register('unselect', o.nodeUnselected);
				});

				showSelectProject();
			});
		});
	}


	function buildDecomposedTree(object, tree, indent) {
		var div = $("<div></div>");
		for (var i=0; i<indent; i++) {
			div.append("&nbsp;");
		}
        var name = object.object['Name'];
		div.append(name);
		tree.append(div);
		object.getIsDecomposedBy(function(isDecomposedBy){
			isDecomposedBy.getRelatedObjects(function(relatedObject){
				buildDecomposedTree(relatedObject, div, indent+1);
			});
		});
	}
	
	function resize(){
		$("#viewport").width($(window).width() + "px");
		$("#viewport").height(($(window).height() - 98) + "px");
		$("#viewport").css("width", $(window).width() + "px");
		$("#viewport").css("height", ($(window).height() - 98) + "px");
		o.viewer.resize($('div#viewport').width(), $('div#viewport').height());
	};
	
	function loadProject(project) {
		o.model = o.bimServerApi.getModel(project.oid, project.lastRevisionId, project.schema, false, function(model){
//			    model.getAllOfType("IfcProject", true, function(project){
//			 	buildDecomposedTree(project, $(".treeClass"), 0);
//			 });
		});

		o.bimServerApi.call("ServiceInterface", "getRevisionSummary", {roid: project.lastRevisionId}, function(summary){
			summary.list.forEach(function(item){
				if (item.name == "IFC Entities") {
					var _this = this;
					var toLoad = {};

					item.types.forEach(function(type){
                        /* get the total count of the IFC Entities */
						totObjects += type.count;
						toLoad[type.name] = {mode: 0};
						if(BIMSURFER.Constants.defaultTypes.indexOf(type.name) != -1) {
						}
					});

					//
					var layerLists = $('div#leftbar').find('div#layer_list').find('.data');
					if($(layerLists).is('.empty')) {
						$(layerLists).empty();
					}

					$(window).resize(resize);

					var models = {};

					// Load the models in to the JS tree
					for (var key in toLoad) {
						jsonTree['core']['data'].push({'id':key, 'parent' : project.oid, "text":key})

					};

					models[project.lastRevisionId] = o.model;
					for (var key in toLoad) {
						o.model.getAllOfType(key, true, function(object){
							object.trans.mode = 0;
							objCount++;
                            /* adding the data to the json  */
							jsonTree['core']['data'].push({'id':object['object']['oid'], 'parent' : object['object']['_t'], "text":object['object']['Name'] + "+" + object['object']['oid'],
                                'data':object['object']});

							///* Get the count and compare. If the count matches refresh the tree */
							if(totObjects == objCount && runOnce){
								runOnce = false;
								$('#treeViewDiv').jstree(true).settings.core.data = jsonTree['core']['data'];
								$('#treeViewDiv').jstree(true).refresh();
								console.log("found the duplicate");
							}
						});
					}
					var geometryLoader = new GeometryLoader(o.bimServerApi, models, o.viewer);

					var progressdiv = $("<div class=\"progressdiv\">");
					var text = $("<div class=\"text\">");
					text.html(project.name);
					var progress = $("<div class=\"progress progress-striped\">");
					var progressbar = $("<div class=\"progress-bar\">");
					progressdiv.append(text);
					progressdiv.append(progress);
					progress.append(progressbar);

					//containerDiv.find(".progressbars").append(progressdiv);

					geometryLoader.addProgressListener(function(progress){
						progressbar.css("width", progress + "%");
						if (progress == 100) {
							progressdiv.fadeOut(800);
						}
					});
					geometryLoader.setLoadTypes(project.lastRevisionId, project.schema, toLoad);
					o.viewer.loadGeometry(geometryLoader);
				}
			});
		});
	}
	
	function showProperty (propertySet, property, headerTr, editable){
		var tr = $("<tr></tr>");
		tr.attr("oid", property.__oid);
		tr.attr("psetoid", propertySet.__oid);
		headerTr.after(tr);
		if (property.changedFields != null && (property.changedFields["NominalValue"] || property.changedFields["Name"])) {
			tr.addClass("warning");
		}
		
		tr.append("<td>" + property.Name + "</td>");
		getValue(tr, property, editable);
	};
	
	function showProperties(propertySet, headerTr) {
		propertySet.getHasProperties(function(property){
			if (property.__type == "IfcPropertySingleValue") {
				showProperty(propertySet, property, headerTr);
			}
		});
	}
	
	function showPropertySet(propertySet) {
		var headerTr = $("<tr class=\"active\"></tr>");
		headerTr.attr("oid", propertySet.__oid);
		headerTr.attr("uri", propertySet.Name);
		if (propertySet.changedFields != null && propertySet.changedFields["Name"]) {
			headerTr.addClass("warning");
		}
		$("#object_info table tbody").append(headerTr);
		var headerTd = $("<td></td>");
		headerTr.append(headerTd);

		headerTd.append("<b>" + propertySet.Name + "</b>");
		showProperties(propertySet, headerTr);
	}

	function getValue(tr, property, editable) {
		(function (tr) {
			property.getNominalValue(function(value){
				var td = $("<td>");
				var v = value == null ? "" : value.value;
				var span = $("<span class=\"value nonEditable\">" + v + "</span>");
				td.append(span);
				tr.append(td);
			});
		} )(tr);
	}
	
	function nodeSelected(node) {
		$("#object_info table tbody tr").remove();
		if (node.id != null) {
			o.model.get(node.id, function(product){
				if (product.oid == node.id) {
					var tr = $("<tr></tr>");
					tr.append("<b>" + product.__type + "</b>");
					if (product.name != null) {
						tr.append("<b>" + product.name + "</b>");
					}
					$("#object_info table tbody").append(tr);
					product.getIsDefinedBy(function(isDefinedBy){
						if (isDefinedBy.__type == "IfcRelDefinesByProperties") {
							isDefinedBy.getRelatingPropertyDefinition(function(propertySet){
								if (propertySet.__type == "IfcPropertySet") {
									showPropertySet(propertySet);
								}
							});
						}
					});
				}
			});
		}

//		function isAlreadyExists(){
//			var myArray = [0,1,2],
//				needle = 1,
//				index = indexOf.call(myArray, needle); // 1
//		}

//		if(typeof this.SYSTEM.scene.data.properties[node.getId()] == 'undefined') {
//			return;
//		}
//		var infoContainer = $('#object_info').find('.data');
//		$(infoContainer).empty();
//
//		var properties = this.SYSTEM.scene.data.properties[node.getId()];
//
//		for(var i in properties) {
//			if(typeof properties[i] == 'string') {
//				$('<div />').append($('<label />').text(i)).appendTo(infoContainer);
//				$('<div />').text(properties[i]).appendTo(infoContainer);
//			}
//		}
	}

	function nodeUnselected(node) {
		$("#object_info table tbody tr").remove();
//		var infoContainer = $('#object_info').find('.data');
//		$(infoContainer).empty();
//		$('<p>').text('No object selected.').appendTo(infoContainer);
	}
});
