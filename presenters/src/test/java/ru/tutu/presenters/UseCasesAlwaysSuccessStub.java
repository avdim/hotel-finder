package ru.tutu.presenters;
import java.util.ArrayList;

import ru.tutu.entities.HotelLookData;
import ru.tutu.use_cases.UseCases;

public class UseCasesAlwaysSuccessStub implements UseCases {
public void lookHotels(String query, LookCallback callback) {
	callback.onSuccess(new ArrayList<HotelLookData.HotelInfo>());
}
public void hotelDetails(int locationId, int hotelId, DetailsCallback callback) {
	callback.onSuccess(new HotelLookData.HotelDetails());
}
}
