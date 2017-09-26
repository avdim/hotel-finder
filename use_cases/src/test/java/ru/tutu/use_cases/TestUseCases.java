package ru.tutu.use_cases;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ru.tutu.entities.Entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestUseCases {

    private List<Entities.HotelInfo> resultHotels;
    private Entities.HotelDetails resultDetails;

    @Before
    public void init() {
        resultHotels = null;
        resultDetails = null;
    }

    @Test
    public void testMock() {
        UseCases.Gateway gateway = mock(UseCases.Gateway.class);
        UseCases useCases = new UseCases(gateway);
        useCases.lookHotels("any", null);
        useCases.hotelDetails(0, 0, null);
        verify(gateway, atLeastOnce()).lookHotels(anyString(), (UseCases.Gateway.LookCallback) any());
        verify(gateway, atLeastOnce()).hotelDetails(anyInt(), anyInt(), (UseCases.Gateway.DetailsCallback) any());
    }

    @Test
    public void testStub() {
        UseCases.Gateway gateway = new GatewayAlwaysSuccessStub();
        UseCases useCases = new UseCases(gateway);
        useCases.lookHotels("any", new UseCases.Gateway.LookCallback() {
            public void onSuccess(List<Entities.HotelInfo> hotels) {
                resultHotels = hotels;
            }

            public void onError(String error) {
                fail(error);
            }
        });
        useCases.hotelDetails(0, 0, new UseCases.Gateway.DetailsCallback() {
            public void onSuccess(Entities.HotelDetails details) {
                resultDetails = details;
            }

            public void onError(String error) {
                fail(error);
            }
        });
        assertNotNull(resultHotels);
        assertNotNull(resultDetails);
    }
}
