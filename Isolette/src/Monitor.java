interface TestConstants
{
	public static final boolean UNIT_TEST_ENABLE = false;
}

public class Monitor {

	private EStatus monitorStatus = EStatus.Init;

	private EOnOffControl alarmControl = EOnOffControl.Off;

	private EOnOffControl lastAlarmState = EOnOffControl.Off;

	private boolean monitorInterfaceFailure = true;

	private boolean monitorInternalFailure = true;

	private EMode mode = EMode.INIT;

	private int lowerAlarmTemp;

	private int upperAlarmTemp;

	private float airTemp;

	private int localAirTemp = 1;


	Monitor()
	{
		lowerAlarmTemp = 96;
		upperAlarmTemp = 102;
		alarmControl = EOnOffControl.Off;
		lastAlarmState = EOnOffControl.Off;
		mode = EMode.INIT;
		monitorStatus = EStatus.Init;
	}


	public void execute_round(int lowerAlarmTemp, int upperAlarmTemp, float airTemp) {

		if (TestConstants.UNIT_TEST_ENABLE)
		{
			System.out.println("LowAlarm: " + this.lowerAlarmTemp + " UpperAlarm: " + this.upperAlarmTemp + " Monitor Status: " + this.monitorStatus + " Mode: " + this.mode);
			System.out.println("Internal Failure:  " + this.monitorInterfaceFailure + " Interface failure: " + this.monitorInterfaceFailure + " Alarm: " + alarmControl);

			airTemp += localAirTemp++;

			//task 1, round 1.setting outputs and checking inputs
			System.out.println("Air Temp:  " + airTemp);
		}

		if(monitorInterfaceFailure && monitorInternalFailure && mode == EMode.INIT)
		{
			if ( CheckMonitorInternalFailure() && CheckMonitorInterfaceFailure())
				this.mode = EMode.NORMAL;
			else
				this.mode = EMode.FAILED;

			if (this.mode == EMode.FAILED)
			{
				this.monitorStatus = EStatus.Failed;
			}

			if (TestConstants.UNIT_TEST_ENABLE) System.out.println("Monitor Mode Task A1  ");

		}
		//Task 2, round 2 setting monitor Status
		else  if (this.mode == EMode.NORMAL && this.monitorStatus == EStatus.Init)
		{
			boolean error = false;
			this.monitorStatus = EStatus.On;

			if (airTemp < 60) error = true;

			if (lowerAlarmTemp >= 96 && lowerAlarmTemp <= 101)
				this.lowerAlarmTemp = lowerAlarmTemp;
			else
				error = true;

			if (upperAlarmTemp >= 97 && upperAlarmTemp <= 102)
				this.upperAlarmTemp = upperAlarmTemp;
			else
				error = true;

			if (error)
			{
				this.monitorStatus = EStatus.Failed;
				this.alarmControl = EOnOffControl.On;
				this.lastAlarmState = this.alarmControl;
			}

			if (TestConstants.UNIT_TEST_ENABLE) System.out.println("Monitor Status Task  A2  ");

		}
		//Task 3 all subsequent rounds.. Alarm control.
		else  if (this.mode == EMode.NORMAL && this.monitorStatus == EStatus.On )
		{
			if ((airTemp < this.lowerAlarmTemp) || (airTemp > this.upperAlarmTemp)) {
				this.alarmControl = EOnOffControl.On;
				this.lastAlarmState = this.alarmControl;

			} else if (((airTemp >= this.lowerAlarmTemp) && (airTemp <= this.lowerAlarmTemp + 0.5)) ||
					((airTemp >= this.upperAlarmTemp - 0.5) && (airTemp <= this.upperAlarmTemp)))
			{

				this.alarmControl = this.lastAlarmState;

			} else {

				this.alarmControl = EOnOffControl.Off;
				this.lastAlarmState = this.alarmControl;
			}

			if (TestConstants.UNIT_TEST_ENABLE) System.out.println("Monitoring Alarm Task A2  ");

		}

		return;

	}

	private boolean CheckMonitorInterfaceFailure()
	{
		monitorInterfaceFailure = false;
		return true;
	}

	private boolean CheckMonitorInternalFailure()
	{
		monitorInternalFailure = false;
		return true;
	}

	public EOnOffControl getAlarmControl() {
		return this.alarmControl;
	}

	public EStatus getMonitorStatus() {
		return this.monitorStatus;
	}

}
