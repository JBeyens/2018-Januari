package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Date;
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

import com.toedter.calendar.JDateChooser;

import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import javax.swing.JComboBox;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel View with TabPanel to show different functionalities
 */

public class View extends JFrame{
	/**
	 * 
	 */
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
	private JButton btnAskEntrance;
	private JDateChooser dateContract;
	private JComboBox<Object> listPerson;
	
	public View(){
		this.setTitle("Gate Administration");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUpTabbedPane();
	}
	
	/*
	 * To display any message to the User
	 */
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
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
	public void addPersons(ArrayList<Person> list){
		this.listPerson = new JComboBox<>(list.toArray());
	}
	
	/*
	 * Returns date choosen by user
	 */
	public Date getDate(){
		return dateContract.getDate();
	}
	
	/*
	 * Returns user firstname
	 */
	public String getFirstName(){
		return tfFirstName.getText();
	}
	
	/*
	 * Returns user lastname
	 */
	public String getLastName(){
		return tfLastName.getText();
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
	
	
	/*
	 * Controller sends all inhabitants information through DAO
	 * List display in table
	 */
	public void setOverview(ArrayList<Person> list){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		Object[] row = new Object[9];
		
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getId();
			row[1] = list.get(i).getFirstname();
			row[2] = list.get(i).getLastname();
			row[3] = list.get(i).getEndOfContract();
			row[4] = list.get(i).getRemote().getId();
			row[5] = list.get(i).getRemote().getSerialNumber();
			row[6] = list.get(i).getAdress().getStreet();
			row[7] = list.get(i).getAdress().getNumber();
			row[8] = list.get(i).getAdress().getMailBox();
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
		dateContract.setMinSelectableDate(new Date());
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
		listPerson = new JComboBox<>();
		listPerson.setBounds(10, 11, 153, 20);
		simulateGate.add(listPerson);
		
		btnAskEntrance = new JButton("Ask Entrance");
		btnAskEntrance.setBounds(188, 10, 119, 23);
		simulateGate.add(btnAskEntrance);
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
		simulateGate.setLayout(null);

		tabbedPane.addTab("Add", new ImageIcon(getScaledImage(iconAdd.getImage(), 30, 30)), addPersonPanel, "Add a new person");
		tabbedPane.addTab("Overview", new ImageIcon(getScaledImage(iconPlane.getImage(), 30, 30)), overView, "Overview inhabitants");

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
}
