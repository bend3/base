package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    // mock TrainController
    TrainController controller;
    // mock TrainUser
    TrainUser user;
    TrainSensorImpl sensor;
    
    @Before
    public void before() {
        // TODO Add initializations
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        sensor = new TrainSensorImpl(controller, user);
    }

    @Test
    public void ThisIsAnExampleTestStub() {
        // TODO Delete this and add test cases based on the issues
    }

    /**
     * New speed limit is negative
     * The alarm should be set
     */
    @Test
    public void NewSpeedLimitIsNegative_SetsAlarm() {

        sensor.overrideSpeedLimit(-1);
        // at lease one time
        verify(user, atLeast(1)).setAlarmState(true);
    }

    /**
     * The new speed limit is over 500
     * The alarm should be set
     */
    @Test
    public void NewSpeedLimitIsOver500_SetsAlarm() {

        sensor.overrideSpeedLimit(501);
        verify(user, times(1)).setAlarmState(true);
    }

    /**
     * The new speed limit is in range
     * The alarm should not be set
     */
    @Test
    public void NewSpeedLimitIsInRange_DoesNotSetAlarm() {

        when(controller.getReferenceSpeed()).thenReturn(100);
        sensor.overrideSpeedLimit(100);
        verify(user, times(0)).setAlarmState(true);
    }

    /**
     * The new speed limit is under 50% of the reference speed
     * The alarm should be set
     */
    @Test
    public void NewSpeedLimitIsUnder50PercentOfReferenceSpeed_SetsAlarm() {

        when(controller.getReferenceSpeed()).thenReturn(100);
        sensor.overrideSpeedLimit(49);
        verify(user, times(1)).setAlarmState(true);
    }

    /**
     * The new speed limit is over 50% of the reference speed
     * The alarm should not be set
     */
    @Test
    public void NewSpeedLimitIsOver50PercentOfReferenceSpeed_DoesNotSetAlarm() {

        when(controller.getReferenceSpeed()).thenReturn(100);
        sensor.overrideSpeedLimit(50);
        verify(user, times(0)).setAlarmState(true);
    }

    /**
     * Get the current speed limit
     * The speed limit should be the same as the one set
     */

    @Test
    public void GetSpeedLimit_ReturnsCorrectSpeedLimit() {

        when(controller.getReferenceSpeed()).thenReturn(100);
        sensor.overrideSpeedLimit(50);
        Assert.assertEquals(50, sensor.getSpeedLimit());
    }

}
