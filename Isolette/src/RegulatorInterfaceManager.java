

public class RegulatorInterfaceManager {

	private int lowerDesiredRange = 97;

	private int upperDesiredRange = 100;

	private boolean interfaceFailure = true;

	private EStatus status = EStatus.Init;

	private int displayTemperature = 0;

	public void execute_round(int upperDesiredTemp, int lowerDesiredTemp, float airTemp, EMode mode) {
	  //Update displayTemperature
	  if(mode == EMode.NORMAL) {
      displayTemperature = Math.round(airTemp);
    }
	  else {
	    displayTemperature = 0;
	  }
	  
	  //Update Status
	  if(mode == EMode.INIT) {
	    status = EStatus.Init;
    }
    else if(mode == EMode.NORMAL) {
      status = EStatus.On;
    }
    else if(mode == EMode.FAILED) {
      status = EStatus.Failed;
    }
	  
	  //Update interfaceFailure and DesiredRange
	  if(upperDesiredTemp <= 100 && lowerDesiredTemp >= 97 && lowerDesiredTemp <= upperDesiredTemp) {
      interfaceFailure = false;
      lowerDesiredRange = lowerDesiredTemp;
      upperDesiredRange = upperDesiredTemp;
    }
    else {
      interfaceFailure = true;
    }
	}

	public int getLowerDesiredRange() {
		return lowerDesiredRange;
	}

	public int getUpperDesiredRange() {
		return upperDesiredRange;
	}

	public boolean getRegulatorInterfaceFailure() {
		return interfaceFailure;
	}

	public EStatus getRegulatorStatus() {
		return status;
	}

	public int getDisplayTemperature() {
		return displayTemperature;
	}

}
