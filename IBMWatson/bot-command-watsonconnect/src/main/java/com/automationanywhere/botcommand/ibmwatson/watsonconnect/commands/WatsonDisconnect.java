package com.automationanywhere.botcommand.ibmwatson.watsonconnect.commands;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.botcommand.ibmwatson.watsonconnect.config.DeleteIBMCredentialFile;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;


@BotCommand
@CommandPkg(label = "IBM Watson Disconnect", name = "IBM Watson Disconnect", description = "Deletes Configuration for IBM Watson Services", icon = "disconnect-icon.svg",
        node_label = "Deletes Configuration for IBM Watson Services")
public class WatsonDisconnect {


    @Execute
    public BooleanValue action() {
        BooleanValue isDisconnected;

        isDisconnected = DeleteIBMCredentialFile.deleteIBMCredentialFile();

        return isDisconnected;
    }

}
