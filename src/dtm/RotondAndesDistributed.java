package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.EliminarRestauranteMDB;
import jms.NonReplyException;
import jms.ProductosMDB;
import jms.RentabilidadRestauranteMDB;
//import jms.AllVideosMDB;
//import jms.NonReplyException;
import tm.RotondAndesTM;
import vos.ListaProductos;
//import vos.ListaVideos;
import vos.ListaRentabilidad;
import vos.RentabilidadRestaurante;

public class RotondAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotondAndesDistributed instance;
	
	private RotondAndesTM tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	//private AllVideosMDB allVideosMQ;
	private RentabilidadRestauranteMDB rentabilidadRestaurantesMQ;
	private ProductosMDB productosMQ;
	//private EliminarRestauranteMDB eliminarRestaurantesMQ;
	private EliminarRestauranteMDB eliminarRestaurantesMQ;
	
	private static String path;


	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		//allVideosMQ = new AllVideosMDB(factory, ctx);
		rentabilidadRestaurantesMQ = new RentabilidadRestauranteMDB(factory, ctx);
		rentabilidadRestaurantesMQ.start();
		
		productosMQ = new ProductosMDB(factory, ctx);
		productosMQ.start();
		
		eliminarRestaurantesMQ = new EliminarRestauranteMDB(factory, ctx);
		eliminarRestaurantesMQ.start();
		
		
		//allVideosMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		rentabilidadRestaurantesMQ.close();
		productosMQ.close();
		eliminarRestaurantesMQ.close();
	}
	
	/**
	 * MÃ©todo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTM tm)
	{
	   this.tm = tm;
	}
	
	private static RotondAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesTM tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTM tm = new RotondAndesTM(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTM tm = new RotondAndesTM(path);
		return getInstance(tm);
	}



	public ListaRentabilidad getLocalRentabilidades(String parametrosUnidos)throws Exception
	{
		String [] parametros = parametrosUnidos.split(",");
		String fecha1 = parametros[0];//<---FECHA DE INICIO
		String fecha2 = parametros[1];//<---FECHA DE FIN
		Integer criterio = Integer.parseInt(parametros[2]);//<---CRITERIO DE BÚSQUEDA.
		Long idProducto = null;//<---ID DEL PRODUCTO, PUEDE SER NULL
		if(!parametros[3].equals("null"))
		{
			idProducto = Long.parseLong(parametros[3]);
		}
		//ATENCIÓN. ESTE PARTE DEL MÉTODO HACE EL LLAMADO A EL MÉTODO LOCAL.
		String[] subFecha1 = fecha1.split("/");
		String[] subFecha2 = fecha2.split("/");
		Date fechaInicio = new Date();
		fechaInicio.setDate(Integer.parseInt(subFecha1[0]));
		fechaInicio.setMonth(Integer.parseInt(subFecha1[1]));
		fechaInicio.setYear(Integer.parseInt(subFecha1[2]));
		Date fechaFin= new Date();
		fechaFin.setDate(Integer.parseInt(subFecha2[0]));
		fechaFin.setMonth(Integer.parseInt(subFecha2[1]));
		fechaFin.setYear(Integer.parseInt(subFecha2[2]));
		Timestamp inicio = new Timestamp(fechaInicio.getTime());
		Timestamp fin = new Timestamp(fechaFin.getTime());
		
		List<RentabilidadRestaurante> lista = tm.darRentabilidadRestaurantes(inicio.getTime(), fin.getTime(), criterio, idProducto);
		return new ListaRentabilidad(lista);
	}
	
	public ListaRentabilidad getRemoteRentabilidades(String parametrosUnidos)throws Exception
	{
		return rentabilidadRestaurantesMQ.getRemoteRentabilidades(parametrosUnidos);
	}

	public ListaProductos getRemoteProductos(String string) throws Exception{
		return productosMQ.getRemoteProductos(string);
	}
	public static final int RESTAURANTE = 1;
	public static final int CATEGORIA = 2;
	public static final int RANGO_PRECIOS= 3;
	public static final int TIPO = 4;
	public ListaProductos getLocalProductos(String payload) throws Exception {
		String [] request = payload.split(",");
		if(request[0].equals("1")){
			return tm.darProductosv2aa(1487777232, 1587777232, "IDREST");
		}else if(request[0].equals("2")){
			return tm.darProductosv2aa(1487777232, 1587777232, "NOMBRE");
		}else if(request[0].equals("3")){
			return tm.darProductosv2aa(Long.parseLong(request[1]),Long.parseLong(request[2]), "NOMBRE");
		}else{
			return tm.darProductosv2aa(1487777232, 1587777232, "TIPO");
		}
	}

	public void eliminarRestauranteRemote(Long id) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, JMSException, IOException, NonReplyException, InterruptedException {
		eliminarRestaurantesMQ.retirarRemoteRestaurantes(""+id);
		
	}
	
	/*public ListaVideos getLocalVideos() throws Exception
	{
		return tm.darVideosLocal();
	}
	
	public ListaVideos getRemoteVideos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allVideosMQ.getRemoteVideos();
	}*/
}
