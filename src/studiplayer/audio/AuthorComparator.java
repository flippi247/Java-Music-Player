package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile>{
	
	public int compare(AudioFile af1, AudioFile af2) throws NullPointerException{
		try {
			
			String t1 = af1.getAuthor();
			String t2 = af2.getAuthor();
			return t1.compareTo(t2);
		}catch (NullPointerException noe){
			throw new NullPointerException("Einer der beiden AudioFiles ist Nullpointer!");		
		}
		
	}

}
