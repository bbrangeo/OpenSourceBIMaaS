package com.bimaas.services.networkrail.rule;
///**
// *
// */
//package com.bimaas.services.networkrail;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//
///**
// * @author admin
// *
// */
//@Path("/")
//public interface ProjectManageService {
//
//	/**
//	 * Create a new project for the given {@link Project} json.
//	 *
//	 * @param project
//	 *            json of the {@link Project}
//	 * @return true if success
//	 * @throws IOException
//	 *             Exceptions
//	 */
//	@POST
//	@Consumes("application/json")
//	@Path("/create")
//	public boolean createProject(Project project) throws IOException; // TESTED
//	// WORKING
//	// FLOW
//
//	/**
//	 * Returns the project json for the given name if exist or null.
//	 *
//	 * @param projectId
//	 *            name of the project
//	 * @return {@link Project} json | NULL
//	 * @throws IOException
//	 *             Exception.
//	 */
//	@GET
//	// @Path("/accessProject/{projectId}")
//	@Path("/accessProject")
//	@Produces("application/json")
//	public Project accessProject(@QueryParam("projectId") long projectId)
//			throws IOException;// TESTED WORKING FLOW
//
//	/**
//	 * Returns the project json for the given name if exist or null.
//	 *
//	 * @param projectId
//	 *            name of the project
//	 * @return List of {@link Project} json | NULL
//	 */
//	@GET
//	// @Path("/accessProjects/{userId}")
//	@Path("/accessProjects")
//	@Produces("application/json")
//	public List<Project> accessProjects(@QueryParam("userId") long userId)
//			throws IOException; // TESTED WORKING FLOW
//
//	/**
//	 * Returns the list of sub projects when project Id is given.
//	 *
//	 * @param projectId
//	 *            id of the parent project.
//	 * @return list of {@link Project}.
//	 * @throws IOException
//	 *             Exception.
//	 */
//	@GET
//	// @Path("/accessSubProjects/{projectId}")
//	@Path("/accessSubProjects")
//	@Produces("application/json")
//	public List<Project> accessSubProjects(
//			@QueryParam("projectId") long projectId) throws IOException; // TESTED
//	// WORKING
//	// FLOW
//
//	/**
//	 * Gives the project details for a particular user with the project name.
//	 *
//	 * @param userId
//	 *            id of the user.
//	 * @param projectName
//	 *            name of the project.
//	 * @return {@link Project} instance.
//	 * @throws IOException
//	 *             IO exception.
//	 */
//	@GET
//	@Path("{userId}/{projectName}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Project getProjectByUser(@PathParam("userId") long userId,
//			@PathParam("projectName") String projectName) throws IOException; // Won't
//	// be
//	// used
//
//	/**
//	 * Update the existing project
//	 *
//	 * @param project
//	 * @return
//	 * @throws IOException
//	 */
//	@POST
//	@Consumes("application/json")
//	@Path("/update")
//	public boolean updateProject(Project project) throws IOException;
//}
