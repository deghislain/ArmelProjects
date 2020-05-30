import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
	  final int lowerDesiredTemp =97;// Integer.parseInt(args[0]);
    final int upperDesiredTemp =99;//Integer.parseInt(args[1]);
    final int lowerAlarmTemp =96;//Integer.parseInt(args[2]);
    final int upperAlarmTemp =100;//Integer.parseInt(args[3]);

		IsoletteSystem isolette = new IsoletteSystem();

		while (true) {

			isolette.execute_round(lowerDesiredTemp, upperDesiredTemp, lowerAlarmTemp, upperAlarmTemp);
			
			try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
		}
	}

}
