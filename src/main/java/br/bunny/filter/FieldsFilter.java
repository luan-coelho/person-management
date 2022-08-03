package br.bunny.filter;

import java.util.List;

public class FieldsFilter {

    public static String validateFieldString(String value){
        return value == null ? "" : value.toLowerCase();
    }
}
