package tutu.ru.hotelfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.tutu.entities.Entities;
import ru.tutu.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

private RecyclerView recyclerView;
private MainPresenter presenter;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(this));
	presenter = new MainPresenter(this, App.getUseCases());
	presenter.searchHotels("Москва");
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu_main, menu);
	SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
		public boolean onQueryTextSubmit(String query) {
			presenter.searchHotels(query);
			return true;
		}
		public boolean onQueryTextChange(String newText) {
			return false;
		}
	});
	return true;
}

public void showLoading() {

}
public void hideLoading() {

}
public void showHotels(final List<Entities.HotelInfo> hotels) {
	recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View modelView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
			return new ViewHolder(modelView);
		}
		public void onBindViewHolder(ViewHolder holder, final int position) {
			final Entities.HotelInfo hotel = hotels.get(position);
			holder.txt.setText(hotel.fullName);
			String pictureUrl = HotelLookApi.SMALL_PICTURE_URL_FORMAT.replace("{hotel_id}", String.valueOf(hotel.id));
			Picasso.with(MainActivity.this).load(pictureUrl).into(holder.img);
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					presenter.clickHotel(hotel);
				}
			});
		}
		public int getItemCount() {
			return hotels.size();
		}
	});
}
public void showDetails(Entities.HotelInfo hotel) {
	Intent intent = new Intent(this, HotelDetailsActivity.class);
	intent.putExtra(HotelDetailsActivity.INTENT_EXTRA_PARAM_LOCATION_ID, hotel.locationId);
	intent.putExtra(HotelDetailsActivity.INTENT_EXTRA_PARAM_HOTEL_ID, hotel.id);
	startActivity(intent);
}

private static class ViewHolder extends RecyclerView.ViewHolder {
	final TextView txt;
	final ImageView img;
	ViewHolder(View view) {
		super(view);
		txt = (TextView) view.findViewById(R.id.list_hotel_title);
		img = (ImageView) view.findViewById(R.id.list_hotel_image);
	}
}
public void showError(String error) {
	Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
}
}
