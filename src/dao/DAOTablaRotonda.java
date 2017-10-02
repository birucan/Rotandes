
package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 */
public class DAOTablaRotonda {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORotonda
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRotonda() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los Rotondas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RotondaS;
	 * @return Arraylist con los Rotondas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Rotonda> darRotondas() throws SQLException, Exception {
		ArrayList<Rotonda> Rotondas = new ArrayList<Rotonda>();

		String sql = "SELECT * FROM Rotonda";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			Long id = rs.getLong("ID");
			Rotondas.add(new Rotonda(id, name));
		}
		return Rotondas;
	}


	/**
	 * Metodo que busca el/los Rotondas con el nombre que entra como parametro.
	 * @param name - Nombre de el/los Rotondas a buscar
	 * @return ArrayList con los Rotondas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Rotonda> buscarRotondasPorName(String name) throws SQLException, Exception {
		ArrayList<Rotonda> Rotondas = new ArrayList<Rotonda>();

		String sql = "SELECT * FROM Rotonda WHERE NAME ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			Long id = rs.getLong("ID");
			Rotondas.add(new Rotonda(id, name2));
		}

		return Rotondas;
	}
	
	/**
	 * Metodo que busca el Rotonda con el id que entra como parametro.
	 * @param name - Id de el Rotonda a buscar
	 * @return Rotonda encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Rotonda buscarRotondaPorId(Long id) throws SQLException, Exception 
	{
		Rotonda Rotonda = null;

		String sql = "SELECT * FROM Rotonda WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name = rs.getString("NAME");
			Long id2 = rs.getLong("ID");
			Rotonda = new Rotonda(id2, name);
		}

		return Rotonda;
	}

	/**
	 * Metodo que agrega el Rotonda que entra como parametro a la base de datos.
	 * @param Rotonda - el Rotonda a agregar. Rotonda !=  null
	 * <b> post: </b> se ha agregado el Rotonda a la base de datos en la transaction actual. pendiente que el Rotonda master
	 * haga commit para que el Rotonda baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Rotonda a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRotonda(Rotonda Rotonda) throws SQLException, Exception {

		String sql = "INSERT INTO Rotonda VALUES (";
		sql += Rotonda.getId() + ",'";
		sql += Rotonda.getName() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el Rotonda que entra como parametro en la base de datos.
	 * @param Rotonda - el Rotonda a actualizar. Rotonda !=  null
	 * <b> post: </b> se ha actualizado el Rotonda en la base de datos en la transaction actual. pendiente que el Rotonda master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Rotonda.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRotonda(Rotonda Rotonda) throws SQLException, Exception {

		String sql = "UPDATE Rotonda SET ";
		sql += "NAME='" + Rotonda.getName() + "',";
		sql += " WHERE ID = " + Rotonda.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el Rotonda que entra como parametro en la base de datos.
	 * @param Rotonda - el Rotonda a borrar. Rotonda !=  null
	 * <b> post: </b> se ha borrado el Rotonda en la base de datos en la transaction actual. pendiente que el Rotonda master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Rotonda.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRotonda(Rotonda Rotonda) throws SQLException, Exception {

		String sql = "DELETE FROM Rotonda";
		sql += " WHERE ID = " + Rotonda.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
