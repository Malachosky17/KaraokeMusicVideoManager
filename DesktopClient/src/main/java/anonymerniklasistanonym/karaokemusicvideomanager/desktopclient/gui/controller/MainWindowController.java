package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoPlaylistTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoSourceDirectoriesTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * The controller class for the main window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MainWindowController {

	// FXML Views

	/**
	 * input search box/field
	 */
	@FXML
	private TextField searchBox;
	/**
	 * label of the input search box/field
	 */
	@FXML
	private Label searchLabel;

	// the music video table
	@FXML
	private TableView<MusicVideoTableView> musicVideoTable;
	@FXML
	private TableColumn<MusicVideoTableView, Number> columnIndex;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnArtist;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnTitle;

	// the directory path table
	@FXML
	private TableView<MusicVideoSourceDirectoriesTableView> directoryPathTable;
	@FXML
	private TableColumn<MusicVideoSourceDirectoriesTableView, String> columnFilePath;

	// the playlist table
	@FXML
	private TableView<MusicVideoPlaylistTableView> playlistTable;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistTime;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistTitle;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistArtist;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistAuthor;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistComment;

	// the tabs
	@FXML
	private TabPane tabView;
	@FXML
	private Tab musicVideoTableTab;
	@FXML
	private Tab playlistTab;
	@FXML
	private Tab sourceTab;

	// music video files table context menu

	/**
	 * music video files table context menu > add file to the play list
	 */
	@FXML
	private MenuItem contextMusicVideoPlaylist;
	/**
	 * music video files table context menu > open directory of file
	 */
	@FXML
	private MenuItem contextMusicVideoDirectory;
	/**
	 * music video files table context menu > ignore the current file
	 */
	@FXML
	private MenuItem contextMusicVideoIgnore;
	/**
	 * music video files table context menu > clear the current selection
	 */
	@FXML
	private MenuItem contextMusicVideoClear;
	/**
	 * music video files table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextMusicVideoRefresh;
	/**
	 * music video file table context menu > rename file
	 */
	@FXML
	private MenuItem contextMusicVideoRename;

	// music video path table context menu

	/**
	 * music video path table context menu > remove the current selected directory
	 */
	@FXML
	private MenuItem contextPathRemove;
	/**
	 * music video path table context menu > clear the current selection
	 */
	@FXML
	private MenuItem contextPathClear;
	/**
	 * music video path table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextPathRefresh;

	// context menu playlist

	/**
	 * music video playlist table context menu > rename file
	 */
	@FXML
	private MenuItem contextPlaylistRemove;

	// music video playlist table context menu

	/**
	 * music video playlist table context menu > remove the current selected
	 * directory
	 */
	@FXML
	private MenuItem contextPlaylistEdit;
	/**
	 * music video playlist table context menu > clear the current selection
	 */
	@FXML
	private MenuItem contextPlaylistClear;
	/**
	 * music video playlist table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextPlaylistRefresh;

	// menu buttons

	/**
	 * export menu button > CSV export
	 */
	@FXML
	private MenuItem menuButtonCsv;
	/**
	 * export menu button > JSON export
	 */
	@FXML
	private MenuItem menuButtonJson;
	/**
	 * export menu button > export of websites
	 */
	@FXML
	private Menu menuButtonWebsites;
	/**
	 * export menu button > HTML static list export
	 */
	@FXML
	private MenuItem menuButtonHtmlStatic;
	/**
	 * export menu button > HTML list with search export
	 */
	@FXML
	private MenuItem menuButtonHtmlSearch;
	/**
	 * export menu button > HTML list with party playlist export
	 */
	@FXML
	private MenuItem menuButtonHtmlParty;

	/**
	 * about menu button > about button
	 */
	@FXML
	private MenuItem aboutButton;
	/**
	 * about menu button > help button
	 */
	@FXML
	private MenuItem helpButton;

	// window buttons

	/**
	 * network toggle button
	 */
	@FXML
	private ToggleButton networkButton;
	/**
	 * YouTube button
	 */
	@FXML
	private Button youTubeButton;
	/**
	 * Random button
	 */
	@FXML
	private Button randomButton;

	/**
	 * Add a directory to the path list button
	 */
	@FXML
	private Button buttonAddDirectory;
	/**
	 * Open the wrong formatted files window button
	 */
	@FXML
	private Button buttonWrongFormattedFiles;
	/**
	 * Open the ignored files window button
	 */
	@FXML
	private Button buttonIgnoredFiles;

	@FXML
	private Button buttonSavePlaylist;
	@FXML
	private Button buttonLoadPlaylist;
	@FXML
	private Button buttonRefreshPlaylist;
	@FXML
	private Button buttonClearPlaylist;

	@FXML
	private MenuItem menuButtonSaveConfiguration;
	@FXML
	private MenuItem menuButtonLoadConfiguration;
	@FXML
	private MenuItem menuButtonSaveConfigurationCustom;
	@FXML
	private MenuItem menuButtonLoadConfigurationCustom;
	@FXML
	private MenuItem menuButtonResetConfiguration;

	@FXML
	private MenuItem menuButtonSftpStatic;
	@FXML
	private MenuItem menuButtonSftpSearch;
	@FXML
	private MenuItem menuButtonSftpParty;
	@FXML
	private MenuItem menuButtonSftpReset;
	@FXML
	private Menu menuButtonSftp;

	@FXML
	private CheckMenuItem menuButtonAlwaysSave;

	// other

	/**
	 * table data of the table with music video files
	 */
	private ObservableList<MusicVideoTableView> tableDataMusicVideo;

	/**
	 * table data of the table with the playlist
	 */
	private ObservableList<MusicVideoPlaylistTableView> tableDataPlaylist;

	/**
	 * table data of the table with music video file directories
	 */
	private ObservableList<MusicVideoSourceDirectoriesTableView> tableDataDirectory;

	/**
	 * get if the last mouse key that was pressed was the left mouse key
	 */
	private boolean leftMouseKeyWasPressed;

	/**
	 * Main class
	 */
	private Main mainWindow;

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the music video table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnIndex.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		columnArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		tableDataMusicVideo = FXCollections.observableArrayList();
		FilteredList<MusicVideoTableView> filteredData = new FilteredList<>(tableDataMusicVideo, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(musicVideoObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (musicVideoObject.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (musicVideoObject.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicVideoTableView> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(musicVideoTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		musicVideoTable.setItems(sortedData);

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the directory path table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnPlaylistTime.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
		columnPlaylistTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		columnPlaylistArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		columnPlaylistAuthor.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		columnPlaylistComment.setCellValueFactory(cellData -> cellData.getValue().getCommentProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		tableDataDirectory = FXCollections.observableArrayList();
		FilteredList<MusicVideoSourceDirectoriesTableView> filteredDataDirectory = new FilteredList<>(
				tableDataDirectory, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataDirectory.setPredicate(directoryPathObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (directoryPathObject.getFilePath().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (directoryPathObject.getFilePath().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicVideoSourceDirectoriesTableView> sortedDataDirectory = new SortedList<>(filteredDataDirectory);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataDirectory.comparatorProperty().bind(directoryPathTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		directoryPathTable.setItems(sortedDataDirectory);

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the playlist table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnFilePath.setCellValueFactory(cellData -> cellData.getValue().getFilePathProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		tableDataPlaylist = FXCollections.observableArrayList();
		FilteredList<MusicVideoPlaylistTableView> filteredDataParty = new FilteredList<>(tableDataPlaylist, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataParty.setPredicate(directoryPathObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (directoryPathObject.getTime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getComment().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					// nothing matches
					return false;
				}
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicVideoPlaylistTableView> sortedDataPlaylist = new SortedList<>(filteredDataParty);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataPlaylist.comparatorProperty().bind(playlistTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		playlistTable.setItems(sortedDataPlaylist);

		/**
		 * Set menu icons
		 */

		// Context menu
		contextMusicVideoPlaylist.setGraphic(WindowModule.createMenuIcon("playlist"));
		contextMusicVideoDirectory.setGraphic(WindowModule.createMenuIcon("directory"));
		contextMusicVideoIgnore.setGraphic(WindowModule.createMenuIcon("ignore"));
		contextMusicVideoClear.setGraphic(WindowModule.createMenuIcon("clear"));
		contextMusicVideoRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));
		contextPathRemove.setGraphic(WindowModule.createMenuIcon("remove"));
		contextPathClear.setGraphic(WindowModule.createMenuIcon("clear"));
		contextPathRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));
		contextMusicVideoRename.setGraphic(WindowModule.createMenuIcon("rename"));
		contextPlaylistRemove.setGraphic(WindowModule.createMenuIcon("remove"));
		contextPlaylistEdit.setGraphic(WindowModule.createMenuIcon("rename"));
		contextPlaylistClear.setGraphic(WindowModule.createMenuIcon("clear"));
		contextPlaylistRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));

		// other buttons
		buttonWrongFormattedFiles.setGraphic(WindowModule.createMenuIcon("wrongFormattedFiles"));
		buttonIgnoredFiles.setGraphic(WindowModule.createMenuIcon("ignore"));
		buttonAddDirectory.setGraphic(WindowModule.createMenuIcon("add"));
		networkButton.setGraphic(WindowModule.createMenuIcon("network"));
		youTubeButton.setGraphic(WindowModule.createMenuIcon("youTube"));
		aboutButton.setGraphic(WindowModule.createMenuIcon("about"));
		randomButton.setGraphic(WindowModule.createMenuIcon("random"));
		helpButton.setGraphic(WindowModule.createMenuIcon("help"));
		buttonLoadPlaylist.setGraphic(WindowModule.createMenuIcon("load"));
		buttonSavePlaylist.setGraphic(WindowModule.createMenuIcon("save"));
		buttonRefreshPlaylist.setGraphic(WindowModule.createMenuIcon("refresh"));
		buttonClearPlaylist.setGraphic(WindowModule.createMenuIcon("remove"));

		// menu buttons
		menuButtonWebsites.setGraphic(WindowModule.createMenuIcon("html_static"));
		menuButtonCsv.setGraphic(WindowModule.createMenuIcon("csv"));
		menuButtonJson.setGraphic(WindowModule.createMenuIcon("json"));
		menuButtonHtmlStatic.setGraphic(WindowModule.createMenuIcon("html_static"));
		menuButtonHtmlSearch.setGraphic(WindowModule.createMenuIcon("html_search"));
		menuButtonHtmlParty.setGraphic(WindowModule.createMenuIcon("html_playlist"));
		menuButtonSaveConfiguration.setGraphic(WindowModule.createMenuIcon("save"));
		menuButtonLoadConfiguration.setGraphic(WindowModule.createMenuIcon("load"));
		menuButtonSaveConfigurationCustom.setGraphic(WindowModule.createMenuIcon("save"));
		menuButtonLoadConfigurationCustom.setGraphic(WindowModule.createMenuIcon("load"));
		menuButtonResetConfiguration.setGraphic(WindowModule.createMenuIcon("reset"));
		menuButtonSftp.setGraphic(WindowModule.createMenuIcon("network"));
		menuButtonSftpReset.setGraphic(WindowModule.createMenuIcon("remove"));
		menuButtonSftpStatic.setGraphic(WindowModule.createMenuIcon("html_static"));
		menuButtonSftpSearch.setGraphic(WindowModule.createMenuIcon("html_search"));
		menuButtonSftpParty.setGraphic(WindowModule.createMenuIcon("html_playlist"));

		// label
		searchLabel.setGraphic(WindowModule.createMenuIcon("search"));

		this.menuButtonSftp.setDisable(true);
	}

	public void setMainWindow(Main window) {
		this.mainWindow = window;

		refreshMusicVideoTable();
		refreshMusicVideoDirectoryTable();
		refreshMusicVideoPlaylistTable();

		this.menuButtonAlwaysSave.setSelected(this.mainWindow.getMusicVideohandler().getAlwaysSave());
	}

	/**
	 * Do the following when a row is selected/clicked with the mouse
	 */
	@FXML
	private void openSelectedVideoFile() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (musicVideoTableTab.isSelected() && selectedEntry != null) {
			// open the music video file with the index
			this.mainWindow.getMusicVideohandler().openMusicVideo(selectedEntry.getIndex() - 1);
		} else {
			// search on YouTube
			searchOnYouTube();
		}
	}

	/**
	 * Monitor if the left mouse key was clicked
	 * 
	 * @param e
	 *            (MouseEvent | Needed to get the mouse key)
	 */
	@FXML
	private void mousePressed(MouseEvent e) {
		leftMouseKeyWasPressed = false;

		if (e.isPrimaryButtonDown()) {
			leftMouseKeyWasPressed = true;
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	private void openMusicVideoFileLeftClick() {
		if (leftMouseKeyWasPressed == true) {
			openSelectedVideoFile();
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	private void openDirectoryLeftClick() {
		if (leftMouseKeyWasPressed == true) {
			showSelectedDirectoryInExplorer();
		}
	}

	/**
	 * Open on enter the video file that is on the top of the table
	 */
	@FXML
	private void openTopMusicVideoFile() {

		// get the selected tab
		Tab selectedTab = tabView.getSelectionModel().getSelectedItem();

		if (selectedTab == musicVideoTableTab) {

			// select the top item
			this.musicVideoTable.getSelectionModel().select(0);

			// open the selected item externally
			openSelectedVideoFile();

			// clear the selection
			this.musicVideoTable.getSelectionModel().clearSelection();

		} else if (selectedTab == playlistTab) {

			// select the top item
			this.playlistTable.getSelectionModel().select(0);

			// open the selected item externally
			openSelectedPlaylistVideoFile();

			// clear the selection
			this.playlistTable.getSelectionModel().clearSelection();

		} else if (selectedTab == sourceTab) {

			// select the top item
			this.directoryPathTable.getSelectionModel().select(0);

			// open the selected directory entry externally
			showSelectedDirectoryInExplorer();

			// clear the selection
			this.directoryPathTable.getSelectionModel().clearSelection();
		}
	}

	/**
	 * Clears the current row selection of the music video file table
	 */
	@FXML
	private void clearSelectionMusicVideoFileTable() {
		// clear the current selection
		this.musicVideoTable.getSelectionModel().clearSelection();
	}

	/**
	 * Clears the current row selection of the music video path table
	 */
	@FXML
	private void clearSelectionMusicVideoPathTable() {
		// clear the current selection
		this.directoryPathTable.getSelectionModel().clearSelection();
	}

	/**
	 * Clears the current text in the search box
	 */
	@FXML
	private void clearSearch() {

		// clear the text in the search box
		this.searchBox.setText("");

		// get the selected tab
		Tab selectedTab = tabView.getSelectionModel().getSelectedItem();

		// change the text in the search box respective to the selected tab
		if (selectedTab == musicVideoTableTab) {
			this.searchBox.setPromptText("Search for music videos...");
		} else if (selectedTab == playlistTab) {
			this.searchBox.setPromptText("Search for playlist entries...");
		} else if (selectedTab == sourceTab) {
			this.searchBox.setPromptText("Search for directories...");
		}

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	private void searchOnYouTube() {

		// Text we want to search on YouTube
		String searchQuery = searchBox.getText();

		// The URL of YouTube
		String youTubeUrl = "https://www.youtube.com";

		// if text field has text try to add it to the urlToOpen
		try {
			if (searchQuery != null && searchQuery.length() > 0) {

				// encode the text to a browser query
				String textToSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");

				// add the search query to the YouTube URL
				youTubeUrl += "/results?search_query=" + textToSearchQuery;

				// open the new URL
				ExternalApplicationModule.openUrl(youTubeUrl);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	private void addSourceFolderDialog() {

		// get a directory
		File directory = DialogModule.chooseDirectory(this.mainWindow.getPrimaryStage(), "Add a path", null);

		// if the directory isn't null
		if (directory != null) {

			// add it to the path list
			this.mainWindow.getMusicVideohandler().addPathToPathList(directory.toPath());

			// update the music video table list
			this.mainWindow.getMusicVideohandler().updateMusicVideoList();

			// update all JavaFx tables
			refreshMusicVideoTable();
			refreshMusicVideoPlaylistTable();
			refreshMusicVideoDirectoryTable();
		}
	}

	/**
	 * Open the About Window
	 */
	@FXML
	private void openAboutWindow() {
		try {

			// load the whole FXML window
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/AboutWindow.fxml"));

			// >> load the window itself
			Parent root1 = (Parent) loader.load();

			// >> load the controller to the window
			loader.getController();

			// create a stage
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.setScene(new Scene(root1));
			stage.setResizable(false);
			stage.setTitle("About");

			// show the stage
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the server login window
	 */
	@FXML
	private void openServerLoginWindow() {

		if (!this.mainWindow.getMusicVideohandler().sftpConnectionEstablished()) {

			if ((this.mainWindow.getMusicVideohandler().getPlaylistHandler() != null
					&& this.mainWindow.getMusicVideohandler().getPlaylistHandler().getPlaylistElements() == null)
					|| DialogModule.yesNoDialog("You really want to continue?",
							"If you login to the server you will lose your current playlist!",
							"If you do not want to loose this playlist save it before you logg yourself in. Load it after the connection was established to overwrite the server data.")) {

				this.networkButton.setSelected(true);
				try {

					// load the whole FXML window
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getClassLoader().getResource("windows/ServerLoginWindow.fxml"));

					// >> load the window itself
					Parent root1 = (Parent) loader.load();

					// create a stage
					Stage stage = new Stage(StageStyle.DECORATED);
					stage.setScene(new Scene(root1));
					stage.setResizable(false);
					stage.setTitle("Server Login");

					// try to add a window icon
					try {
						stage.getIcons().addAll(WindowModule.getWindowIcons());
					} catch (Exception e) {
						System.err.println("Exception while loding icons");
					}

					// do this on a close request
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent event) {
							checkNetwork();
							refreshMusicVideoPlaylistTable();
						}
					});

					// Connection to the Controller to the stage
					ServerLoginWindowController aboutWindowController = loader.getController();
					aboutWindowController.setServerLoginWindow(this.mainWindow, stage);

					// show the stage
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (DialogModule.yesNoDialog("Logout?", "Do you want to log out?",
					"You are already logged in. Click OK to logout.")) {
				this.mainWindow.getMusicVideohandler().sftpDisconnect();
				this.networkButton.setSelected(false);
			} else {
				this.networkButton.setSelected(true);
			}

		}

	}

	/**
	 * Open the wrong formatted files window
	 */
	@FXML
	private void openWrongFormattedFilesWindow() {
		try {

			// load the whole FXML window
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/WrongFomattedFilesWindow.fxml"));

			// >> load the window itself
			Parent root1 = (Parent) loader.load();

			// >> load the controller to the window and give him the main window
			WrongFormattedFilesWindowController windowController = loader.getController();
			windowController.setWindowController(this.mainWindow, this);

			// create a stage
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(root1));
			stage.setResizable(true);
			stage.setMinWidth(450);
			stage.setMinHeight(350);
			stage.setTitle("Wrong Formatted Files");

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowModule.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			// show the stage/window
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the wrong formatted files window
	 */
	@FXML
	private void openIgnoredFilesWindow() {
		try {

			// load the whole FXML window
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/IgnoredFilesWindow.fxml"));

			// >> load the window itself
			Parent root1 = (Parent) loader.load();

			// >> load the controller to the window and give him the main window
			IgnoredFilesWindowController windowController = loader.getController();
			windowController.setWindowController(this.mainWindow, this);

			// create a stage
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(root1));
			stage.setResizable(true);
			stage.setMinWidth(450);
			stage.setMinHeight(350);
			stage.setTitle("Ignored Music Video Files");

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowModule.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			// show the stage/window
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the server login window
	 */
	@FXML
	private void openRandomWindow() {
		try {

			// load the whole FXML window
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/RandomMusicVideoWindow.fxml"));

			// >> load the window itself
			Parent root1 = (Parent) loader.load();

			// >> load the controller to the window and give him the main window
			RandomWindowController windowController = loader.getController();
			windowController.setWindowController(this.mainWindow, this);

			// create a stage
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(root1));
			stage.setResizable(true);
			stage.setMinHeight(300);
			stage.setMinWidth(600);

			stage.setTitle("Random music video files");

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowModule.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove a directory from the path list
	 */
	@FXML
	private void removeDirectory() {

		// get the currently selected directory/path
		MusicVideoSourceDirectoriesTableView selectedEntry = this.directoryPathTable.getSelectionModel()
				.getSelectedItem();

		// if something was selected
		if (selectedEntry != null) {
			// remove this entry from the path list
			this.mainWindow.getMusicVideohandler().removeFromPathList(selectedEntry.getFilePath());

			// update now both tables
			refreshMusicVideoDirectoryTable();
			refreshMusicVideoTable();
		}
	}

	/**
	 * Show the currently selected directory in the default file manager
	 */
	@FXML
	private void showSelectedDirectoryInExplorer() {

		// get the currently selected entry
		MusicVideoSourceDirectoriesTableView selectedEntry = this.directoryPathTable.getSelectionModel()
				.getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {
			// open the music video file with the index
			ExternalApplicationModule.openFile(Paths.get(selectedEntry.getFilePath()).toFile());
		}
	}

	/**
	 * Show the currently selected files directory in the default file manager
	 */
	@FXML
	private void showDirectoryInExplorerMusicVideoList() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {

			// get the path of the music video object over the table index - 1
			MusicVideo selectedFile = this.mainWindow.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1];

			// open the selected music video file externally
			ExternalApplicationModule.openDirectory(selectedFile.getPath().toFile());
		}
	}

	/**
	 * Add currently selected music video file to play list
	 */
	@FXML
	private void addVideoToPlaylist() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry not null
		if (selectedEntry != null) {

			// get the table index
			int a = selectedEntry.getIndex();

			// dialog to get author and optional a comment
			String[] authorComment = DialogModule.playlistDialog("", "", "Create a new Playlist entry",
					"Add an author and comment", "Add to playlist");

			// if author not null add it to the playlist
			if (authorComment != null && authorComment[0] != null) {
				if (authorComment[1] == null) {
					authorComment[1] = "";
				}

				this.mainWindow.getMusicVideohandler().addMusicVideoToPlaylist(a, authorComment[0], authorComment[1]);
				refreshMusicVideoPlaylistTable();
			}

		}
	}

	/**
	 * Refresh the music video file table
	 */
	@FXML
	public void refreshMusicVideoTable() {

		// update the music video data
		this.mainWindow.getMusicVideohandler().updateMusicVideoList();

		// get music video data
		MusicVideo[] listOfVideos = this.mainWindow.getMusicVideohandler().getMusicVideoList();

		// clear table
		tableDataMusicVideo.clear();

		// add music video data
		if (listOfVideos != null) {
			for (int i = 0; i < listOfVideos.length; i++) {
				tableDataMusicVideo
						.add(new MusicVideoTableView(i + 1, listOfVideos[i].getArtist(), listOfVideos[i].getTitle()));
			}
		}
	}

	/**
	 * Refresh the music video path table
	 */
	@FXML
	public void refreshMusicVideoDirectoryTable() {

		// get music video data
		Path[] listOfPaths = this.mainWindow.getMusicVideohandler().getPathList();

		// clear table
		tableDataDirectory.clear();

		// add music video data
		if (listOfPaths != null) {
			for (Path directory : listOfPaths) {
				tableDataDirectory.add(new MusicVideoSourceDirectoriesTableView(directory.toString()));
			}
		}
	}

	/**
	 * Refresh the music video playlist table
	 */
	@FXML
	public void refreshMusicVideoPlaylistTable() {

		// clear the table
		this.tableDataPlaylist.clear();

		if (this.mainWindow.getMusicVideohandler().sftpConnectionEstablished()) {
			// retrieve SFTP playlist
			this.mainWindow.getMusicVideohandler().sftpRetrievePlaylist();
		}

		// get music video data
		MusicVideoPlaylistElement[] listOfEntries = this.mainWindow.getMusicVideohandler().getPlaylistHandler()
				.getPlaylistElements();

		// add music video data
		if (listOfEntries != null) {
			int i = 0;
			for (MusicVideoPlaylistElement element : listOfEntries) {
				System.out.println("Refresh and add file by " + element.getAuthor());
				MusicVideo a = this.mainWindow.getMusicVideohandler()
						.getMusicVideoOfPlaylistItem(element.getMusicVideoFile().getPath());

				if (a != null) {
					tableDataPlaylist.add(new MusicVideoPlaylistTableView(i, element.getMusicVideoIndex(),
							element.getUnixTimeString(), a.getTitle(), a.getArtist(), element.getAuthor(),
							element.getComment()));
					i++;
				} else {
					System.err.println(
							"File not added because it's either ignored or not in the current music video list!");
				}

			}
		}
	}

	/**
	 * Ignore the currently selected music video file
	 */
	@FXML
	private void ignoreMusicVideoFile() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {

			MusicVideo selectedFile = this.mainWindow.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1];
			this.mainWindow.getMusicVideohandler().addIgnoredFileToIgnoredFilesList(selectedFile.getPath());
		}

		// update the music video list after this
		refreshMusicVideoTable();
	}

	/**
	 * Add network: Check if a working network connection is established
	 */
	private void checkNetwork() {
		if (this.mainWindow.getMusicVideohandler().sftpConnectionEstablished()) {
			this.networkButton.setSelected(true);
			this.mainWindow.getMusicVideohandler().sftpRetrievePlaylist();
			this.menuButtonSftp.setDisable(false);
			// update the table
			refreshMusicVideoPlaylistTable();
		} else {
			this.networkButton.setSelected(false);
			this.menuButtonSftp.setDisable(true);
		}

	}

	/**
	 * Export the music video list as static HTML table
	 */
	@FXML
	private void exportHtmlStatic() {
		File htmlFileDestination = DialogModule.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory",
				FileSystems.getDefault().getPath(".").toFile());

		if (htmlFileDestination != null) {
			this.mainWindow.getMusicVideohandler().saveHtmlList(htmlFileDestination.toPath(), true);
		}
	}

	/**
	 * Export the music video list as HTML table with a search
	 */
	@FXML
	private void exportHtmlSearch() {
		File htmlFileDestination = DialogModule.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory",
				FileSystems.getDefault().getPath(".").toFile());

		if (htmlFileDestination != null) {
			this.mainWindow.getMusicVideohandler().saveHtmlSearch(htmlFileDestination.toPath(), true);
		}
	}

	/**
	 * Export the music video list as HTML table with a playlist
	 */
	@FXML
	private void exportHtmlParty() {
		File htmlFileDestination = DialogModule.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory",
				FileSystems.getDefault().getPath(".").toFile());

		if (htmlFileDestination != null) {
			this.mainWindow.getMusicVideohandler().saveHtmlParty(htmlFileDestination.toPath(), true);
		}
	}

	/**
	 * Export the music video list as a CSV table
	 */
	@FXML
	private void exportCsv() {
		ExtensionFilter csvFilter = new ExtensionFilter("Csv File", "*.csv");
		File[] csvFile = DialogModule.chooseFile(this.mainWindow.getPrimaryStage(),
				"Export music video list to a CSV file - Choose a directory and filename",
				FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { csvFilter },
				DialogModule.CHOOSE_ACTION.SAVE);

		if (csvFile != null && csvFile[0] != null) {
			this.mainWindow.getMusicVideohandler().saveCsv(csvFile[0].toPath());
		}
	}

	/**
	 * Export the music video list as a JSON file
	 */
	@FXML
	private void exportJson() {
		ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
		File[] jsonFile = DialogModule.chooseFile(this.mainWindow.getPrimaryStage(),
				"Export music video list to a JSON file - Choose a directory and filename",
				FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { jsonFilter },
				DialogModule.CHOOSE_ACTION.SAVE);

		if (jsonFile != null && jsonFile[0] != null) {
			this.mainWindow.getMusicVideohandler().saveJson(jsonFile[0].toPath());
		}
	}

	@FXML
	private void saveConfiguartion() {
		this.mainWindow.getMusicVideohandler().saveSettingsToFile();
	}

	@FXML
	private void loadConfiguartion() {
		this.mainWindow.getMusicVideohandler().loadSettingsFromFile();
		refreshMusicVideoTable();
		refreshMusicVideoDirectoryTable();
	}

	@FXML
	private void saveConfiguartionCustom() {
		ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
		File[] jsonFile = DialogModule.chooseFile(this.mainWindow.getPrimaryStage(),
				"Save a Custom Named Configuration File", FileSystems.getDefault().getPath(".").toFile(),
				new ExtensionFilter[] { jsonFilter }, DialogModule.CHOOSE_ACTION.SAVE);
		if (jsonFile != null && jsonFile[0] != null) {
			File saveToThis = Paths.get(jsonFile[0].getAbsolutePath() + ".json").toFile();
			this.mainWindow.getMusicVideohandler().saveSettings(saveToThis);
		}
	}

	@FXML
	private void loadConfiguartionCustom() {
		ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
		File[] jsonFile = DialogModule.chooseFile(this.mainWindow.getPrimaryStage(),
				"Load a Custom Named Configuration File", FileSystems.getDefault().getPath(".").toFile(),
				new ExtensionFilter[] { jsonFilter }, DialogModule.CHOOSE_ACTION.NORMAL);
		if (jsonFile != null && jsonFile[0] != null) {
			this.mainWindow.getMusicVideohandler().loadSettings(jsonFile[0]);
		}
		refreshMusicVideoTable();
		refreshMusicVideoDirectoryTable();
	}

	@FXML
	private void resetConfiguartion() {
		if (DialogModule.yesNoDialog("Confirm to Continue", "Reset Everything",
				"Do you really want to reset EVERYTHING?")) {
			this.mainWindow.getMusicVideohandler().reset();
			refreshMusicVideoTable();
			refreshMusicVideoDirectoryTable();
			refreshMusicVideoPlaylistTable();
		}

	}

	@FXML
	public void openGitHubHelpLink() {
		ExternalApplicationModule.openUrl("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
	}

	@FXML
	private void renameFile() {

		// get the currently selected entry in the table
		MusicVideoTableView selectedEntry = musicVideoTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			Path pathOfSelectedFile = this.mainWindow.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1].getPath();
			// check if the file really exists and isn't a directory
			File selectedFile = pathOfSelectedFile.toFile();
			if (selectedFile.exists() && selectedFile.isFile()) {
				// show dialog to rename the file
				String a = DialogModule.textInputDialog("Rename wrong formatted file", selectedFile.getName(),
						"Rename the file", "Enter a new name:");
				if (a != null) {
					FileReadWriteModule.rename(selectedFile,
							Paths.get(selectedFile.getParentFile().getAbsolutePath() + "/" + a).toFile());
					refreshMusicVideoTable();
				}
			}
		}

	}

	@FXML
	private void openMusicVideoPlaylistFileLeftClick() {
		if (this.leftMouseKeyWasPressed) {
			openSelectedPlaylistVideoFile();
		}
	}

	private void openSelectedPlaylistVideoFile() {

		// get the currently selected entry in the table
		MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			this.mainWindow.getMusicVideohandler().openMusicVideo(selectedEntry.getMusicVideoIndex() - 1);
		}

	}

	@FXML
	private void savePlaylistDialog() {
		if (this.tableDataPlaylist.isEmpty()) {
			DialogModule.informationAlert("Operation failed", "There is no playlist!", AlertType.ERROR);
		} else {
			ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
			File[] fileName = DialogModule.chooseFile(this.mainWindow.getPrimaryStage(), "Save current playlist",
					FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { jsonFilter },
					DialogModule.CHOOSE_ACTION.SAVE);
			if (fileName != null && fileName[0] != null) {
				File realFileName = new File(fileName[0].getParent() + "/" + fileName[0].getName());
				this.tableDataPlaylist.clear();
				this.refreshMusicVideoPlaylistTable();
				this.mainWindow.getMusicVideohandler().savePlaylist(realFileName);
			}
		}

	}

	@FXML
	private void loadPlaylistDialog() {
		if (this.tableDataPlaylist.isEmpty() || DialogModule.yesNoDialog("This action has consequences",
				"Do you really want to clear your current playlist?",
				"The current playlist will be cleared if you continue")) {
			ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
			File[] fileName = DialogModule.chooseFile(this.mainWindow.getPrimaryStage(), "Load a saved playlist",
					FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { jsonFilter },
					DialogModule.CHOOSE_ACTION.NORMAL);
			if (fileName != null && fileName[0] != null) {
				this.mainWindow.getMusicVideohandler().loadPlaylist(fileName[0]);
			}
			refreshMusicVideoPlaylistTable();
		}
	}

	@FXML
	private void clearSelectionPlaylistTable() {
		this.playlistTable.getSelectionModel().clearSelection();
	}

	@FXML
	private void removeEntry() {
		// get the currently selected entry in the table
		MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			this.mainWindow.getMusicVideohandler().removeEntryFromPlaylist(selectedEntry.getIndex());
		}
		refreshMusicVideoPlaylistTable();

	}

	@FXML
	private void editEntry() {
		// get the currently selected entry in the table
		MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			String[] authorComment = DialogModule.playlistEditDialog(selectedEntry.getAuthor(),
					selectedEntry.getComment(), "Edit the selected Playlist entry", "Edit author and comment",
					"Save Changes");

			if (authorComment != null && authorComment[0] != null) {
				if (authorComment[1] == null) {
					authorComment[1] = "";
				}
				this.mainWindow.getMusicVideohandler().editMusicVideoToPlaylist(selectedEntry.getIndex(),
						authorComment[0], authorComment[1]);
				refreshMusicVideoPlaylistTable();
			}

		}
	}

	@FXML
	private void sftpReset() {
		this.mainWindow.getMusicVideohandler().resetSftp();
		checkNetwork();
	}

	@FXML
	private void sftpParty() {
		this.mainWindow.getMusicVideohandler().transferHtmlParty();
	}

	@FXML
	private void sftpStatic() {
		this.mainWindow.getMusicVideohandler().transferHtmlStatic();
	}

	@FXML
	private void sftpSearch() {
		this.mainWindow.getMusicVideohandler().transferHtmlSearch();
	}

	@FXML
	public void changeAlwaysSave() {
		this.menuButtonAlwaysSave.setSelected(this.mainWindow.getMusicVideohandler()
				.setAlwaysSave(!this.mainWindow.getMusicVideohandler().getAlwaysSave()));
	}

	@FXML
	private void clearMusicVideoPlaylistTable() {
		if (!this.tableDataPlaylist.isEmpty()
				&& DialogModule.yesNoDialog("Clear the playlist", "Do you really want to clear the playlist?",
						"If you enter yes all playlist elements will be removed!")) {
			this.mainWindow.getMusicVideohandler().clearPlaylist();
			this.tableDataPlaylist.clear();
		}

	}

}
