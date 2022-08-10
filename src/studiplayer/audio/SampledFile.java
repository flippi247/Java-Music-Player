package studiplayer.audio;

import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile{
	
	// CTOR's
	
	public SampledFile() throws NotPlayableException{
		super();
	}
	
	public SampledFile(String s) throws NotPlayableException{
		super(s);
	}

	
	// weitere Methoden
	
	public void play()  throws NotPlayableException{
		try {
		BasicPlayer.play(this.getPathname());
		}catch(RuntimeException re) {
			throw new NotPlayableException(this.getPathname(),"Nicht spielbar");
		}
	}
	
	public void togglePause() {
		BasicPlayer.togglePause();

	}
	
	public void stop() {
		BasicPlayer.stop();

	}

	
	public String getFormattedDuration() {
		return timeFormatter(this.getDuration());

	}

	public String getFormattedPosition() {
		return 	timeFormatter(BasicPlayer.getPosition());
	}
	
	public static String timeFormatter(long microtime) {
		long seconds_long = microtime / 1000000;
		long minutes = (seconds_long) / 60 ;
		long seconds = (seconds_long) % 60;
		
		if (microtime < 0) {
			throw new RuntimeException("Negative time value provided");
		} 
		if (minutes > 99) {
			throw new RuntimeException("Time value exceeds allowed format");
		}  
		if (minutes <= 99 && microtime >=0) {
			String min = Long.toString(minutes);
			String sec = Long.toString(seconds);
			String timestamp = ("00" + min).substring(min.length()) + ":" + ("00" + sec).substring(sec.length());
			return timestamp;
		} else { return "";}
		
	}
}
