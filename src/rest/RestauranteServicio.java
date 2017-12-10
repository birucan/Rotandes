package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Ingrediente;
import vos.Menu;
import vos.Pedido;
import vos.ProductoOLD;
import vos.RentabilidadRestaurante;
import vos.RestauranteOLD;
import vos.Producto;
import vos.ProductoBase;
import vos.Restaurante;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Path("restaurantes")
public class RestauranteServicio {
	@Context
	private ServletContext context;

	

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addrotonda(RestauranteOLD rotonda) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.registrarRestaurante(rotonda);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(rotonda).build();
	}
	
	@POST
	@Path("/{idRestaurante}/producto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarProducto(@PathParam("idRestaurante") int idRestaurante, ProductoOLD producto){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.registrarProducto(idRestaurante, producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
	
	@POST
	@Path("/{idRestaurante}/producto/{idProducto}/ingrediente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarIngrediente(@PathParam("idRestaurante") int idRestaurante,@PathParam("idProducto")int idProducto, Ingrediente ingrediente){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.registrarIngrediente(idRestaurante,idProducto, ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingrediente).build();
	}
	@POST
	@Path("/{idRestaurante}/menu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarMenu(@PathParam("idRestaurante")int idRestaurante, Menu menu){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.registrarMenu(idRestaurante, menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
	@PUT
	@Path("/{idRestaurante}/pedidos/{timestamp}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response aceptarPedido(@PathParam("idRestaurante")long idRestaurante,@PathParam("timestamp")long timestamp, Pedido pedido){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.aceptarPedido(idRestaurante, timestamp);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();

	}
	@PUT
	@Path("/pedidosMesa/{mesa}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response aceptarPedidos(@PathParam("mesa")long idMesa, Pedido pedido){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		String text = "test";
		try {
			tm.aceptarPedidosMesa(idMesa);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(text).build();

	}
	
	@GET
	@Path("/{idRestaurante}/Rentabilidad/{timestampI}/{timestampF}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response darRentabilidad(@PathParam("idRestaurante")long idRestaurante,@PathParam("timestampI")long timestampI, @PathParam("timestampF")long timestampF){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		String response = "";
		try {
			tm.darRentabilidad(idRestaurante, timestampI, timestampF);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(response).build();
	}
	
	@PUT
	@Path("{idRestaurante}/producto/{idProducto}/ingrediente/{nombreIngrediente}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEquivalenciaI(@PathParam("idRestaurante") int idRestaurante,@PathParam("idProducto")int idProducto, @PathParam("nombreIngrediente") String nombreI, Ingrediente ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		String response = "";
		try {
			tm.updateEquivalenciaI(idRestaurante, idProducto, nombreI, ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(response).build();		
	}
	
	@PUT
	@Path("{idRestaurante}/producto/{idProducto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEquivalenciaP(@PathParam("idRestaurante") int idRestaurante,@PathParam("idProducto")int idProducto, ProductoOLD producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		String response = "";
		try {
			tm.updateEquivalenciaP(idRestaurante, idProducto, producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(response).build();		
	}
	@PUT
	@Path("{idRestaurante}/surtir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response surtir(@PathParam("idRestaurante")int idRestaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		String response = "";
		try {
			tm.surtir(idRestaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(response).build();		
	}
	
	@DELETE
	@Path("{idRestaurante}/eliminarP/{timestamp}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarPedido(@PathParam("idRestaurante")long idRestaurante, @PathParam("timestamp")long timestamp, Pedido pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		
		try {
			tm.eliminarPedido(idRestaurante, timestamp);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();		
	}
	@GET
	@DELETE
	@Path("{idRestaurante}/ventasProducto/{idProducto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ventasProductos(@PathParam("idRestaurante")long idRestaurante, @PathParam("idProducto")long idProducto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Pedido> returner;
		try {
			returner = tm.ventasProducto(idRestaurante, idProducto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(returner).build();		
	}
	
	//iteracion 5
	
	
	@GET
	@Path("/{idRestaurante}/Rentabilidadv2/{timestampI}/{timestampF}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response darRentabilidadv2(@PathParam("idRestaurante")long idRestaurante,@PathParam("timestampI")long timestampI, @PathParam("timestampF")long timestampF){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<String> response = null;
		try {
			response = tm.darRentabilidadv2Grand(idRestaurante, timestampI, timestampF);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(response).build();
	}
	
}
