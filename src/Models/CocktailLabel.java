package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CocktailLabel{
    public String type;
    public String value;
    @JsonProperty("xml:lang")
    public String xmlLang;
}