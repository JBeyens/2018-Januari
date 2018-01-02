package model.business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.business.GateModule;
import model.business.RemoteModule;
import model.entities.Person;
import model.entities.Remote;
import model.interfaces.IGateModule;
import model.interfaces.IRemoteModule;
import values.DefaultSettings;

/**
 * 	@Author Jef Beyens & Ben Vandevorst
	@Datum 02/01/2018
	@Project Afstandsbediening
	@Doel Test of RemoteModule
 */
public class RemoteModuleTest {
	private Remote remote;
	private RemoteModule remoteModule;
	private ArrayList<Person> persons;
	long newFrequency;
	
	@Before
	public void setUp(){
		remote = new Remote("xyz", 911);
		remoteModule = new RemoteModule(remote);
		newFrequency = 666;
		ArrayList<Person> persons = new ArrayList<>();
	}
	
	@Test
	public void Set_Frequency_Should_Modify_The_Remote() {
		remoteModule.setFrequency(newFrequency);
		assertEquals(newFrequency, remote.getFrequency());
	}
	
	@Test
	public void Ask_Open_Gate_Should_Fail_If_Not_Verified_By_Gate() {
		GateModuleForTest gateModule = new GateModuleForTest(newFrequency, false);
		assertFalse(remoteModule.askOpenGate(gateModule));
	}
	
	@Test
	public void Ask_Open_Gate_Should_Fail_If_Verified_By_Gate() {
		GateModuleForTest gateModule = new GateModuleForTest(newFrequency, true);
		assertTrue(remoteModule.askOpenGate(gateModule));
	}
	
	@Test
	public void Get_Serial_Number_Should_Return_Serial_From_Remote() {
		assertTrue(remote.getSerialNumber().equals(remoteModule.getSerialNumber()));
	}
	
	private class GateModuleForTest implements IGateModule {
		private long frequency;
		private boolean verifySuccesfull;
		
		public GateModuleForTest(long frequency, boolean willVerifyBeSuccesfull) {
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