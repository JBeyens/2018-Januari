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
		
	}
	
	public void start(){
		this.view.setSize(700, 800);
		this.view.setResizable(false);
		this.view.setVisible(true);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setOverView(){
		view.setOverview((ArrayList<Person>)personDAO.findAll());
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
