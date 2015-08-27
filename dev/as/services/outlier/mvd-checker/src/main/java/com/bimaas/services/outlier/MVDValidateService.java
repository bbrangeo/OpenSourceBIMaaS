/**
 * 
 */
package com.bimaas.services.outlier;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import com.bimaas.exception.BimaasException;

/**
 * @author isuru
 * 
 */
@Path("/")
public interface MVDValidateService {

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
	@Path("/info")
	public boolean getValidatorDetails() throws BimaasException;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/validate")
	public String validate(@Multipart("attachment") Attachment attachment)
			throws BimaasException;

	@GET
	@Produces("application/zip")
	@Path("/report/{fileName}")
	public Response validate(@PathParam("fileName") String fileName)
			throws BimaasException;

	/**
	 * @param projectId
	 * @param ruleId
	 * @return
	 * @throws BimaasException
	 */
	/*
	 * @POST
	 * 
	 * @Produces("application/json")
	 * 
	 * @Path("/get-rule/{projectId}/{ruleId}") public void
	 * getRuleProperties(@PathParam("projectId") long projectId,
	 * 
	 * @PathParam("ruleId") long ruleId) throws BimaasException;
	 */

}
