package tutu.ru.hotelfinder;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ru.tutu.entities.Entities;
import ru.tutu.use_cases.UseCases;

//Декоратор для кэширования данных, чтобы уменьшить возможное число обращений к серверу.
//Сейчас кэш хранится в оперативной памяти, но можно также сохоанять в SQLite.
public class CacheGatewayDecorator implements UseCases.Gateway {
    private final UseCases.Gateway gateway;
    private final HashMap<String, List<Entities.HotelInfo>> lookCache = new HashMap<>();
    private final HashMap<Integer, Entities.HotelDetails> detailCache = new HashMap<>();

    public CacheGatewayDecorator(UseCases.Gateway gateway) {
        this.gateway = gateway;
    }

    public void lookHotels(final String query, final LookCallback callback) {
        List<Entities.HotelInfo> result = lookCache.get(query);
        if (result != null) {
            callback.onSuccess(result);
            return;
        }
        gateway.lookHotels(query, new LookCallback() {
            public void onSuccess(List<Entities.HotelInfo> hotels) {
                lookCache.put(query, hotels);
                callback.onSuccess(hotels);
            }

            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public void hotelDetails(int locationId, int hotelId, final DetailsCallback callback) {
        final int key = Objects.hash(locationId, hotelId);
        Entities.HotelDetails result = detailCache.get(key);
        if (result != null) {
            callback.onSuccess(result);
            return;
        }
        gateway.hotelDetails(locationId, hotelId, new DetailsCallback() {
            public void onSuccess(Entities.HotelDetails details) {
                detailCache.put(key, details);
                callback.onSuccess(details);
            }

            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
}
