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
import com.automationanywhere.botcommand.exception.BotCommandException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


/**
 * @author Venkatravamma & RamaKrishna
 */

public class FuzzyMatchTest {

    /* Fuzzy match between two input strings with positive scenario*/

    @Test
    public void fuzzyMatchBetweenTwoInputStringsWithData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input String"; // or "PDF"
            String inputString = "Google Chrome";
            String filePath1 = ""; // or C:\FuzzySearch\FuzzySearchAlgorithm\src\test\resources\File1.pdf
            String input2String = "Input String"; // or "PDF"
            String outputString = "Google";
            String filePath2 = "";  // or C:\FuzzySearch\FuzzySearchAlgorithm\src\test\resources\File2.pdf
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        }catch(BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }
    /* Fuzzy match between two input files as .txt format with positive scenario */

    @Test
    public void fuzzyMatchBetweenTwoTextFilesWithData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input File"; // or "PDF"
            String input2String = "Input File"; // or "PDF"
            String inputString = "";
            String outputString = "";
            String filePath1 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File1.txt";
            String filePath2 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File2.txt";
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }

    /* Fuzzy match between two input files as .pdf format with positive scenario */

    @Test
    public void fuzzyMatchBetweenTwoPdfFilesWithData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input File"; // or "PDF"
            String inputString = "";
            String filePath1 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File1.pdf"; // or C:\FuzzySearch\FuzzySearchAlgorithm\src\test\resources\File1.pdf
            String input2String = "Input File"; // or "PDF"
            String outputString = "";
            String filePath2 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File2.pdf";  // or C:\FuzzySearch\FuzzySearchAlgorithm\src\test\resources\File2.pdf
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }

    /* Fuzzy match between strings with null with negative scenario*/

    @Test
    public void fuzzyMatchBetweenDataWithNull() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            Value<String> string = fuzzyMatch.action(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
            assertNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }catch (Exception e) {
            assertEquals(e.getMessage(), null);
        }
    }

    /* Fuzzy Match between two Input Strings With Empty Data*/
    @Test
    public void fuzzyMatchBetweenInputStringsWithEmptyData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String inputFuzzyString = "Input String"; // or "PDF"
            String fuzzyString = "Input String"; // or "PDF"
            String inputString = "";
            String outputString = "";
            String filePath1 = "";
            String filePath2 = "";
            Value<String> string = fuzzyMatch.action(inputFuzzyString, inputString, filePath1, fuzzyString, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }

    /*  Fuzzy Match between two Input Text Files With Empty Data */
    @Test
    public void fuzzyMatchBetweenTwoInputTextFilesWithEmptyData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input File"; // or "PDF"
            String input2String = "Input File"; // or "PDF"
            String inputString = "";
            String outputString = "";
            String filePath1 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File3.txt";
            String filePath2 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File4.txt";
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }

    /*  Fuzzy Match between two Input PDF Files With Empty Data */
    @Test
    public void fuzzyMatchBetweenTwoInputPDFFilesWithEmptyData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input File"; // or "PDF"
            String input2String = "Input File"; // or "PDF"
            String inputString = "";
            String outputString = "";
            String filePath1 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File3.pdf";
            String filePath2 = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File4.pdf";
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }
    /* Fuzzy match between One InputString and One Input File with empty data  */
    @Test
    public void fuzzyMatchBetweenInputStringAndInputTxtFileWithEmptyData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input String"; // or "PDF"
            String input2String = "Input File"; // or "PDF"
            String inputString = "Google";
            String outputString = "";
            String filePath1 = "";
            String filePath2 =  Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File3.txt";  ;
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }

    /* Fuzzy match between One InputString and One Input File with empty data  */
    @Test
    public void fuzzyMatchBetweenInputStringAndInputPdfFileWithEmptyData() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input String"; // or "PDF"
            String input2String = "Input File"; // or "PDF"
            String inputString = "Google";
            String outputString = "";
            String filePath1 = "";
            String filePath2 =  Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\File3.pdf";
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }
    }

    /*  InputStrings with null data fuzzy match comparision */
    @Test
    public void fuzzyMatchBetweenInputStringsWithNull() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input String"; // or "PDF"
            String input2String = "Input String"; // or "PDF"
            String inputString = null;
            String outputString = null;
            String filePath1 = "";
            String filePath2 = "";
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }catch (Exception e) {
            assertEquals(e.getMessage(), null);
        }
    }

    /*  InputStrings with null data fuzzy match comparision */
    @Test
    public void fuzzyMatchBetweenInputFilesWithNull() throws IOException {
        try {
            FuzzyMatch fuzzyMatch = new FuzzyMatch();
            String input1String = "Input File"; // or "PDF"
            String input2String = "Input File"; // or "PDF"
            String inputString = "";
            String outputString = "";
            String filePath1 = null;
            String filePath2 = null;
            Value<String> string = fuzzyMatch.action(input1String, inputString, filePath1, input2String, outputString, filePath2);
            assertNotNull(string);
        } catch (BotCommandException e) {
            assertEquals(e.getMessage(), "Given 'Input' is empty.");
        }catch (Exception e) {
            assertEquals(e.getMessage(), null);
        }
    }


}

