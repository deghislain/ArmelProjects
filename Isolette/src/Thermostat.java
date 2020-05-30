

public class Thermostat implements IThermostat {

	private Monitor monitor;

	private Regulator regulator;

	Thermostat()
	{
		this.monitor  = new Monitor();
		this.regulator  = new Regulator();
	}

	public void execute_round(int upperAlarmTemp, int lowerAlarmTemp, int upperDesiredTemp, int lowerDesiredTemp, float airTemp) {

		Thread monitorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				monitor.execute_round(lowerAlarmTemp, upperAlarmTemp,  airTemp);
			}
		});

		Thread regulatorThread = new Thread(new Runnable() {
      @Override
      public void run() {
        regulator.execute_round(lowerDesiredTemp, upperDesiredTemp, airTemp);        
      }     
    });		
		
		monitorThread.start();
		regulatorThread.start();

		try {
		  monitorThread.join();
			regulatorThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public EStatus getMonitorStatus() {
		return monitor.getMonitorStatus();
	}

	public EStatus getRegulatorStatus() {
		return regulator.getStatus();
	}

	public EOnOffControl getAlarmControl() {
		return monitor.getAlarmControl();
	}

	public EOnOffControl getHeatControl() {
		return regulator.getHeatControl();
	}
	
	public int getDisplayTemperature() {
	  return regulator.getDisplayTemperature();
	}

}
