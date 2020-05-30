
public class Regulator {

	private RegulatorInterfaceManager interfaceManager;

	private RegulatorModeManager modeManager;

	private HeatSourceManager heatSourceManager;

	private RegulatorFailureDetector failureDetector;

	Regulator(){
	  interfaceManager = new RegulatorInterfaceManager();
	  modeManager = new RegulatorModeManager();
	  heatSourceManager = new HeatSourceManager();
	  failureDetector = new RegulatorFailureDetector();
	}
	
	public void execute_round(int lowerDesiredTemp, int upperDesiredTemp, float airTemp) {
	  final int lowerDesiredRange = interfaceManager.getLowerDesiredRange();
	  final int upperDesiredRange = interfaceManager.getUpperDesiredRange();
	  final boolean interfaceFailure = interfaceManager.getRegulatorInterfaceFailure();
	  final EMode mode = modeManager.getMode();
	  final boolean internalFailure = failureDetector.getInternalFailure();
	  
	  interfaceManager.execute_round(upperDesiredTemp, lowerDesiredTemp, airTemp, mode);
	  modeManager.execute_round(interfaceFailure, internalFailure, airTemp);
	  heatSourceManager.execute_round(lowerDesiredRange, upperDesiredRange, mode, airTemp);
	  failureDetector.execute_round();
	}

	public EOnOffControl getHeatControl() {
		return heatSourceManager.getHeatControl();
	}

	public EStatus getStatus() {
		return interfaceManager.getRegulatorStatus();
	}
	
	public int getDisplayTemperature() {
	  return interfaceManager.getDisplayTemperature();
	}

}
