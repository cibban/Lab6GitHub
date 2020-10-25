package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class was automatically imported with the query.json file.
 */

public class CocktailLabel{
    public String type;
    public String value;
    @JsonProperty("xml:lang")
    public String xmlLang;
}