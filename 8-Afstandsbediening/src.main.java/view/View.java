package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import model.business.User;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Dimension;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel View with TabPanel to show different functionalities
 */

public class View extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	
	/** FIELDS FOR 'Entrance' TAB **/
	private JPanel entranceTab;
	
	private JComboBox<User> entranceTabListUsers;
	private JButton entranceTabBtnRegister;
	private JButton entranceTabBtnDeactivate;
	private JButton entranceTabBtnAskEntrance;
	
	private JLabel entranceTabLblSerialNumberUser;
	private JLabel entranceTabLblFrequencyUser;
	private JLabel entranceTabLblRegisteredUser;
	
	private JLabel entranceTabLblFirstNameUser;
	private JLabel entranceTabLblLastNameUser;
	private JLabel entranceTabLblEndOfContractUser;
	private JLabel entranceTablLblAddressStreetUser;
	private JLabel entranceTablLblAddressCityUser;
	private JLabel entranceTablLblAddressCountryUser;
	
	private JLabel entranceTabLblFrequencyGate;
	private JLabel entranceTabLblRequest;
	

	/** FIELDS FOR 'AddPerson' TAB **/
	private JPanel addPersonTab;	
	
	private JButton addPersonBtnAdd;
	private JButton addPersonBtnClearDatabase;
	private JButton addPersonBtnGenerateData;	
	private JList<Remote> addPersonListInactiveRemote;
	private JList<Address> addPersonListAddress;
	
	private JTextField addPersonTfFirstName;
	private JTextField addPersonTfLastName;
	private JDateChooser addPersonDateContract;
	
	
	/** FIELDS FOR 'Overview' TAB **/
	private JPanel overviewTab;
	private JTable overviewTable;	
	private JButton overviewBtnRefresh;
	
	
	
	/** CONSTRUCTOR **/
	public View(){
		setSize(new Dimension(625, 500));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gate Administration");
		setUpTabbedPane();
	}
	
	
	/** METHODS INDEPENDANT FROM TAB **/
	// To display any message in new message dialog
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	
	/** METHODS FOR 'EntranceTab' **/
	// Controller fills combobox with data from DB
	public void addUsers(ArrayList<User> userList){
		for (User user : userList) {
			entranceTabListUsers.addItem(user);
		}
	}
	
	// Getter for selected user
	public User getUserForGate() {
		return (User)entranceTabListUsers.getSelectedItem();
	}

	// Setters	for text in labels
	public void setEntranceTabLblSerialNumberUser(String serialNumber) {
		this.entranceTabLblSerialNumberUser.setText(serialNumber); }
	public void setEntranceTabLblFrequencyUser(String frequencyRemote) {
		this.entranceTabLblFrequencyUser.setText(frequencyRemote); } 
	public void setEntranceTabLblRegisteredUser(boolean isActive) { // Sets text for true or false
		this.entranceTabLblRegisteredUser.setText( (isActive ? "Yes" : "No") );} 
	public void setEntranceTabLblFirstNameUser(String firstName) {
		this.entranceTabLblFirstNameUser.setText(firstName); } 
	public void setEntranceTabLblLastNameUser(String lastName) {
		this.entranceTabLblLastNameUser.setText(lastName); } 
	public void setEntranceTabLblEndOfContractUser(String endOfContract) {
		this.entranceTabLblEndOfContractUser.setText(endOfContract); } 
	public void setEntranceTablLblAddressStreetUser(String street) {
		this.entranceTablLblAddressStreetUser.setText(street); } 
	public void setEntranceTablLblAddressCityUser(String city) {
		this.entranceTablLblAddressCityUser.setText(city); } 
	public void setEntranceTablLblAddressCountryUser(String country) {
		this.entranceTablLblAddressCountryUser.setText(country); } 
	public void setEntranceTabLblFrequencyGate(String frequencyGate) {
		this.entranceTabLblFrequencyGate.setText(frequencyGate); } 
	
	// Displays acces denied/granted
	public void setEntranceTabRequest(Boolean bool){

		if(bool){
			entranceTabLblRequest.setBackground(Color.GREEN);
			entranceTabLblRequest.setText("Access granted!");
		}
		
		else{
			entranceTabLblRequest.setBackground(Color.RED);
			entranceTabLblRequest.setText("Acces denied!");
		}
	}

	// Controller subscribes to this button
	public void entranceTabAddRegisterUserListener(ActionListener e){
		this.entranceTabBtnRegister.addActionListener(e);
	}
	// Controller subscribes to this button
	public void entranceTabAddDeactivateUserListener(ActionListener e){
		this.entranceTabBtnDeactivate.addActionListener(e);
	}
	// Controller subscribes to this button
	public void entranceTabAddAskEntranceListener(ActionListener e){
		this.entranceTabBtnAskEntrance.addActionListener(e);
	}
	// Controller subscribes to this list event
	public void entranceTabAddUserListItemListener(ItemListener i) {
		this.entranceTabListUsers.addItemListener(i);
	}
	
	
	
	/** METHODS FOR 'AddPerson' TAB **/
	
	
	
	/** METHODS FOR 'Overview' TAB **/
	// Controller subscribes to this button
	public void addOverviewUpdateListener(ActionListener e){
		this.overviewBtnRefresh.addActionListener(e);
	}
	
	
	/*
	 * Controller subscribes to this button
	 */
	public void addAddPersonListener(ActionListener e){
		this.addPersonBtnAdd.addActionListener(e);
	}
	
	/*
	 * Controller sends unused address through DAO
	 * List set as model
	 */
	public void setUnusedAddress(ArrayList<Address> list){
		DefaultListModel<Address> model = new DefaultListModel<>();
		
		for (Address address : list) {
			model.addElement(address);
		}
		
		addPersonListAddress.setModel(model);
	}
	
	/*
	 * Controller sends inactive remotes through DAO
	 * List set as model
	 */
	public void setInactiveRemote(ArrayList<Remote> list){
		DefaultListModel<Remote> model = new DefaultListModel<>();
		
		for (Remote remote : list)
			model.addElement(remote);
		
		addPersonListInactiveRemote.setModel(model);
	}
	
	// Returns date choosen by user
	public Date getDate(){
		return (Date) addPersonDateContract.getDate();
	}
	
	// Sets date 
	public void setDate(Date date){
		this.addPersonDateContract.setDate(date);
	}
	
	// Getter&Setter for firstname PersonTab
	public String getFirstNamePersonTab(){
		return addPersonTfFirstName.getText(); }
	public void setFirstNamePersonTab(String name){
		this.addPersonTfFirstName.setText(name);	}
	
	/*
	 * Returns user lastname
	 */
	public String getLastName(){
		return addPersonTfLastName.getText();
	}
	
	/*
	 * Sets firstname texfield
	 */
	public void setLastName(String name){
		this.addPersonTfLastName.setText(name);
	}
	
	/*
	 * Returns user address
	 */
	public Address getAddress(){
		return addPersonListAddress.getSelectedValue();
	}
	
	/*
	 * Returns user remote
	 */
	public Remote getRemote(){
		return addPersonListInactiveRemote.getSelectedValue();
	}
	
	/*
	 * Controller sends all inhabitants information through DAO
	 * List display in table
	 */
	public void setOverview(ArrayList<Person> list){
		DefaultTableModel model = (DefaultTableModel) overviewTable.getModel();
		model.setRowCount(0);
		
		Object[] row = new Object[9];
		Remote remote;
		Address address;
		
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getId();
			row[1] = list.get(i).getFirstname();
			row[2] = list.get(i).getLastname();
			row[3] = list.get(i).getEndOfContract();
			
			remote = list.get(i).getRemote();
			row[4] = remote != null ? remote.getId() : "";
			row[5] = remote != null ? remote.getSerialNumber() : "";
			
			address = list.get(i).getAdress();
			row[6] = address != null ? address.getStreet() : "";
			row[7] = address != null ? address.getNumber() : "";
			row[8] = address != null ? address.getMailBox() : "";
			
			model.addRow(row);
		}
	}
	
	
	/*
	 * Component setup for parent TabPanel
	 */
	private void setUpTabbedPane(){
		tabbedPane = new JTabbedPane();
		addPersonTab = new JPanel();
		overviewTab = new JPanel();
		entranceTab = new JPanel();
		
		setUpEntrance();
		setUpOverView();
		setUpAddPerson();
		
		URL uAdd = getClass().getResource("add.png");
		ImageIcon iconAdd = new ImageIcon(uAdd);
		
		URL uGate = getClass().getResource("gate.png");
		ImageIcon iconGate = new ImageIcon(uGate);
		
		URL uPlane = getClass().getResource("plane.jpg");
		ImageIcon iconPlane = new ImageIcon(uPlane);
		
		tabbedPane.addTab("Request entrance", new ImageIcon(getScaledImage(iconGate.getImage(), 30, 30)), entranceTab, "Simulates request from inhabitant");
		tabbedPane.addTab("Add", new ImageIcon(getScaledImage(iconAdd.getImage(), 30, 30)), addPersonTab, "Add a new person");

		tabbedPane.addTab("Overview", new ImageIcon(getScaledImage(iconPlane.getImage(), 30, 30)), overviewTab, "Overview inhabitants");
		entranceTab.setLayout(null);

		getContentPane().add(tabbedPane);
	}
	
	/*
	 * Component setup for gate TabPanel
	 */
	private void setUpEntrance(){	
		JLabel lblChooseUser = new JLabel("Choose user");
		lblChooseUser.setBounds(10, 19, 100, 14);
		entranceTab.add(lblChooseUser);
		
		entranceTabBtnDeactivate = new JButton("Deactivate user");
		entranceTabBtnDeactivate.setBounds(425, 15, 150, 23);
		entranceTab.add(entranceTabBtnDeactivate);
		
		entranceTabBtnRegister = new JButton("Register user");
		entranceTabBtnRegister.setBounds(250, 15, 150, 23);
		entranceTab.add(entranceTabBtnRegister);
		
		entranceTabListUsers = new JComboBox<User>();
		entranceTabListUsers.setBounds(10, 44, 565, 20);
		entranceTab.add(entranceTabListUsers);
		
		entranceTabBtnAskEntrance = new JButton("Ask Entrance");
		entranceTabBtnAskEntrance.setBounds(10, 250, 250, 23);
		entranceTab.add(entranceTabBtnAskEntrance);
		
		// Remote data
		JLabel lblRemote = new JLabel("REMOTE");
		lblRemote.setBounds(10, 75, 127, 14);
		entranceTab.add(lblRemote);
		
		JLabel lblSerialNumber = new JLabel("Serial number");
		lblSerialNumber.setBounds(10, 100, 80, 14);
		entranceTab.add(lblSerialNumber);
		
		entranceTabLblSerialNumberUser = new JLabel("...");
		entranceTabLblSerialNumberUser.setBounds(100, 100, 160, 14);
		entranceTab.add(entranceTabLblSerialNumberUser);
		
		JLabel lblFrequency = new JLabel("Frequency");
		lblFrequency.setBounds(10, 125, 65, 14);
		entranceTab.add(lblFrequency);
		
		entranceTabLblFrequencyUser = new JLabel("...");
		entranceTabLblFrequencyUser.setBounds(100, 125, 160, 14);
		entranceTab.add(entranceTabLblFrequencyUser);
		
		JLabel lblRegistered = new JLabel("Activated");
		lblRegistered.setBounds(10, 150, 65, 14);
		entranceTab.add(lblRegistered);
		
		entranceTabLblRegisteredUser = new JLabel("...");
		entranceTabLblRegisteredUser.setBounds(100, 150, 160, 14);
		entranceTab.add(entranceTabLblRegisteredUser);

		// User data
		JLabel lblUserData = new JLabel("USER DATA");
		lblUserData.setBounds(300, 75, 138, 14);
		entranceTab.add(lblUserData);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(300, 100, 100, 14);
		entranceTab.add(lblFirstName);
		
		entranceTabLblFirstNameUser = new JLabel("...");
		entranceTabLblFirstNameUser.setBounds(400, 100, 160, 14);
		entranceTab.add(entranceTabLblFirstNameUser);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setBounds(300, 125, 100, 14);
		entranceTab.add(lblLastName);
		
		entranceTabLblLastNameUser = new JLabel("...");
		entranceTabLblLastNameUser.setBounds(400, 125, 160, 14);
		entranceTab.add(entranceTabLblLastNameUser);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(300, 175, 100, 14);
		entranceTab.add(lblAddress);
		
		JLabel lblEndOfContract = new JLabel("End of contract");
		lblEndOfContract.setBounds(300, 150, 100, 14);
		entranceTab.add(lblEndOfContract);
		
		entranceTabLblEndOfContractUser = new JLabel("...");
		entranceTabLblEndOfContractUser.setBounds(400, 150, 160, 14);
		entranceTab.add(entranceTabLblEndOfContractUser);
		
		entranceTablLblAddressStreetUser = new JLabel("...");
		entranceTablLblAddressStreetUser.setBounds(400, 175, 160, 14);
		entranceTab.add(entranceTablLblAddressStreetUser);
		
		entranceTablLblAddressCityUser = new JLabel("...");
		entranceTablLblAddressCityUser.setBounds(400, 195, 160, 14);
		entranceTab.add(entranceTablLblAddressCityUser);
		
		entranceTablLblAddressCountryUser = new JLabel("...");
		entranceTablLblAddressCountryUser.setBounds(400, 215, 160, 14);
		entranceTab.add(entranceTablLblAddressCountryUser);
		
		JLabel lblGate = new JLabel("GATE");
		lblGate.setBounds(10, 200, 46, 14);
		entranceTab.add(lblGate);
		
		lblFrequency = new JLabel("Frequency"); // already made this label for frequency remote, so simply reference it to new label
		lblFrequency.setBounds(10, 225, 80, 14);
		entranceTab.add(lblFrequency);
		
		entranceTabLblFrequencyGate = new JLabel("...");
		entranceTabLblFrequencyGate.setBounds(100, 225, 160, 14);
		entranceTab.add(entranceTabLblFrequencyGate);
			
		
		entranceTabLblRequest = new JLabel("", SwingConstants.CENTER);
		entranceTabLblRequest.setBackground(Color.GRAY);
		entranceTabLblRequest.setBounds(10, 290, 565, 62);
		entranceTabLblRequest.setOpaque(true);
		entranceTab.add(entranceTabLblRequest);
		
		JLabel lblFreqUpdated = new JLabel("Frequency will only be updated for registered users with a valid contract");
		lblFreqUpdated.setBounds(10, 389, 565, 14);
		entranceTab.add(lblFreqUpdated);
		
		JLabel lblAccessIsGranted = new JLabel("Access is granted to all registered users who have the correct frequency!");
		lblAccessIsGranted.setBounds(10, 364, 565, 14);
		entranceTab.add(lblAccessIsGranted);
	}
	
	/*
	 * Component setup for overview tab
	 */
	private void setUpOverView(){
		overviewTable = new JTable();
		overviewTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PersonId", "FirstName", "LastName", "EndOfContract", "RemoteId", "SerialNumber", "Street", "Nr", "MailBox"
			}
		));
		
		JTableHeader header = overviewTable.getTableHeader();
		
		overviewBtnRefresh = new JButton("Update");
		overviewBtnRefresh.setBounds(181, 5, 84, 23);
		
		overviewTab.setLayout(null);
		
		overviewBtnRefresh.setIcon(null);
		
		overviewTab.add(overviewBtnRefresh);
		overviewTab.add(header);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(25, 33, 550, 400);
		scroll.setViewportView(overviewTable);
		overviewTab.add(scroll);
	}
	
	/*
	 * Components setup for AddPerson tab
	 */
	private void setUpAddPerson(){
		addPersonTab.setLayout(null);
		
		JScrollPane scrollRemote = new JScrollPane();
		scrollRemote.setBounds(10, 100, 400, 150);
		addPersonTab.add(scrollRemote);
		addPersonListInactiveRemote = new JList<Remote>();
		scrollRemote.setViewportView(addPersonListInactiveRemote);
		addPersonListInactiveRemote.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollAddress = new JScrollPane();
		scrollAddress.setBounds(10, 275, 400, 150);
		addPersonTab.add(scrollAddress);
		addPersonListAddress = new JList<Address>();
		scrollAddress.setViewportView(addPersonListAddress);
		addPersonListAddress.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		addPersonBtnAdd = new JButton("Add");
		addPersonBtnAdd.setBounds(200, 32, 89, 23);
		addPersonTab.add(addPersonBtnAdd);		
		
		addPersonBtnClearDatabase = new JButton("Clear database");
		addPersonBtnClearDatabase.setBounds(415, 10, 150, 25);
		addPersonTab.add(addPersonBtnClearDatabase);
		
		addPersonBtnGenerateData = new JButton("Generate data");
		addPersonBtnGenerateData.setBounds(415, 45, 150, 25);
		addPersonTab.add(addPersonBtnGenerateData);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(10, 11, 73, 14);
		addPersonTab.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(10, 36, 73, 14);
		addPersonTab.add(lblLastname);
		
		JLabel lblContract = new JLabel("Contract");
		lblContract.setBounds(10, 61, 73, 14);
		addPersonTab.add(lblContract);
		
		addPersonTfFirstName = new JTextField();
		addPersonTfFirstName.setBounds(93, 8, 86, 20);
		addPersonTab.add(addPersonTfFirstName);
		addPersonTfFirstName.setColumns(10);
		
		addPersonTfLastName = new JTextField();
		addPersonTfLastName.setBounds(93, 33, 86, 20);
		addPersonTab.add(addPersonTfLastName);
		addPersonTfLastName.setColumns(10);
		
		addPersonDateContract = new JDateChooser();
		addPersonDateContract.setMinSelectableDate(Date.valueOf(LocalDate.now()));
		addPersonDateContract.setBounds(93, 61, 102, 20);
		addPersonTab.add(addPersonDateContract);
	}
	
	/*
	 * function to scale image to appropriate size
	 */
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
}
