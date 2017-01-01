package com.dev.command.impl;

import com.dev.command.Command;
import com.dev.model.Robot;

/**
 * This is the concrete command which defines relationship between Remote Control and Robot. This command helps ordering
 * robot to start.
 * 
 * @author Piyush.Baheti
 *
 */
public class StartCommand implements Command {
    private Robot robot;

    public StartCommand(Robot robot) {
        this.robot = robot;
    }

    public void execute() {
        robot.start();
    }

}
