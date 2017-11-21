package generador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.csvreader.*;


import vos.*;
public class genUsuarios {
	public static final String datosH = "./data/hombres.csv";
	public static final String datosM = "./data/mujeres.csv";
	public static final String datosA = "./data/apellidos.csv";
	private static String nombre;
	private static String apellido;
	private static String email;
		public static void main(String[] args) throws Exception{
			genUsuarios gen = new genUsuarios();
			
			ArrayList<String> usuariosH = new ArrayList<String>();
			ArrayList<String> usuariosM = new ArrayList<String>();
			ArrayList<String> usuariosA = new ArrayList<String>();
			usuariosH = gen.dataExtractor(datosH);
			usuariosM = gen.dataExtractor(datosM);
			usuariosA = gen.dataExtractorA(datosA);
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			
			for (int i = 0; i <1000000; i++) {
				gen.getNombre(usuariosH, usuariosM, usuariosA);
				
				Usuario agregar = new Usuario(null, apellido, apellido, apellido, apellido);
				agregar.setId((long)i);
				agregar.setNombre(nombre+"_"+apellido);
				agregar.setCorreo(email);
				agregar.setIdentificacion("cedula");
				agregar.setTipoUsuario("comensal");
				System.out.println("Nombre: "+agregar.getNombre());

				System.out.println(i);
				usuarios.add(agregar);
			}
			
			gen.agregarCSV(usuarios);
			
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
		public void agregarCSV(ArrayList<Usuario> usuarios) {
			String outputFile = "C:\\Users\\t.kavanagh\\Desktop\\csv\\usuarios.csv";
			boolean alreadyExists = new File(outputFile).exists();

			if(alreadyExists){
				File Archivo = new File(outputFile);
				Archivo.delete();
			}        

			try {

				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

				csvOutput.write("ID");
				csvOutput.write("NOMBRE");
				csvOutput.write("IDENTIFICACION");
				csvOutput.write("CORREO");
				csvOutput.write("TIPO");
				csvOutput.endRecord();

				for(int i = 0; i<usuarios.size(); i++){
					Usuario user = usuarios.get(i);
					csvOutput.write(""+user.getId()+"");
					csvOutput.write(user.getNombre());
					csvOutput.write(user.getIdentificacion());
					csvOutput.write(user.getCorreo());
					csvOutput.write(user.getTipoUsuario());
					csvOutput.endRecord();                   
				}

				csvOutput.close();

			} catch (IOException e) {
				e.printStackTrace();
			}


		}

		
}
