package com.automationanywhere.botcommand.ibmwatson.speechtotext.commands;

import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.botcommand.ibmwatson.speechtotext.adapter.SpeechToTextAdapter;
import com.automationanywhere.botcommand.ibmwatson.speechtotext.commands.constants.LanguageConstants;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.FileExtension;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.DataType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.automationanywhere.botcommand.ibmwatson.speechtotext.adapter.SpeechInputOutputConstants.*;
import static com.automationanywhere.commandsdk.model.AttributeType.*;
import static com.automationanywhere.commandsdk.model.DataType.DICTIONARY;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

@BotCommand
@CommandPkg(label = "IBM Watson Speech To Text", name = "IBM Watson Speech To Text", description = "Converts speech audio file to text", icon = "speech-to-text.svg",
        node_label = "Converts audio file To text",
        return_type = DICTIONARY, return_label = "Assign the output to variable", return_required = true)
public class IBMWatsonSpeechToText {


    private static final Logger LOGGER = LogManager.getLogger(IBMWatsonSpeechToText.class);
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.ibmwatson.speechtotext.messages");

    @Execute
    public DictionaryValue action(@Idx(index = "1", type = FILE) @Pkg(label = "Upload audio file", description = "") @NotEmpty @FileExtension("OGG,WEBM,MP3,MPEG,WAV,FLAC,PCM,OPUS") String audioFilePath,
                                  @Idx(index = "2", type = SELECT, options = {
                                          @Idx.Option(index = "2.1", pkg = @Pkg(label = LanguageConstants.ARABIC_MODERN_STANDARD, value = LanguageConstants.ARABIC_MODERN_STANDARD)),
                                          @Idx.Option(index = "2.2", pkg = @Pkg(label = LanguageConstants.BRAZILIAN_PORTUGUESE, value = LanguageConstants.BRAZILIAN_PORTUGUESE)),
                                          @Idx.Option(index = "2.3", pkg = @Pkg(label = LanguageConstants.CHINESE_MANDARIN, value = LanguageConstants.CHINESE_MANDARIN)),
                                          @Idx.Option(index = "2.4", pkg = @Pkg(label = LanguageConstants.ENGLISH_UNITED_KINGDOM, value = LanguageConstants.ENGLISH_UNITED_KINGDOM)),
                                          @Idx.Option(index = "2.5", pkg = @Pkg(label = LanguageConstants.ENGLISH_UNITED_STATES, value = LanguageConstants.ENGLISH_UNITED_STATES)),
                                          @Idx.Option(index = "2.6", pkg = @Pkg(label = LanguageConstants.FRENCH, value = LanguageConstants.FRENCH)),
                                          @Idx.Option(index = "2.7", pkg = @Pkg(label = LanguageConstants.GERMAN, value = LanguageConstants.GERMAN)),
                                          @Idx.Option(index = "2.8", pkg = @Pkg(label = LanguageConstants.JAPANESE, value = LanguageConstants.JAPANESE)),
                                          @Idx.Option(index = "2.9", pkg = @Pkg(label = LanguageConstants.KOREAN, value = LanguageConstants.KOREAN)),
                                          @Idx.Option(index = "2.10", pkg = @Pkg(label = LanguageConstants.SPANISH_ARGENTINIAN_BETA, value = LanguageConstants.SPANISH_ARGENTINIAN_BETA)),
                                          @Idx.Option(index = "2.11", pkg = @Pkg(label = LanguageConstants.SPANISH_CASTILIAN, value = LanguageConstants.SPANISH_CASTILIAN)),
                                          @Idx.Option(index = "2.12", pkg = @Pkg(label = LanguageConstants.SPANISH_CHILEAN_BETA, value = LanguageConstants.SPANISH_CHILEAN_BETA)),
                                          @Idx.Option(index = "2.13", pkg = @Pkg(label = LanguageConstants.SPANISH_COLOMBIAN_BETA, value = LanguageConstants.SPANISH_COLOMBIAN_BETA)),
                                          @Idx.Option(index = "2.14", pkg = @Pkg(label = LanguageConstants.SPANISH_MEXICAN_BETA, value = LanguageConstants.SPANISH_MEXICAN_BETA)),
                                          @Idx.Option(index = "2.15", pkg = @Pkg(label = LanguageConstants.SPANISH_PERUVIAN_BETA, value = LanguageConstants.SPANISH_PERUVIAN_BETA)),
                                  })
                                  @Pkg(label = "Select language", default_value = LanguageConstants.ENGLISH_UNITED_STATES, default_value_type = DataType.STRING) String languageName,
                                  @Idx(index = "3", type = CHECKBOX) @Pkg(label = "Detect speakers (Beta)", description = "Identify which individuals spoke which words in a multi-participant exchange", default_value = "false", default_value_type = DataType.BOOLEAN) Boolean speakerLabels,
                                  @Idx(index = "4", type = CHECKBOX) @Pkg(label = "Keyword spotting", description = "detects specified strings in a transcript", default_value = "false", default_value_type = DataType.BOOLEAN) Boolean keywordSpotting,
                                  @Idx(index = "4.1", type = TEXTAREA) @Pkg(label = "Enter keywords", description = "Enter words with comma separated", default_value = "", default_value_type = STRING) String keywords,
                                  @Idx(index = "5", type = CHECKBOX) @Pkg(label = "Smart formatting (Beta)", description = "Parameter directs the service to convert to DOB,AM,PM etc..", default_value = "false", default_value_type = DataType.BOOLEAN) Boolean smartFormatting,
                                  @Idx(index = "6", type = CHECKBOX) @Pkg(label = "Allow profanity", description = "parameter indicates whether the service is to censor profanity from its results", default_value = "false", default_value_type = DataType.BOOLEAN) Boolean profanityFiltering) {

        if ("".equals(audioFilePath.trim()))
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "audioFilePath"));

        Map<String, String> inputOutputParams = new HashMap<>();


        inputOutputParams.put(AUDIO_FILE_PATH, audioFilePath.trim());
        inputOutputParams.put(LANGUAGE_NAME, languageName);

        if (speakerLabels.equals(BOOLEAN_TRUE)) {
            inputOutputParams.put(IS_SPEAKER_LABELS, YES_STR);
        }
        if (keywordSpotting.equals(BOOLEAN_TRUE)) {
            if ("".equals(keywords.trim()) || keywords == null) {
                throw new BotCommandException(MESSAGES.getString("emptyInputString", "keywords"));
            } else {
                inputOutputParams.put(IS_SPOT_KEYWORDS, YES_STR);
                inputOutputParams.put(KEYWORDS, keywords);
            }
        }
        if (smartFormatting.equals(BOOLEAN_TRUE)) {
            inputOutputParams.put(IS_SMART_FORMATTING, YES_STR);
        }
        if (profanityFiltering.equals(BOOLEAN_TRUE)) {
            inputOutputParams.put(IS_PROFANITY_FILTERING, YES_STR);
        }


        SpeechToTextAdapter ibmWatsonSpeechToTextAdapter = new SpeechToTextAdapter();

        return ibmWatsonSpeechToTextAdapter.getTextForSpeech(inputOutputParams);

    }

}
