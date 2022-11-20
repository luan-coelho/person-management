package br.bunny.filter;

public class FieldsFilter {

    public static String validateFieldString(String value){
        return value == null ? "" : value.toLowerCase();
    }
}
