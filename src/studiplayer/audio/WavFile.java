package studiplayer.audio;

import studiplayer.basic.WavParamReader;
public class WavFile extends SampledFile{
	
	
	
	// CTOR's
	
	public WavFile() throws NotPlayableException {
		super();
	}
	
	public WavFile(String path)  throws NotPlayableException{
		super(path);
		this.readAndSetDurationFromFile(this.getPathname());
	}
	
	
	// TOSTRING
	public String toString() {
		return super.toString() + " - "+ this.getFormattedDuration();
	}
	
	// weitere Methoden
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		long duration = (numberOfFrames *1000000/ (long) frameRate);
		return duration;	
	}
	

	public String[] fields(){
		String[] arr = new String[]{this.getAuthor(), this.getTitle(), "", this.getFormattedDuration()};
		return arr;
	}
	
	public void readAndSetDurationFromFile(String pathname)  throws NotPlayableException{
		try {
			WavParamReader.readParams(pathname);
			float frameRate = WavParamReader.getFrameRate();
			long nOF = WavParamReader.getNumberOfFrames();
			long dur = computeDuration(nOF, frameRate);
			this.duration= (dur);
		
		}catch(RuntimeException re) {
			throw new NotPlayableException(this.getPathname(),"Nicht spielbar");
		}
	}
}