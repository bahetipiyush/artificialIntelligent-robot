package com.dev.rules;

import com.dev.exception.RobotOperateException;

/**
 * Class which acts as rule engine for different robot behaviors
 * 
 * @author Piyush.Baheti
 *
 */
public class RobotRules {

    public static final float MAX_WIGHT_CARRY = 10;

    public static final float MIN_BATTERY_PERCENTAGE = 15;

    public static final float MAX_WALK_CAPACITY_PER_CHARGE = 5;

    public static final float MAX_BATTERY_CAPACITY = 100;

    public static final float MAX_BATTER_PERCENTAGE_PER_KM = 2;

    public static void canCarryWeight(float weightCarry) throws RobotOperateException {
        if (weightCarry > MAX_WIGHT_CARRY) {
            throw new RobotOperateException("Robot is overweight. Robot Can carry only upto 10Kg weight");
        }
    }

    public static float calculateRobotBatteryConsumption(float currentBatteryPercentage, float walkDistance,
            float weightCarry) {
        float expectedWalkBatterConsmption = 0;
        if (walkDistance > 0) {
            expectedWalkBatterConsmption =
                    RobotRules.calculateWalkBatteryConsumption(currentBatteryPercentage, walkDistance);
        }

        float weightBatteryConsumption = RobotRules.calcuateWeightBatteryConsumption(weightCarry);
        float calculatedConsumption =
                currentBatteryPercentage - (expectedWalkBatterConsmption + weightBatteryConsumption);

        if (calculatedConsumption < 0) {
            calculatedConsumption = 0;
        }

        return calculatedConsumption;
    }

    public static float calculateWalkBatteryConsumption(float currentBatteryPercentage, float walkDistance) {
        return (walkDistance * MAX_BATTERY_CAPACITY) / MAX_WALK_CAPACITY_PER_CHARGE;
    }

    public static float calcuateWeightBatteryConsumption(float weightCarry) {
        return weightCarry * MAX_BATTER_PERCENTAGE_PER_KM;
    }

    public static void checkFullBatteryConsumption(float remainingBatteryPercentage) throws RobotOperateException {
        if (remainingBatteryPercentage <= 0) {
            throw new RobotOperateException("Robot battery is empty. Please charge Again");
        }
    }

    public static boolean checkRobotBatteryStatus(float remainingBatteryPercentage) {
        boolean isBatteryLow = false;
        if (remainingBatteryPercentage < MIN_BATTERY_PERCENTAGE) {

            if (remainingBatteryPercentage <= 0) {
                System.out.println("Robot might not complete the task");
            }
            isBatteryLow = true;
        }
        return isBatteryLow;
    }

}
