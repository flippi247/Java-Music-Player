package studiplayer.audio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
@SuppressWarnings("serial")
public class PlayList extends LinkedList<AudioFile>  {
	
	//Attribute
	
	private int current;
	private boolean randomOrder;
	
	
	//Setter & Getter
	public int getCurrent() {
		return this.current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public void setRandomOrder(boolean randomOrder) {
		if (randomOrder == true) {
			Collections.shuffle(this);
			this.randomOrder = randomOrder;

			
		}else {
		this.randomOrder = randomOrder;
		}
	}
	public boolean isRandomOrder() {
		return this.randomOrder;
	}
	
	
	// CTOR's
	public PlayList() {
		this.current = 0;
		this.randomOrder = false;
	}
	
	public PlayList(String filename) {
		this();
		if (filename != null)
		loadFromM3U(filename);
	}
	
	
	
	// Weitere Methoden
	public AudioFile getCurrentAudioFile() {
		if ((this.getCurrent() > this.size()-1) || (this.size() == 0)) {
			return null;
		} else {
			return this.get(current);
		}
	} 
	
	public void changeCurrent() {
		if ((this.getCurrent() == this.size()-1) || (this.getCurrent() > this.size()-1) || (this.size() == 0)) {
			if (this.isRandomOrder()){
				setRandomOrder(false);
				setRandomOrder(true);
			}
			setCurrent(0);
		} else {
			setCurrent(this.getCurrent() +1);
		}
	}
	
	public void saveAsM3U(String pathname) {
		FileWriter writer = null;
		String fname = pathname;
		String linesep = System.getProperty("line.separator");
		try {
			writer = new FileWriter(fname);
			writer.write("#"+fname+linesep);
			writer.write(linesep);
			for (int i = 0; i < this.size();i++) {
				writer.write(this.get(i).getPathname()+linesep);
			}
		}catch (IOException e) {
			throw new RuntimeException("Unable to write to file"+ fname + e.getMessage());
		}finally {
			try {
				writer.close();
			} catch (Exception e){
				// ...
			}
		}
	}
	
	public void loadFromM3U(String pathname) {
		String fname = pathname;
		Scanner scanner = null;
		String line;
		try { // Create a Scanner
			scanner = new Scanner(new File(fname));
			this.clear();
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (line.length() != 0) {
					if ((line.charAt(0) == ('#') || line.charAt(0) == ' ' || line.charAt(0) == '\t')) {
						// skip
					} else { 
						try {
						if (AudioFileFactory.getInstance(line) instanceof AudioFile) {
							this.add(AudioFileFactory.getInstance(line));
							}
						}catch (NotPlayableException e) {
							e.printStackTrace();
							}
						}
				}
			}
		}catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			// ...
			try {
				scanner.close();
			} catch (Exception e){
				// ...
			}
		}
	}
	
	
	
	public void sort(SortCriterion order) {
		if (order == SortCriterion.TITLE) {
		this.sort(new TitleComparator());
		}
		
		if (order == SortCriterion.AUTHOR) {
		this.sort(new AuthorComparator());
		}
		
		if (order == SortCriterion.DURATION) {
		this.sort(new DurationComparator());
		}
		
		if (order == SortCriterion.ALBUM) {
		this.sort(new AlbumComparator());
		}
	}
}
