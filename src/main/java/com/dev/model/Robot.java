package com.dev.model;

import java.util.Random;

import com.dev.constant.Color;
import com.dev.exception.RobotOperateException;
import com.dev.rules.RobotRules;

/**
 * Class defining state and behavior of Robot object
 * 
 * @author Piyush.Baheti
 *
 */
public class Robot {

    private float currentBatteryPercentage;
    private Color colorOnHead;
    private String ledDisplayMessage;
    private float walkDistance;
    private float weightCarry;
    private boolean canScan;

    private Robot(RobotBuilder robotBuilder) {
        this.currentBatteryPercentage = robotBuilder.currentBatteryPercentage;
        this.colorOnHead = robotBuilder.colorOnHead;
        this.ledDisplayMessage = robotBuilder.ledDisplayMessage;
        this.walkDistance = robotBuilder.walkDistance;
        this.weightCarry = robotBuilder.weightCarry;
        this.canScan = robotBuilder.canScan;
    }

    public static class RobotBuilder {
        private float currentBatteryPercentage;
        private Color colorOnHead;
        private String ledDisplayMessage;
        private float walkDistance;
        private float weightCarry;
        private boolean canScan;

        public RobotBuilder currentBatteryPercentage(float currentBatteryPercentage) {
            this.currentBatteryPercentage = currentBatteryPercentage;
            return this;
        }

        public RobotBuilder colorOnHead(Color colorOnHead) {
            this.colorOnHead = colorOnHead;
            return this;
        }

        public RobotBuilder ledDisplayMessage(String ledDisplayMessage) {
            this.ledDisplayMessage = ledDisplayMessage;
            return this;
        }

        public RobotBuilder walkDistance(float walkDistance) {
            this.walkDistance = walkDistance;
            return this;
        }

        public RobotBuilder weightCarry(float weightCarry) {
            this.weightCarry = weightCarry;
            return this;
        }

        public RobotBuilder canScan(boolean canScan) {
            this.canScan = canScan;
            return this;
        }

        public Robot build() {
            return new Robot(this);
        }
    }

    private void setBatteryLowMessage() {
        this.ledDisplayMessage = "Battery is Low. Please Charge the Battery";
    }

    public float getCurrentBatteryPercentage() {
        return currentBatteryPercentage;
    }

    public Color getColorOnHead() {
        return colorOnHead;
    }

    public String getLedDisplayMessage() {
        return ledDisplayMessage;
    }

    public float getWalkDistance() {
        return walkDistance;
    }

    public float getWeightCarry() {
        return weightCarry;
    }

    @Override
    public String toString() {
        return "Robot [currentBatteryPercentage=" + currentBatteryPercentage + ", colorOnHead=" + colorOnHead
                + ", ledDisplayMessage=" + ledDisplayMessage + ", walkDistance=" + walkDistance + ", weightCarry="
                + weightCarry + ", canScan=" + canScan + "]";
    }

    /**
     * This method starts the robot based on its battery status.
     * 
     * @throws RobotOperateException
     */
    public void start() throws RobotOperateException {
        try {
            RobotRules.canCarryWeight(weightCarry);

            float expectedTotalBatteryConsumption =
                    RobotRules.calculateRobotBatteryConsumption(currentBatteryPercentage, walkDistance, weightCarry);

            System.out.println("Robot Start...");
            boolean isBatteryLow = RobotRules.checkRobotBatteryStatus(expectedTotalBatteryConsumption);

            if (isBatteryLow) {
                this.colorOnHead = Color.RED;
                setBatteryLowMessage();
            }
        } catch (RobotOperateException robotOperateException) {
            this.ledDisplayMessage = "Robot is OverWight. Please decrease the load on Robot";
            throw robotOperateException;
        }
    }

    /**
     * This method makes robot walk, lift weight and updates battery consumption
     * 
     * @throws RobotOperateException
     */
    public void walk() throws RobotOperateException {
        System.out.println("Robot Started Walking...");
        this.currentBatteryPercentage =
                RobotRules.calculateRobotBatteryConsumption(currentBatteryPercentage, walkDistance, weightCarry);
        RobotRules.checkFullBatteryConsumption(this.currentBatteryPercentage);
        boolean isBatteryLow = RobotRules.checkRobotBatteryStatus(this.currentBatteryPercentage);

        if (isBatteryLow) {
            this.colorOnHead = Color.RED;
            setBatteryLowMessage();
        } else {
            this.ledDisplayMessage = "Robot has finished Walking distance " + walkDistance;
        }
    }

    /**
     * This method makes robot scan different products, based on inputs (canScan) from the API.
     * 
     * @throws RobotOperateException
     */
    public void scanBarCode() throws RobotOperateException {
        if (canScan) {
            Random random = new Random();
            int price = random.nextInt(50) + 1;
            this.ledDisplayMessage = "Price is " + price;
        } else {
            this.ledDisplayMessage = "Scan Failure";
        }

    }

}
