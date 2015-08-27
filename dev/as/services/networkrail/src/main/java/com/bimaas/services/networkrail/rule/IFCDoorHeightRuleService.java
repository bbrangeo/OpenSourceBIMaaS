/**
 * 
 */
package com.bimaas.services.networkrail.rule;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.bimaas.dto.RuleDTO;
import com.bimaas.exception.BimaasException;
import com.bimaas.model.RuleProperty;

/**
 * @author isuru
 * 
 */
@Path("/")
public interface IFCDoorHeightRuleService {

	/**
	 * Update the parameters of IFC Door Height Rule.
	 * 
	 * @param ruleProperty
	 *            ruleParam parameters.
	 * @return true if success
	 * 
	 * @throws BimaasException
	 *             custom exception.
	 */
	@POST
	@Consumes("application/json")
	@Path("/update")
	public boolean updateRuleValues(RuleProperty ruleProperty)
			throws BimaasException;

	/**
	 * @param projectId
	 * @param ruleId
	 * @return
	 * @throws BimaasException
	 */
	@POST
	@Produces("application/json")
	@Path("/get-rule/{projectId}/{ruleId}")
	public RuleDTO getRuleProperties(@PathParam("projectId") long projectId,
			@PathParam("ruleId") long ruleId) throws BimaasException;

}
