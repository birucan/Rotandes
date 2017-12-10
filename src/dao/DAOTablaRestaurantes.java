package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.ProductoLocal;
import vos.RentabilidadRestaurante;
import vos.Restaurante;
import vos.RestauranteOLD;

public class DAOTablaRestaurantes {
	
	public final static Integer NINGUNO = 0;
	public final static Integer CATEGORIA = 1;
	public final static Integer PRODUCTO = 2;
	public final static Integer ZONA = 3;
	


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurante
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurantes() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los Restaurantes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RestauranteS;
	 * @return Arraylist con los Restaurantes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<RestauranteOLD> darRestaurantes() throws SQLException, Exception {
		ArrayList<RestauranteOLD> Restaurantes = new ArrayList<RestauranteOLD>();

		String sql = "SELECT * FROM Restaurante";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String a = rs.getString("DESCRIPCION");
			String b = rs.getString("TIPOCOMIDA");
			String c = rs.getString("PAGINAWEB");
			Long idz = rs.getLong("ZONA");
			Restaurantes.add(new RestauranteOLD(id, name, a, b, c, idz));
		}
		return Restaurantes;
	}


	/**
	 * Metodo que busca el/los Restaurantes con el nombre que entra como parametro.
	 * @param name - Nombre de el/los Restaurantes a buscar
	 * @return ArrayList con los Restaurantes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<RestauranteOLD> buscarRestaurantesPorName(String name) throws SQLException, Exception {
		ArrayList<RestauranteOLD> Restaurantes = new ArrayList<RestauranteOLD>();

		String sql = "SELECT * FROM Restaurante WHERE NAME ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			//String name2 = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String a = rs.getString("DESCRIPCION");
			String b = rs.getString("TIPOCOMIDA");
			String c = rs.getString("PAGINAWEB");
			Long idz = rs.getLong("ZONA");
			Restaurantes.add(new RestauranteOLD(id, name, a, b, c, idz));
		}

		return Restaurantes;
	}
	
	/**
	 * Metodo que busca el Restaurante con el id que entra como parametro.
	 * @param name - Id de el Restaurante a buscar
	 * @return Restaurante encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public RestauranteOLD buscarRestaurantePorId(Long id) throws SQLException, Exception 
	{
		RestauranteOLD Restaurante = null;

		String sql = "SELECT * FROM Restaurante WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name = rs.getString("NOMBRE");
			Long id2 = rs.getLong("ID");
			String a = rs.getString("DESCRIPCION");
			String b = rs.getString("TIPOCOMIDA");
			String c = rs.getString("PAGINAWEB");
			Long idz = rs.getLong("ZONA");
			Restaurante = new RestauranteOLD(id2, name, a, b, c, idz);
		}

		return Restaurante;
	}

	/**
	 * Metodo que agrega el Restaurante que entra como parametro a la base de datos.
	 * @param Restaurante - el Restaurante a agregar. Restaurante !=  null
	 * <b> post: </b> se ha agregado el Restaurante a la base de datos en la transaction actual. pendiente que el Restaurante master
	 * haga commit para que el Restaurante baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Restaurante a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(RestauranteOLD Restaurante) throws SQLException, Exception {


		 String sql = "INSERT INTO Restaurante VALUES (";
		  sql += Restaurante.getId() + ", '";
		  sql += Restaurante.getNombre() +"', '";
		  sql += Restaurante.getDescripcion()+"', '";
		  sql += Restaurante.getTipoComida()+"', '";
		  sql += Restaurante.getPaginaWeb()+"')";
		


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el Restaurante que entra como parametro en la base de datos.
	 * @param Restaurante - el Restaurante a actualizar. Restaurante !=  null
	 * <b> post: </b> se ha actualizado el Restaurante en la base de datos en la transaction actual. pendiente que el Restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(RestauranteOLD Restaurante) throws SQLException, Exception {

		String sql = "UPDATE Restaurante SET ";
		sql += "NAME='" + Restaurante.getNombre() + "',";
		sql += " WHERE ID = " + Restaurante.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Restaurante que entra como parametro en la base de datos.
	 * @param Restaurante - el Restaurante a borrar. Restaurante !=  null
	 * <b> post: </b> se ha borrado el Restaurante en la base de datos en la transaction actual. pendiente que el Restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(RestauranteOLD Restaurante) throws SQLException, Exception {

		String sql = "DELETE FROM Restaurante";
		sql += " WHERE ID = " + Restaurante.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//iteracion 5
	public List<Long> darRestaurantesZona(long idZona) throws Exception{
		RestauranteOLD Restaurante = null;
		List<Long> returner = new ArrayList();

		String sql = "SELECT * FROM Restaurante WHERE ZONA =" + idZona;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id2 = rs.getLong("ID");
			returner.add(id2);
		}

		return returner;
	}
	
	public List<RentabilidadRestaurante> darRentabilidadDeRestaurantes(long fecha1, long fecha2, Integer criterio, Long idProducto)throws SQLException, Exception
	{
		String sql;
		List<RentabilidadRestaurante> respuesta = new ArrayList<RentabilidadRestaurante>();
		List<RestauranteOLD> restaurantes = darRestaurantes();
			for (int i = 0; i < restaurantes.size(); i++) {
				sql = "SELECT * FROM RESTAURANTE WHERE ID = "+i;
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next())
				{
					//ingreso
					String sql1 = "SELECT * FROM INGRESO WHERE IDRESTAURANTE = "+ i;
					PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
					recursos.add(prepStmt);
					ResultSet rs1 = prepStmt.executeQuery();
					Double rentabilidad = (double) 0;
					Double ingresos = (double) 0;
					Double gastos = (double) 0;
					while(rs1.next()) {
						ingresos = (rs.getDouble("BALANCE"));
						gastos = (ingresos/3)*2;
						rentabilidad = ingresos/3;
					}
					
					Restaurante restaurante = new Restaurante((long) i, rs.getString("NOMBRE"), rs.getString("PAGINAWEB"), null, null, gastos, null); 
					String categoria =  "";
					
					//cant productos
					String sql2 = "SELECT COUNT(TIMESTAMP) FROM PEDIDO WHERE IDRESTAURANTE = "+i+" AND TIMESTAMP > "+fecha1+" AND TIMESTAMP < "+fecha2;
					PreparedStatement prepStmt2 = conn.prepareStatement(sql1);
					recursos.add(prepStmt);
					ResultSet rs2 = prepStmt.executeQuery();
					Integer cantidadPedidos = null;
					while(rs1.next()) {
						cantidadPedidos= (int) rs.getLong("COUNT(TIMESTAMP)");
					}

					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, categoria, null, null));
				}
			}

		
//		else if(criterio.equals(PRODUCTO))
//		{
//			if(idProducto == null)
//			{
//				sql = "";
//				PreparedStatement prepStmt = conn.prepareStatement(sql);
//				recursos.add(prepStmt);
//				ResultSet rs = prepStmt.executeQuery();
//				while(rs.next())
//				{
//					
//					Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
//					Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
//					Producto producto = darProducto(rs.getLong("ID_PROD"), rs.getLong("ID_REST"));
//					Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
//					Double ingresos = rs.getDouble("INGRESOS");
//					Double gastos = rs.getDouble("GASTOS");
//					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, producto, null));
//				}
//			}
//			else
//			{
//				sql = "";
//				PreparedStatement prepStmt = conn.prepareStatement(sql);
//				recursos.add(prepStmt);
//				ResultSet rs = prepStmt.executeQuery();
//				while(rs.next())
//				{
//					
//					Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
//					Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
//					Producto producto = darProducto(rs.getLong("ID_PROD"), rs.getLong("ID_REST"));
//					Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
//					Double ingresos = rs.getDouble("INGRESOS");
//					Double gastos = rs.getDouble("GASTOS");
//					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, producto, null));
//				}
//			}
//		}
//		else if(criterio.equals(ZONA))
//		{
//			sql = "";
//			PreparedStatement prepStmt = conn.prepareStatement(sql);
//			recursos.add(prepStmt);
//			ResultSet rs = prepStmt.executeQuery();
//			while(rs.next())
//			{
//				
//				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
//				
//				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
//				Double ingresos = rs.getDouble("INGRESOS");
//				Double gastos = rs.getDouble("GASTOS");
//				Zona zona = darZona(rs.getLong("ZONA"));
//				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, null, null, null, zona));
//			}
//		}
//		else
//		{
//			sql = ""
//			PreparedStatement prepStmt = conn.prepareStatement(sql);
//			recursos.add(prepStmt);
//			ResultSet rs = prepStmt.executeQuery();
//			while(rs.next())
//			{
//				
//				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
//				Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
//				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
//				Double ingresos = rs.getDouble("INGRESOS");
//				Double gastos = rs.getDouble("GASTOS");
//				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, null, null));
//			}
//		}
		return respuesta;
	}
	
}
