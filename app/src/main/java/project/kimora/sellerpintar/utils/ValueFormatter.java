package project.kimora.sellerpintar.utils;

import java.text.DecimalFormat;

/**
 * Created by msinfo on 11/02/23.
 */

public class ValueFormatter {

    public static String formatPrice(long value) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String newValue = formatter.format(value);

        return newValue;
    }
}
