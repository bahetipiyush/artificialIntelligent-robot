package robot;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dev.command.Command;
import com.dev.command.control.RemotControl;
import com.dev.command.impl.ScanCommand;
import com.dev.command.impl.StartCommand;
import com.dev.command.impl.WalkCommand;
import com.dev.constant.Color;
import com.dev.exception.RobotOperateException;
import com.dev.model.Robot;

/**
 * Test class to validate Different Robot Commands
 * 
 * @author Piyush.Baheti
 *
 */
public class RobotCommandTest {

    private static RemotControl remoteControl;

    @BeforeClass
    public static void intializeBefore() {
        remoteControl = new RemotControl();
    }

    @Test
    public void testRobotWalk() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).walkDistance(3.5f).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command walk = new WalkCommand(robot);
        remoteControl.setCommand(walk);
        remoteControl.commandRobot();

        float expectedBatteryResult = 30.0f;
        Assert.assertEquals(expectedBatteryResult, robot.getCurrentBatteryPercentage(), 0);
    }

    @Test
    public void testRobotWalksAndCarriesWeight() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).walkDistance(2).weightCarry(3).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command walk = new WalkCommand(robot);
        remoteControl.setCommand(walk);
        remoteControl.commandRobot();

        float expectedBatteryResult = 54.0f;
        Assert.assertEquals(expectedBatteryResult, robot.getCurrentBatteryPercentage(), 0);
    }

    @Test
    public void testBatteryCriticalStatus() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).walkDistance(4.5f).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command walk = new WalkCommand(robot);
        remoteControl.setCommand(walk);
        remoteControl.commandRobot();
        String expectedLedDisplayMessage = "Battery is Low. Please Charge the Battery";
        Color ColorOnRobotHead = Color.RED;
        assertEquals(expectedLedDisplayMessage, robot.getLedDisplayMessage());
        assertEquals(ColorOnRobotHead, robot.getColorOnHead());
    }

    @Test
    public void testBarCodeScanClear() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).canScan(true).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command scan = new ScanCommand(robot);
        remoteControl.setCommand(scan);
        remoteControl.commandRobot();
        String sub = robot.getLedDisplayMessage().substring(0, 8);
        assertEquals("Price is", sub);
    }

    @Test
    public void testBarCodeScanNotClear() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).canScan(false).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command scan = new ScanCommand(robot);
        remoteControl.setCommand(scan);
        remoteControl.commandRobot();
        String expectedMessage = "Scan Failure";
        assertEquals(expectedMessage, robot.getLedDisplayMessage());
    }

    @Test(expected = RobotOperateException.class)
    public void testBatteryExhaustThrowsROE() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).walkDistance(7.0f).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command walk = new WalkCommand(robot);
        remoteControl.setCommand(walk);
        remoteControl.commandRobot();
    }

    @Test(expected = RobotOperateException.class)
    public void testNoActionWhenEmptyBattery() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(0).weightCarry(9).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command walk = new WalkCommand(robot);
        remoteControl.setCommand(walk);
        remoteControl.commandRobot();
    }

    @Test(expected = RobotOperateException.class)
    public void testRobotOverloadThrowsROE() {
        Robot robot = new Robot.RobotBuilder().currentBatteryPercentage(100).weightCarry(12).build();

        Command start = new StartCommand(robot);
        remoteControl.setCommand(start);
        remoteControl.commandRobot();

        Command walk = new WalkCommand(robot);
        remoteControl.setCommand(walk);
        remoteControl.commandRobot();
    }

}
