package com.dev.command.impl;

import com.dev.command.Command;
import com.dev.model.Robot;

/**
 * This is the concrete command which defines relationship between Remote Control and Robot. This command helps ordering
 * robot to scan a product.
 * 
 * @author Piyush.Baheti
 *
 */
public class ScanCommand implements Command {

    private Robot robot;

    public ScanCommand(Robot robot) {
        this.robot = robot;
    }

    public void execute() {
        robot.scanBarCode();
    }

}
