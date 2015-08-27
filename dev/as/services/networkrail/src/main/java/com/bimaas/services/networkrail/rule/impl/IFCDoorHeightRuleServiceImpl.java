/**
 * 
 */
package com.bimaas.services.networkrail.rule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bimaas.data.service.RuleDataService;
import com.bimaas.dto.RuleDTO;
import com.bimaas.exception.BimaasException;
import com.bimaas.model.RuleProperty;
import com.bimaas.services.networkrail.rule.IFCDoorHeightRuleService;

/**
 * Service for door height rule.
 * 
 * @author isuru
 * 
 */
public class IFCDoorHeightRuleServiceImpl implements IFCDoorHeightRuleService {

	/**
	 * Log.
	 */
	private static final Log LOG = LogFactory
			.getLog(IFCDoorHeightRuleServiceImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateRuleValues(RuleProperty ruleProperty)
			throws BimaasException {
		LOG.info("Updating height rule for project: "
				+ ruleProperty.getRuleId());
		RuleDataService ruleDataService = new RuleDataService();
		return ruleDataService.updateRuleValues(ruleProperty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RuleDTO getRuleProperties(long projectId, long ruleId)
			throws BimaasException {
		LOG.info("Requesting rule property for the rule: " + ruleId);
		RuleDataService ruleDataService = new RuleDataService();
		return ruleDataService.getRuleProperties(projectId, ruleId);
	}
}
