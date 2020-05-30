
public class Air implements IAir {

	private float heat = 7500.0f;

	private final float decayRate = 100.0f;

	public void execute_round(float heatDelta) {
	  if(heatDelta != 0.0f) {
	    heat += heatDelta;
	  }
	  else {
	    heat -= decayRate * 0.1f;
	  }
	}

	public float getHeat() {
		return heat;
	}

}
