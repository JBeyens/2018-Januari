package model.business.interfaces;

public interface IGateSubject {
	public void registerGate(IGateObserver gateModule);
	public void unregisterGate(IGateObserver gateModule);
	
	// add / remove / notify functie => void return type allemaal
	// concrete subject heeft dan lijst van igateobserver uiteraard
}
