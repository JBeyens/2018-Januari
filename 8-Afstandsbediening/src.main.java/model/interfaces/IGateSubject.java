package model.interfaces;

public interface IGateSubject {
	public void registerGate(IGateObserver remote);
	public void unregisterGate(IGateObserver remote);
}
