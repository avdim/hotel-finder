package ru.tutu.use_cases;

import java.util.List;

import ru.tutu.entities.Entities;

public interface UseCases {
void lookHotels(String query, LookCallback callback);
void hotelDetails(int locationId, int hotelId, DetailsCallback callback);

interface LookCallback {
	void onSuccess(List<Entities.HotelInfo> hotels);
	void onError(String error);
}
interface DetailsCallback {
	void onSuccess(Entities.HotelDetails details);
	void onError(String error);
}
}
