package generador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.csvreader.*;


import vos.*;
public class genRestaurantes {
	public static final String datosH = "./data/hombres.csv";
	public static final String datosM = "./data/mujeres.csv";
	public static final String datosA = "./data/apellidos.csv";
	public static final String datosC = "./data/foods.csv";
	private static String nombre;
	private static String apellido;
	private static String email;
		public static void main(String[] args) throws Exception{
			genRestaurantes gen = new genRestaurantes();
			
			ArrayList<String> usuariosH = new ArrayList<String>();
			ArrayList<String> usuariosM = new ArrayList<String>();
			ArrayList<String> usuariosA = new ArrayList<String>();
			ArrayList<String> comida = new ArrayList<String>();
			usuariosH = gen.dataExtractor(datosH);
			usuariosM = gen.dataExtractor(datosM);
			usuariosA = gen.dataExtractorA(datosA);
			comida = gen.dataExtractorC(datosC);
			
			ArrayList<RestauranteOLD> usuarios = new ArrayList<RestauranteOLD>();
			
			for (int i = 0; i <100; i++) {
				gen.getNombre(usuariosH, usuariosM, usuariosA);
				RestauranteOLD agregar = new RestauranteOLD (i, apellido, apellido, apellido, apellido, i);
				agregar.setId((long)i);
				agregar.setNombre(nombre +"\'s "+gen.genComida(comida));
				agregar.setDescripcion("descripcion");
				agregar.setPaginaWeb("www."+agregar.getNombre()+".com");
				agregar.setTipoComida("tipoComida");
				System.out.println("Nombre: "+agregar.getNombre());

				System.out.println(i);
				usuarios.add(agregar);
			}
			
			gen.agregarCSV(usuarios);
			
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
		public void agregarCSV(ArrayList<RestauranteOLD> usuarios) {
			String outputFile = "C:\\Users\\t.kavanagh\\Desktop\\csv\\restaurante.csv";
			boolean alreadyExists = new File(outputFile).exists();

			if(alreadyExists){
				File Archivo = new File(outputFile);
				Archivo.delete();
			}        

			try {

				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

				csvOutput.write("ID");
				csvOutput.write("NOMBRE");
				csvOutput.write("DESCRIPCION");
				csvOutput.write("TIPOCOMIDA");
				csvOutput.write("PAGINAWEB");
				csvOutput.write("ZONA");
				csvOutput.endRecord();

				for(int i = 0; i<usuarios.size(); i++){
					RestauranteOLD user = usuarios.get(i);
					csvOutput.write(""+user.getId()+"");
					csvOutput.write(user.getNombre());
					csvOutput.write(user.getDescripcion());
					csvOutput.write("tipoComdia");
					csvOutput.write(""+1);
					csvOutput.endRecord();                   
				}

				csvOutput.close();

			} catch (IOException e) {
				e.printStackTrace();
			}


		}

		
}
