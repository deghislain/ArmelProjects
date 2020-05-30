import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsoletteSystem {

  private IAir air;

  private IHeatSource heatSource;

  private ITemperatureSensor temperatureSensor;

  private IThermostat thermostat;

  private IOperatorInterface operatorInterface;

  IsoletteSystem() {
    this.air = new Air();
    this.heatSource = new HeatSource();
    this.temperatureSensor = new TemperatureSensor();
    this.thermostat = new Thermostat();
    this.operatorInterface = new OperatorInterface();
  }
int i = 1;
  public void execute_round(int nurseLowerDesiredTemp, int nurseUpperDesiredTemp, int nurseLowerAlarmTemp, int nurseUpperAlarmTemp) {
	  System.out.println();
	  System.out.println("ROUND" + i++);
    final float heat = air.getHeat();
    final float heatDelta = heatSource.getHeatDelta();
    final float airTemp = temperatureSensor.getAirTemp();
    final EStatus monitorStatus = thermostat.getMonitorStatus();
    final EStatus regulatorStatus = thermostat.getRegulatorStatus();
    final EOnOffControl alarmControl = thermostat.getAlarmControl();
    final EOnOffControl heatControl = thermostat.getHeatControl();
    final int displayTemperature = thermostat.getDisplayTemperature();
    final int lowerDesiredTemp = operatorInterface.getLowerDesiredTemp();
    final int upperDesiredTemp = operatorInterface.getUpperDesiredTemp();
    final int lowerAlarmTemp = operatorInterface.getLowerAlarmTemp();
    final int upperAlarmTemp = operatorInterface.getUpperAlarmTemp();
    
    Thread airThread = new Thread(new Runnable() {
      @Override
      public void run() {
        air.execute_round(heatDelta);
        System.out.println("Run by " + Thread.currentThread().getName() +" "+ convertTime(System.currentTimeMillis()));
      }
      
    });
    Thread heatSourceThread = new Thread(new Runnable() {
      @Override
      public void run() {
    	  System.out.println("Run by " + Thread.currentThread().getName() +" "+ convertTime(System.currentTimeMillis()));
        heatSource.execute_round(heatControl);
      }
    });

    Thread temperatureSensorThread = new Thread(new Runnable() {
      @Override
      public void run() {
    	  System.out.println("Run by " + Thread.currentThread().getName() +" "+ convertTime(System.currentTimeMillis()));
        temperatureSensor.execute_round(heat);
      }
    });

    Thread operatorInterfaceThread = new Thread(new Runnable() {
      @Override
      public void run() {
    	  System.out.println("Run by " + Thread.currentThread().getName() +" "+ convertTime(System.currentTimeMillis()));
        operatorInterface.execute_round(regulatorStatus, displayTemperature, alarmControl, monitorStatus,
            nurseUpperAlarmTemp, nurseLowerAlarmTemp, nurseUpperDesiredTemp, nurseLowerDesiredTemp);
      }
    });
    airThread.setName("air");
    heatSourceThread.setName("heat");
    temperatureSensorThread.setName("ts");
    operatorInterfaceThread.setName("oi");
    airThread.start();
    heatSourceThread.start();
    temperatureSensorThread.start();
    operatorInterfaceThread.start();

    thermostat.execute_round(upperAlarmTemp, lowerAlarmTemp, upperDesiredTemp, lowerDesiredTemp, airTemp);

    try {
      airThread.join();
      heatSourceThread.join();
      temperatureSensorThread.join();
      operatorInterfaceThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
	    return format.format(date);
	}
}


