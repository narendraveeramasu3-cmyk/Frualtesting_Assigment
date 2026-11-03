
package tests;

import base.BaseApiTest;
import utilities.BookingData;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingApiTest extends BaseApiTest {

    // Helper method: Generate authentication token
    private String generateAuthToken() {

        Response response = request()
                .body(BookingData.authData())
                .when()
                .post("/auth");

        Assert.assertEquals(response.statusCode(), 200);

        String token = response.jsonPath().getString("token");

        Assert.assertNotNull(
                token,
                "Authentication token should not be null"
        );

        return token;
    }

    // Helper method: Create a booking and return booking ID
    private int createNewBooking() {

        Response response = request()
                .body(BookingData.createBooking())
                .when()
                .post("/booking");

        Assert.assertEquals(response.statusCode(), 200);

        int bookingId = response.jsonPath().getInt("bookingid");

        Assert.assertTrue(bookingId > 0);

        return bookingId;
    }

    // Q2(a): Complete booking workflow
    @Test
    public void verifyCompleteBookingWorkflow() {

        // Step 1: Generate authentication token
        String token = generateAuthToken();

        System.out.println("Authentication successful!");

        // Step 2: Create booking
        Response createResponse = request()
                .body(BookingData.createBooking())
                .when()
                .post("/booking");

        Assert.assertEquals(createResponse.statusCode(), 200);

        int bookingId =
                createResponse.jsonPath().getInt("bookingid");

        Assert.assertTrue(bookingId > 0);

        Assert.assertEquals(
                createResponse.jsonPath().getString("booking.firstname"),
                "Anil"
        );

        System.out.println("Booking created. ID: " + bookingId);

        // Step 3: Retrieve booking
        Response getResponse = request()
                .when()
                .get("/booking/" + bookingId);

        Assert.assertEquals(getResponse.statusCode(), 200);

        Assert.assertEquals(
                getResponse.jsonPath().getString("firstname"),
                "Anil"
        );

        System.out.println("Booking retrieved successfully!");

        // Step 4: Update booking using token
        Response updateResponse = request()
                .cookie("token", token)
                .body(BookingData.updatedBooking())
                .when()
                .put("/booking/" + bookingId);

        Assert.assertEquals(updateResponse.statusCode(), 200);

        Assert.assertEquals(
                updateResponse.jsonPath().getString("firstname"),
                "Anil Updated"
        );

        Assert.assertEquals(
                updateResponse.jsonPath().getInt("totalprice"),
                2000
        );

        System.out.println("Booking updated successfully!");

        // Step 5: Delete booking
        Response deleteResponse = request()
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId);

        Assert.assertEquals(deleteResponse.statusCode(), 201);

        System.out.println("Booking deleted successfully!");

        // Verify deleted booking
        Response deletedResponse = request()
                .when()
                .get("/booking/" + bookingId);

        Assert.assertEquals(deletedResponse.statusCode(), 404);

        System.out.println("Deleted booking verification passed!");
    }

    // Q2(b)(i): Update booking without authentication token
    @Test
    public void verifyUpdateBookingWithoutToken() {

        // Create a booking
        int bookingId = createNewBooking();

        // Attempt to update without token
        Response updateResponse = request()
                .body(BookingData.updatedBooking())
                .when()
                .put("/booking/" + bookingId);

        Assert.assertEquals(
                updateResponse.statusCode(),
                403,
                "Updating without token should return 403 Forbidden"
        );

        System.out.println("Update without token test passed!");

        // Cleanup: Delete the test booking using a valid token
        String token = generateAuthToken();

        Response deleteResponse = request()
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId);

        Assert.assertEquals(deleteResponse.statusCode(), 201);
    }

    // Q2(b)(ii): Fetch a non-existing booking
    @Test
    public void verifyNonExistingBooking() {

        Response response = request()
                .when()
                .get("/booking/99999999");

        Assert.assertEquals(
                response.statusCode(),
                404,
                "Non-existing booking should return 404"
        );

        System.out.println("Non-existing booking test passed!");
    }

    // Q2(b)(iii): Fetch booking after deletion
    @Test
    public void verifyDeletedBookingCannotBeRetrieved() {

        // Create booking
        int bookingId = createNewBooking();

        // Generate authentication token
        String token = generateAuthToken();

        // Delete booking
        Response deleteResponse = request()
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId);

        Assert.assertEquals(deleteResponse.statusCode(), 201);

        System.out.println("Booking deleted successfully!");

        // Try retrieving deleted booking
        Response getResponse = request()
                .when()
                .get("/booking/" + bookingId);

        Assert.assertEquals(
                getResponse.statusCode(),
                404,
                "Deleted booking should return 404"
        );

        System.out.println("Deleted booking negative test passed!");
    }
}
