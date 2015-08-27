package com.bimaas.mediators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONObject;

/**
 * This class is used to check if the response from bim server carry an exception.
 * <p>
 * If the response from bim server has an exception this mediator sets a property, <code>BIM_SERVER_EXCEPTION = true</code>.
 * If the respnse does not contain an exception set the property, <code>BIM_SERVER_EXCEPTION = false</code>
 * </p>
 */

public class CheckBimServerExceptionMediator extends AbstractMediator{

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(CheckBimServerExceptionMediator.class);
	
	/**
	 * Mediate overridden method.
	 */
	@Override
	public boolean mediate(MessageContext context) {
		
		try{
			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) context)
							.getAxis2MessageContext());
			JSONObject originalJsonBody = new JSONObject(jsonPayloadToString);

			if (new JSONObject(originalJsonBody.getString("response"))
			.has("exception")) {
				
				context.setProperty("BIM_SERVER_EXCEPTION", "true");
				LOG.warn("Exception response received from bim server\n"
						+ originalJsonBody);
				
			}else{
				
				context.setProperty("BIM_SERVER_EXCEPTION", "false");
				LOG.info("Successfully received response from bim server\n"
						+ originalJsonBody);
				
			}
			return true;
			
		}catch (Exception e) {
			LOG.error("Error occurred\n" + e);
			return false;
		}	
		
	}
}
