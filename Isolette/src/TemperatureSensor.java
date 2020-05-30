

public class TemperatureSensor implements ITemperatureSensor {

	private float airTemp = 75.0f;

	public void execute_round(float heat) {
	  airTemp = heat / 100.0f;
	}

	public float getAirTemp() {
		return airTemp;
	}

}
