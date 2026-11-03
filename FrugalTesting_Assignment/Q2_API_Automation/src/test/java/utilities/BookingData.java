
package utilities;

import java.util.HashMap;
import java.util.Map;

public class BookingData {

    // Booking creation data
    public static Map<String, Object> createBooking() {

        Map<String, Object> booking = new HashMap<>();

        booking.put("firstname", "Anil");
        booking.put("lastname", "Kumar");
        booking.put("totalprice", 1500);
        booking.put("depositpaid", true);

        Map<String, String> dates = new HashMap<>();

        dates.put("checkin", "2026-11-03");
        dates.put("checkout", "2026-11-05");

        booking.put("bookingdates", dates);
        booking.put("additionalneeds", "Breakfast");

        return booking;
    }

    // Booking update data
    public static Map<String, Object> updatedBooking() {

        Map<String, Object> booking = createBooking();

        booking.put("firstname", "Anil Updated");
        booking.put("totalprice", 2000);

        return booking;
    }

    // Authentication data
    public static Map<String, String> authData() {

        Map<String, String> auth = new HashMap<>();

        auth.put("username", "admin");
        auth.put("password", "password123");

        return auth;
    }
}
