package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import server.Budget;
import server.ClientThread;
import server.DatabaseTest;
import server.Destination;
import server.Geocode;
import server.Song;


public class Controller extends Control {

	private Main main;
	private DatabaseTest database;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private StackPane root;
	/*
	 * launch page
	 */
	@FXML
	private VBox launchPane;
	@FXML
	private ImageView launchLogo;
	@FXML
	private Button launchLoginButton;
	@FXML
	private Button launchCreateAccountButton;
	@FXML
	private Button launchGuestButton;
	/*
	 * login page
	 */
	@FXML
	private VBox loginPane;
	@FXML
	private ImageView loginLogo;
	@FXML
	private TextField loginUsernameTextField;
	@FXML
	private TextField loginPasswordTextField;
	@FXML
	private Button loginButton;
	@FXML
	private Button loginBackToStartButton;
	@FXML
	private Label loginLabel;
	/*
	 * create account page
	 */
	@FXML
	private VBox createAccountPane;
	@FXML
	private ImageView createAccountLogo;
	@FXML
	private TextField fnameTextField;
	@FXML
	private TextField lnameTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private TextField imageTextField;
	@FXML
	private Button createAccountButton;
	@FXML
	private Button createAccountBackToStartButton;
	@FXML
	private Label createAccountLabel;
	/*
	 * menu/title bar
	 */
	@FXML
	private BorderPane feedPane;
	@FXML
	private HBox titleHBox;
	@FXML
	private ImageView titleLogo;
	@FXML
	private MenuButton menuButton;
	@FXML
	private MenuItem feedMenuOption;
	@FXML
	private MenuItem groupsMenuOption;
	@FXML
	private MenuItem createGroupMenuOption;
	@FXML
	private MenuItem logoutMenuOption;
	@FXML
	private MenuItem quitMenuOption;
	@FXML
	private ImageView menuButtonImage;
	@FXML
	private Button notificationButton;
	@FXML
	private ImageView notificationButtonImage;
	/*
	 * feed page
	 */
	private MapView mapView;
	@FXML
	private VBox feedLeftVBox;
	@FXML
	private ListView feedListView;
	@FXML
	private Button goToTripButton;
	@FXML
	private Label feedGoToTripErrorLabel;
	@FXML
	private Button joinTripButton;
	/*
	 * groups page
	 */
	@FXML private BorderPane groupsPane;
	private MapView myGroupMapView;
	@FXML
	private ListView myGroupsListView;
	@FXML
	private Button myGoToTripButton;
	@FXML
	private Label goToTripErrorLabel;
	@FXML
	private VBox groupsLeftVBox;
	@FXML
	private VBox groupsRightVBox;
	/*
	 * create group page
	 */
	@FXML
	private VBox createGroupPane;
	@FXML
	private ImageView createGroupLogo;
	@FXML
	private TextField groupNameTextField;
	@FXML
	private TextField startLocationTextField;
	@FXML
	private ComboBox<String> hourComboBox;
	@FXML
	private ComboBox<String> minuteComboBox;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private Slider CreateGroupSlider;
	@FXML
	private TextArea commentTextField;
	@FXML
	private Button createGroupButton;
	@FXML
	private Label createGroupErrorLabel;

