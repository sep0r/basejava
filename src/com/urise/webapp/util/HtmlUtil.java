package com.urise.webapp.util;

import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ListSection;

public class HtmlUtil {

    public static String textToList(AbstractSection abstractSection) {
        if (abstractSection != null) {
            return String.join("\n", ((ListSection) abstractSection).getListText().toArray((new String[0]))
            );
        } else return "";
    }
}