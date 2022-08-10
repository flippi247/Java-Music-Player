package studiplayer.audio;
public class AudioFileFactory {
	 
	
	public static AudioFile getInstance(String pathname)  throws NotPlayableException{
		pathname = pathname.trim();
		if ((pathname.length() > 4)) {
			if (pathname.substring(pathname.length()-4, pathname.length()).toLowerCase().equals(".wav")) {
			 WavFile wf = new WavFile(pathname);
			 return wf;
			} else if ((pathname.substring(pathname.length()-4, pathname.length()).toLowerCase().equals(".mp3")) ||
				 	pathname.substring(pathname.length()-4, pathname.length()).toLowerCase().equals(".ogg")){
			 TaggedFile tf =  new TaggedFile(pathname);
			 return tf;
		 } else {
			 throw new  NotPlayableException(pathname,"Datei nicht spielbar");
			}
		 } else {
			 throw new  NotPlayableException(pathname,"Kein Pathname vorhanden");
		 }
	 }
	
}
