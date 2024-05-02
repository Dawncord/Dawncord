package io.github.dawncord.api.types;

/**
 * Represents supported locales with their corresponding locale codes.
 */
public enum Locale {
    /**
     * Indonesian.
     */
    Indonesian("id"),

    /**
     * Danish.
     */
    Danish("da"),

    /**
     * German.
     */
    German("de"),

    /**
     * English UK.
     */
    EnglishUK("en-GB"),

    /**
     * English US.
     */
    EnglishUS("en-US"),

    /**
     * Spanish.
     */
    Spanish("es-ES"),

    /**
     * French.
     */
    French("fr"),

    /**
     * Croatian.
     */
    Croatian("hr"),

    /**
     * Italian.
     */
    Italian("it"),

    /**
     * Lithuanian.
     */
    Lithuanian("lt"),

    /**
     * Hungarian.
     */
    Hungarian("hu"),

    /**
     * Dutch.
     */
    Dutch("nl"),

    /**
     * Norwegian.
     */
    Norwegian("no"),

    /**
     * Polish.
     */
    Polish("pl"),

    /**
     * Brazilian Portuguese.
     */
    BrazilianPortuguese("pt-BR"),

    /**
     * Romanian.
     */
    Romanian("ro"),

    /**
     * Finnish.
     */
    Finnish("fi"),

    /**
     * Swedish.
     */
    Swedish("sv-SE"),

    /**
     * Vietnamese.
     */
    Vietnamese("vi"),

    /**
     * Turkish.
     */
    Turkish("tr"),

    /**
     * Czech.
     */
    Czech("cs"),

    /**
     * Greek.
     */
    Greek("el"),

    /**
     * Bulgarian.
     */
    Bulgarian("bg"),

    /**
     * Russian.
     */
    Russian("ru"),

    /**
     * Ukrainian.
     */
    Ukrainian("uk"),

    /**
     * Hindi.
     */
    Hindi("hi"),

    /**
     * Thai.
     */
    Thai("th"),

    /**
     * Chinese China.
     */
    ChineseChina("zh-CN"),

    /**
     * Japanese.
     */
    Japanese("ja"),

    /**
     * Chinese Taiwan.
     */
    ChineseTaiwan("zh-TW"),

    /**
     * Korean.
     */
    Korean("ko");

    private final String localeCode;

    Locale(String localeCode) {
        this.localeCode = localeCode;
    }

    /**
     * Gets the locale code.
     *
     * @return The locale code.
     */
    public String getLocaleCode() {
        return localeCode;
    }
}

