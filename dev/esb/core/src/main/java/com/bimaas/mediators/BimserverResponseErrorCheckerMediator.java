/**
 * 
 */
package com.bimaas.mediators;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used to check the response from the bimserver is an error or
 * not. Used in create project.
 * <p>
 * If its an error this mediator sets a property, <code>STATUS = 1</code>.
 * </p>
 * 
 * @author isuru
 * 
 */
public class BimserverResponseErrorCheckerMediator extends AbstractMediator {

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(BimserverResponseErrorCheckerMediator.class);

	/**
	 * Mediate overridden method.
	 */
	@Override
	public boolean mediate(MessageContext context) {
		try {
			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) context)
							.getAxis2MessageContext());
			LOG.info(">>>>>ORIGINAL_JSON_BODY>>>>>\n" + jsonPayloadToString
					+ "\n<<<<<_<<<<<<");
			JSONObject originalJsonBody = new JSONObject(
					jsonPayloadToString.trim());

			// response.result.oid
			String oid = new JSONObject(new JSONObject(
					originalJsonBody.getString("response")).getString("result"))
			.getString("oid");

			LOG.info("POID found from bimserver repsonse: " + oid);
			context.setProperty("POID", oid);
			context.setProperty("STATUS", "0");

			setResponse(context, jsonPayloadToString.trim());

		} catch (JSONException e) {
			LOG.info("Error occured in finding POID in repsonse.result.oid path"
					+ e);
			context.setProperty("STATUS", "1");
			// return true coz of the project created in bimserver but not in
			// bimaas db.
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
