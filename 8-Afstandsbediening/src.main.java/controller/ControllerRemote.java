package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;
import view.View;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Single controller for handling interaction model<>view
 */

public class ControllerRemote {
	private View view;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Address> addressDAO;
	private GenericDAO<Remote> remoteDAO;
	
	public ControllerRemote(){
		view = new View();
		personDAO = new GenericDAO<>(Person.class);
		addressDAO = new GenericDAO<>(Address.class);
		remoteDAO = new GenericDAO<>(Remote.class);
		
		view.addOVerViewUpdateListener(new RefreshOverViewListener());
		view.addAddPersonListener(new AddPersonListener());
		setInactiveRemote();
	}
	
	public void start(){
		this.view.setSize(600, 500);
		this.view.setResizable(false);
		this.view.setVisible(true);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setInactiveRemote(){
		ArrayList<Remote> list = new ArrayList<>();
		
		for (Remote remote : remoteDAO.findAll()) {
			if(!remote.getIsActive())
				list.add(remote);
		}

		view.setInactiveRemote(list);
	}
	
	private void setOverView(){
		view.setOverview((ArrayList<Person>)personDAO.findAll());
	} 
	
	private class AddPersonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Person person = new Person();
				person.setFirstname(view.getFirstName());
				person.setLastname(view.getLastName());
				person.setEndOfContract(view.getDate());
				person.setAdress(view.getAddress());
				person.setRemote(view.getRemote());
				
				personDAO.create(person);
			} catch (Exception e1) {
				e1.printStackTrace();
				view.showMessage("Input parameters not correct!");
			}
			
		}	
	}
	
	private class RefreshOverViewListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				setOverView();
			} catch (Exception e1) {
				e1.printStackTrace();
				view.showMessage("Loading from database failed!");
			}
		}	
	}
}
