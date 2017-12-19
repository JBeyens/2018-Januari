package controller;

import javax.swing.JFrame;

import view.View;

public class ControllerRemote {
	private View view;
	
	public ControllerRemote(){
		view = new View();
	}
	
	public void start(){
		this.view.setSize(700, 800);
		this.view.setResizable(false);
		this.view.setVisible(true);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
