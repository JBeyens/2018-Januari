package controller;

/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Main() to start controllerApp
 */

public class ControllerRunnerApp {

	public static void main(String[] args) {
		ControllerRemote cRemote = new ControllerRemote();
		cRemote.start();
	}

}
