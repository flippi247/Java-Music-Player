package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {
	
	public int compare(AudioFile af1, AudioFile af2) throws NullPointerException {
		try {
	
			String t1 = af1.getTitle();
			String t2 = af2.getTitle();
			if (t1 != "" && t2 != "") {
				return t1.compareTo(t2);
				}
			else {
				throw new NullPointerException("Einer der beiden Titel ist: \"");
			}
		}catch (NullPointerException noe){
			throw new NullPointerException("Einer der beiden AudioFiles ist Nullpointer!");		
		}
		
	}

}
