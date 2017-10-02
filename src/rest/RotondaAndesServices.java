//
//package rest;
//
//
//import java.util.List;
//
//import javax.servlet.ServletContext;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.codehaus.jackson.map.ObjectMapper;
//
//import tm.RotondAndesTM;
//import vos.Rotonda;
//
///**
// * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas/...
// * @author Monitores 2017-20
// */
//@Path("rotondas")
//public class RotondaAndesServices {
//
//	/**
//	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
//	 */
//	@Context
//	private ServletContext context;
//
//	/**
//	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
//	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
//	 */
//	private String getPath() {
//		return context.getRealPath("WEB-INF/ConnectionData");
//	}
//	
//	
//	private String doErrorMessage(Exception e){
//		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
//	}
//	
//
//	/**
//	 * Metodo que expone servicio REST usando GET que da todos los rotondas de la base de datos.
//	 * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas
//	 * @return Json con todos los rotondas de la base de datos o json con 
//     * el error que se produjo
//	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getrotondas() {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		List<Rotonda> rotondas;
//		try {
//			rotondas = tm.darRotonda();
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(rotondas).build();
//	}
//
//	 /**
//     * Metodo que expone servicio REST usando GET que busca el rotonda con el id que entra como parametro
//     * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas/<<id>>" para la busqueda"
//     * @param name - Nombre del rotonda a buscar que entra en la URL como parametro 
//     * @return Json con el/los rotondas encontrados con el nombre que entra como parametro o json con 
//     * el error que se produjo
//     */
//	@GET
//	@Path( "{id: \\d+}" )
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getrotonda( @PathParam( "id" ) Long id )
//	{
//		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
//		try
//		{
//			Rotonda v = tm.buscarRotondaPorId( id );
//			return Response.status( 200 ).entity( v ).build( );			
//		}
//		catch( Exception e )
//		{
//			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
//		}
//	}
//
//    /**
//     * Metodo que expone servicio REST usando GET que busca el rotonda con el nombre que entra como parametro
//     * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas/nombre/nombre?nombre=<<nombre>>" para la busqueda"
//     * @param name - Nombre del rotonda a buscar que entra en la URL como parametro 
//     * @return Json con el/los rotondas encontrados con el nombre que entra como parametro o json con 
//     * el error que se produjo
//     */
//	@GET
//	@Path( "{nombre}" )
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getrotondaName( @QueryParam("nombre") String name) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		List<Rotonda> rotondas;
//		try {
//			if (name == null || name.length() == 0)
//				throw new Exception("Nombre del rotonda no valido");
//			rotondas = tm.buscarrotondasPorName(name);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(rotondas).build();
//	}
//
//
//    /**
//     * Metodo que expone servicio REST usando POST que agrega el rotonda que recibe en Json
//     * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas/rotonda
//     * @param rotonda - rotonda a agregar
//     * @return Json con el rotonda que agrego o Json con el error que se produjo
//     */
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addrotonda(Rotonda rotonda) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.addrotonda(rotonda);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(rotonda).build();
//	}
//	
//    /**
//     * Metodo que expone servicio REST usando POST que agrega los rotondas que recibe en Json
//     * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas/varios
//     * @param rotondas - rotondas a agregar. 
//     * @return Json con el rotonda que agrego o Json con el error que se produjo
//     */
//	@POST
//	@Path("/varios")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addrotonda(List<Rotonda> rotondas) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.addrotondas(rotondas);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(rotondas).build();
//	}
//	
//    /**
//     * Metodo que expone servicio REST usando PUT que actualiza el rotonda que recibe en Json
//     * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas
//     * @param rotonda - rotonda a actualizar. 
//     * @return Json con el rotonda que actualizo o Json con el error que se produjo
//     */
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updaterotonda(Rotonda rotonda) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.updaterotonda(rotonda);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(rotonda).build();
//	}
//	
//    /**
//     * Metodo que expone servicio REST usando DELETE que elimina el rotonda que recibe en Json
//     * <b>URL: </b> http://"ip o nombre de host":8080/rotondaAndes/rest/rotondas
//     * @param rotonda - rotonda a aliminar. 
//     * @return Json con el rotonda que elimino o Json con el error que se produjo
//     */
//	@DELETE
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleterotonda(Rotonda rotonda) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.deleterotonda(rotonda);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(rotonda).build();
//	}
//
//
//}
