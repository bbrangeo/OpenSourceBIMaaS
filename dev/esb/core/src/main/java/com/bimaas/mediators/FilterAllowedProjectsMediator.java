/**
 * 
 */
package com.bimaas.mediators;

import java.util.HashMap;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * This class is used to filter allowed projects. Bimserver's response contains
 * the project list as the json body and the data service
 * <code>getAllProjectsByUser</code> as a property.
 * <p>
 * If its an error this mediator sets a property, <code>STATUS = 1</code>.
 * </p>
 * 
 * @author isuru
 * 
 */
public class FilterAllowedProjectsMediator extends AbstractMediator {

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(FilterAllowedProjectsMediator.class);

	/**
	 * Mediate overridden method.
	 */
	@Override
	public boolean mediate(MessageContext context) {

		try {
			String allowedProjects = (String) context
					.getProperty("ALLOWED_PROJECTS");

			// List<String> poids = getPoids(allowedProjects);
			JSONObject bimProjectsResponse = XML.toJSONObject(allowedProjects);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Allowed projects: " + allowedProjects);
				LOG.debug("Allowed projects in json format: "
						+ bimProjectsResponse);
			}

			JSONArray bimProjects = bimProjectsResponse
					.getJSONObject("soapenv:Envelope")
					.getJSONObject("soapenv:Body").getJSONObject("projects")
					.getJSONArray("project");

			// To keep the poid and the project name
			HashMap<Long, String> parentMap = new HashMap<Long, String>();
			parentMap.put(-1L, "NULL");
			for (int i = 0; i < bimProjects.length(); i++) {
				JSONObject bimPrj = bimProjects.getJSONObject(i);
				parentMap.put(bimPrj.getLong("poid"),
						bimPrj.getString("projectName"));
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("<<<<<<BIMaaS Project Map>>>>>>\n"
						+ parentMap.toString());
			}

			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) context)
							.getAxis2MessageContext());

			if (LOG.isDebugEnabled()) {
				LOG.debug("<<<<<<ORIGINAL JSON>>>>>>>\n" + jsonPayloadToString);
			}
			JSONObject originalJsonBody = new JSONObject(
					jsonPayloadToString.trim());
			JSONArray allProjects = new JSONArray(new JSONObject(
					originalJsonBody.getString("response")).getString("result"));

			JSONArray allowedProjectsJson = new JSONArray();

			for (int i = 0; i < allProjects.length(); i++) {
				JSONObject project = (JSONObject) allProjects.get(i);
				if (LOG.isDebugEnabled()) {
					LOG.debug("project to be evaluated: \n" + project);
				}
				long oid = project.getLong("oid");
				if (LOG.isDebugEnabled()) {
					LOG.debug("Checking poid: " + oid
							+ " is allowed for the current user...");
				}

				// if (poids.contains(String.valueOf(oid))) {
				// if (LOG.isDebugEnabled()) {
				// LOG.debug("poid: " + oid
				// + " is an allowed project for the current user");
				// }
				// allowedProjectsJson.put(project);
				// }
				for (int j = 0; j < bimProjects.length(); j++) {
					JSONObject bimPrj = bimProjects.getJSONObject(j);
					if (bimPrj.getString("poid").equals(String.valueOf(oid))) {

						if (LOG.isDebugEnabled()) {
							LOG.debug("Found valid project..." + bimPrj);
						}
						bimPrj.accumulate("parentName",
								parentMap.get(bimPrj.getLong("parentId")));

						project.put("bimaasDetails", bimPrj);
						if (LOG.isDebugEnabled()) {
							LOG.debug("poid: "
									+ oid
									+ " is an allowed project for the current user");
							LOG.debug("Adding project object: " + project);
						}
						allowedProjectsJson.put(project);
					}
				}
			}

			JSONObject result = new JSONObject();
			JSONObject response = new JSONObject();

			result.put("result", allowedProjectsJson);
			response.put("response", result);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Response with allowed project list: " + response);
			}
			setResponse(context, response.toString());

		} catch (JSONException e) {
			LOG.info("Error occured: " + e);
			context.setProperty("STATUS", "1");
			LOG.warn("Continuing with errors...");
			return true;

		} catch (Exception e) {
			LOG.error("Error occurred\n" + e);
			return false;
		}
		return true;
	}

	/**
	 * Set the response to message context.
	 * 
	 * @param messageContext
	 * @param responseBody
	 *            json body to set.
	 * @throws AxisFault
	 *             Exception.
	 */
	private void setResponse(MessageContext messageContext, String responseBody)
			throws AxisFault {
		LOG.info(">>>>>NEW_JSON_BODY>>>>>\n" + responseBody + "\n<<<<<_<<<<<<");
		JsonUtil.newJsonPayload(
				((Axis2MessageContext) messageContext).getAxis2MessageContext(),
				responseBody, true, true);
	}

}
