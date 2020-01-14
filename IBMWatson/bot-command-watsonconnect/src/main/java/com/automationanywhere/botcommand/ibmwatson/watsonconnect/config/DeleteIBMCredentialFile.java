package com.automationanywhere.botcommand.ibmwatson.watsonconnect.config;

import com.automationanywhere.botcommand.data.impl.BooleanValue;

import java.io.File;

public class DeleteIBMCredentialFile {



    public static BooleanValue deleteIBMCredentialFile() {
        File credentialFile = new File(System.getProperty(WatsonConnectConstants.USER_HOME) + "/" + WatsonConnectConstants.IBM_CREDENTIALS_FILE);

        if (credentialFile.exists()) {
            credentialFile.delete();
        }

        return new BooleanValue("true");
    }
}