	/*
	 * add user page
	 */
	@FXML
	private VBox addUserPane;
	@FXML
	private ComboBox<String> selectGroupComboBox;
	@FXML
	private ListView<String> currentMemberListView;
	@FXML
	private TextField addUserTextField;
	@FXML
	private Button addUserButton;
	@FXML
	private Label addUserErrorLabel;
	@FXML
	private Button skipAddUserButton;
	/*
	 * specific group page
	 */
	private MapView groupMapView;
	private Geocode geo;
	@FXML
	private VBox specificGroupPane;
	@FXML
	private Label groupNameLabel;
	@FXML
	private Label meetingTimeLabel;
	@FXML
	private Label startDateLabel;
	@FXML
	private Label endDateLabel;
	@FXML
	private Label commentLabel;
	@FXML
	private HBox specificGroupButtonHBox;
	@FXML
	private Button budgetButton;
	@FXML
	private Button songsButton;
	@FXML
	private Button addUsersButton;
	@FXML
	private TextField destinationSearchField;
	@FXML
	private Label destinationSearchErrorLabel;
	@FXML
	private Button destinationSearchButton;
	@FXML
	private Label destinationCoordinateLabel;
	@FXML
	private Button addMarkerButton;
	@FXML
	private VBox tripMapVBox;
	@FXML
	private ListView<String> destinationListView;
	// comment editor
	@FXML
	private VBox editCommentPopup;
	@FXML
	private Button closeCommentEditorButton;
	@FXML
	private TextArea commentTextBox;
	@FXML
	private Button editCommentSubmitButton;
	// budget
	@FXML
	private VBox budgetPopup;
	@FXML
	private Button closeBudgetButton;
	@FXML
	private TableView<Budget> budgetTableView;
	@FXML
	private Label addBudgetErrorLabel;
	@FXML
	private TableColumn<Budget, String> budgetItemColumn;
	@FXML
	private TableColumn<Budget, String> budgetAmountColumn;
	@FXML
	private TextField budgetItemTextField;
	@FXML
	private TextField budgetAmountTextField;
	@FXML
	private Button addBudgetItemButton;
	// songs
	@FXML
	private VBox songsPopup;
	@FXML
	private Button closeSongsButton;
	@FXML
	private ListView<String> songsListView;
	@FXML
	private Button upvoteSongButton;
	@FXML
	private Button downvoteSongButton;
	@FXML
	private Label songsErrorLabel;
	@FXML
	private TextField addSongTextField;
	@FXML
	private Button addSongButton;

	/*
	 * notifications
	 */
	private Vector<String> notifications;
	@FXML
	private StackPane notificationStackPane;
	@FXML
	private VBox notificationPopup;
	@FXML
	private ListView<String> notificationListView;
	@FXML
	private Button clearNotificationsButton;
	@FXML
	private Button closeNotificationsButton;

	/*
	 * GUEST PAGE
	 */
	private MapView guestMapView;
	@FXML
	private BorderPane guestFeedPane;
	@FXML
	private HBox guestTitleHBox;
	@FXML
	private MenuButton guestMenuButton;
	@FXML
	private MenuItem loginMenuOption;
	@FXML
	private ListView guestFeedListView;

	/*
	 * server stuff
	 */
	private ClientThread thread;
	private String username;

	private int currentGroupID; // need to keep track of current group in order to modify comments


	public void initialize() {
		System.out.println("Initialize controller, create model objects");

		database = new DatabaseTest();
		// initialize map
		mapView = new MapView();
		guestMapView = new MapView();
		//initialize the geocode
		geo = new Geocode();

		// add to feed borderpane
		feedPane.setCenter(mapView);
		guestFeedPane.setCenter(guestMapView);

		// initialize comboboxes
		initComboBoxes();

		// init notification list
		notifications = new Vector<String>();

	}

	public void setMain(Main main) {
		this.main =	main;
	}

	/*
	 * LAUNCH PAGE BUTTON CONTROLS
	 */
	// if login button is clicked, show login pane
	@FXML
	private void showLoginPage() {
		hideAllPanes();
		loginPane.setVisible(true);
		loginPane.toFront();
	}
	// if create account button is clicked, show create account pane
	@FXML
	private void showCreateAccountPage() {
		launchPane.setVisible(false);
		createAccountPane.setVisible(true);
		createAccountPane.toFront();
	}
	// if guest button is clicked, show feed pane
	@FXML
	private void showGuestFeedPage() {
		hideAllPanes();
		Map<Integer, String> pubGroups = database.getAllPublicGroup();
		ObservableList<String> tripNames = FXCollections.observableArrayList();
		for(String value : pubGroups.values())
		{
			tripNames.add(value);
		}
		guestFeedListView.setItems((ObservableList<String>) tripNames);

		launchPane.setVisible(false);
		guestFeedPane.setVisible(true);
		guestFeedPane.toFront();
	}

	/*
	 * LOGIN PAGE BUTTON CONTROLS
	 */
	// if login button is clicked, check credentials
	@FXML
	private void attemptLogin() {

		String username = loginUsernameTextField.getText();
		String password = loginPasswordTextField.getText();
		if(!database.validateUsername(username))
		{
			if(database.getPassword(username).equals(password))
			{
				//correct username and password
				//pull up the home page
				this.username = username;
				hideAllPanes();
				Map<Integer, String> pubGroups = database.getAllPublicGroup();
				ObservableList<String> tripNames = FXCollections.observableArrayList();
				for(String value : pubGroups.values())
				{
					tripNames.add(value);
				}
				feedListView.setItems((ObservableList<String>) tripNames);
				feedPane.setVisible(true);
				feedPane.toFront();
				thread = new ClientThread(username, this);
			}
			else
			{
				//notify user they have the wrong password
				loginLabel.setText("Incorrect password!");
				loginLabel.setTextFill(Color.rgb(210, 39, 30));
				username = "";
				password = "";
			}
		}
		else
		{
			//notify user they have the wrong username
			loginLabel.setText("Incorrect username!");
			loginLabel.setTextFill(Color.rgb(210, 39, 30));
			username = "";
			password = "";
		}
		loginUsernameTextField.clear();
		loginPasswordTextField.clear();
	}

