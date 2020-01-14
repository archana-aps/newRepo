package com.automationanywhere.botcommand.ibmwatson.watsonconnect.commands;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.botcommand.ibmwatson.watsonconnect.config.CreateIBMCredentialFile;
import com.automationanywhere.botcommand.ibmwatson.watsonconnect.config.WatsonConnectConstants;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.automationanywhere.commandsdk.model.AttributeType.*;

@BotCommand
@CommandPkg(label = "IBM Watson Authentication", name = "IBM Watson Authentication", description = "API Keys and URL's for IBM Watson Services", icon = "connect-icon.svg",
        node_label = "API Keys and URL's for IBM Watson Services")
public class WatsonConnect {


    private static final Logger LOGGER = LogManager.getLogger(WatsonConnect.class);
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.ibmwatson.watsonconnect.messages");

    @Execute
    public BooleanValue execute(@Idx(index = "1", type = TEXT) @Pkg(label = "Enter Speech To Text API Key", description = "", default_value = "")  String speechToTextAPIKey,
            @Idx(index = "2", type = TEXT) @Pkg(label = "Enter Speech To Text URL", default_value = "",description  = "eg: https://...") String speechToTextUrl,
            @Idx(index = "3", type = TEXT) @Pkg(label = "Enter Compare And Comply  API Key", description = "", default_value = "")  String compareComplyAPIKey,
            @Idx(index = "4", type = TEXT) @Pkg(label = "Enter Compare And Comply URL", default_value = "",description  = "eg: https://...") String compareComplyUrl,
            @Idx(index = "5", type = TEXT) @Pkg(label = "Enter Natural Language Understanding  API Key", description = "", default_value = "")  String naturalLangUnderstandingAPIKey,
            @Idx(index = "6", type = TEXT) @Pkg(label = "Enter Natural Language Understanding URL", description = "eg: https://...", default_value = "") String naturalLangUnderstandingUrl) {

        String fileContent = "";
        BooleanValue isCredentialsCreated =  new BooleanValue("false");

        Pattern p = Pattern.compile("(https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");
        Matcher m;


        if (("".equals(speechToTextAPIKey.trim()) && !"".equals(speechToTextUrl.trim())) || (!"".equals(speechToTextAPIKey.trim()) && "".equals(speechToTextUrl.trim()))) {
            throw new BotCommandException(MESSAGES.getString("shouldEnterBothAPIKeyAndUrlForSpeechToText",""));
        }

        if (("".equals(compareComplyAPIKey.trim()) && !"".equals(compareComplyUrl.trim())) || (!"".equals(compareComplyAPIKey.trim()) && "".equals(compareComplyUrl.trim()))) {
            throw new BotCommandException(MESSAGES.getString("shouldEnterBothAPIKeyAndUrlForCompareComply",""));
        }

        if (("".equals(naturalLangUnderstandingAPIKey.trim()) && !"".equals(naturalLangUnderstandingUrl.trim())) || (!"".equals(naturalLangUnderstandingAPIKey.trim()) && "".equals(naturalLangUnderstandingUrl.trim()))) {
            throw new BotCommandException(MESSAGES.getString("shouldEnterBothAPIKeyAndUrlForNaturalLanguageUnderstanding",""));
        }


        if (!"".equals(speechToTextAPIKey.trim()) && !"".equals(speechToTextUrl.trim())) {

            m = p.matcher(speechToTextUrl);

            if (!m.find()) {
                throw new BotCommandException(MESSAGES.getString("enterValidURL","speechToTextUrl"));
            }

            fileContent = new StringBuilder().append(fileContent).append(WatsonConnectConstants.SPEECH_TO_TEXT_IAM_APIKEY)
                    .append(WatsonConnectConstants.FILE_DELIMITER).append(speechToTextAPIKey)
                    .append(WatsonConnectConstants.NEW_LINE)
                    .append(WatsonConnectConstants.SPEECH_TO_TEXT_URL)
                    .append(WatsonConnectConstants.FILE_DELIMITER).append(speechToTextUrl)
                    .toString();
        }

        if (!"".equals(compareComplyAPIKey.trim()) && !"".equals(compareComplyUrl.trim())) {

            m = p.matcher(compareComplyUrl);

            if (!m.find()) {
                throw new BotCommandException(MESSAGES.getString("enterValidURL","compareComplyUrl"));
            }

            fileContent = new StringBuilder().append(fileContent).append(WatsonConnectConstants.COMPARE_AND_COMPLY_IAM_APIKEY)
                    .append(WatsonConnectConstants.FILE_DELIMITER).append(compareComplyAPIKey)
                    .append(WatsonConnectConstants.NEW_LINE)
                    .append(WatsonConnectConstants.COMPARE_AND_COMPLY_URL)
                    .append(WatsonConnectConstants.FILE_DELIMITER).append(compareComplyUrl)
                    .toString();
        }

        if (!"".equals(naturalLangUnderstandingAPIKey.trim()) && !"".equals(naturalLangUnderstandingUrl.trim())) {

            m = p.matcher(naturalLangUnderstandingUrl);

            if (!m.find()) {
                throw new BotCommandException(MESSAGES.getString("enterValidURL","naturalLangUnderstandingUrl"));
            }

            fileContent = new StringBuilder().append(fileContent).append(WatsonConnectConstants.NATURAL_LANGUAGE_UNDERSTANDING_IAM_APIKEY)
                    .append(WatsonConnectConstants.FILE_DELIMITER).append(naturalLangUnderstandingAPIKey)
                    .append(WatsonConnectConstants.NEW_LINE)
                    .append(WatsonConnectConstants.NATURAL_LANGUAGE_UNDERSTANDING_URL)
                    .append(WatsonConnectConstants.FILE_DELIMITER).append(naturalLangUnderstandingUrl)
                    .toString();
        }

        if (!"".equals(fileContent.trim())) {
            isCredentialsCreated = CreateIBMCredentialFile.createIBMCredentailFile(fileContent);
        }

        return isCredentialsCreated;
    }

}
