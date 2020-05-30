

public class OperatorInterface implements IOperatorInterface {

	private int lowerDesiredTemp = 97;

	private int upperDesiredTemp = 98;

	private int lowerAlarmTemp = 96;

	private int upperAlarmTemp = 100;
	
	private EStatus thermostatStatus;
	
	private EOnOffControl alarmStatus;
	 
	private int currentTemperature;
	
	private ETemperatureStatus lowerDesiredTempStatus;
	
	private ETemperatureStatus upperDesiredTempStatus;
	
	private ETemperatureStatus lowerAlarmTempStatus;
	
	private ETemperatureStatus upperAlarmTempStatus;

	
	
	public OperatorInterface() {
		this.thermostatStatus = EStatus.Init;
		this.lowerDesiredTempStatus = ETemperatureStatus.INVALID;
		this.upperDesiredTempStatus = ETemperatureStatus.INVALID;
		this.lowerAlarmTempStatus = ETemperatureStatus.INVALID;
		this.upperAlarmTempStatus = ETemperatureStatus.INVALID;
		this.alarmStatus = EOnOffControl.Off;
	}

	public void execute_round(EStatus regulatorStatus, int displayTemperature, EOnOffControl alarmControl, EStatus monitorStatus, int upperAlarmTemp, int lowerAlarmTemp, int upperDesiredTemp, int lowerDesiredTemp) {
		setThermostatStatus(monitorStatus, regulatorStatus);
		if(thermostatStatus == EStatus.Normal) {
			setDesiredTemperature(lowerDesiredTemp, upperDesiredTemp);
			setAlarmTemperature(lowerAlarmTemp, upperAlarmTemp);
		}
		setAlarmStatus(alarmControl);
		setCurrentTemperature(displayTemperature);
		updateDisplay();
	}

	public int getLowerDesiredTemp() {
		return this.lowerDesiredTemp;
	}

	public int getUpperDesiredTemp() {
		return this.upperDesiredTemp;
	}

	public int getLowerAlarmTemp() {
		return this.lowerAlarmTemp;
	}

	public int getUpperAlarmTemp() {
		return this.upperAlarmTemp;
	}
	
	 private void setDesiredTemperature(int lowDesiredTemp, int upperDesiredTemp){
		 this.lowerDesiredTemp = lowDesiredTemp;
		 this.upperDesiredTemp = upperDesiredTemp;
		this.lowerDesiredTempStatus = validateLowDesirableTemp(lowDesiredTemp, upperDesiredTemp);
		this.upperDesiredTempStatus = validateUpperDesirableTemp(upperDesiredTemp);
		}
	 private void setAlarmTemperature(int lowAlarmTemp, int upperAlarmTemp){
		 this.lowerAlarmTemp = lowAlarmTemp;
		 this.upperAlarmTemp = upperAlarmTemp;
		this.lowerAlarmTempStatus = validateLowAlarmTemp(lowAlarmTemp, upperAlarmTemp);
		this.upperAlarmTempStatus = validateUpperAlarmTemp(upperAlarmTemp,this.upperDesiredTemp);
	 }
	 private void setAlarmStatus(EOnOffControl alarmControl){
			this.alarmStatus = alarmControl;
		}
		private void setCurrentTemperature(int currTemp) {
			 this.currentTemperature = currTemp;
		}
		
	 private ETemperatureStatus validateLowDesirableTemp(int lowDesiredTemp, int upperDesiredTemp) {
			if(MIN_LOW_DESIRABLE_TEMP <= lowDesiredTemp && lowDesiredTemp <= MAX_LOW_DESIRABLE_TEMP && lowDesiredTemp <= upperDesiredTemp-1) {
				return ETemperatureStatus.VALID;
			}else{
				return ETemperatureStatus.INVALID;
			}
		}
		
		private ETemperatureStatus validateUpperDesirableTemp(int upperDesiredTemp) {
			if(MIN_UPPER_DESIRABLE_TEMP <= upperDesiredTemp && upperDesiredTemp <= MAX_UPPER_DESIRABLE_TEMP) {
				return ETemperatureStatus.VALID;
			}else{
				return ETemperatureStatus.INVALID;
			}
		}
		
		private ETemperatureStatus validateLowAlarmTemp(int lowAlarmTemp, int lowDesiredTemp) {
			if(MIN_LOW_ALARM_TEMP <= lowAlarmTemp && lowAlarmTemp <= MAX_LOW_ALARM_TEMP && lowAlarmTemp <= lowDesiredTemp-1) {
				return ETemperatureStatus.VALID;
			}else{
				return ETemperatureStatus.INVALID;
			}
		}
		
		private ETemperatureStatus validateUpperAlarmTemp(int upperAlarmTemp, int upperDesiredTemp) {
			if(MIN_UPPER_ALARM_TEMP <= upperAlarmTemp && upperAlarmTemp <= MAX_UPPER_ALARM_TEMP && upperAlarmTemp >= upperDesiredTemp+1) {
				return ETemperatureStatus.VALID;
			}else{
				return ETemperatureStatus.INVALID;
			}
		}
		
		private void setThermostatStatus(EStatus monitorStatus, EStatus regulatorStatus) {
			if (((monitorStatus == EStatus.Init) && (regulatorStatus != EStatus.Failed)) || ((regulatorStatus == EStatus.Init) && (monitorStatus != EStatus.Failed))) {
				this.thermostatStatus = EStatus.Init;
			 }else if ((monitorStatus == EStatus.On) && (regulatorStatus == EStatus.On)) {
				 this.thermostatStatus = EStatus.Normal;
			 }else if ((monitorStatus == EStatus.Failed) || (regulatorStatus == EStatus.Failed)) {
				this.thermostatStatus = EStatus.Failed;
			 }
			
		}
		
		private void updateDisplay() {
			String currTemperature = "UNSPECIFIED";
			if(this.thermostatStatus == EStatus.Normal) {
				currTemperature = Integer.toString(this.currentTemperature);
			}
			System.out.printf("%-10s %-10s %-10s %-10s %-20s %-20s %-10s %n", "LDT: " + this.lowerDesiredTemp,
			        "UDT: " + this.upperDesiredTemp, "LAT: " + this.lowerAlarmTemp, "UAT: " + this.upperAlarmTemp, "Thermostat Status: " + this.thermostatStatus,
			        "Current Temperature: " + currTemperature, "Alarm Status: " + this.alarmStatus);
			String tempStatus = null;
			
			if(this.thermostatStatus == EStatus.Normal) {
			if(this.lowerDesiredTempStatus == ETemperatureStatus.INVALID) {
				tempStatus = "The LDT is INVALID";  
			}
			if(this.upperDesiredTempStatus == ETemperatureStatus.INVALID) {
				tempStatus = tempStatus + "   The UDT is INVALID";  
			}
			if(this.lowerAlarmTempStatus == ETemperatureStatus.INVALID) {
				tempStatus = tempStatus + "   The LAT is INVALID";  
			}
			if(this.upperAlarmTempStatus == ETemperatureStatus.INVALID) {
				tempStatus = tempStatus + "   The UAT is INVALID";  
			}
			if(null != tempStatus)
			System.out.println(tempStatus);
			}
		}

}
