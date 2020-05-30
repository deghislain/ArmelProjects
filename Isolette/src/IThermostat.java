

public interface IThermostat {

	public void execute_round(int upperAlarmTemp, int lowerAlarmTemp, int upperDesiredTemp, int lowerDesiredTemp, float airTemp);

	public EStatus getMonitorStatus();

	public EStatus getRegulatorStatus();

	public EOnOffControl getAlarmControl();

	public EOnOffControl getHeatControl();

	public int getDisplayTemperature();
	
}
