package model.business.interfaces;

public interface IGateModule {
	public void verifyAndUpdateFrequencyRemote(IRemoteModule remote);
	public long getFrequency();
}
