package ru.tutu.presenters;
import ru.tutu.use_cases.UseCases;

public class UseCasesAlwaysFailsStub implements UseCases {
public void lookHotels(String query, LookCallback callback) {
	callback.onError("some error");
}
public void hotelDetails(int locationId, int hotelId, DetailsCallback callback) {
	callback.onError("some error");
}
}