	// if back to start button is clicked, show launch pane
	@FXML
	private void loginBackToStart() {
		loginPane.setVisible(false);
		launchPane.setVisible(true);
		launchPane.toFront();
	}
	/*
	 * CREATE ACCOUNT PAGE BUTTON CONTROLS
	 */
	// if create account button is clicked, check credentials
	@FXML
	private void attemptCreateAccount() {
		// use login class functions to validate account creation
		// if attempt returns true, create account and show feed pane
		// else show error message and try again
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String fname = fnameTextField.getText();
		String lname = lnameTextField.getText();
		String image = imageTextField.getText();


		if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()
				|| fnameTextField.getText().isEmpty() || lnameTextField.getText().isEmpty()
				|| imageTextField.getText().isEmpty()) {
			System.out.println("Field(s) cannot be empty!");
			createAccountLabel.setText("Field(s) cannot be empty!");
			createAccountLabel.setTextFill(Color.rgb(210, 39, 30));
			username = "";
			password = "";
			fname ="";
			lname="";
			image="";
		}
		else {
			// success
			if(database.validateUsername(username))
			{
				database.addUser(username, password, fname, lname, image);
				this.username = username;
				thread = new ClientThread(username, this);
				hideAllPanes();
				feedPane.setVisible(true);
				feedPane.toFront();
			}
			else
			{
				//notify user they have the wrong username
				createAccountLabel.setText("Username has already been taken!");
				createAccountLabel.setTextFill(Color.rgb(210, 39, 30));
				username = "";
				password = "";
				fname ="";
				lname="";
				image="";
			}
		}
		usernameTextField.clear();
		fnameTextField.clear();
		lnameTextField.clear();
		imageTextField.clear();
		passwordTextField.clear();
	}
	// if back to start button is clicked, show launch pane
	@FXML
	private void createAccountBackToStart() {
		createAccountPane.setVisible(false);
		launchPane.setVisible(true);
		launchPane.toFront();
	}
	/*
	 * CREATE GROUP MENU OPTION CONTROLS
	 */
	// if create account button is clicked, check credentials
	@FXML
	private void attemptCreateGroup() {
		// check that all fields are completed
		if ( groupNameTextField.getText().isEmpty() || startLocationTextField.getText().isEmpty()
				|| hourComboBox.getSelectionModel().isEmpty() || minuteComboBox.getSelectionModel().isEmpty()
				|| startDatePicker.getValue() == null || endDatePicker.getValue() == null ) {
			//System.out.println("Field(s) cannot be empty!");
			createGroupErrorLabel.setText("Field(s) cannot be empty!");
			createGroupErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		// make sure end date is not before start date
		else if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
			createGroupErrorLabel.setText("End date cannot be earlier than start date!");
			createGroupErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		else {
			String name = groupNameTextField.getText();
			String startLocation = startLocationTextField.getText();
			String startDate = startDatePicker.getValue().toString();
			String endDate = endDatePicker.getValue().toString();
			String meetingTime = hourComboBox.getValue().toString() + ":" + minuteComboBox.getValue().toString();
			double value =  CreateGroupSlider.getValue();
			boolean ifPublic = false;
			if(value == 0){
				ifPublic = true;
			}
			String comment = commentTextField.getText();
			boolean isValid = geo.parseLatLon(startLocation);
			if(!isValid)
			{
				createGroupErrorLabel.setText("Invalids starting location.");
			}
			// successfully created group
			else if(database.validateGroupName(name) && isValid) {
				database.createNewGroup(name, ifPublic, startDate, endDate, meetingTime, comment);
				int tripID = database.getGroupID(name);
				database.addDestination(startLocation, tripID, geo.la, geo.lo);
				database.addUserGroup(tripID, username);

				// show add user page
				showAddUserPage();
			}
			else {
				//notify user they have the wrong username
				createGroupErrorLabel.setText("Trip name has already been taken!");
				createGroupErrorLabel.setTextFill(Color.rgb(210, 39, 30));
			}
		}
		// clear and reset all fields
		groupNameTextField.clear();
		startLocationTextField.clear();
		startDatePicker.getEditor().clear();
		startDatePicker.setValue(null);
		endDatePicker.getEditor().clear();
		endDatePicker.setValue(null);
		hourComboBox.setValue(null);
		minuteComboBox.setValue(null);
		commentTextField.clear();

	}

	/*
	 * ADD USER TO GROUP
	 */
	@FXML
	private void showAddUserPage() {
		hideAllPanes();
		// make error label blank
		addUserErrorLabel.setText("");
		// populate groups combobox
		int userID = database.getUserID(this.username);
		ArrayList<String> g = database.getGroupsByUser(userID);
		ObservableList<String> groups = FXCollections.observableArrayList(g);
		selectGroupComboBox.setItems(groups);
		addUserPane.setVisible(true);
		addUserPane.toFront();
	}

	@FXML
	private void populateCurrentMemberList() {
		// populate current member list view
		int groupID = database.getGroupID(selectGroupComboBox.getSelectionModel().getSelectedItem());
		ArrayList<String> m = database.getUsersFromGroup(groupID);
		ObservableList<String> members = FXCollections.observableArrayList(m);
		currentMemberListView.setItems(members);
	}

	@FXML
	private void addUserToGroup() {
		// error if user doesn't select a group
		if (selectGroupComboBox.getSelectionModel().getSelectedItem() == null) {
			addUserErrorLabel.setText("Please select a group to add the user to");
			addUserErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		// user selected a group
		else {
			// error if user didn't enter a username
			if (addUserTextField.getText().isEmpty()) {
				addUserErrorLabel.setText("Please enter a user to add");
				createGroupErrorLabel.setTextFill(Color.rgb(210, 39, 30));
			}
			else {
				// success
				if (!database.validateUsername(addUserTextField.getText().toString())) {
					int id = database.getGroupID(selectGroupComboBox.getSelectionModel().getSelectedItem());
					String username = addUserTextField.getText().toString();
					database.addUserGroup(id, username);
					// send message to server
					thread.sendMessageToServer(selectGroupComboBox.getSelectionModel().getSelectedItem(), "AddUser", addUserTextField.getText().toString());
					// let user know that username was added
					addUserErrorLabel.setText(username + " has been added to " + selectGroupComboBox.getSelectionModel().getSelectedItem());
					addUserErrorLabel.setTextFill(Color.rgb(63, 232, 118));
					// reload current member list
					populateCurrentMemberList();
					addUserTextField.clear();
				}
				// user doesn't exist
				else {
					addUserErrorLabel.setText("User doesn't exist!");
					addUserErrorLabel.setTextFill(Color.rgb(210, 39, 30));
				}
			}
		}


	}
	
	@FXML
	private void joinTrip() {
		// error if no group selected
		if (feedListView.getSelectionModel().getSelectedItem() == null) {
			feedGoToTripErrorLabel.setText("Please select a group");
			feedGoToTripErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		else {
			String name = feedListView.getSelectionModel().getSelectedItem().toString();
			int id = database.getGroupID(name);
			// already in group
			if (database.checkPair(id, database.getUserID(username))) {
				feedGoToTripErrorLabel.setText("You are already a member of this group");
				feedGoToTripErrorLabel.setTextFill(Color.rgb(210, 39, 30));
			}
			else {
				database.addUserGroup(id, username);
				feedGoToTripErrorLabel.setText("You have joined " + name);
				feedGoToTripErrorLabel.setTextFill(Color.rgb(210, 39, 30));
			}
		}
	}

	/*
	 * SPECIFIC GROUP PAGE
	 */
	@FXML
	private void showTripPage() {
		if (feedListView.getSelectionModel().getSelectedItem() == null) {
			goToTripErrorLabel.setText("Please select a group");
			goToTripErrorLabel.setTextFill(Color.rgb(210, 39, 30));
			goToTripErrorLabel.setVisible(true);
			goToTripErrorLabel.toFront();
		}
		else {
			String name = feedListView.getSelectionModel().getSelectedItem().toString();
			int id = database.getGroupID(name);
			currentGroupID = id;
			// map constructor
			tripMapVBox.getChildren().remove(groupMapView);
			//initialize group map
			groupMapView = new MapView(id);
			//groupMapView.browser.setMinHeight(400);
			groupMapView.browser.setMaxHeight(400);
			tripMapVBox.getChildren().add(groupMapView);
			// set group name labels
			groupNameLabel.setText(name);
			//get all destinations and display them
			ArrayList<Destination> allPlaces = database.getDestination(id);
			ObservableList<String> tripNames = FXCollections.observableArrayList();
			
			for(Destination d : allPlaces)
			{
				tripNames.add(d.getDestination());
			}
			destinationListView.setItems((ObservableList<String>) tripNames);
			meetingTimeLabel.setText("Meeting Time: " + database.getMeetingTime(id));
			startDateLabel.setText("Start Date: " + database.getStartDate(id));
			endDateLabel.setText("End date: " + database.getEndDate(id));
			commentLabel.setText(database.getComment(id));
			commentTextBox.setText(database.getComment(id));
		}

		//hide current panel
		hideAllPanes();
		//show specific group panel
		specificGroupPane.setVisible(true);
		specificGroupPane.toFront();

	}
	// comment editor
	@FXML
	private void showCommentEditor() {
		editCommentPopup.setVisible(true);
		editCommentPopup.toFront();
	}
	@FXML
	private void closeCommentEditor() {
		editCommentPopup.setVisible(false);
	}
	@FXML
	private void editComment() {
		database.changeComment(currentGroupID, commentTextBox.getText().toString());
		commentLabel.setText(commentTextBox.getText().toString());
		//System.out.println("comment for group " + currentGroupID + " changed");
	}
	// budget
	@FXML
	private void showBudget() {
		// get budget and add to table
		ObservableList<Budget> budgetData = FXCollections.observableArrayList();
		for (String i : database.getBudgetList(currentGroupID).keySet()) {
			//System.out.println(i);
			//System.out.println(database.getBudgetList(currentGroupID).get(i));
			budgetData.add(new Budget(i, database.getBudgetList(currentGroupID).get(i)));
		}
		budgetItemColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("item"));
		budgetAmountColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("amount"));
		budgetTableView.setItems(budgetData);
		//budgetTableView.getColumns().addAll(budgetItemColumn, budgetAmountColumn);
		budgetPopup.setVisible(true);
		budgetPopup.toFront();
	}
	@FXML
	private void hideBudget() {
		budgetPopup.setVisible(false);
	}
	@FXML
	private void addBudgetItem() {
		// error if both fields not filled out
		if (budgetItemTextField.getText().isEmpty() || budgetAmountTextField.getText().isEmpty()) {
			addBudgetErrorLabel.setText("Please fill out both fields");
			addBudgetErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		else {
			try {
				String item = budgetItemTextField.getText().toString();
				int amount = Integer.parseInt(budgetAmountTextField.getText().toString());
				database.addBudget(currentGroupID, item, amount);
				// send message
				thread.sendMessageToServer(database.getTripName(currentGroupID), "AddBudget", item + " (" + amount + ")");
				addBudgetErrorLabel.setText(item + " added to budget");
				addBudgetErrorLabel.setTextFill(Color.rgb(63, 232, 118));
				showBudget();

				// clear fields
				budgetItemTextField.clear();
				budgetAmountTextField.clear();

			} catch (NumberFormatException e) {
				addBudgetErrorLabel.setText("Amount must be an integer");
				addBudgetErrorLabel.setTextFill(Color.rgb(210, 39, 30));
			}
		}
	}
	// songs
	@FXML
	private void showSongs() {
		ArrayList<Song> songlist = database.getSongQueue(currentGroupID);
		ObservableList<String> songs = FXCollections.observableArrayList();
		for (int i = 0; i < songlist.size(); i++) {
			songs.add(songlist.get(i).getName());
		}
		songsListView.setItems(songs);

		songsPopup.setVisible(true);
		songsPopup.toFront();
	}
	@FXML
	private void hideSongs() {
		songsPopup.setVisible(false);
	}
	@FXML
	private void upvoteSong() {
		// error if no song selected
		if (songsListView.getSelectionModel().isEmpty()) {
			songsErrorLabel.setText("Please select a song");
			songsErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		//success
		else {
			database.upvoteSong(songsListView.getSelectionModel().getSelectedItem().toString(), currentGroupID);
			songsErrorLabel.setText(songsListView.getSelectionModel().getSelectedItem().toString() + " has been upvoted");
			songsErrorLabel.setTextFill(Color.rgb(63, 232, 118));
			showSongs();
		}
	}
	@FXML
	private void downvoteSong() {
		// error if no song selected
		if (songsListView.getSelectionModel().isEmpty()) {
			songsErrorLabel.setText("Please select a song");
			songsErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		//success
		else {
			database.downvoteSong(songsListView.getSelectionModel().getSelectedItem().toString(), currentGroupID);
			songsErrorLabel.setText(songsListView.getSelectionModel().getSelectedItem().toString() + " has been downvoted");
			songsErrorLabel.setTextFill(Color.rgb(63, 232, 118));
			showSongs();
		}
	}
	@FXML
	private void addSong() {
		// error if no song entered
		if (addSongTextField.getText().isEmpty()) {
			songsErrorLabel.setText("Please enter a song name");
			songsErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		// success
		else {
			database.addSong(currentGroupID, addSongTextField.getText().toString());
			songsErrorLabel.setText(addSongTextField.getText().toString() + " has been added");
			songsErrorLabel.setTextFill(Color.rgb(63, 232, 118));
			thread.sendMessageToServer(database.getTripName(currentGroupID), "AddSong", addSongTextField.getText().toString());

			addSongTextField.clear();
			showSongs();
		}
	}

	// destination search
	@FXML
	private void searchForLocation() {
		System.out.println("button clicked");
		// error if empty search field
		if (destinationSearchField.getText().isEmpty()) {
			destinationSearchErrorLabel.setText("Please enter a location");
			destinationSearchErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		else {
			String loc = destinationSearchField.getText().toString();
			boolean valid = geo.parseLatLon(loc);
			geo.destinationName = loc;
			if (valid) {
				System.out.println(geo.lo + " " + geo.la);

				// show labels
				destinationCoordinateLabel.setText("Longitude: " + geo.lo + " Latitude: " + geo.la);
				destinationCoordinateLabel.setVisible(true);
				// display the button to add the marker
				addMarkerButton.setVisible(true);
			}
			else
			{
				destinationCoordinateLabel.setText("Invalid location.");
				destinationCoordinateLabel.setVisible(true);
			}
		}
	}
	@FXML
	private void addMarkerToMap() {
		groupMapView.addMarkerAt(geo.la, geo.lo);
		//add it to the database 
		database.addDestination(geo.destinationName, currentGroupID , geo.la, geo.lo);
		//get all destinations and display them
		ArrayList<Destination> allPlaces = database.getDestination(currentGroupID);
		ObservableList<String> tripNames = FXCollections.observableArrayList();
		
		for(Destination d : allPlaces)
		{
			tripNames.add(d.getDestination());
		}
		//send thread information

	}


	/*
	 * FEED MENU OPTION CONTROLS
	 */
	// if feed button is clicked, show feed pane
	@FXML
	private void menuShowFeed() {
		hideAllPanes();
		// reload list view
		Map<Integer, String> pubGroups = database.getAllPublicGroup();
		ObservableList<String> tripNames = FXCollections.observableArrayList();
		for(String value : pubGroups.values())
		{
			tripNames.add(value);
		}
		feedListView.setItems((ObservableList<String>) tripNames);
		feedPane.setVisible(true);
		feedPane.toFront();
	}
	// if my groups button is clicked, show groups pane
	@FXML
	private void menuShowGroups() {
		hideAllPanes();
		int id = database.getUserID(username);
		ArrayList<String> userGroups = database.getGroupsByUser(id);
		ObservableList<String> tripNames = FXCollections.observableArrayList();
		for(String value : userGroups)
		{
			tripNames.add(value);
		}
		myGroupsListView.setItems((ObservableList<String>) tripNames);
		myGroupsListView.setVisible(true);

		//add the map to the view
		groupsPane.getChildren().remove(myGroupMapView);
		myGroupMapView = new MapView(id, false);
		groupsPane.setCenter(myGroupMapView);
		//groupsPane.getChildren().add(myGroupMapView);
		groupsPane.setVisible(true);

		groupsPane.toFront();
		//thread.sendMessageToServer("Trip to Chicago", "AddUser", "Hiiii from " + thread.getUsername());
	}
	@FXML
	private void myShowTripPage() {
		//gets called when the user clicks the button to show a trip
		if (myGroupsListView.getSelectionModel().getSelectedItem() == null) {
			//addUserErrorLabel.setText("Please select a group");
			//createGroupErrorLabel.setTextFill(Color.rgb(210, 39, 30));
		}
		else {
			String name = myGroupsListView.getSelectionModel().getSelectedItem().toString();
			int id = database.getGroupID(name);
			currentGroupID = id;
			// map constructor
			tripMapVBox.getChildren().remove(groupMapView);
			//initialize group map
			groupMapView = new MapView(id);
			groupMapView.browser.setMaxHeight(400);
			tripMapVBox.getChildren().add(groupMapView);
			// set group name labels
			groupNameLabel.setText(name);
			//get all destinations and display them
			ArrayList<Destination> allPlaces = database.getDestination(id);
			ObservableList<String> tripNames = FXCollections.observableArrayList();
			
			for(Destination d : allPlaces)
			{
				tripNames.add(d.getDestination());
			}
			destinationListView.setItems((ObservableList<String>) tripNames);
			meetingTimeLabel.setText("Meeting Time: " + database.getMeetingTime(id));
			startDateLabel.setText("Start Date: " + database.getStartDate(id));
			endDateLabel.setText("End date: " + database.getEndDate(id));
			commentLabel.setText(database.getComment(id));
			commentTextBox.setText(database.getComment(id));

			// get budget and add to table
			ObservableList<Budget> budgetData = FXCollections.observableArrayList();
			for (String i : database.getBudgetList(id).keySet()) {
				budgetData.add(new Budget(i, database.getBudgetList(id).get(i)));
			}
			budgetItemColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("item"));
			budgetAmountColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("amount"));
			budgetTableView.setItems(budgetData);
			//budgetTableView.getColumns().addAll(budgetItemColumn, budgetAmountColumn);
		}

		//hide current panel
		hideAllPanes();
		//show specific group panel
		specificGroupPane.setVisible(true);
		specificGroupPane.toFront();
	}
	// if create group button is clicked, show create group pane
	@FXML
	private void menuShowCreateGroup() {
		hideAllPanes();
		createGroupPane.setVisible(true);
		createGroupPane.toFront();
	}
	// if logout button is clicked, logout and show login page
	@FXML
	private void menuLogout() {
		this.username = null;
		this.thread.killThread();
		this.clearNotifications();

		hideAllPanes();
		loginPane.setVisible(true);
		loginPane.toFront();
	}
	// if quit button is clicked, show launch page
	@FXML
	private void menuQuit() {
		hideAllPanes();
		launchPane.setVisible(true);
		launchPane.toFront();
	}


	/*
	 * NOTIFICATION CONTROLS
	 */
	@FXML
	private void showNotifications() {
		ObservableList<String> n = FXCollections.observableArrayList(notifications);
		notificationListView.setItems(n);
		notificationStackPane.setVisible(true);
		notificationStackPane.toFront();
	}
	@FXML
	private void closeNotifications() {
		notificationStackPane.setVisible(false);
		notificationStackPane.toBack();
	}
	@FXML
	private void clearNotifications() {
		notificationListView.getItems().clear();
		notifications.clear();
	}
	// for thread to add notification to vector
	public void addNotification(String n) {
		notifications.add(n);
		showNotifications();
	}


	/*
	 * GOOGLE MAPS CONTAINER CLASS
	 */
	class MapView extends Region {

		final WebView browser = new WebView();
		final WebEngine webEngine = browser.getEngine();

		public MapView() {
			browser.setMinWidth(740);
			browser.setMinHeight(740);

			// load the web page
			webEngine.getLoadWorker().stateProperty().addListener(
					new ChangeListener<State>() {
						@Override public void changed(ObservableValue ov, State oldState, State newState) {
							if (newState == State.SUCCEEDED) {
								//go through their first destinations and make a marker.
								Map<Integer, String> dest = database.getAllPublicGroup();
								for (int id : dest.keySet()) {
									double lon = database.getFirstDesLon(id);
									double lat = database.getFirstDesLat(id);
									if(lon != -1.0 && lat != -1.0)
									{
										try{
											webEngine.executeScript("addMarker("+lon+", "+lat+")");
										} catch (JSException js)
										{
											//dont do anything
										}
									}
								}
								if(dest.size() == 0)
								{
									
									ObservableList<String> tripNames = FXCollections.observableArrayList();
									destinationListView.setItems(tripNames);

								}
							}
						}
					});

			URL url = getClass().getResource("feed.html");
			webEngine.load(url.toExternalForm());
			//webEngine.load("http://maps.google.com");
			//add the web view to the scene
			getChildren().add(browser);

		}
		//CALLED TO DISPLAY GROUP'S DESTINATIONS
		public MapView(int groupID) {
			browser.setMinWidth(740);
			browser.setMinHeight(400);
			browser.setPrefHeight(740);
			browser.setMaxHeight(740);

			// load the web page
			webEngine.getLoadWorker().stateProperty().addListener(
					new ChangeListener<State>() {
						@Override public void changed(ObservableValue ov, State oldState, State newState) {
							if (newState == State.SUCCEEDED) {
								//go through their first destinations and make a marker.
								ArrayList<Destination> dest = database.getDestination(groupID);
								for(Destination d : dest)
								{
									double lon = d.getLon();
									double lat = d.getLat();
									if(lon != -1.0 && lat != -1.0)
									{
										try{
											webEngine.executeScript("addMarker("+lon+", "+lat+")");
										} catch (JSException js)
										{
											//dont do anything
										}
									}
								}
								if(dest.size() == 0)
								{
									
									ObservableList<String> tripNames = FXCollections.observableArrayList();
									destinationListView.setItems(tripNames);

								}

							}
						}
					});

			URL url = getClass().getResource("group.html");
			webEngine.load(url.toExternalForm());
			//webEngine.load("http://maps.google.com");
			//add the web view to the scene
			getChildren().add(browser);

		}
		//CALLED TO DISPLAY A USER'S PERSONAL GROUPS
		public MapView(int userID, boolean b) {
			browser.setMinWidth(740);
			browser.setMinHeight(740);

			// load the web page
			webEngine.getLoadWorker().stateProperty().addListener(
					new ChangeListener<State>() {
						@Override public void changed(ObservableValue ov, State oldState, State newState) {
							if (newState == State.SUCCEEDED) {
								//go through their first destinations and make a marker.
								ArrayList<String> userGroups = database.getGroupsByUser(userID);
								for(String g : userGroups)
								{
									int id = database.getGroupID(g);
									double lat = database.getFirstDesLat(id);
									double lon = database.getFirstDesLon(id);
									if(lon != -1.0 && lat != -1.0)
									{
										try{
											webEngine.executeScript("addMarker("+lon+", "+lat+")");
										} catch (JSException js)
										{
											//dont do anything
										}
									}
								}
								if(userGroups.size() == 0)
								{
									
									ObservableList<String> tripNames = FXCollections.observableArrayList();
									destinationListView.setItems(tripNames);

								}

							}
						}
					});

			URL url = getClass().getResource("group.html");
			webEngine.load(url.toExternalForm());
			//webEngine.load("http://maps.google.com");
			//add the web view to the scene
			getChildren().add(browser);

		}

		public void addMarkerAt(double lat, double lon)
		{
			try{
				webEngine.executeScript("addMarker("+lat+", "+lon+")");
			} catch (JSException js)
			{
				//dont do anything
			}
		}
		private Node createSpacer() {
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			return spacer;
		}

		@Override protected void layoutChildren() {
			double w = getWidth();
			double h = getHeight();
			layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
		}

		@Override protected double computePrefWidth(double height) {
			return 740;
		}

		@Override protected double computePrefHeight(double width) {
			return 740;
		}
	}
	/*
	// budget class - for populating table with data
	class Budget {

		private SimpleStringProperty item;
		private SimpleStringProperty amount;

		private Budget(String item, int amount) {
			this.item = new SimpleStringProperty(item);
			this.amount = new SimpleStringProperty("$" + amount);
		}

		public String getItem() {
			return item.get();
		}

		public String getAmount() {
			return amount.get();
		}
	}
	 */

	/*
	 * HELPER FUNCTIONS
	 */
	// set all panes visibility to false
	private void hideAllPanes() {
		launchPane.setVisible(false);
		loginPane.setVisible(false);
		createAccountPane.setVisible(false);
		feedPane.setVisible(false);
		specificGroupPane.setVisible(false);
		createGroupPane.setVisible(false);
		addUserPane.setVisible(false);
		groupsPane.setVisible(false);
		guestFeedPane.setVisible(false);
	}
	// initialize combobox items
	private void initComboBoxes() {
		ObservableList<String> hours = FXCollections.observableArrayList();
		for (int i = 0; i < 24; i++) {
			hours.add(String.format("%02d", i));
		}
		hourComboBox.setItems((ObservableList<String>) hours);

		ObservableList<String> minutes = FXCollections.observableArrayList();
		for (int i = 0; i < 60; i++) {
			minutes.add(String.format("%02d", i));
		}
		minuteComboBox.setItems(minutes);
	}

}
