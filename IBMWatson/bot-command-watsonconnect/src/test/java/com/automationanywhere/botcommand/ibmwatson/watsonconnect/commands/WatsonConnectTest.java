package com.automationanywhere.botcommand.ibmwatson.watsonconnect.commands;


import com.automationanywhere.botcommand.data.impl.BooleanValue;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.File;

public class WatsonConnectTest {

    @Test
    public void testIsCredentialFileCreated() {
        WatsonConnect watsonConnect = new WatsonConnect();
        BooleanValue fileCreated = watsonConnect.execute("abcdef","https://www.google.com","","","","");
        Assert.assertTrue(fileCreated.get().booleanValue());
    }

    @Test
    public void testIsCredentialFileReCreate() {
        WatsonConnect watsonConnect = new WatsonConnect();
        BooleanValue fileCreated = watsonConnect.execute("abcdef","https://www.google.com","","","","");
        File fileBefore = new File(System.getProperty("user.home")+"\\"+"ibm-credentials.env");
        Assert.assertTrue(fileCreated.get().booleanValue());
        Assert.assertFalse(fileBefore.exists());
        fileCreated = watsonConnect.execute("jkmlnoq","https://www.google.com","","","","");
        File fileAfter = new File(System.getProperty("user.home")+"\\"+"ibm-credentials.env");
        Assert.assertTrue(fileCreated.get().booleanValue());
        Assert.assertFalse(fileAfter.exists());
    }

}
