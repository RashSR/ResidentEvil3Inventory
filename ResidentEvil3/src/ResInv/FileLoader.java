package ResInv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
	private File file; //gibt die Datei des zu ladenden Objekts zurück
	private ArrayList<String> ar;//Ausgelesene Daten der Datei
	//Konstruktor überprüft ob der Pfad verfügbar ist
	public FileLoader(String fileName) {
		File file = new File(fileName);
		if(file.exists()) {
			this.file = file;
			System.out.println("[FileLoader] File vorhanden!");
		}else {
			System.out.println("[FileLoader] File dont exist!");
			this.file = null;
		}
	}
	//Liest die Datei zeilenweise und speichert jede Zeile in einer String ArrayList
	public ArrayList<String> readFile() {
		Scanner sc;
		ar = new ArrayList<>();
		try {
			if(file==null){
				System.out.println("[FileLoader] File nicht lesbar!");
				return null;
			}
			sc = new Scanner(this.file);
			while(sc.hasNext()) {
				String sr = sc.nextLine();
				ar.add(sr);
			}
			sc.close();
			return ar;
			
		} catch (FileNotFoundException e) {
			System.out.println("[FileLoader] FILE NOT FOUND EXCEPTION");
		}
		return null;
	}
}