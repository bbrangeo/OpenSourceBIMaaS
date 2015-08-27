/**
 * This package keeps the mediators created for BIMaaS.
 */
package com.bimaas.networkrail.mediators;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bimaas.exception.JSONErrorBuilder;

/**
 * This mediator is used to evaluate the Ifc Door Height rule.
 * <p>
 * Required parameters should be set to the context as properties prior to this.
 * </p>
 * 
 * @author isuru
 * 
 */
public class IfcDoorHeightRuleMediator extends AbstractMediator {

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(IfcDoorHeightRuleMediator.class);

	/**
	 * Minimum height of a door.
	 */
	private double minHeight;

	/**
	 * Maximum height of a door.
	 */
	private double maxHeight;

	/**
	 * Mediate overridden method.
	 */
	@Override
	public boolean mediate(MessageContext context) {

		try {

			LOG.info("Executing rule: "
					+ IfcDoorHeightRuleMediator.class.getName() + "....");

			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) context)
							.getAxis2MessageContext());
			JSONObject jsonBody = new JSONObject(jsonPayloadToString);

			// maxHeight=2.1;minHeight=1.9
			String[] ruleParams = ((String) context.getProperty("RULE_PARAMS"))
					.trim().split(";");

			for (String param : ruleParams) {
				String pair[] = param.trim().split("=");

				if ("minHeight".equals(pair[0])) {
					minHeight = Double.parseDouble(pair[1]);
				}
				if ("maxHeight".equals(pair[0])) {
					maxHeight = Double.parseDouble(pair[1]);
				}
			}

			JSONObject originalResult = jsonBody.getJSONObject("response")
					.getJSONObject("result");
			JSONArray valueArray = originalResult.getJSONArray("values");

			double actualHeight = -1;
			boolean isValidDoor = true;

			JSONObject doorObject = new JSONObject();

			for (int i = 0; i < valueArray.length(); i++) {
				JSONObject value = valueArray.getJSONObject(i);
				if (value.has("fieldName")
						&& value.getString("fieldName").equalsIgnoreCase(
								"OverallHeight")) {
					actualHeight = Double.parseDouble(value
							.getString("stringValue"));

					doorObject.put("height", actualHeight);

					if ((actualHeight > maxHeight)
							|| (actualHeight <= minHeight)) {
						isValidDoor = false;
						LOG.info("Found a door which is not compliance with the door height rule\nOverall Height: "
								+ actualHeight);
						break;
					}
				}
			}

			JSONObject response = new JSONObject();

			doorObject.put("validity", isValidDoor);
			doorObject.put("guid", originalResult.getString("guid"));
			doorObject.put("oid", originalResult.getLong("oid"));
			doorObject.put("name", originalResult.getString("name"));
			doorObject.put("type", originalResult.getString("type"));
			response.put("response", doorObject);

			setResponse(context, response.toString());

		} catch (Exception e) {
			String error = "Error occurred\n" + e;
			LOG.error(error);
			try {
				setResponse(context, JSONErrorBuilder.getErrorResponse(error)
						.toString());
			} catch (AxisFault e1) {
				e1.printStackTrace();
				return false;
			}
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
		JsonUtil.newJsonPayload(
				((Axis2MessageContext) messageContext).getAxis2MessageContext(),
				responseBody, true, true);
	}

}
