package com.automationanywhere.botcommand.ibmwatson.watsonconnect.config;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateIBMCredentialFile {

    private static final Logger LOGGER = LogManager.getLogger(CreateIBMCredentialFile.class);
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.ibmwatson.watsonconnect.messages");


    public static BooleanValue createIBMCredentailFile(String fileContent) {

        try {
            File credentailFile = new File(System.getProperty(WatsonConnectConstants.USER_HOME) + "/" + WatsonConnectConstants.IBM_CREDENTIALS_FILE);

            if (credentailFile.exists()) {
                credentailFile.delete();
                credentailFile.createNewFile();
                FileWriter fileWriter = new FileWriter(credentailFile);
                fileWriter.write(fileContent);
                fileWriter.close();
            } else {
                credentailFile.createNewFile();
                FileWriter fileWriter = new FileWriter(credentailFile);
                fileWriter.write(fileContent);
                fileWriter.close();
            }
        } catch (IOException io) {
            LOGGER.error(io);
            return new BooleanValue("false");
        }
        return new BooleanValue("true");
    }
}
