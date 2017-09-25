package ru.tutu.use_cases;

import java.util.List;

import ru.tutu.entities.HotelLookData;

public interface UseCases {
void lookHotels(String query, LookCallback callback);
void hotelDetails(int locationId, int hotelId, DetailsCallback callback);

public interface LookCallback {
	public void onSuccess(List<HotelLookData.HotelInfo> hotels);
	public void onError(String error);
}
public interface DetailsCallback {
	public void onSuccess(HotelLookData.HotelDetails details);
	public void onError(String error);
}
}
