package model.observer;

import model.entities.Remote;

public interface IRemoteObserver {
	public void updateFrequency(long frequency);
	public String sendSerialId();
	public Remote getRemote();
}
