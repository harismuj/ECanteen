package com.jungkatjungkit.ecanteen.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    // Method to convert date string to "10 November 2023" format
    public static String convertDateString(String dateString) {
        // Assuming dateString is in the format "yyyy-MM-dd"
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        try {
            // Parse the date string
            Date date = inputFormat.parse(dateString);

            // Format the date to the desired output format
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing error if needed
            return dateString; // Return the original string in case of an error
        }
    }
}

