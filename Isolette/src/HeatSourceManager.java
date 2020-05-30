

public class HeatSourceManager {

	private EOnOffControl heatControl = EOnOffControl.Off;

	public void execute_round(int lowerDesiredTemp, int upperDesiredTemp, EMode mode, float airTemp) {
	  if(mode != EMode.NORMAL) {
      heatControl = EOnOffControl.Off;
    }
    else {
      if(airTemp > upperDesiredTemp) {
        heatControl = EOnOffControl.Off;
      }
      else if(airTemp < lowerDesiredTemp) {
        heatControl = EOnOffControl.On;
      }
    }
	}

	public EOnOffControl getHeatControl() {
		return heatControl;
	}

}
