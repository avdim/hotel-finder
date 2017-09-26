package ru.tutu.use_cases;

import ru.tutu.entities.Entities;

import java.util.ArrayList;

public class GatewayAlwaysSuccessStub implements UseCases.Gateway {
    public void lookHotels(String query, LookCallback callback) {
        callback.onSuccess(new ArrayList<Entities.HotelInfo>());
    }

    public void hotelDetails(int locationId, int hotelId, DetailsCallback callback) {
        callback.onSuccess(new Entities.HotelDetails());
    }
}
