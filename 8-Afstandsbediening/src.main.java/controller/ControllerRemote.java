package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;
import view.View;

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
		
		setOverView();
	}
	
	public void start(){
		this.view.setSize(700, 800);
		this.view.setResizable(false);
		this.view.setVisible(true);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setOverView(){
		view.setOverview((ArrayList<Person>)personDAO.findAll());
	} 
}
