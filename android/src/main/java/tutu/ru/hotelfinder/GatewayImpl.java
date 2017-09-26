package tutu.ru.hotelfinder;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tutu.entities.Entities;

public class GatewayImpl implements ru.tutu.use_cases.UseCases.Gateway {

    private final HotelLookApi api;

    public GatewayImpl() {
        api = new Retrofit.Builder()
                .baseUrl(HotelLookApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(HotelLookApi.class);
    }

    public void lookHotels(String query, final LookCallback callback) {
        api.lookHotels(query).enqueue(new Callback<LookObject>() {
            public void onResponse(Call<LookObject> call, Response<LookObject> response) {
                callback.onSuccess(response.body().results.hotels);
            }

            public void onFailure(Call<LookObject> call, Throwable t) {
                callback.onError("error while loading lookHotels");
            }
        });
    }

    public void hotelDetails(int locationId, int hotelId, final DetailsCallback callback) {
        //Для того чтобы получить цену номера гостиницы нужно передать дату заезда и выезда. Для простоты рассматриваем прибывание в гостинице на 1 день.
        Calendar checkIn = new GregorianCalendar();
        GregorianCalendar checkOut = new GregorianCalendar();
        checkOut.add(Calendar.DAY_OF_MONTH, 1);
        api.details(locationId, hotelId, dateString(checkIn), dateString(checkOut)).enqueue(new Callback<Entities.HotelDetails>() {
            public void onResponse(Call<Entities.HotelDetails> call, Response<Entities.HotelDetails> response) {
                callback.onSuccess(response.body());
            }

            public void onFailure(Call<Entities.HotelDetails> call, Throwable t) {
                callback.onError("eror while loading hotelDetails");
            }
        });
    }

    public static class LookObject {
        public String status;
        public ResultsObject results;
    }

    public static class ResultsObject {
        public List<Entities.HotelInfo> hotels;
    }

    public static String dateString(Calendar calendar) {
        // Специальный формат для нашего api. Обязательно чтобы месяцы и дни состояли из двух цифр:
        // (неправильно) 2017-9-25 -> 2017-09-25 (правильно)
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }

}
