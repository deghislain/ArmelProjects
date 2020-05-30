

public class RegulatorModeManager {

	private EMode mode = EMode.INIT;

	private float initTimeout = 1.0f;

	private long startTime = 0L;

	public void execute_round(boolean interfaceFailure, boolean internalFailure, float airTemp) {
	  //Calculate status
	  final boolean status = !(interfaceFailure || internalFailure) && airTemp != 0.0f;
	  
	  //Update mode
	  if(mode == EMode.NORMAL && !status) {
	    mode = EMode.FAILED;
    }
    else if(mode == EMode.INIT) {
      if(startTime == 0L) {
        startTime = System.nanoTime();
      }
      
      if((System.nanoTime() - startTime) / 1000000000 >= initTimeout) {
        mode = EMode.FAILED;
      }
      else if(status) {
        mode = EMode.NORMAL;
      }
    }
	}

	public EMode getMode() {
		return mode;
	}

}
