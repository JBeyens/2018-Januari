package test;

import controller.ControllerRemote;

/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Copy of ControllerRunnerApp (for teacher to find)
 */

public class TestMVC {

	public static void main(String[] args) {
		ControllerRemote cRemote = new ControllerRemote();
		cRemote.start();
	}
}