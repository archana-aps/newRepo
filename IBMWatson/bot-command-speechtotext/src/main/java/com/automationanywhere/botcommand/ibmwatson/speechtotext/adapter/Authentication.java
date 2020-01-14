package com.automationanywhere.botcommand.ibmwatson.speechtotext.adapter;

import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.ibm.cloud.sdk.core.service.security.IamOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Authentication {

    private static String IBM_CREDENTIALS_FILE = "ibm-credentials.env";
    private static String SPEECH_TO_TEXT_IAM_APIKEY="SPEECH_TO_TEXT_IAM_APIKEY";
    private static final String USER_HOME = "user.home";
    private static final Logger LOGGER = LogManager.getLogger(Authentication.class);
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.ibmwatson.speechtotext.messages");


    public static IamOptions getIamOptionsConfig() {
        return new IamOptions.Builder()
                .apiKey(getSpeechToTextAPIKey())
                .build();
    }

    private static String getSpeechToTextAPIKey() {
        String fileName = System.getProperty(USER_HOME)+"/"+IBM_CREDENTIALS_FILE;
        String apikey = "";
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName))) ){
            String line;
            while((line = br.readLine()) != null){
                LOGGER.debug("line for credential file "+line);
                if (line.contains(SPEECH_TO_TEXT_IAM_APIKEY)) {
                    apikey = line.substring(line.indexOf("=")+1).replace("\n","").trim();
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
            throw new BotCommandException(MESSAGES.getString("fileNotFoundOrNotRunningWithConnect"),e);
        }catch (IOException io) {
            LOGGER.error(io);
            throw new BotCommandException(MESSAGES.getString("ioExceptionInReadingIBMCredentialsFile"),io);
        }
        if ("".equals(apikey.trim())) {
            throw new BotCommandException(MESSAGES.getString("exceptingInReadingEnvFile"));
        }
        return apikey;
    }
}
