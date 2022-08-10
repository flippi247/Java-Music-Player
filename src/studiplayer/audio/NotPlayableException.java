package studiplayer.audio;


@SuppressWarnings("serial")
public class NotPlayableException extends Exception {
	private String pathname;
	
	public String getPathname() {
		return this.pathname;
	}
	
	public NotPlayableException(String pathname, String msg) {
		super(msg);
		this.pathname = pathname;
	}
	
	public NotPlayableException(String pathname, Throwable t) {
		super(t);
		this.pathname = pathname;

	}
	
	public NotPlayableException(String pathname, String msg, Throwable t) {
		super(msg, t);
		this.pathname = pathname;

	}
	@Override
	public String toString() {
		return this.getPathname() + " : " + super.toString();
	}
	
}
