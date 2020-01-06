package com.automationanywhere.botcommand.ibmwatson.speechtotext.adapter;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SpeechToTextAdapter {
    private static final Logger LOGGER = LogManager.getLogger(SpeechToTextAdapter.class);
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.ibmwatson.speechtotext.messages");


    public DictionaryValue getTextForSpeech(Map<String, String> inputOutputParams) {

        Map<String, Value> resultMap = new HashMap<>();

        IamOptions config;
        config = Authentication.getIamOptionsConfig();

        SpeechToText service = new SpeechToText(config);
        RecognizeOptions recognizeOptions;
        SpeechRecognitionResults transcript;


        try {
            recognizeOptions = SpeechToTextRecognizeOptsBuilder.getRecognizeOptionsObject(inputOutputParams);

            LOGGER.debug("recognize options are "+recognizeOptions);

            transcript = service.recognize(recognizeOptions).execute().getResult();
        } catch (Exception e) {
            throw new BotCommandException(MESSAGES.getString("exceptionInGettingResponseFromWatson"),e);
        }

        if (transcript != null) {
            resultMap.put(SpeechInputOutputConstants.SPEECH_TEXT_RESULT, new StringValue(transcript.getResults().toString()));
            if (inputOutputParams.containsKey(SpeechInputOutputConstants.IS_SPEAKER_LABELS)) {
                resultMap.put(SpeechInputOutputConstants.SPEAKER_LABELS, new StringValue(transcript.getSpeakerLabels().toString()));
            }
            if (inputOutputParams.containsKey(SpeechInputOutputConstants.IS_SPOT_KEYWORDS)) {
                resultMap.put(SpeechInputOutputConstants.KEYWORDS_SPOTTED, new StringValue(transcript.getResults().get(0).getKeywordsResult().toString()));
            }
        }

        return new DictionaryValue(resultMap);
    }

}
