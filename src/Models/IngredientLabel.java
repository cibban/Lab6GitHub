package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IngredientLabel{
    @JsonProperty("xml:lang")
    public String xmlLang;
    public String type;
    public String value;
}