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

import model.business.PersonWrapper;
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
	
	private JComboBox<PersonWrapper> entranceTabListUsers;
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
	
	private JButton addPersonTabBtnAdd;
	private JButton addPersonTabBtnClearDatabase;
	private JButton addPersonTabBtnGenerateData;	
	private JList<Remote> addPersonTabInactiveRemoteList;
	private JList<Address> addPersonTabAddressList;
	
	private JTextField addPersonTabTfFirstName;
	private JTextField addPersonTabTfLastName;
	private JDateChooser addPersonTabEndDateContract;
	
	
	/** FIELDS FOR 'Overview' TAB **/
	private JPanel overviewTab;
	private JTable overviewTabTable;	
	private JButton overviewTabBtnRefresh;
	
	
	
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
	
	
	
	/** PUBLIC METHODS FOR 'EntranceTab' **/	
	// Getter for selected user
	public PersonWrapper getUserForGate() {
		return (PersonWrapper)entranceTabListUsers.getSelectedItem();
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

	// Setter fills combobox with inputted data
	public void setEntranceTabUsers(ArrayList<PersonWrapper> userList){
		entranceTabListUsers.removeAllItems();
		for (PersonWrapper user : userList) {
			entranceTabListUsers.addItem(user);
		}
	}
	// Displays acces denied/granted (if null input, then will set to default)
	public void setEntranceTabRequest(Boolean bool){
		if(bool == null) {
			entranceTabLblRequest.setBackground(Color.GRAY);
			entranceTabLblRequest.setText("");
		}
		else if(bool){
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
		this.entranceTabBtnRegister.addActionListener(e); }
	// Controller subscribes to this button
	public void entranceTabAddDeactivateUserListener(ActionListener e){
		this.entranceTabBtnDeactivate.addActionListener(e); }
	// Controller subscribes to this button
	public void entranceTabAddAskEntranceListener(ActionListener e){
		this.entranceTabBtnAskEntrance.addActionListener(e); }
	// Controller subscribes to this list event
	public void entranceTabAddUserListItemListener(ItemListener i) {
		this.entranceTabListUsers.addItemListener(i); }
	
	
	
	/** PUBLIC METHODS FOR 'AddPerson' TAB **/	
	// Getters for values filled in or chosen in the 'AddPerson' tab
	public String getAddPersonTabFirstName(){
		return addPersonTabTfFirstName.getText(); }
	public String getAddPersonTabLastName(){
		return addPersonTabTfLastName.getText(); }
	public Date getAddPersonTabDate(){
		return addPersonTabEndDateContract.getDate()==null ? null : new Date(addPersonTabEndDateContract.getDate().getTime()); }
	public Address getAddPersonTabChosenAddress(){
		return addPersonTabAddressList.getSelectedValue(); }
	public Remote getAddPersonTabChosenRemote(){
		return addPersonTabInactiveRemoteList.getSelectedValue(); }

	// Setters for the fields in the 'AddPerson' tab
	public void setAddPersonTabFirstName(String name){
		this.addPersonTabTfFirstName.setText(name);	}
	public void setAddPersonTabLastName(String name){
		this.addPersonTabTfLastName.setText(name); }
	public void setAddPersonTabDate(Date date){
		this.addPersonTabEndDateContract.setDate(date);	}
	// Sets given address list to 'AddressList' in the 'AddPerson' tab
	public void setAddPersonUnusedAddresses(ArrayList<Address> list){
		DefaultListModel<Address> model = new DefaultListModel<>();
		
		for (Address address : list) {
			model.addElement(address);
		}
		
		addPersonTabAddressList.setModel(model);
	}
	// Sets given remote list to 'InactiveRemoteList' in the 'AddPerson' tab
	public void setAddPersonInactiveRemotes(ArrayList<Remote> list){
		DefaultListModel<Remote> model = new DefaultListModel<>();
		
		for (Remote remote : list)
			model.addElement(remote);
		
		addPersonTabInactiveRemoteList.setModel(model);
	}
	

	// Controller subscribes to these buttons
	public void addPersonTabBtnAddListener(ActionListener e){
		this.addPersonTabBtnAdd.addActionListener(e); }
	public void addPersonTabBtnClearDatabaseListener(ActionListener e){
		this.addPersonTabBtnClearDatabase.addActionListener(e); }
	public void addPersonTabBtnGenerateDataListener(ActionListener e){
		this.addPersonTabBtnGenerateData.addActionListener(e); }
	
	
	
	/** PUBLIC METHODS FOR 'Overview' TAB **/
	// Controller subscribes to this button
	public void addOverviewUpdateListener(ActionListener e){
		this.overviewTabBtnRefresh.addActionListener(e);
	}	
	// Method to assign list of persons to the table in the 'Overview' tab
	public void setOverviewTabTable(ArrayList<Person> list){
		DefaultTableModel model = (DefaultTableModel) overviewTabTable.getModel();
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
	
	
	
	
	/** PRIVATE METHODS **/
	// Component setup for parent TabPanel
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
		
		entranceTabListUsers = new JComboBox<PersonWrapper>();
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
		
		entranceTabLblSerialNumberUser = new JLabel();
		entranceTabLblSerialNumberUser.setBounds(100, 100, 160, 14);
		entranceTab.add(entranceTabLblSerialNumberUser);
		
		JLabel lblFrequency = new JLabel("Frequency");
		lblFrequency.setBounds(10, 125, 65, 14);
		entranceTab.add(lblFrequency);
		
		entranceTabLblFrequencyUser = new JLabel();
		entranceTabLblFrequencyUser.setBounds(100, 125, 160, 14);
		entranceTab.add(entranceTabLblFrequencyUser);
		
		JLabel lblRegistered = new JLabel("Activated");
		lblRegistered.setBounds(10, 150, 65, 14);
		entranceTab.add(lblRegistered);
		
		entranceTabLblRegisteredUser = new JLabel();
		entranceTabLblRegisteredUser.setBounds(100, 150, 160, 14);
		entranceTab.add(entranceTabLblRegisteredUser);

		// User data
		JLabel lblUserData = new JLabel("USER DATA");
		lblUserData.setBounds(300, 75, 138, 14);
		entranceTab.add(lblUserData);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(300, 100, 100, 14);
		entranceTab.add(lblFirstName);
		
		entranceTabLblFirstNameUser = new JLabel();
		entranceTabLblFirstNameUser.setBounds(400, 100, 160, 14);
		entranceTab.add(entranceTabLblFirstNameUser);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setBounds(300, 125, 100, 14);
		entranceTab.add(lblLastName);
		
		entranceTabLblLastNameUser = new JLabel();
		entranceTabLblLastNameUser.setBounds(400, 125, 160, 14);
		entranceTab.add(entranceTabLblLastNameUser);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(300, 175, 100, 14);
		entranceTab.add(lblAddress);
		
		JLabel lblEndOfContract = new JLabel("End of contract");
		lblEndOfContract.setBounds(300, 150, 100, 14);
		entranceTab.add(lblEndOfContract);
		
		entranceTabLblEndOfContractUser = new JLabel();
		entranceTabLblEndOfContractUser.setBounds(400, 150, 160, 14);
		entranceTab.add(entranceTabLblEndOfContractUser);
		
		entranceTablLblAddressStreetUser = new JLabel();
		entranceTablLblAddressStreetUser.setBounds(400, 175, 160, 14);
		entranceTab.add(entranceTablLblAddressStreetUser);
		
		entranceTablLblAddressCityUser = new JLabel();
		entranceTablLblAddressCityUser.setBounds(400, 195, 160, 14);
		entranceTab.add(entranceTablLblAddressCityUser);
		
		entranceTablLblAddressCountryUser = new JLabel();
		entranceTablLblAddressCountryUser.setBounds(400, 215, 160, 14);
		entranceTab.add(entranceTablLblAddressCountryUser);
		
		JLabel lblGate = new JLabel("GATE");
		lblGate.setBounds(10, 200, 46, 14);
		entranceTab.add(lblGate);
		
		lblFrequency = new JLabel("Frequency"); // already made this label for frequency remote, so simply reference it to new label
		lblFrequency.setBounds(10, 225, 80, 14);
		entranceTab.add(lblFrequency);
		
		entranceTabLblFrequencyGate = new JLabel();
		entranceTabLblFrequencyGate.setBounds(100, 225, 160, 14);
		entranceTab.add(entranceTabLblFrequencyGate);
			
		
		entranceTabLblRequest = new JLabel("", SwingConstants.CENTER);
		entranceTabLblRequest.setBackground(Color.GRAY);
		entranceTabLblRequest.setBounds(10, 290, 565, 62);
		entranceTabLblRequest.setOpaque(true);
		entranceTab.add(entranceTabLblRequest);
		
		JLabel lblAccessIsGranted = new JLabel("Access is granted to all registered users who have the correct frequency!");
		lblAccessIsGranted.setBounds(10, 364, 565, 14);
		entranceTab.add(lblAccessIsGranted);
		
		JLabel lblFreqUpdated = new JLabel("Frequency will only be updated for registered users with a valid contract");
		lblFreqUpdated.setBounds(10, 389, 565, 14);
		entranceTab.add(lblFreqUpdated);
	}
	
	/*
	 * Component setup for overview tab
	 */
	private void setUpOverView(){
		overviewTabTable = new JTable();
		overviewTabTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PersonId", "FirstName", "LastName", "EndOfContract", "RemoteId", "SerialNumber", "Street", "Nr", "MailBox"
			}
		));
		
		JTableHeader header = overviewTabTable.getTableHeader();
		
		overviewTabBtnRefresh = new JButton("Update");
		overviewTabBtnRefresh.setBounds(181, 5, 84, 23);
		
		overviewTab.setLayout(null);
		
		overviewTabBtnRefresh.setIcon(null);
		
		overviewTab.add(overviewTabBtnRefresh);
		overviewTab.add(header);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(25, 33, 550, 400);
		scroll.setViewportView(overviewTabTable);
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
		addPersonTabInactiveRemoteList = new JList<Remote>();
		scrollRemote.setViewportView(addPersonTabInactiveRemoteList);
		addPersonTabInactiveRemoteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollAddress = new JScrollPane();
		scrollAddress.setBounds(10, 275, 400, 150);
		addPersonTab.add(scrollAddress);
		addPersonTabAddressList = new JList<Address>();
		scrollAddress.setViewportView(addPersonTabAddressList);
		addPersonTabAddressList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		addPersonTabBtnAdd = new JButton("Add");
		addPersonTabBtnAdd.setBounds(200, 32, 89, 23);
		addPersonTab.add(addPersonTabBtnAdd);		
		
		addPersonTabBtnClearDatabase = new JButton("Clear database");
		addPersonTabBtnClearDatabase.setBounds(415, 10, 150, 25);
		addPersonTab.add(addPersonTabBtnClearDatabase);
		
		addPersonTabBtnGenerateData = new JButton("Generate data");
		addPersonTabBtnGenerateData.setBounds(415, 45, 150, 25);
		addPersonTab.add(addPersonTabBtnGenerateData);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(10, 11, 73, 14);
		addPersonTab.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(10, 36, 73, 14);
		addPersonTab.add(lblLastname);
		
		JLabel lblContract = new JLabel("Contract");
		lblContract.setBounds(10, 61, 73, 14);
		addPersonTab.add(lblContract);
		
		addPersonTabTfFirstName = new JTextField();
		addPersonTabTfFirstName.setBounds(93, 8, 86, 20);
		addPersonTab.add(addPersonTabTfFirstName);
		addPersonTabTfFirstName.setColumns(10);
		
		addPersonTabTfLastName = new JTextField();
		addPersonTabTfLastName.setBounds(93, 33, 86, 20);
		addPersonTab.add(addPersonTabTfLastName);
		addPersonTabTfLastName.setColumns(10);
		
		addPersonTabEndDateContract = new JDateChooser();
		addPersonTabEndDateContract.setMinSelectableDate(Date.valueOf(LocalDate.now()));
		addPersonTabEndDateContract.setBounds(93, 61, 102, 20);
		addPersonTab.add(addPersonTabEndDateContract);
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
