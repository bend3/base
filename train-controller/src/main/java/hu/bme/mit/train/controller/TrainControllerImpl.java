package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.Timer;
import java.util.TimerTask;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	Table<Integer, Integer, Integer> tacographData = HashBasedTable.create();
	private Timer timer;

	//override constructor
	public TrainControllerImpl() {
		//start a timer and follow speed every 1000ms
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				followSpeed();
			}
		}, 0, 1000);
	}

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

	@Override
	public void emergencyBrake(){
		setSpeedLimit(0);
	}

	@Override
	public void saveTachographData(){
		// save data using google guava table
		tacographData.put(referenceSpeed, speedLimit, step);
	}

	@Override
	public Table<Integer, Integer, Integer> getTachographData(){
		return tacographData;
	}

}
