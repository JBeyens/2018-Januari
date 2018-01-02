package model.interfaces;

public interface IGateModule {
	public void verifyAndUpdateFrequencyRemote(IRemoteModule remote);
	public long getFrequency();
}
