package model.business.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.business.RemoteModule;
import model.business.interfaces.IGateModule;
import model.business.interfaces.IRemoteModule;
import model.entities.Remote;

/**
 * 	@Author Jef Beyens & Ben Vandevorst
	@Datum 02/01/2018
	@Project Afstandsbediening
	@Doel Test of RemoteModule
 */
public class RemoteModuleTest {
	private Remote remote;
	private RemoteModule remoteModule;
	long newFrequency;
	
	@Before
	public void setUp(){
		remote = new Remote("xyz", 911);
		remoteModule = new RemoteModule(remote);
		newFrequency = 666;
	}
	
	@Test
	public void Set_Frequency_Should_Modify_The_Remote() {
		remoteModule.setFrequency(newFrequency);
		assertEquals(newFrequency, remote.getFrequency());
	}
	
	@Test
	public void Ask_Open_Gate_Should_Fail_If_Not_Verified_By_Gate() {
		GateModuleMock gateModule = new GateModuleMock(newFrequency, false);
		assertFalse(remoteModule.askOpenGate(gateModule));
	}
	
	@Test
	public void Ask_Open_Gate_Should_Fail_If_Verified_By_Gate() {
		GateModuleMock gateModule = new GateModuleMock(newFrequency, true);
		assertTrue(remoteModule.askOpenGate(gateModule));
	}
	
	@Test
	public void Get_Serial_Number_Should_Return_Serial_From_Remote() {
		assertTrue(remote.getSerialNumber().equals(remoteModule.getSerialNumber()));
	}
	
	/**
	 * New class to mock GateModule behavior so RemoteModule can be tested.
	 **/
	private class GateModuleMock implements IGateModule {
		private long frequency;
		private boolean verifySuccesfull;
		
		public GateModuleMock(long frequency, boolean willVerifyBeSuccesfull) {
			this.frequency = frequency;
			this.verifySuccesfull = willVerifyBeSuccesfull;
		}

		@Override
		public void verifyAndUpdateFrequencyRemote(IRemoteModule remote) {
			if (verifySuccesfull)
				remote.setFrequency(getFrequency());			
		}

		@Override
		public long getFrequency() {
			return frequency;
		}
		
	}
}