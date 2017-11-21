package generador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import vos.Pedido;
import vos.Producto;
import vos.Usuario;

public class genPedido {
	public static final String datosH = "./data/hombres.csv";
	public static final String datosM = "./data/mujeres.csv";
	public static final String datosA = "./data/apellidos.csv";
	public static final String datosC = "./data/foods.csv";
	private static String nombre;
	private static String apellido;
	private static String email;
		public static void main(String[] args) throws Exception{
			genPedido gen = new genPedido();
			
			ArrayList<String> usuariosH = new ArrayList<String>();
			ArrayList<String> usuariosM = new ArrayList<String>();
			ArrayList<String> usuariosA = new ArrayList<String>();
			ArrayList<String> comida = new ArrayList<String>();
			usuariosH = gen.dataExtractor(datosH);
			usuariosM = gen.dataExtractor(datosM);
			usuariosA = gen.dataExtractorA(datosA);
			comida = gen.dataExtractorC(datosC);
			ArrayList<Pedido> pedido = new ArrayList<Pedido>();
			
			for (int i = 0; i <1000000; i++) {
				gen.getNombre(usuariosH, usuariosM, usuariosA);
				
				Random ran = new Random();
				Pedido agregar = new Pedido(i, i, i, i, i, false, i, i);
				agregar.setAceptado(false);
				agregar.setIdCliente(1+ran.nextInt(999999));
				agregar.setIdMenu(0);
				agregar.setIdMesa(0);
				ran = new Random();
				agregar.setIdProducto(1+ran.nextInt(9999));
				agregar.setIdRestaurante(1+ran.nextInt(99));
				agregar.setPrecio((long) Math.random() * 50001);
				agregar.setTimestamp((long) (1479666273+(Math.random() * 31556926)));
				agregar.setPrecio((long) (Math.random() * 50000));
				System.out.println("timestamp: "+agregar.getTimestamp()+"\n producto: "+agregar.getIdProducto()+"\n Restaurante: "+agregar.getIdRestaurante());

				System.out.println(i);
				pedido.add(agregar);
			}
			
			gen.agregarCSV(pedido);
			
		}
		
		private ArrayList<String> dataExtractorC(String data) throws Exception {
			CsvReader csv = new CsvReader(data);
			csv.readHeaders();
			ArrayList<String> returner = new ArrayList<String>();
			int aa = 0;
			while(csv.readRecord()) {
				aa++;
				System.out.println(aa);
				String nombree = csv.get("name");
				System.out.println(nombree);
				returner.add(nombree);
				System.out.println(nombree);
			}
			return returner;
		}
		
		private static String genComida(ArrayList<String> comida) {
			long tamano = comida.size();
			tamano--;
			Random rn = new Random();
			int answer = rn.nextInt((int) tamano) + 1;
			return comida.get(answer);
		}
		
		private ArrayList dataExtractor(String data) throws Exception {
			CsvReader csv = new CsvReader(data);
			csv.readHeaders();
			ArrayList<String> returner = new ArrayList<String>();
			int aa = 0;
			while(csv.readRecord()) {
				aa++;
				System.out.println(aa);
				String nombree = csv.get("nombre");
				System.out.println(nombree);
				returner.add(nombree);
				System.out.println(nombree);
			}
			return returner;
		} 
		

		
		private ArrayList dataExtractorA(String data) throws Exception {
			CsvReader csv = new CsvReader(data);
			csv.readHeaders();
			ArrayList<String> returner = new ArrayList<String>();
			while(csv.readRecord()) {
				String nombre = csv.get("apellido");
				returner.add(nombre);
				System.out.println(nombre);
			}
			return returner;
		} 
		private void getNombre2(ArrayList<String> usuariosH, ArrayList<String> usuariosM, ArrayList<String> usuariosA) {
			int tamanoH =  usuariosH.size();
			int tamanoM =  usuariosM.size();
			int tamanoA =  usuariosA.size();
				if(Math.random()*2==2) {
					String nombreT = usuariosH.get((int) (Math.random()*tamanoH));
					nombreT.trim();
					nombreT = nombreT.replace(' ', '_');
					nombre = nombreT;
					
					
					String nombreA = usuariosA.get((int) (Math.random()*tamanoA));
					nombreA.trim();
					nombreA = nombreA.replace(' ', '_');
					apellido = nombreA;
					
					email = nombre+apellido+"@"+"gmail.com";
					
					
					
				}else {
					String nombreT = usuariosM.get((int) (Math.random()*tamanoM));
					nombreT.trim();
					nombreT = nombreT.replace(' ', '_');
					nombre = nombreT;
					
					
					String nombreA = usuariosA.get((int) (Math.random()*tamanoA));
					nombreA.trim();
					nombreA = nombreA.replace(' ', '_');
					apellido = nombreA;
					
					email = nombre+apellido+"@"+"gmail.com";
				}
				
			}
		
		
		private void getNombre(ArrayList<String> usuariosH, ArrayList<String> usuariosM, ArrayList<String> usuariosA) {
			int tamanoH =  usuariosH.size();
			int tamanoM =  usuariosM.size();
			int tamanoA =  usuariosA.size();
				if(Math.random()*2==2) {
					String nombreT = usuariosH.get((int) (Math.random()*tamanoH));
					nombreT.trim();
					nombreT = nombreT.replace(' ', '_');
					nombre = nombreT;
					
					
					String nombreA = usuariosA.get((int) (Math.random()*tamanoA));
					nombreA.trim();
					nombreA = nombreA.replace(' ', '_');
					apellido = nombreA;
					
					email = nombre+apellido+"@"+"gmail.com";
					
					
					
				}else {
					String nombreT = usuariosM.get((int) (Math.random()*tamanoM));
					nombreT.trim();
					nombreT = nombreT.replace(' ', '_');
					nombre = nombreT;
					
					
					String nombreA = usuariosA.get((int) (Math.random()*tamanoA));
					nombreA.trim();
					nombreA = nombreA.replace(' ', '_');
					apellido = nombreA;
					
					email = nombre+apellido+"@"+"gmail.com";
				}
				
			}
		public void agregarCSV(ArrayList<Pedido> pedido) {
			String outputFile = "C:\\Users\\t.kavanagh\\Desktop\\csv\\pedidos.csv";
			boolean alreadyExists = new File(outputFile).exists();

			if(alreadyExists){
				File Archivo = new File(outputFile);
				Archivo.delete();
			}        

			try {

				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

				csvOutput.write("TIMESTAMP");
				csvOutput.write("IDCLIENTE");
				csvOutput.write("IDRESTAURANTE");
				csvOutput.write("IDPRODUCTO");
				csvOutput.write("IDMENU");
				csvOutput.write("ACEPTADO");
				csvOutput.write("PRECIO");
				csvOutput.write("IDMESA");
				csvOutput.endRecord();

				for(int i = 0; i<pedido.size(); i++){
					Pedido prod = pedido.get(i);
					csvOutput.write(""+prod.getTimestamp());
					csvOutput.write(""+prod.getIdCliente());
					csvOutput.write(""+prod.getIdRestaurante());
					csvOutput.write(""+prod.getIdProducto());
					csvOutput.write(""+prod.getIdMenu());
					csvOutput.write(""+0);
					csvOutput.write(""+prod.getPrecio());
					csvOutput.write(""+0);
					csvOutput.endRecord();                   
				}

				csvOutput.close();

			} catch (IOException e) {
				e.printStackTrace();
			}


		}

		
}
