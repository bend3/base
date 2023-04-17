package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		// new limit should be between 0 and 500, otherwise set alarm
		if (speedLimit < 0 || speedLimit > 500) {
			user.setAlarmState(true);
		}

		// set alarm if new speed limit is less than 50% of the reference speed
		if (speedLimit < controller.getReferenceSpeed() * 0.5) {
			user.setAlarmState(true);
		}
		controller.setSpeedLimit(speedLimit);
	}

}
