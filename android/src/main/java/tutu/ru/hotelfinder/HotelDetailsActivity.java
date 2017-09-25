package tutu.ru.hotelfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ru.tutu.entities.HotelLookData;
import ru.tutu.presenters.DetailsPresenter;

public class HotelDetailsActivity extends AppCompatActivity implements DetailsPresenter.View {

public static final String INTENT_EXTRA_PARAM_LOCATION_ID = "ru.tutu.INTENT_PARAM_LOCATION_ID";
public static final String INTENT_EXTRA_PARAM_HOTEL_ID = "ru.tutu.INTENT_PARAM_HOTEL_ID";
private DetailsPresenter presenter;
private ImageView image;
private TextView title;
private TextView stars;
private TextView price;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_hotel_details);
	image = (ImageView) findViewById(R.id.detailImage);
	title = (TextView) findViewById(R.id.detailTitle);
	stars = (TextView) findViewById(R.id.detailStars);
	price = (TextView) findViewById(R.id.detailPrice);
	int locationId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_LOCATION_ID, 0);
	int hotelId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_HOTEL_ID, 0);
	if(locationId == 0 || hotelId == 0) {
		showError("bad intent");
		return;
	}
	this.presenter = new DetailsPresenter(this, App.getUseCases(), locationId, hotelId);
}
public void showLoading() {

}
public void hideLoading() {

}
public void showData(HotelLookData.HotelDetails data) {
	if(data == null) {
		showError("missing hotel data");
		return;
	}
	String pictureUrl = HotelLookApi.BIG_PICTURE_URL_FORMAT.replace("{hotel_id}", String.valueOf(data.hotelId));
	Picasso.with(this).load(pictureUrl).into(image);
	title.setText(data.hotelName);
	stars.setText(data.stars + " Stars");
	price.setText(data.priceAvg + " $");
}
public void showError(String error) {
	Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
}
}