package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class was created according to the instructions on https://json2csharp.com/json-to-pojo.
 * Some variables are marked as "not used" by IntelliJ but they are needed to read and parse the
 * JSON file.
 */

public class CocktailLabel{
    public String type;
    public String value;
    @JsonProperty("xml:lang")
    public String xmlLang;
}