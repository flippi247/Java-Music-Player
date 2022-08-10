package studiplayer.ui;

import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

public class Player extends Application {
	
	// Attribute
	
	private Button playButton = createButton("play.png");
	private Button pauseButton = createButton("pause.png");
	private Button stopButton = createButton("stop.png");
	private Button nextButton = createButton("next.png");
	private Button editorButton = createButton("pl_editor.png");
	private PlayList playList;
	public static final String DEFAULT_PLAYLIST = "playlists/playList.cert.m3u";
	private String playListPathname;
	private static final String START_TIME = "00:00";
	private static final String TITLE_PRE = "Current Song: ";
	private Label songDescription;
	private Label playTime;
	private Stage primaryStage;
	private volatile boolean  stopped = true;
	private PlayListEditor playListEditor;
	private boolean editorVisible;
	
	
	// CTOR
	public Player() {
		
	}
	
	
	// GETTER / SETTER
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public PlayList getPlayList() {
		return this.playList;
	}
	public Button getPlayButton() {
		return playButton;
	}
	public void setPlayButton(Button playButton) {
		this.playButton = playButton;
	}
	public Button getPauseButton() {
		return pauseButton;
	}
	public void setPauseButton(Button pauseButton) {
		this.pauseButton = pauseButton;
	}
	public Button getStopButton() {
		return stopButton;
	}
	public void setStopButton(Button stopButton) {
		this.stopButton = stopButton;
	}
	public Button getNextButton() {
		return nextButton;
	}
	public void setNextButton(Button nextButton) {
		this.nextButton = nextButton;
	}
	public Button getEditorButton() {
		return editorButton;
	}
	public void setEditorButton(Button editorButton) {
		this.editorButton = editorButton;
	}

	public String getPlayListPathname() {
		return this.playListPathname;
	}
	public void setPlayListPathname(String playListPathname) {
		this.playListPathname = playListPathname;
	}
	public void setSongDescription(Label sd) {
		this.songDescription = sd;
	}
	public void setPlayTime(Label pl) {
		this.playTime = pl;
	}
	public Label getSongDescription() {
		return this.songDescription;
	}
	public Label getPlayTime() {
		return this.playTime;
	}
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	public boolean isStopped() {
		return stopped;
	}
	public boolean isEditorVisible() {
		return editorVisible;
	}
	public void setEditorVisible(boolean editorVisible) {
		this.editorVisible = editorVisible;
	}
	public void setPlayList(String playListPath) {
		this.playList = new PlayList(playListPath);
		refreshUI();
		}

	// START
	@Override
	public void start(Stage primaryStage) throws Exception{
	
		this.primaryStage = primaryStage;
		PlayList pl = new PlayList();
		playList = pl;
		
		
		// Labels Default
		this.setSongDescription(new Label("no current song"));
		this.setPlayTime(new Label("--:--"));
		// Bereitstellen Playlistpathname
		List<String> parameters = getParameters().getRaw();				
		if (parameters.size() > 0 && (!parameters.get(0).equals(null))) {
			String arg = parameters.get(0);		
			setPlayListPathname(arg);
			setPlayList(playListPathname);
		} else {	
			setPlayListPathname(DEFAULT_PLAYLIST);
			setPlayList(DEFAULT_PLAYLIST);
	}
		
		//PlayList laden
		playListEditor = new PlayListEditor(this, this.playList);
		editorVisible = false;

		// Layout 
		BorderPane mainPane = new BorderPane();
		HBox hBox = new HBox();
		
		
		// Füllen der Pane / HBox
		mainPane.setCenter(hBox);

		
		// Stage & Scene
		Scene scene = new Scene(mainPane, 700, 90);
		primaryStage.setScene(scene);
		primaryStage.show();
				
		
		// Initiale Werte im GUI			
		updateSongInfo(getPlayList().getCurrentAudioFile());
		setButtonStates(false,true,false,true,false);
			
		
			// Layout füllen
		hBox.getChildren().addAll(getPlayTime(), playButton, pauseButton, stopButton, nextButton, editorButton);
		mainPane.setTop(getSongDescription());

		
		// Ereignisbehandlung
		playButton.setOnAction(e -> {
			refreshUI();

 			playCurrentSong();
			setButtonStates(true,false,false,false,false);

		});	
		stopButton.setOnAction(e -> {
			refreshUI();

			setButtonStates(false,true,false,true,false);
			if (playList.size()>0) {
				stopCurrentSong();
			}});
		pauseButton.setOnAction(e -> {
			refreshUI();

			pauseCurrentSong();
			setButtonStates(true,false,false,false,false);

		});
		nextButton.setOnAction(e -> {
			setButtonStates(true,false,false,false,false);

			if (playList.size()>0) {
			stopCurrentSong();
			nextSong();
			playCurrentSong();
			}})
		;		
		editorButton.setOnAction(e -> {
			refreshUI();
			if(editorVisible) {
				editorVisible = false;
				playListEditor.hide();
			}else {
				editorVisible = true;
				playListEditor.show();
			}
		});
}
		
