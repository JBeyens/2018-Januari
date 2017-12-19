package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelPersistent.Person;

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
	
	public View(){
		this.setTitle("Gate Administration");
		setUpTabbedPane();
	}
	
	public void setOverview(ArrayList<Person> list){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
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
	
	private void setUpTabbedPane(){
		tabbedPane = new JTabbedPane();
		addPersonPanel = new JPanel();
		overView = new JPanel();
		simulateGate = new JPanel();
		
		setUpOverView();
		
		URL uAdd = getClass().getResource("add.png");
		ImageIcon iconAdd = new ImageIcon(uAdd);
		
		URL uGate = getClass().getResource("gate.png");
		ImageIcon iconGate = new ImageIcon(uGate);
		
		URL uPlane = getClass().getResource("plane.jpg");
		ImageIcon iconPlane = new ImageIcon(uPlane);
		
		tabbedPane.addTab("Request entrance", new ImageIcon(getScaledImage(iconGate.getImage(), 30, 30)), simulateGate, "Simulates request from inhabitant");
		tabbedPane.addTab("Add", new ImageIcon(getScaledImage(iconAdd.getImage(), 30, 30)), addPersonPanel, "Add a new person");
		tabbedPane.addTab("Overview", new ImageIcon(getScaledImage(iconPlane.getImage(), 30, 30)), overView, "Overview inhabitants");

		getContentPane().add(tabbedPane);
	}
	
	private void setUpOverView(){
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PersonId", "FirstName", "LastName", "EndOfContract", "RemoteId", "SerialNumber", "Street", "Nr", "MailBox"
			}
		));
		overView.add(table);
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
}
