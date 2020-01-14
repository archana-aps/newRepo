package com.automationanywhere.botcommand.ibmwatson.speechtotext.adapter;

public enum SpeechToTextLangEnum {
    ARABIC_MODERN_STANDARD("Arabic (Modern Standard)", "ar-AR_BroadbandModel", "Not supported"),
    BRAZILIAN_PORTUGUESE("Brazilian Portuguese", "pt-BR_BroadbandModel", "pt-BR_NarrowbandModel"),
    CHINESE_MANDARIN("Chinese (Mandarin)", "zh-CN_BroadbandModel", "zh-CN_NarrowbandModel"),
    ENGLISH_UNITED_KINGDOM("English (United Kingdom)", "en-GB_BroadbandModel", "en-GB_NarrowbandModel"),
    ENGLISH_UNITED_STATES("English (United States)", "en-US_BroadbandModel", "en-US_NarrowbandModel,en-US_ShortForm_NarrowbandModel"),
    FRENCH("French", "fr-FR_BroadbandModel", "fr-FR_NarrowbandModel"),
    GERMAN("German", "de-DE_BroadbandModel", "de-DE_NarrowbandModel"),
    JAPANESE("Japanese", "ja-JP_BroadbandModel", "ja-JP_NarrowbandModel"),
    KOREAN("Korean", "ko-KR_BroadbandModel", "ko-KR_NarrowbandModel"),
    SPANISH_ARGENTINIAN_BETA("Spanish (Argentinian, Beta)", "es-AR_BroadbandModel", "es-AR_NarrowbandModel"),
    SPANISH_CASTILIAN("Spanish (Castilian)", "es-ES_BroadbandModel", "es-ES_NarrowbandModel"),
    SPANISH_CHILEAN_BETA("Spanish (Chilean, Beta)", "es-CL_BroadbandModel", "es-CL_NarrowbandModel"),
    SPANISH_COLOMBIAN_BETA("Spanish (Colombian, Beta)", "es-CO_BroadbandModel", "es-CO_NarrowbandModel"),
    SPANISH_MEXICAN_BETA("Spanish (Mexican, Beta)", "es-MX_BroadbandModel", "es-MX_NarrowbandModel"),
    SPANISH_PERUVIAN_BETA("Spanish (Peruvian, Beta)", "es-PE_BroadbandModel", "es-PE_NarrowbandModel");


    public String langLabel;
    public String langBroadbandModel;
    public String langNarrowbandModel;

    SpeechToTextLangEnum(String langLabel, String langBroadbandModel, String langNarrowbandModel) {
        this.langLabel = langLabel;
        this.langBroadbandModel = langBroadbandModel;
        this.langNarrowbandModel = langNarrowbandModel;
    }

    public String getLangLabel() {
        return langLabel;
    }

    public String getLangBroadbandModel() {
        return langBroadbandModel;
    }

    public String getLangNarrowbandModel() {
        return langNarrowbandModel;
    }

    public void setLangLabel(String langLabel) {
        this.langLabel = langLabel;
    }

    public void setLangBroadbandModel(String langBroadbandModel) {
        this.langBroadbandModel = langBroadbandModel;
    }

    public void setLangNarrowbandModel(String langNarrowbandModel) {
        this.langNarrowbandModel = langNarrowbandModel;
    }

    public static SpeechToTextLangEnum getLangEnum(String langLabel) {
        for (SpeechToTextLangEnum langEnum : SpeechToTextLangEnum.values()) {
            if (langLabel.equals(langEnum.getLangLabel())) {
                return langEnum;
            }
        }
        return null;
    }
}
