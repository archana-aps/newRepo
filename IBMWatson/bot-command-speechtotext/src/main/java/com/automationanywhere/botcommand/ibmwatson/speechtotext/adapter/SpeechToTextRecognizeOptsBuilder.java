package com.automationanywhere.botcommand.ibmwatson.speechtotext.adapter;

import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class SpeechToTextRecognizeOptsBuilder {
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.ibmwatson.speechtotext.messages");
    private static final Logger LOGGER = LogManager.getLogger(SpeechToTextRecognizeOptsBuilder.class);


    public static RecognizeOptions getRecognizeOptionsObject(Map<String, String> speechToTextInputsOutputParrams) {
        RecognizeOptions recognizeOptions;

        LOGGER.debug("audio file path "+speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.AUDIO_FILE_PATH));

        File audio = new File(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.AUDIO_FILE_PATH));

        LOGGER.debug("audio file "+audio);

        if (!audio.exists()) {
            throw new BotCommandException(MESSAGES.getString("fileNotFoundException", speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.AUDIO_FILE_PATH)));
        }

        String audioType = getSupportedAudioType(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.AUDIO_FILE_PATH));

        RecognizeOptions.Builder recognizeOptionBuilder = new RecognizeOptions.Builder();
        try {
            recognizeOptionBuilder.audio(audio);
        } catch (FileNotFoundException e) {
            throw new BotCommandException(MESSAGES.getString("fileNotFoundException", speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.AUDIO_FILE_PATH)));
        }
        recognizeOptionBuilder.contentType(audioType);
        if (SpeechInputOutputConstants.YES_STR.equals(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.IS_PROFANITY_FILTERING))) {
            recognizeOptionBuilder.profanityFilter(SpeechInputOutputConstants.BOOLEAN_TRUE);
        }
        if (SpeechInputOutputConstants.YES_STR.equals(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.IS_SMART_FORMATTING))) {
            recognizeOptionBuilder.smartFormatting(SpeechInputOutputConstants.BOOLEAN_TRUE);
        }
        if (SpeechInputOutputConstants.YES_STR.equals(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.IS_SPEAKER_LABELS))) {
            recognizeOptionBuilder.speakerLabels(SpeechInputOutputConstants.BOOLEAN_TRUE);
        }
        if (SpeechInputOutputConstants.YES_STR.equals(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.IS_SPOT_KEYWORDS))) {
            List keywordsList = Arrays.asList(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.KEYWORDS).split(","));
            if (keywordsList != null && !keywordsList.isEmpty()) {
                recognizeOptionBuilder.keywords(keywordsList);
                recognizeOptionBuilder.keywordsThreshold(SpeechInputOutputConstants.KEYWORDS_THRESHOLD);
            }
        }
        if (speechToTextInputsOutputParrams.containsKey(SpeechInputOutputConstants.LANGUAGE_NAME)) {
            recognizeOptionBuilder.model(SpeechToTextLangEnum.getLangEnum(speechToTextInputsOutputParrams.get(SpeechInputOutputConstants.LANGUAGE_NAME)).langBroadbandModel);
        }

        recognizeOptions = recognizeOptionBuilder.build();

        return recognizeOptions;
    }

    private static String getSupportedAudioType(String filePath) {
        String audioType;

        File audio = new File(filePath);
        String fileType = getFileExtension(audio);

        if ("".equals(fileType))
            throw new BotCommandException(MESSAGES.getString("fileShouldHaveExtension", "audioFilePath"));

        switch (fileType.toLowerCase()) {
            case SupportingAudioFormats.MP3:
                    audioType = RecognizeOptions.ContentType.AUDIO_MP3;
                    break;
            case SupportingAudioFormats.MPEG:
                    audioType = RecognizeOptions.ContentType.AUDIO_MPEG;
                    break;
            case SupportingAudioFormats.FLAC:
                    audioType = RecognizeOptions.ContentType.AUDIO_FLAC;
                    break;
            case SupportingAudioFormats.OGG:
                    audioType = RecognizeOptions.ContentType.AUDIO_OGG;
                    break;
            case SupportingAudioFormats.WAV:
                    audioType = RecognizeOptions.ContentType.AUDIO_WAV;
                    break;
            case SupportingAudioFormats.OPUS:
                    audioType = RecognizeOptions.ContentType.AUDIO_OGG_CODECS_OPUS;
                    break;
            case SupportingAudioFormats.WEBM:
                    audioType = RecognizeOptions.ContentType.AUDIO_WEBM;
                    break;
            default:
                throw new BotCommandException(MESSAGES.getString("unsupportedFileType", "audioFilePath"));
        }
        return audioType;
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }
}
