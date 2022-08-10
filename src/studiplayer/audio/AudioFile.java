package studiplayer.audio;

import java.io.File;

 public abstract class AudioFile {

	
	// Attribute
	private String filename;
	private String pathname;
	protected String title;
	protected String author;
	private char driveLetter;
	protected long duration;
	

	//Schnittstellenmethoden
	public abstract void play() throws NotPlayableException;
	public abstract void togglePause();
	public abstract void stop();
	public abstract String getFormattedDuration();
	public abstract String getFormattedPosition();
	public abstract String[] fields ();

	
	
	// CTOR's
	
	public AudioFile()  throws NotPlayableException{
		this("");
	}
	
	public AudioFile(String input)  throws NotPlayableException{
		parsePathname(input);
		parseFilename(getFilename());
		File f = new File(getPathname());
		if (f.canRead() == false) {
			throw new  NotPlayableException(this.getPathname(),"Datei nicht abspielbar");
		};
	}
	
	
	// TOSTRING
	
	public String toString() {
		String retValue = "";
		if (this.getAuthor().isEmpty() ==true) {
			retValue = this.getTitle();
		} else {
			retValue = this.getAuthor() + " - " + this.getTitle();
		}
		return retValue;
	}
	
	
	// weitere Methoden
	
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPathname() {	
		return this.pathname;
	}
	public String getFilename() {
		return this.filename;
	}
	public String getAuthor() {
		return this.author;
	}
	public String getTitle() {
		return this.title;
	}
	
	
	
	
	
	public void parsePathname(String name) {
		
		// Trennzeichen definieren + umwandeln
			char separatorChar = System.getProperty("file.separator").charAt(0);
			String sepaStr = Character.toString(separatorChar);
			String sepWind = "\\" ;
			char sepWindC = '\\';
			String sepUnix = "/";
			char sepUnixC = '/';
		
			
		// Fehler parsePathname("-") abfangen
			
			if  (name.length() ==1) {
				if (name.equals(" ")) {
					pathname = " ";
					filename = " ";
				} else {
				filename = name;
				pathname = name;
				}
				
			}else if (name.length() ==0){
				filename ="";
				pathname = "";
			} else {
				
		//  Pfad normalisieren (mehrfach Separatoren ändern)
				pathname ="";
				if ((name.contains(sepUnix) == true) || (name.contains(sepWind) == true)){
					for (int i = 0; i < name.length(); i++) {
						if ((name.charAt(i) == '/') && (i != name.length()-1) && (name.charAt(i+1) == '/') || 
							(name.charAt(i) == '/') && (i != name.length()-1) && (name.charAt(i+1) == '\\') ||
							(name.charAt(i) == '\\') && (i != name.length()-1) && (name.charAt(i+1) == '/') ||
							(name.charAt(i) == '\\') && (i != name.length()-1) && (name.charAt(i+1) == '\\')) {
							
							} else if ((name.charAt(i) == '/') || 
									  (name.charAt(i) == '\\')) {
								pathname += sepaStr;
							} else {
								pathname += Character.toString(name.charAt(i));
							}
						}
				} else {
				pathname = name;
				}

		// Laufwerksbuchstabe: Variante 1: (/d/)
				
				if (((pathname.charAt(0) == sepWindC) || (pathname.charAt(0) == sepUnixC))
						&& (Character.isLetter(pathname.charAt(1)) 
						&& (pathname.charAt(2) == sepWindC) || (pathname.charAt(2) == sepUnixC)) == true ) 
				{
					driveLetter = pathname.charAt(1);
					if ((System.getProperty("os.name").toLowerCase().indexOf("win") >=0) == true) {
						pathname = driveLetter + ":" + sepaStr + pathname.substring(3);

				} 
				} else 	
					
		// Laufwerksbuchstabe: Variante 2: (d:\)
				if (((Character.isLetter(pathname.charAt(0)) 
						&& (pathname.charAt(1) == ':') 
						&& ((pathname.charAt(2) == sepWindC) || (pathname.charAt(2) == sepUnixC)))) == true ) 
				{
					driveLetter = pathname.charAt(0);
					if ((System.getProperty("os.name").toLowerCase().indexOf("win") >=0) == false) {
						pathname = sepUnix + driveLetter + pathname.substring(2);

				}
				} else {
					
				}
								
			// Filename bestimmen & letztes Zeichen auf Separator prüfen
				if (pathname.contains(sepaStr) && ( pathname.lastIndexOf(sepaStr) != pathname.length()-1)){
					filename = pathname.substring(pathname.lastIndexOf(sepaStr)+1);
				} else if (pathname.contains(sepaStr) && ( pathname.lastIndexOf(sepaStr) == pathname.length()-1)) {
					filename = "";
				} else {
					filename = pathname;
				}		
	}}
	
	

	
	public void parseFilename(String filename) {
		
	// Fileformat prüfen und entfernen
		if (filename.contains(".")) {
			filename = filename.substring(0,filename.lastIndexOf("."));
		}
		
	// "-" - Fällle
		if (filename.contains("-")) {
			
			if (filename.length() == 1) {
				author = "";
				title = "-";
				
			}else if (filename.indexOf("-") != 0 && (filename.indexOf("-") == filename.lastIndexOf("-"))) {
				
			author = filename.split("-")[0].trim();
			title = filename.split("-")[1].trim();
			
			} else if (filename.indexOf("-") != filename.lastIndexOf("-")) {
				if (filename.contains(" - ")) {
				author = filename.split(" - ")[0];
				title = filename.split(" - ")[1];
				} 
				else {
					author ="";
					title = filename;
			}}
		} else {
			if (this.getAuthor() == null) {
				this.setAuthor("");
			}
			title = filename;
		}
	}	
	public static void main(String[] args) {


	}

}