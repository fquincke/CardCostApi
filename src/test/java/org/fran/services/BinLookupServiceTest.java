package org.fran.services;

import org.fran.dataAccess.interfaces.IStorage;
import org.fran.dtos.LookupResponseDto;
import org.fran.dtos.SimpleHttpResponse;
import org.fran.entities.BinDetails;
import org.fran.entities.Country;
import org.fran.exeptions.BinNotFoundExeption;
import org.fran.exeptions.InvalidCreditCardException;
import org.fran.exeptions.TooManyRequestsExeption;
import org.fran.services.interfaces.IHttpClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BinLookupServiceTest {

    @Mock
    IStorage storage;

    @Mock
    private IHttpClientService httpService;

    private BinLookupService binLookupService;

    private final String validCard = "1234567890123456";
    private final String bin = "123456";

    @BeforeEach
    void setup() {
        binLookupService = new BinLookupService(storage, httpService);

        binLookupService.setApiBaseUrl("http://test-url/");
    }

    // Validation tests
    @Test
    void look_nullCard_throwsException() {
        assertThrows(InvalidCreditCardException.class,
                () -> binLookupService.look(null));
    }

    @Test
    void look_shortCard_throwsException() {
        assertThrows(InvalidCreditCardException.class,
                () -> binLookupService.look("1234567"));
    }

    @Test
    void look_longCard_throwsException() {
        assertThrows(InvalidCreditCardException.class,
                () -> binLookupService.look("12345678901234567"));
    }

    @Test
    void look_200Response_returnsData() throws Exception {
        SimpleHttpResponse httpResponse = new SimpleHttpResponse(200, "{" +
                "    \"country\" : {" +
                "    \"numeric\" : \"1\"," +
                "    \"alpha2\" : \"US\"," +
                "    \"name\" : \"United States\"" +
                "  }" +
                "}");

        // Arrange
        when(httpService.sendRequest(anyString())).thenReturn(httpResponse);

        BinDetails binDetails = new BinDetails();
        Country country = new Country();
        country.setAlpha2("US");
        binDetails.setCountry(country);

        when(storage.getCostOfCard("US")).thenReturn(5);

        // Act
        LookupResponseDto result = binLookupService.look(validCard);

        // Assert
        assertEquals("US", result.country);
        assertEquals(5, result.clearingCost);
    }

    @Test
    void look_400Response_throwsBinNotFoundException() throws Exception {
        SimpleHttpResponse httpResponse = new SimpleHttpResponse(400, "{}");

        when(httpService.sendRequest(anyString())).thenReturn(httpResponse);

        assertThrows(BinNotFoundExeption.class,
                () -> binLookupService.look(validCard));
    }

    @Test
    void look_429Response_throwsException() throws Exception {
        SimpleHttpResponse httpResponse = new SimpleHttpResponse(429, "{}");

        when(httpService.sendRequest(anyString())).thenReturn(httpResponse);

        assertThrows(TooManyRequestsExeption.class,
                () -> binLookupService.look(validCard));
    }

    @Test
    void look_500Response_throwsIOException() throws Exception {
        SimpleHttpResponse httpResponse = new SimpleHttpResponse(500, "{}");

        when(httpService.sendRequest(anyString())).thenReturn(httpResponse);

        assertThrows(IOException.class,
                () -> binLookupService.look(validCard));
    }

    @Test
    void look_httpClientThrowsIoException_propagatesException() throws Exception {
        SimpleHttpResponse httpResponse = new SimpleHttpResponse(500, "{}");

        when(httpService.sendRequest(anyString())).thenReturn(httpResponse);

        assertThrows(IOException.class,
                () -> binLookupService.look(validCard));
    }
}