	// Funktionen für die Ereignisbehandlung
	public void playCurrentSong() {
		updateSongInfo(playList.getCurrentAudioFile());
		if (playList.getCurrentAudioFile() != null) {
			setStopped(false);

				//Start Thread
				(new TimerThread()).start();
				(new PlayerThread()).start();
		}			
	}
	public void stopCurrentSong() {
			setStopped(true);
			playList.getCurrentAudioFile().stop();
			updateSongInfo(playList.getCurrentAudioFile());
	}
	public void pauseCurrentSong() {
		if (playList.getCurrentAudioFile() != null) {
			playList.getCurrentAudioFile().togglePause();
			updateSongInfo(playList.getCurrentAudioFile());
		}
	}
	public void nextSong() {
		if (playList.getCurrentAudioFile() != null) {
			if ( isStopped()) {
				stopCurrentSong();
			}
			playList.changeCurrent();
			}
		}
	

	// Methode zum Updaten der GUI Informationen (Labels & StageTitel)
	private void updateSongInfo(AudioFile af) {
		if ((playList.size() == 0 )){
			getSongDescription().setText("no current song");
			getPlayTime().setText("--:--");
			getPrimaryStage().setTitle("no current song");

		} else{
			getSongDescription().setText(playList.getCurrentAudioFile().toString());
			getPlayTime().setText(START_TIME);
			getPrimaryStage().setTitle(TITLE_PRE+playList.getCurrentAudioFile().toString());

		}
	}
	

	// Anpassen der Liedinfos & Buttonzustände
	private void refreshUI() {
		Platform.runLater(() -> {
			if (playList != null && playList.size() > 0) {
				updateSongInfo(playList.getCurrentAudioFile());
				//setButtonStates(false,true,false,true,false);
			} else {
				updateSongInfo(null);
				//setButtonStates(true,true,true,true,false);
			}
		});
		
	}
	
	private void setButtonStates(boolean playButtonState, boolean stopButtonState,
			boolean nextButtonState, boolean pauseButtonState, boolean editorButtonState) {
			getPlayButton().setDisable(playButtonState);
			getStopButton().setDisable(stopButtonState);
			getNextButton().setDisable(nextButtonState);
			getPauseButton().setDisable(pauseButtonState);
			getEditorButton().setDisable(editorButtonState);


	}
	
	
	// BUTTONS
	private Button createButton(String iconfile) {
		Button button = null;
		try {
			URL url = getClass().getResource("/icons/"+iconfile);
			Image icon = new Image(url.toString());
			ImageView imageView = new ImageView(icon);
			imageView.setFitHeight(48);
			imageView.setFitWidth(48);
			button = new Button("", imageView);
			button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		} catch (Exception e) {
			System.out.println("Image "+ "icons/" + iconfile + " not found!");
			System.exit(-1);
		}
		return button;
	}
		
	// MAIN
	public static void main(String[] args) {
		
		launch(args);
	}
	
	
	// Innere Thread Klassen
	private class TimerThread extends Thread{
		public void run() {
			while(!(isStopped()) && playList.size() > 0) {
				Platform.runLater(
						  () -> {
							  getPlayTime().setText(playList.getCurrentAudioFile().getFormattedPosition());
						  });
				try{
					sleep(100);
				}catch (Exception e) {
					//
				}
			}
		}
		
	}
	
	private class PlayerThread extends Thread {
		public void run(){
			while (!(isStopped()) && playList.size() > 0) { 
				try {
					playList.getCurrentAudioFile().play();
				} catch (NotPlayableException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}





