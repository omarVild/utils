package utils.fileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Permite dividir un archivo de texto en multiples archivos de tamaño aproximadamente igual,
 *  la división se hace por numero de lineas, de esta menera no se cortan las palabras o lineas en si.
 * @author Omar Villalpando
 *
 */
public class Spliter {
	
	
	/**
	 * Contiene el listado de nombres de archivos resultantes de dividir el archivo
	 */
	private List<String> archivos = new ArrayList<>();
	
	
	/**
	 * Divide el archivo en partes
	 * @param inputfile  archivo a dividir
	 * @param numberFiles numero de partes a dividir el archivo principal
	 * @throws IOException
	 */
	public void splitFile(Path inputfile, int numberFiles) throws IOException {
		System.out.println("Hora y fecha: "+ new Date());
		
		double numberLine = contadorLineas(inputfile.toString());
		int numeroLineasArchivo = 0;
		Double numeroLineasTMP = numberLine / numberFiles;
		if(numeroLineasTMP == Math.floor(numeroLineasTMP) ){
			numeroLineasArchivo = numeroLineasTMP.intValue();
		}else{
			numeroLineasArchivo = numeroLineasTMP.intValue()+1;
		}
		
		int sufijo=1;
		OutputStream os = new BufferedOutputStream(new FileOutputStream(inputfile.getParent().toString() + "/"+ sufijo));
		int lineaTMP = 0;
		archivos.add(inputfile.getParent().toString() + "/"+ sufijo);
		InputStream is = new BufferedInputStream(new FileInputStream(inputfile.toString()));
		byte[] c = new byte[1024];
		int chars = 0;
		while ((chars = is.read(c)) != -1) {
			for (int i = 0; i < chars; ++i) {
				os.write(c[i]);
				if (c[i] == '\n') {
					if (numeroLineasArchivo == lineaTMP) {
						os.close();
						sufijo++;
						String nombreArchivo =inputfile.getParent().toString() + "/"+ sufijo;
						os = new BufferedOutputStream( new BufferedOutputStream(new FileOutputStream(nombreArchivo)));
						archivos.add(nombreArchivo);
						lineaTMP = 0;
					}
					lineaTMP++;
				}
			}
		}
		os.close();
		is.close();
		System.out.println("Hora y fecha: "+ new Date());
	}
	/**
	 * Permite contar el numero de lineas del archivo
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static int contadorLineas(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		byte[] c = new byte[1024];
		boolean vacio = true;
		int chars = 0;
		int contador = 0;
		
		if(is.available()>0){
			vacio=false;	
		}
		while ((chars = is.read(c)) != -1) {
			for (int i = 0; i < chars; ++i) {
				if (c[i] == '\n') {
					contador++;
				}
			}
		}
		is.close();
		if(contador == 0 && !vacio){
			System.out.println(1);
			return 1;
		}else{
			System.out.println(contador);
			return contador;
		}
	}

	public List<String> getArchivos() {
		return archivos;
	}
}
