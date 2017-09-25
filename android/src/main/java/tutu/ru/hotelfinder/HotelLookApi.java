package tutu.ru.hotelfinder;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.tutu.entities.HotelLookData;

public interface HotelLookApi {
String BASE_URL = "http://engine.hotellook.com/api/v2/";
String SMALL_PICTURE_URL_FORMAT = "https://photo.hotellook.com/image_v2/limit/h{hotel_id}_3/256.jpg";
String BIG_PICTURE_URL_FORMAT = "https://photo.hotellook.com/image_v2/limit/h{hotel_id}_3/512.jpg";

@GET("lookup.json?lang=en&lookFor=both&limit=10")
Call<AndroidUseCasesImpl.LookObject> lookHotels(@Query("query") String query);

@GET("cache.json?lang=en&currency=usd")
Call<HotelLookData.HotelDetails> details(@Query("location") int location,
																				 @Query("hotelId") int hotelId,
																				 @Query("checkIn") String checkIn,
																				 @Query("checkOut") String checkOut
																				 );
}
