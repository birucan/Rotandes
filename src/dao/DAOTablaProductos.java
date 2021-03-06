package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ProductoOLD;

public class DAOTablaProductos {



	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaProductos() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los Productos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ProductoS;
	 * @return Arraylist con los Productos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ProductoOLD> darProductos() throws SQLException, Exception {
		ArrayList<ProductoOLD> Productos = new ArrayList<ProductoOLD>();

		String sql = "SELECT * FROM Producto";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String name = rs.getString("NOMBRE");
			String a = rs.getString("DESCRIPCIONES");
			String b = rs.getString("DESCRIPCIONEN");
			String c = rs.getString("TIPO");
			Long d = rs.getLong("IDREST");
			Long e = rs.getLong("PRECIO");
			Long f = rs.getLong("EQUIVALENCIA");
			Long g = rs.getLong("CANTIDAD");
			Long h = rs.getLong("MAXIMO");
			Productos.add(new ProductoOLD(id, name, a, b, c, d, e, f, g, h));
		}
		return Productos;
	}


	/**
	 * Metodo que busca el/los Productos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los Productos a buscar
	 * @return ArrayList con los Productos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ProductoOLD> buscarProductosPorName(String name) throws SQLException, Exception {
		ArrayList<ProductoOLD> Productos = new ArrayList<ProductoOLD>();

		String sql = "SELECT * FROM Producto WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String name2 = rs.getString("NOMBRE");
			String a = rs.getString("DESCRIPCIONES");
			String b = rs.getString("DESCRIPCIONEN");
			String c = rs.getString("TIPO");
			Long d = rs.getLong("IDREST");
			Long e = rs.getLong("PRECIO");
			Long f = rs.getLong("EQUIVALENCIA");
			Long g = rs.getLong("CANTIDAD");
			Long h = rs.getLong("MAXIMO");
			Productos.add(new ProductoOLD(id, name, a, b, c, d, e, f, g, h));
		}

		return Productos;
	}
	
	/**
	 * Metodo que busca el Producto con el id que entra como parametro.
	 * @param name - Id de el Producto a buscar
	 * @return Producto encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ProductoOLD buscarProductoPorId(Long id) throws SQLException, Exception 
	{
		ProductoOLD Producto = null;

		String sql = "SELECT * FROM Producto WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id2 = rs.getLong("ID");
			String name = rs.getString("NOMBRE");
			String a = rs.getString("DESCRIPCIONES");
			String b = rs.getString("DESCRIPCIONEN");
			String c = rs.getString("TIPO");
			Long d = rs.getLong("IDREST");
			Long e = rs.getLong("PRECIO");
			Long f = rs.getLong("EQUIVALENCIA");
			Long g = rs.getLong("CANTIDAD");
			Long h = rs.getLong("MAXIMO");
			Producto = new ProductoOLD(id2, name, a, b, c, d, e, f, g, h);
		}

		return Producto;
	}

	/**
	 * Metodo que agrega el Producto que entra como parametro a la base de datos.
	 * @param Producto - el Producto a agregar. Producto !=  null
	 * <b> post: </b> se ha agregado el Producto a la base de datos en la transaction actual. pendiente que el Producto master
	 * haga commit para que el Producto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Producto a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProducto(ProductoOLD Producto) throws SQLException, Exception {
		//TODO fix this by adding the new parameters to the sentence

		 String sql = "INSERT INTO Producto VALUES (";
		  sql += Producto.getId() + ", '";
		  sql += Producto.getNombre() +"', '";
		  sql += Producto.getDescripcionES()+"', '";
		  sql += Producto.getDescripcionEN()+"', '";
		  sql += Producto.getTipo()+"',";
		  sql += Producto.getIdRest()+",";
		  sql += Producto.getPrecio()+",";
		  sql += Producto.getEquivalencia()+")";
		


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el Producto que entra como parametro en la base de datos.
	 * @param Producto - el Producto a actualizar. Producto !=  null
	 * <b> post: </b> se ha actualizado el Producto en la base de datos en la transaction actual. pendiente que el Producto master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Producto.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateProducto(ProductoOLD Producto) throws SQLException, Exception {

		String sql = "UPDATE Producto SET ";
		sql += "NAME='" + Producto.getNombre() + "',";
		sql += " WHERE ID = " + Producto.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Producto que entra como parametro en la base de datos.
	 * @param Producto - el Producto a borrar. Producto !=  null
	 * <b> post: </b> se ha borrado el Producto en la base de datos en la transaction actual. pendiente que el Producto master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Producto.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteProducto(ProductoOLD Producto) throws SQLException, Exception {

		String sql = "DELETE FROM Producto";
		sql += " WHERE ID = " + Producto.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void updateEquivalencia(int idProducto, long equivalencia) throws SQLException {
		String sql = "UPDATE PRODUCTO SET ";
		sql += "EQUIVALENCIA = " + equivalencia;
		sql += " WHERE ID = " + idProducto;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
	}

	public void surtir(int idRestaurante) throws SQLException {
		String sql = "UPDATE PRODUCTO SET ";
		sql += "CANTIDAD = " + "MAXIMO";
		sql += " WHERE IDREST = " + idRestaurante;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		
	}
	public void consumir(long l) throws Exception{
		String sql = "UPDATE PRODUCTO SET ";
		sql += "CANTIDAD = CANTIDAD - 1";
		sql += " WHERE ID = " + l;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}