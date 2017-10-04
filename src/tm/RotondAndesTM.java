/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: rotondaAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaIngredientes;
import dao.DAOTablaMenus;
import dao.DAOTablaPedidos;
import dao.DAOTablaPreferenciaClientes;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZonas;
import vos.Ingrediente;
import vos.Menu;
import vos.Pedido;
import vos.PreferenciaCliente;
import vos.Producto;
import vos.Restaurante;
import vos.Usuario;
import vos.Zona;

/**
 * Transaction Manager de la aplicacion (TM)
 * Fachada en patron singleton de la aplicacion
 * @author Monitores 2017-20
 */
public class RotondAndesTM {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase rotondaAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto rotondaAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

		/**
		 * RF1 y RF2
		 * @param usuario
		 * @throws Exception
		 */
		public void registrarUsuario(Usuario usuario) throws Exception{
			
			DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
			try 
			{
				
				this.conn = darConexion();
				daoUsuario.setConn(conn);
				daoUsuario.addUsuario(usuario);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoUsuario.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
	}
		/**
		 * 
		 * @param idUsuario
		 */
		public void makeAdmim(int idUsuario) throws SQLException{
				DAOTablaUsuarios daoTablaUsuarios = new DAOTablaUsuarios();
				try 
				{
					this.conn = darConexion();
					daoTablaUsuarios.setConn(conn);
					daoTablaUsuarios.makeAdmin(idUsuario);
					conn.commit();
				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					conn.rollback();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					conn.rollback();
					throw e;
				} finally {
					try {
						daoTablaUsuarios.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
		/*
		 * RF3
		 */
		public void registrarRestaurante(Restaurante foo) throws Exception {
			DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
			try 
			{
				
				this.conn = darConexion();
				daoRestaurantes.setConn(conn);
				daoRestaurantes.addRestaurante(foo);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}
		/*
		 * RF4
		 */
		public void registrarProducto(int idRestaurante, Producto producto) throws Exception {
			DAOTablaProductos daoProductos = new DAOTablaProductos();
			try 
			{
				
				this.conn = darConexion();
				daoProductos.setConn(conn);
				System.out.println("aca 1");
				daoProductos.addProducto(producto);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoProductos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		public void registrarIngrediente(int idRestaurante, int idProducto, Ingrediente ingrediente) throws Exception {
			DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
			try 
			{
				
				this.conn = darConexion();
				daoIngredientes.setConn(conn);
				System.out.println("aca 1");
				daoIngredientes.addIngrediente(ingrediente);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngredientes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public void registrarMenu(int idRestaurante, Menu menu) throws Exception {
			DAOTablaMenus daoMenus = new DAOTablaMenus();
			try 
			{
				
				this.conn = darConexion();
				daoMenus.setConn(conn);
				System.out.println("aca 1");
				daoMenus.addMenu(menu);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoMenus.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public void registrarZona(int idUsuario, Zona zona) throws Exception {
			DAOTablaZonas daoZonas = new DAOTablaZonas();
			try 
			{
				Usuario foo;
				//DAOTablaUsuarios fee = new DAOTablaUsuarios();
//				foo = fee.buscarUsuarioPorId((long)idUsuario);
//				if(!foo.getTipoUsuario().contains("admin")){
//					return;
//				}
				//TODO THIS SHIT
				this.conn = darConexion();
				daoZonas.setConn(conn);
				daoZonas.addZona(zona);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZonas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public void registrarPreferencia(int idUsuario, PreferenciaCliente preferencia) throws Exception {
			DAOTablaPreferenciaClientes daoPreferencia = new DAOTablaPreferenciaClientes();
			try 
			{

				this.conn = darConexion();
				daoPreferencia.setConn(conn);
				preferencia.setIdClient(idUsuario);
				daoPreferencia.addPreferenciaCliente(preferencia);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPreferencia.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public void quitarPreferencia(int idUsuario, PreferenciaCliente preferencia) throws Exception {
			DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
			DAOTablaPreferenciaClientes daoPreferencias = new DAOTablaPreferenciaClientes();
			try 
			{
				this.conn = darConexion();
				daoPreferencias.setConn(conn);
				daoUsuarios.setConn(conn);
				if(preferencia.getIdClient()== idUsuario){
					daoPreferencias.deletePreferenciaCliente(preferencia);
					conn.commit();
				}else{
					throw new Exception ("usuario no le pertenece preferencia");
				}
				

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPreferencias.cerrarRecursos();
					daoUsuarios.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		public void registrarPedido(int idUsuario, Pedido pedido) throws Exception {
			pedido.setTimestamp(System.currentTimeMillis());
			pedido.setIdCliente(idUsuario);
			long tempPrecio=0;
			DAOTablaProductos daoproducto = new DAOTablaProductos();
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			DAOTablaMenus daoMenu = new DAOTablaMenus();
			try 
			{
				
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoproducto.setConn(conn);
				daoMenu.setConn(conn);
				if(daoproducto.buscarProductoPorId(pedido.getIdProducto())!=null){
					Producto producto = daoproducto.buscarProductoPorId((long)pedido.getIdProducto());
					tempPrecio+=producto.getPrecio();
				}
				if(daoMenu.buscarMenuPorId(pedido.getIdMenu())!=null){
					Menu menu = daoMenu.buscarMenuPorId((long) pedido.getIdMenu());
					tempPrecio+=menu.getPrecio();
				}
				pedido.setPrecio(tempPrecio);
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPedido.cerrarRecursos();
					daoproducto.cerrarRecursos();
					daoMenu.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
			
		}



}
