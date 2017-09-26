package ru.tutu.use_cases;

import java.util.List;

import ru.tutu.entities.Entities;

public class UseCases {
private final Gateway gateway;
public UseCases(Gateway gateway) {
	this.gateway = gateway;
}
public void hotelDetails(int locationId, int hotelId, Gateway.DetailsCallback callback) {
	gateway.hotelDetails(locationId, hotelId, callback);
}
public void lookHotels(String query, Gateway.LookCallback callback) {
	gateway.lookHotels(query, callback);
}
public interface Gateway {
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

}
