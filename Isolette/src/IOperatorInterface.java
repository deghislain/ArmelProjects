

public interface IOperatorInterface {
	/**
	 * Indicate the min low desirable temperature value that can be set by a nurse
	 */
	public static final int MIN_LOW_DESIRABLE_TEMP = 97;

	/**
	 * Indicate the max low desirable temperature value that can be set by a nurse
	 */
	public static final int MAX_LOW_DESIRABLE_TEMP = 99;

	/**
	 * Indicate the min upper desirable temperature value that can be set by a nurse
	 */
	public static final int MIN_UPPER_DESIRABLE_TEMP = 98;

	/**
	 * Indicate the max  low desirable temperature value that can be set by a nurse
	 */
	public static final int MAX_UPPER_DESIRABLE_TEMP = 100;

	/**
	 * Indicate the min low alarm temperature value that can be set by a nurse
	 */
	public static final int MIN_LOW_ALARM_TEMP = 93;

	/**
	 * Indicate the max  low alarm temperature value that can be set by a nurse
	 */
	public static final int MAX_LOW_ALARM_TEMP = 98;

	/**
	 * Indicate the min upper alarm temperature value that can be set by a nurse
	 */
	public static final int MIN_UPPER_ALARM_TEMP = 99;

	/**
	 * Indicate the max  low alarm temperature value that can be set by a nurse
	 */
	public static final int MAX_UPPER_ALARM_TEMP = 103;

	public int getLowerDesiredTemp();

	public int getUpperDesiredTemp();

	public int getLowerAlarmTemp();

	public int getUpperAlarmTemp();

	public void execute_round(EStatus regulatorStatus, int displayTemperature, EOnOffControl alarmControl, EStatus monitorStatus, int upperAlarmTemp, int lowerAlarmTemp, int upperDesiredTemp, int lowerDesiredTemp);

}
