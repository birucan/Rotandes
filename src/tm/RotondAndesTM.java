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
import dao.DAOTablaIngresos;
import dao.DAOTablaMenus;
import dao.DAOTablaPedidos;
import dao.DAOTablaPreferenciaClientes;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZonas;
import vos.Ingrediente;
import vos.Ingreso;
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

		public void aceptarPedido(long idRestaurante, long timestamp) throws Exception {
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			DAOTablaIngresos daoIngreso = new DAOTablaIngresos();

			try 
			{
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoIngreso.setConn(conn);
				daoPedido.aceptarPedido(timestamp);
				daoIngreso.addIngreso(daoPedido.buscarPedidoPorTimestamp(timestamp).getPrecio(), idRestaurante);

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
					daoIngreso.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public List<Zona> consultarZonas(String order) throws Exception {
			DAOTablaZonas daoZona = new DAOTablaZonas();
			List<Zona> foo = null;
			this.conn = darConexion();
			daoZona.setConn(conn);
			try {
				if(order.equals("ID")||order.equals("DESCRIPCION")){
				
					foo=daoZona.darZonaO(order);
				
				}else{
					foo=daoZona.darZonas();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					daoZona.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return foo;
		}

		public String darTodoUsuario(long idUsuario) throws Exception {
			DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
			DAOTablaPreferenciaClientes daoPreferencia = new DAOTablaPreferenciaClientes();
			DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
			String foo = "";
			
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoPreferencia.setConn(conn);
			daoPedidos.setConn(conn);
			
			try {
				foo += daoUsuarios.buscarUsuarioPorId(idUsuario).toString();
				foo += daoPreferencia.buscarPreferenciaClientePorId(idUsuario).toString();
				foo += daoPedidos.darPedidosUser(idUsuario).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					daoUsuarios.cerrarRecursos();
					daoPreferencia.cerrarRecursos();
					daoPedidos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return foo;
		}

		public String darProductoMas() throws SQLException  {
			DAOTablaMenus daoMenu = new DAOTablaMenus();
			DAOTablaProductos daoProd = new DAOTablaProductos();
			Producto mejor= null;
			
			
			try {
				this.conn = darConexion();
				daoMenu.setConn(conn);
				daoProd.setConn(conn);
				
				
				List<Menu> foo =daoMenu.darMenus();
				int mas[]=new int[daoProd.darProductos().size()];
				long actual=0;
				
				for(int a=0;a<foo.size();a++){
					for(int b=0;b<daoProd.darProductos().size();b++){
						actual =b;
					if(actual==foo.get(a).getAcom()||actual==foo.get(a).getBebida()||actual==foo.get(a).getEntrada()||actual==foo.get(a).getFuerte()||actual==foo.get(a).getPostre()){
						mas[b]++;
					}
					}
				}
				
				int max=0;
				for (int counter = 1; counter < mas.length; counter++)
				{
				     if (mas[counter] > max)
				     {
				      max = mas[counter];
				      mejor =daoProd.buscarProductoPorId((long)counter);
				     }
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					daoMenu.cerrarRecursos();
					daoProd.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return mejor.toString();
			
		}

		public String darRentabilidad(long idRestaurante, long timestampI, long timestampF) throws Exception {
			DAOTablaRestaurantes daoRest = new DAOTablaRestaurantes();
			DAOTablaPedidos daoPed = new DAOTablaPedidos();
			DAOTablaIngresos daoIng = new DAOTablaIngresos();
			String response = "";
			
			
			try {
				this.conn = darConexion();
				daoRest.setConn(conn);
				daoPed.setConn(conn);
				daoIng.setConn(conn);
				
				response += daoRest.buscarRestaurantePorId(idRestaurante);
				
				List<Pedido> pedialite = daoPed.darPedidos();
				
				for(int a=0; a<pedialite.size();a++){
					if(pedialite.get(a).getTimestamp()> timestampI && pedialite.get(a).getTimestamp()> timestampF && pedialite.get(a).getIdRestaurante() == idRestaurante){
						response += pedialite.get(a).toString();
					}
				}
				List<Ingreso> pedialite2 = daoIng.darIngresos();
				
				for(int a=0; a<pedialite2.size();a++){
					if(pedialite2.get(a).getIdRestaurante() == idRestaurante){
						response += pedialite.get(a).toString();
					}
				}
				
				
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					daoRest.cerrarRecursos();
					daoPed.cerrarRecursos();
					daoIng.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return response;
			
			
		}

		public void updateEquivalenciaI(int idRestaurante, int idProducto, String nombreI, Ingrediente ingrediente) throws SQLException {
			DAOTablaIngredientes daoIngrediente = new DAOTablaIngredientes();

			try 
			{
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				daoIngrediente.updateEquivalencia(nombreI, ingrediente.getEquivalencia());

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
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public void updateEquivalenciaP(int idRestaurante, int idProducto, Producto producto) throws SQLException {
			DAOTablaProductos daoProducto = new DAOTablaProductos();

			try 
			{
				this.conn = darConexion();
				daoProducto.setConn(conn);
				daoProducto.updateEquivalencia(idProducto, producto.getEquivalencia());

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
					daoProducto.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
			
		}

		public void surtir(int idRestaurante) throws SQLException {
			DAOTablaProductos daoProducto = new DAOTablaProductos();

			try 
			{
				this.conn = darConexion();
				daoProducto.setConn(conn);
				daoProducto.surtir(idRestaurante);

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
					daoProducto.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public void registrarPedidoE(int idUsuario, Pedido pedido, String acom, String beb, String entrada, String fuerte,
				String postre) throws Exception {
			pedido.setTimestamp(System.currentTimeMillis());
			pedido.setIdCliente(idUsuario);
			long tempPrecio=0;
			DAOTablaProductos daoproducto = new DAOTablaProductos();
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			DAOTablaMenus daoMenu = new DAOTablaMenus();
			boolean tiene = false;
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
					if(acom.equals("si")) {
						tiene = true;
						Producto tempA=daoproducto.buscarProductoPorId(menu.getAcom());
						menu.setAcom(tempA.getEquivalencia());
					}if(beb.equals("si")) {
						tiene = true;
						Producto tempB=daoproducto.buscarProductoPorId(menu.getBebida());
						menu.setBebida(tempB.getEquivalencia());
					}if(entrada.equals("si")) {
						tiene = true;
						Producto tempC=daoproducto.buscarProductoPorId(menu.getEntrada());
						menu.setEntrada(tempC.getEquivalencia());
					}if(fuerte.equals("si")) {
						tiene = true;
						Producto tempD=daoproducto.buscarProductoPorId(menu.getFuerte());
						menu.setFuerte(tempD.getEquivalencia());
					}if(acom.equals("si")) {
						tiene = true;
						Producto tempE=daoproducto.buscarProductoPorId(menu.getAcom());
						menu.setAcom(tempE.getEquivalencia());
					}if(acom.equals("si")) {
						tiene = true;
						Producto tempF=daoproducto.buscarProductoPorId(menu.getAcom());
						menu.setAcom(tempF.getEquivalencia());
					}
					daoMenu.updateTemp(menu);
					pedido.setIdMenu(menu.getId());
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

		public void aceptarPedidosMesa(long idMesa) throws Exception {
			DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			DAOTablaIngresos daoIngreso = new DAOTablaIngresos();
			DAOTablaProductos daoProductos = new DAOTablaProductos();
			DAOTablaMenus daoMenu = new DAOTablaMenus();

			
			try 
			{
				List<Pedido> pedidos;
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoIngreso.setConn(conn);
				daoPedidos.setConn(conn);
				daoProductos.setConn(conn);
				daoMenu.setConn(conn);
				pedidos=daoPedidos.darPedidosMesa(idMesa);
				System.out.println(pedidos.get(0).getTimestamp());
				
				long timestamp;
				long idRestaurante;
				
				for (int i = 0; i < pedidos.size(); i++) {
					timestamp = pedidos.get(i).getTimestamp();
					idRestaurante = pedidos.get(i).getIdRestaurante();
					daoPedido.aceptarPedido(timestamp);
					daoIngreso.addIngreso(daoPedido.buscarPedidoPorTimestamp(timestamp).getPrecio(), idRestaurante);
					daoProductos.consumir(pedidos.get(i).getIdProducto());
					
					Menu menu = daoMenu.buscarMenuPorId(pedidos.get(i).getIdMenu());
				
					
					daoProductos.consumir(menu.getAcom());
					daoProductos.consumir(menu.getBebida());
					daoProductos.consumir(menu.getEntrada());
					daoProductos.consumir(menu.getFuerte());
					daoProductos.consumir(menu.getPostre());

				
					
				}
					
				
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
					daoIngreso.cerrarRecursos();
					daoPedidos.cerrarRecursos();
					daoProductos.cerrarRecursos();
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

		public void eliminarPedido(long idRestaurante, long timestamp) throws Exception {
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			

			try 
			{
				this.conn = darConexion();
				daoPedido.setConn(conn);
				//!daoPedido.buscarPedidoPorTimestamp( timestamp).isAceptado() && 
				if(daoPedido.buscarPedidoPorTimestamp(timestamp).getIdRestaurante()==idRestaurante) {
					daoPedido.eliminar(timestamp);
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
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

		public List<Pedido> darPedidos(long idUsuario, String parametro) throws SQLException {
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			

			try 
			{
				this.conn = darConexion();
				daoPedido.setConn(conn);
					
				List<Pedido> returner =daoPedido.darPedidosP(idUsuario, parametro);
				return returner;
				
				
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
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
			
		}

		public List<Pedido> ventasProducto(long idRestaurante, long idProducto) throws Exception {
			DAOTablaPedidos daoPedido = new DAOTablaPedidos();
			

			try 
			{
				this.conn = darConexion();
				daoPedido.setConn(conn);
					
				List<Pedido> returner =daoPedido.darPedidosProd(idProducto, idRestaurante);
				return returner;
				
				
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
