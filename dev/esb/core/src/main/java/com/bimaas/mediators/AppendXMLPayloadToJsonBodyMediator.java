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
import org.json.XML;

/**
 * This class is used to append the xml body which is provided as property named
 * <code>APPEND_PAYLOAD</code> to existing json body. And the final body present
 * as a json response.
 * <p>
 * If its an error this mediator sets a property, <code>STATUS = 1</code>.
 * </p>
 * 
 * @author isuru
 * 
 */
public class AppendXMLPayloadToJsonBodyMediator extends AbstractMediator {

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(AppendXMLPayloadToJsonBodyMediator.class);

	/**
	 * Name of the appending xml body.
	 */
	private String appendBodyName;

	/**
	 * Mediate overridden method.
	 */
	@Override
	public boolean mediate(MessageContext context) {

		try {
			String bodyToAppend = (String) context
					.getProperty("APPEND_PAYLOAD");
			if (LOG.isDebugEnabled()) {
				LOG.debug("APPEND_PAYLOAD: " + bodyToAppend);
			}

			JSONObject jsonToAppend = XML.toJSONObject(bodyToAppend);

			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) context)
							.getAxis2MessageContext());

			if (LOG.isDebugEnabled()) {
				LOG.debug("<<<<<<ORIGINAL JSON>>>>>>>\n" + jsonPayloadToString);
			}
			JSONObject originalJsonBody = new JSONObject(
					jsonPayloadToString.trim());

			originalJsonBody.put(getAppendBodyName(), jsonToAppend);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Response with allowed project list: "
						+ originalJsonBody);
			}
			setResponse(context, originalJsonBody.toString());

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

	/**
	 * @return the appendBodyName
	 */
	public final String getAppendBodyName() {
		return appendBodyName;
	}

	/**
	 * @param appendBodyName
	 *            the appendBodyName to set
	 */
	public final void setAppendBodyName(String appendBodyName) {
		this.appendBodyName = appendBodyName;
	}

}
