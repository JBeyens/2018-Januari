package model.interfaces;

public interface IGateSubject {
	public void registerGate(IGateObserver gateModule);
	public void unregisterGate(IGateObserver gateModule);
}
