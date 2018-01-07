package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
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
	/**FIELDS FOR 'Ask Entrance' tab**/
	private JComboBox<User> listUsers;
	private JButton btnAskEntrance;
	
	private JLabel lblSerialNumberUser;
	private JLabel lblFrequencyUser;
	private JLabel lblRegisteredUser;
	
	private JLabel lblFirstNameUser;
	private JLabel lblLastNameUser;
	private JLabel lblEndOfContractUser;
	private JLabel lblAddressUser;
	
	private JLabel lblFrequencyGate;
	
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel addPersonPanel;
	private JPanel overView;
	private JPanel simulateGate;
	private JTable table;
	private JButton btnRefresh;
	private JList<Remote> listInactiveRemote;
	private JList<Address> listAddress;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JButton btnAddPerson;
	private JDateChooser dateContract;
	private JLabel lblRequest;
	
	public View(){
		setSize(new Dimension(625, 500));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gate Administration");
		setUpTabbedPane();
	}
	
	/*
	 * To display any message to the User
	 */
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	/*
	 * Displays acces denied/granted
	 */
	public void setRequest(Boolean bool){

		if(bool){
			lblRequest.setBackground(Color.GREEN);
			lblRequest.setText("Access granted");
		}
		
		else{
			lblRequest.setBackground(Color.RED);
			lblRequest.setText("Acces denied");
		}
	}
	
	/*
	 * Controller subscrives to this button
	 */
	public void addAskEntranceListener(ActionListener e){
		this.btnAskEntrance.addActionListener(e);
	}
	
	/*
	 * Controller subscribes to this button
	 */
	public void addOVerViewUpdateListener(ActionListener e){
		this.btnRefresh.addActionListener(e);
	}
	
	/*
	 * Controller subscribes to this button
	 */
	public void addAddPersonListener(ActionListener e){
		this.btnAddPerson.addActionListener(e);
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
		
		listAddress.setModel(model);
	}
	
	/*
	 * Controller sends inactive remotes through DAO
	 * List set as model
	 */
	public void setInactiveRemote(ArrayList<Remote> list){
		DefaultListModel<Remote> model = new DefaultListModel<>();
		
		for (Remote remote : list)
			model.addElement(remote);
		
		listInactiveRemote.setModel(model);
	}
	
	/*
	 * Controller fills combobox with data from DB
	 */
	public void addUsers(ArrayList<User> userList){
		for (User user : userList) {
			listUsers.addItem(user);
		}
	}
	
	/*
	 * Returns date choosen by user
	 */
	public Date getDate(){
		return (Date) dateContract.getDate();
	}
	
	/*
	 * set date
	 */
	public void setDate(Date date){
		this.dateContract.setDate(date);
	}
	
	/*
	 * Returns user firstname
	 */
	public String getFirstName(){
		return tfFirstName.getText();
	}
	
	/*
	 * Sets firstname texfield
	 */
	public void setFirstName(String name){
		this.tfFirstName.setText(name);
	}
	
	/*
	 * Returns user lastname
	 */
	public String getLastName(){
		return tfLastName.getText();
	}
	
	/*
	 * Sets firstname texfield
	 */
	public void setLastName(String name){
		this.tfLastName.setText(name);
	}
	
	/*
	 * Returns user address
	 */
	public Address getAddress(){
		return listAddress.getSelectedValue();
	}
	
	/*
	 * Returns user remote
	 */
	public Remote getRemote(){
		return listInactiveRemote.getSelectedValue();
	}
	
	public User getUserForGate() {
		return (User)listUsers.getSelectedItem();
	}
	
	/*
	 * Controller sends all inhabitants information through DAO
	 * List display in table
	 */
	public void setOverview(ArrayList<Person> list){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
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
	 * Components setup for AddPerson tab
	 */
	private void setUpAddPerson(){
		addPersonPanel.setLayout(null);
		
		JScrollPane scrollRemote = new JScrollPane();
		scrollRemote.setBounds(10, 92, 200, 130);
		addPersonPanel.add(scrollRemote);
		listInactiveRemote = new JList<Remote>();
		scrollRemote.setViewportView(listInactiveRemote);
		listInactiveRemote.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollAddress = new JScrollPane();
		scrollAddress.setBounds(220, 92, 199, 130);
		addPersonPanel.add(scrollAddress);
		listAddress = new JList<Address>();
		scrollAddress.setViewportView(listAddress);
		listAddress.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnAddPerson = new JButton("Add");
		btnAddPerson.setBounds(200, 32, 89, 23);
		addPersonPanel.add(btnAddPerson);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(10, 11, 73, 14);
		addPersonPanel.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(10, 36, 73, 14);
		addPersonPanel.add(lblLastname);
		
		tfFirstName = new JTextField();
		tfFirstName.setBounds(93, 8, 86, 20);
		addPersonPanel.add(tfFirstName);
		tfFirstName.setColumns(10);
		
		tfLastName = new JTextField();
		tfLastName.setBounds(93, 33, 86, 20);
		addPersonPanel.add(tfLastName);
		tfLastName.setColumns(10);
		
		dateContract = new JDateChooser();
		dateContract.setMinSelectableDate(Date.valueOf(LocalDate.now()));
		dateContract.setBounds(93, 61, 102, 20);
		addPersonPanel.add(dateContract);
		
		JLabel lblContract = new JLabel("Contract");
		lblContract.setBounds(10, 61, 73, 14);
		addPersonPanel.add(lblContract);
	}
	
	/*
	 * Component setup for gate TabPanel
	 */
	private void setUpGate(){	
		JLabel lblChooseUser = new JLabel("Choose user");
		lblChooseUser.setBounds(10, 19, 100, 14);
		simulateGate.add(lblChooseUser);
		
		listUsers = new JComboBox<User>();
		listUsers.setBounds(10, 44, 409, 20);
		listUsers.addItemListener(new UserLabelsItemListener());
		simulateGate.add(listUsers);
		
		btnAskEntrance = new JButton("Ask Entrance");
		btnAskEntrance.setBounds(300, 10, 119, 23);
		simulateGate.add(btnAskEntrance);
		
		// Remote data
		JLabel lblRemote = new JLabel("REMOTE");
		lblRemote.setBounds(10, 75, 127, 14);
		simulateGate.add(lblRemote);
		
		JLabel lblSerialNumber = new JLabel("Serial number");
		lblSerialNumber.setBounds(10, 100, 80, 14);
		simulateGate.add(lblSerialNumber);
		
		lblSerialNumberUser = new JLabel("...");
		lblSerialNumberUser.setBounds(100, 100, 160, 14);
		simulateGate.add(lblSerialNumberUser);
		
		JLabel lblFrequency = new JLabel("Frequency");
		lblFrequency.setBounds(10, 125, 65, 14);
		simulateGate.add(lblFrequency);
		
		lblFrequencyUser = new JLabel("...");
		lblFrequencyUser.setBounds(100, 125, 160, 14);
		simulateGate.add(lblFrequencyUser);
		
		JLabel lblRegistered = new JLabel("Activated");
		lblRegistered.setBounds(10, 150, 65, 14);
		simulateGate.add(lblRegistered);
		
		lblRegisteredUser = new JLabel("...");
		lblRegisteredUser.setBounds(100, 150, 160, 14);
		simulateGate.add(lblRegisteredUser);

		// User data
		JLabel lblUserData = new JLabel("USER DATA");
		lblUserData.setBounds(300, 75, 138, 14);
		simulateGate.add(lblUserData);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(300, 100, 80, 14);
		simulateGate.add(lblFirstName);
		
		lblFirstNameUser = new JLabel("...");
		lblFirstNameUser.setBounds(390, 100, 160, 14);
		simulateGate.add(lblFirstNameUser);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setBounds(300, 125, 80, 14);
		simulateGate.add(lblLastName);
		
		lblLastNameUser = new JLabel("...");
		lblLastNameUser.setBounds(390, 125, 160, 14);
		simulateGate.add(lblLastNameUser);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(300, 175, 46, 14);
		simulateGate.add(lblAddress);
		
		lblAddressUser = new JLabel("...");
		lblAddressUser.setVerticalAlignment(SwingConstants.TOP);
		lblAddressUser.setBounds(390, 175, 160, 50);
		simulateGate.add(lblAddressUser);
		
		JLabel lblEndOfContract = new JLabel("End of contract");
		lblEndOfContract.setBounds(300, 150, 80, 14);
		simulateGate.add(lblEndOfContract);
		
		lblEndOfContractUser = new JLabel("...");
		lblEndOfContractUser.setBounds(390, 150, 160, 14);
		simulateGate.add(lblEndOfContractUser);
		
		JLabel lblGate = new JLabel("GATE");
		lblGate.setBounds(10, 200, 46, 14);
		simulateGate.add(lblGate);
		
		lblFrequency = new JLabel("Frequency"); // already made this label for frequency remote, so simply reference it to new label
		lblFrequency.setBounds(10, 225, 80, 14);
		simulateGate.add(lblFrequency);
		
		lblFrequencyGate = new JLabel("...");
		lblFrequencyGate.setBounds(100, 225, 160, 14);
		simulateGate.add(lblFrequencyGate);
		
		
		
		lblRequest = new JLabel("", SwingConstants.CENTER);
		lblRequest.setBackground(Color.GRAY);
		lblRequest.setBounds(10, 290, 409, 62);
		lblRequest.setOpaque(true);
		simulateGate.add(lblRequest);
	}
	
	/*
	 * Component setup for parent TabPanel
	 */
	private void setUpTabbedPane(){
		tabbedPane = new JTabbedPane();
		addPersonPanel = new JPanel();
		overView = new JPanel();
		simulateGate = new JPanel();
		
		setUpGate();
		setUpOverView();
		setUpAddPerson();
		
		URL uAdd = getClass().getResource("add.png");
		ImageIcon iconAdd = new ImageIcon(uAdd);
		
		URL uGate = getClass().getResource("gate.png");
		ImageIcon iconGate = new ImageIcon(uGate);
		
		URL uPlane = getClass().getResource("plane.jpg");
		ImageIcon iconPlane = new ImageIcon(uPlane);
		
		tabbedPane.addTab("Request entrance", new ImageIcon(getScaledImage(iconGate.getImage(), 30, 30)), simulateGate, "Simulates request from inhabitant");
		tabbedPane.addTab("Add", new ImageIcon(getScaledImage(iconAdd.getImage(), 30, 30)), addPersonPanel, "Add a new person");
		tabbedPane.addTab("Overview", new ImageIcon(getScaledImage(iconPlane.getImage(), 30, 30)), overView, "Overview inhabitants");
		simulateGate.setLayout(null);

		getContentPane().add(tabbedPane);
	}
	
	/*
	 * Component setup for overview tab
	 */
	private void setUpOverView(){
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PersonId", "FirstName", "LastName", "EndOfContract", "RemoteId", "SerialNumber", "Street", "Nr", "MailBox"
			}
		));
		
		JTableHeader header = table.getTableHeader();
		
		btnRefresh = new JButton("Update");
		btnRefresh.setBounds(181, 5, 84, 23);
		
		overView.setLayout(null);
		
		btnRefresh.setIcon(null);
		
		overView.add(btnRefresh);
		overView.add(header);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(25, 33, 550, 400);
		scroll.setViewportView(table);
		overView.add(scroll);

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
	
	
	/*
	 * Listener for 'On selected user changed' (3rd tab)
	 */
	private class UserLabelsItemListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
		}	
	}
}
