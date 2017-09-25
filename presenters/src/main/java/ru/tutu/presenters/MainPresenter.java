package ru.tutu.presenters;

import java.util.List;

import ru.tutu.entities.HotelLookData;
import ru.tutu.use_cases.UseCases;

public class MainPresenter {
private final View view;
private final UseCases useCases;
public MainPresenter(View view, UseCases useCases) {
	this.view = view;
	this.useCases = useCases;
}
public void searchHotels(String query) {
	view.showLoading();
	useCases.lookHotels(query, new UseCases.LookCallback() {
		public void onSuccess(List<HotelLookData.HotelInfo> hotels) {
			view.hideLoading();
			view.showHotels(hotels);
		}
		public void onError(String error) {
			view.showError(error);
		}
	});
}
public void clickHotel(HotelLookData.HotelInfo hotel) {
	view.showDetails(hotel);
}
public interface View {
	void showLoading();
	void hideLoading();
	void showHotels(List<HotelLookData.HotelInfo> hotels);
	void showDetails(HotelLookData.HotelInfo hotel);
	void showError(String error);
}
}
