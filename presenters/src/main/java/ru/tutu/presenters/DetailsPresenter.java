package ru.tutu.presenters;

import ru.tutu.entities.HotelLookData;
import ru.tutu.use_cases.UseCases;

public class DetailsPresenter {
private final View view;
private final UseCases useCases;
public DetailsPresenter(final View view, UseCases useCases, int locationId, int hotelId) {
	this.view = view;
	this.useCases = useCases;
	view.showLoading();
	useCases.hotelDetails(locationId, hotelId, new UseCases.DetailsCallback() {
		public void onSuccess(HotelLookData.HotelDetails details) {
			view.hideLoading();
			view.showData(details);
		}
		public void onError(String error) {
			view.showError(error);
		}
	});
}
public interface View {
	void showLoading();
	void hideLoading();
	void showData(HotelLookData.HotelDetails data);
	void showError(String error);
}
}
