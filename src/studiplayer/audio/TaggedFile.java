package studiplayer.audio;

import studiplayer.basic.TagReader;
import java.util.Map;

public class TaggedFile extends SampledFile{
	protected String album;



	// CTOR's
	
	public TaggedFile() throws NotPlayableException {
		super();
	}
	
	public TaggedFile(String s) throws NotPlayableException {
		super(s);

		this.readAndStoreTags();
	}

	// TOSTRING

	public String toString(){
		String ret = "";
		if (this.getAuthor()!= "" && this.getAuthor() != null) {
			ret += this.getAuthor() +" - ";
		}
		if (this.getTitle()!= null) {
			ret += this.getTitle() +" - ";
		}
		if (this.getAlbum() != null) {
			ret += this.getAlbum() +" - ";
		}
		if (this.getDuration() != 0) {
			ret += this.getFormattedDuration();
		}
		return ret;
	}
	
	
	
	//Weitere Methoden
	
	
	public String getAlbum() {
		return album;
	}
	
	
	public String[] fields(){
		String[] arr = new String[]{this.getAuthor(), this.getTitle(), this.getAlbum(), this.getFormattedDuration()};
		return arr;
	}
	
	public void readAndStoreTags()  throws NotPlayableException{
		try {
			Map<String, Object> tags = TagReader.readTags(getPathname());
			
			String title_tag = (String) tags.get("title");
			String author_tag = (String) tags.get("author");
			String album_tag = (String) tags.get("album");
			long duration_tag = (long) tags.get("duration");
			
			if (title_tag != null) {
				this.title = title_tag.trim();
			}
			
			if (author_tag != null) {
				this.author = author_tag.trim();
			}
			
			if (album_tag != null) {
				this.album = album_tag.trim();
			}
			
			if (duration_tag != 0) {
				this.duration = duration_tag;
			}
		}catch(RuntimeException re) {
			throw new NotPlayableException(this.getPathname(),"Nicht spielbar");
		}
		
		
	}
	
}




