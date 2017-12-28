package model.observer;

public interface IRemoteSubject {
	public void registerUserRemote(IRemoteObserver remote);
	public void deactivateUserRemote(IRemoteObserver remote);
}
