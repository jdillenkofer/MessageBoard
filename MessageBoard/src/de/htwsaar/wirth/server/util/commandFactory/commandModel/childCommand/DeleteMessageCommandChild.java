package de.htwsaar.wirth.server.util.commandFactory.commandModel.childCommand;

import de.htwsaar.wirth.remote.Notifiable;
import de.htwsaar.wirth.remote.model.interfaces.Message;
import de.htwsaar.wirth.server.util.commandFactory.commandModel.Command;

import java.rmi.RemoteException;

public class DeleteMessageCommandChild extends ChildCommand {

    private Message messageToDelete;
    private Notifiable childServer;

    public DeleteMessageCommandChild(Notifiable childServer, Message messageToDelete) {
        this.messageToDelete = messageToDelete;
        this.childServer = childServer;
    }

    public DeleteMessageCommandChild(Message messageToDelete) {
        this.messageToDelete = messageToDelete;
    }

    public void execute() throws RemoteException {
        childServer.notifyDelete(messageToDelete);
    }

    @Override
    public void setNotifiable(Notifiable childServer) {
        this.childServer = childServer;
    }

    public ChildCommand clone() {
        return new DeleteMessageCommandChild(this.childServer,this.messageToDelete);
    }
}


