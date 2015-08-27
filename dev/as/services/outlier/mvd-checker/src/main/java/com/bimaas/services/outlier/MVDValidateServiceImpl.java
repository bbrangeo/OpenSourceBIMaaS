/**
 * 
 */
package com.bimaas.services.outlier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.Response;

import nl.tue.ddss.ifc_check.MVDCheckerTest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import com.bimaas.exception.BimaasException;

/**
 * @author isuru
 * 
 */
public class MVDValidateServiceImpl implements MVDValidateService {
	/**
	 * Log.
	 */
	private static final Log LOG = LogFactory
			.getLog(MVDValidateServiceImpl.class);

	@Override
	public boolean getValidatorDetails() throws BimaasException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String validate(Attachment attachment) throws BimaasException {

		String response = "";
		String ifcFile = attachment.getContentDisposition().getParameter(
				"filename");
		System.out.println(ifcFile
				+ " file recived to validate....>>>>>>>>>>>>>>>>");

		LOG.info(ifcFile + " file recived to validate....>>>>>>>>>>>>>>>>");
		String resources = System.getProperty("user.dir")
				+ "/repository/deployment/server/webapps/services-mvdchecker-as-1.0.0/WEB-INF/classes/";
		String schema = resources + "mvd/schema/IFC2X3_TC1.exp";
		String tempifcFolder = resources + "mvd/tempifc";
		String tempifcFile = resources + "mvd/tempifc/" + ifcFile;
		String rulexml = resources + "mvd/rulexml/rule.xml";
		String reportFolder = resources + "mvd/report";
		String reportFile = resources + "mvd/report/" + ifcFile
				+ "_bcf_report.zip";

		LOG.info("Files which are to be contribute in validation:\n" + schema
				+ "\n" + tempifcFile + "\n" + rulexml + "\n" + reportFile
				+ "\n");

		System.out.println("Files which are to be contribute in validation:\n"
				+ schema + "\n" + tempifcFile + "\n" + rulexml + "\n"
				+ reportFile + "\n");

		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File tempIfcDir = new File(tempifcFolder);
			if (!tempIfcDir.exists()) {
				if (tempIfcDir.mkdir()) {
					System.out.println("Directory is created for temp ifc!");
				} else {
					System.out.println("Failed to create temp directory!");
					throw new BimaasException(
							"Error in creating temp directory for ifc file");
				}
			}

			inStream = attachment.getObject(InputStream.class);
			outStream = new FileOutputStream(new File(tempifcFile));
			IOUtils.copy(inStream, outStream);

			File reportDir = new File(reportFolder);
			if (!reportDir.exists()) {
				if (reportDir.mkdir()) {
					System.out.println("Directory is created for report!");
				} else {
					System.out.println("Failed to create directory!");
					throw new BimaasException("Error in creating report");
				}
			}
			// PrintWriter writer = new PrintWriter(report, "UTF-8");
			// writer.println("Result...");
			// writer.close();

			MVDCheckerTest checker = new MVDCheckerTest();
			response = checker.getMVDCheckerResponse(schema, tempifcFile,
					rulexml, reportFile);

		} catch (FileNotFoundException e) {
			LOG.error("Error occurred in mvd model checker when reading | writing streams: "
					+ e);
			throw new BimaasException(
					"Error occurred in mvd model checker when reading | writing streams: ",
					e);

		} catch (Exception e) {
			LOG.error("Error occurred: " + e);
			throw new BimaasException("Error occurred in mvd model checker", e);
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				LOG.error("Error occurred when closing streams: " + e);
				throw new BimaasException(
						"Error occurred when closing streams: ", e);
			}
		}
		// ResponseBuilder response = Response.ok((Object) new
		// File(reportFile));
		// response.header("Content-Disposition",
		// "attachment; filename=report.zip");
		// response.header("Content-Type", "application/zip");
		// response.type("application/zip");
		return response
				+ "\nBCF File Download URL: "
				+ "http://demo.bimaas.uk:9771/services-mvdchecker-as-1.0.0/mvd-validate/report/"
				+ reportFile;
		// return response.build();
	}

	@Override
	public Response validate(String fileName) throws BimaasException {

		String resources = System.getProperty("user.dir")
				+ "/repository/deployment/server/webapps/services-mvdchecker-as-1.0.0/WEB-INF/classes/";
		String report = resources + "mvd/report/" + fileName
				+ "_bcf_report.zip";

		File reportFile = new File(report);
		String mt = new MimetypesFileTypeMap().getContentType(reportFile);

		return Response
				.ok(reportFile, mt)
				.header("Content-Disposition",
						"attachment; filename=" + fileName + "_report.zip")
						.build();
	}
}
