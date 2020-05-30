
public class HeatSource implements IHeatSource {

	private float heatDelta = 0.0f;
	
	private final float heatingRate = 100.0f;

	public void execute_round(EOnOffControl heatControl) {
	  if(heatControl == EOnOffControl.On) {
	    heatDelta = heatingRate * 0.1f;
	  }
	  else {
	    heatDelta = 0.0f;
	  }
	}

	public float getHeatDelta() {
		return heatDelta;
	}

}
