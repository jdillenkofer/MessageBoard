package de.htwsaar.wirth.server.service;


import static org.junit.Assert.*;

import org.junit.Test;

import de.htwsaar.wirth.remote.model.MessageImpl;
import de.htwsaar.wirth.remote.model.interfaces.Message;

/**
 * Created by olli on 08.02.17.
 */
public class MessageServiceTest {
    MessageServiceImpl service = new MessageServiceImpl();

    @Test
    public void saveMessageTest(){
        Message message = new MessageImpl("Testnachricht", "oseibert", "Wirth");

        int sizeBeforeSave = service.getAll().size();
        service.saveMessage(message);
        int sizeAfterSave = service.getAll().size();

        assertEquals(sizeBeforeSave + 1, sizeAfterSave);
    }
}