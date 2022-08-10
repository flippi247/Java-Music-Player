package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
	
	public int compare(AudioFile af1, AudioFile af2) throws NullPointerException{
		try{
			if ((af1 instanceof TaggedFile) && (af2 instanceof TaggedFile)) {	
		
			String a1 = ((TaggedFile) af1).getAlbum();
			String a2 = ((TaggedFile) af1).getAlbum();
			return a1.compareTo(a2);
			}else if ((af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)){
				return 1;
			}else if (! (af1 instanceof TaggedFile) && (af2 instanceof TaggedFile)){
				return -1;
			} else {
				return 0;
			}
		}catch(NullPointerException npe) {
			throw new NullPointerException("Nullpointerexception im Album Comparator!");
		}
	}
}
