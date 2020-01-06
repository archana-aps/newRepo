
/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */

package com.automationanywhere.botcommand.fuzzymatch;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.FileExtension;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.DataType;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.automationanywhere.commandsdk.model.AttributeType.*;
import static com.automationanywhere.commandsdk.model.DataType.STRING;


/**
 * @author Venkatravamma & RamaKrishna
 */

@BotCommand
@CommandPkg(label = "[[FuzzyMatch.label]]", name = "fuzzyMatch", description = "[[FuzzyMatch.description]]", icon = "pkg.svg",
        node_label = "[[FuzzyMatch.node_label]]",

        return_type = STRING, return_label = "[[FuzzyMatch.return_label]]", return_required = true)
public class FuzzyMatch {
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.fuzzymatch.messages");
    private static final Logger LOGGER = LogManager.getLogger(FuzzyMatch.class);

    @Execute
    public Value<String> action(
            @Idx(index = "1", type = RADIO, options = {
                    @Idx.Option(index = "1.1", pkg = @Pkg(node_label = "[[Index.node_label]]",label = "[[Index.label]]", value = "Input String")),
                    @Idx.Option(index = "1.2", pkg = @Pkg(node_label = "[[Index.node_label_file]]",label = "[[Index.label_file]]", value = "Input File")),
            }) @Pkg(label = "[[FuzzyMatch.input1]]", default_value = "Input String", default_value_type = DataType.STRING)
            @Inject
                    String input1String,

            @Idx(index = "1.1.1", type = TEXT) @Pkg(description = "[[Index.description]]", default_value_type = DataType.STRING) @NotEmpty @Inject String inputString,


            @Idx(index = "1.2.1", type = FILE) @Pkg(description = "[[Index.description_file]]",default_value_type = DataType.FILE) @NotEmpty
            @FileExtension("TXT,PDF")@Inject String filePath1,


            @Idx(index = "2", type = RADIO, options = {
                    @Idx.Option(index = "2.1", pkg = @Pkg(node_label = "[[Index.node_label]]",label = "[[Index.label]]", value = "Input String")),
                    @Idx.Option(index = "2.2", pkg = @Pkg(node_label = "[[Index.node_label_file]]",label = "[[Index.label_file]]", value = "Input File")),

            }) @Pkg(label = "[[FuzzyMatch.input2]]", default_value = "Input String", default_value_type = DataType.STRING)
            @Inject
                    String input2String,

            @Idx(index = "2.1.1", type = TEXT) @Pkg(description = "[[Index.description]]", default_value_type = DataType.STRING) @NotEmpty @Inject String outputString,

            @Idx(index = "2.2.1", type = FILE) @Pkg(description = "[[Index.description_file]]",default_value_type = DataType.FILE) @NotEmpty
            @FileExtension("TXT,PDF") @Inject String filePath2) throws IOException {



        if ( filePath1 != null && !filePath1.isEmpty()) {
            if ("".equals(filePath1.trim()))
                throw new BotCommandException(MESSAGES.getString("emptyInputString", "File"));

            inputString=getFileContentAsString(filePath1);

        }
        if ( filePath2 != null && !filePath2.isEmpty()) {
            if ("".equals(filePath2.trim()))
                throw new BotCommandException(MESSAGES.getString("emptyInputString", "File"));

            outputString = getFileContentAsString(filePath2);

        }
        if ( inputString != null && !inputString.isEmpty()) {
            LOGGER.debug(" Input1 String : "+inputString);
            if ("".equals(inputString.trim())) {
                throw new BotCommandException(MESSAGES.getString("emptyInputString", "Input"));
            }
        }
        if (outputString != null && !outputString.isEmpty()) {
            LOGGER.debug(" Input2 String : "+outputString);
            if ("".equals(outputString.trim())) {
                throw new BotCommandException(MESSAGES.getString("emptyInputString", "Input"));
            }
        }

        double similarity = 0.0;

        JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
        similarity = jaroWinklerSimilarity.apply(inputString.trim(), outputString.trim());
        LOGGER.debug(" In JaroWinklerSimilarity : " + similarity);

        return new StringValue(String.valueOf(similarity));


    }
    private String getFileContentAsString(String filePath){
        String output=null;
        if ( filePath != null && !filePath.isEmpty()) {
            LOGGER.debug(" File Path : " + filePath);
            File file = new File(filePath);
            String fileExtn = getFileExtension(file);
            LOGGER.debug(" File Extn : " + fileExtn);
            if (fileExtn.equalsIgnoreCase("pdf")) {
                try {
                    output = readResourceFilePdf(filePath);
                    LOGGER.debug("  output : " + output);
                } catch (Exception e) {
                    LOGGER.error(e);
                }
            } else if (fileExtn.equalsIgnoreCase("txt")) {
                try {
                    output = readResourceFile(filePath);
                    LOGGER.debug(" output :  " + output);
                }catch(Exception e){
                    LOGGER.error(e);
                }
            }
        }
        return output;
    }
    private static String readResourceFile(String file) throws IOException {
        File filePath = new File(file);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuilder string_builder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        String line = null;

        while ((line = br.readLine()) != null) {
            string_builder.append(line);
            string_builder.append(ls);
        }
        if (string_builder.length() > 0 && string_builder != null) {
            string_builder.deleteCharAt(string_builder.length() - 1);
        }
        return string_builder.toString();

    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else return "";
    }

    private static String readResourceFilePdf(String filePath) throws IOException {
        File file = new File(filePath);
        PDDocument document = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);

        //Closing the document
        document.close();
        return text;
    }
}

