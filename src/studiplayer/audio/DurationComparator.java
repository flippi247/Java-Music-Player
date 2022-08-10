package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {
		
		public int compare(AudioFile af1, AudioFile af2) throws NullPointerException{
			try{
				return ((Long)af1.getDuration()).compareTo((Long)af2.getDuration());
				
			}catch(NullPointerException npe) {
				throw new NullPointerException("Nullpointerexception im Duration Comparator!");
			}
		}
	}
