

public interface IHeatSource {

	public void execute_round(EOnOffControl heatControl);

	public float getHeatDelta();

}
