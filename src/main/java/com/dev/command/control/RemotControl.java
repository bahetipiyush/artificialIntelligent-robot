package com.dev.command.control;

import com.dev.command.Command;

/**
 * Control/Receiver class which knows which command to execute, to carry out the request.
 * 
 * @author Piyush.Baheti
 *
 */
public class RemotControl {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void commandRobot() {
        command.execute();
    }

}
