package tutu.ru.hotelfinder;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ru.tutu.entities.Entities;
import ru.tutu.use_cases.GatewayAlwaysFailsStub;
import ru.tutu.use_cases.GatewayAlwaysSuccessStub;
import ru.tutu.use_cases.UseCases;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CacheDecoratorTest {
private Entities.HotelDetails details1;
private Entities.HotelDetails details2;
private List<Entities.HotelInfo> look1;
private List<Entities.HotelInfo> look2;
@Before
public void init() {
	details1 = null;
	details2 = null;
}
@Test
public void testAlwaysSuccess() {
	UseCases.Gateway gatewaySpy = spy(new GatewayAlwaysSuccessStub());
	CacheGatewayDecorator cacheDecorator = new CacheGatewayDecorator(gatewaySpy);
	cacheDecorator.lookHotels("", new UseCases.Gateway.LookCallback() {
		public void onSuccess(List<Entities.HotelInfo> hotels) {
			look1 = hotels;
		}
		public void onError(String error) {
			fail(error);
		}
	});
	cacheDecorator.lookHotels("", new UseCases.Gateway.LookCallback() {
		public void onSuccess(List<Entities.HotelInfo> hotels) {
			look2 = hotels;
		}
		public void onError(String error) {
			fail(error);
		}
	});
	cacheDecorator.hotelDetails(0, 0, new UseCases.Gateway.DetailsCallback() {
		public void onSuccess(Entities.HotelDetails details) {
			details1 = details;
		}
		public void onError(String error) {
			fail(error);
		}
	});
	cacheDecorator.hotelDetails(0, 0, new UseCases.Gateway.DetailsCallback() {
		public void onSuccess(Entities.HotelDetails details) {
			details2 = details;
		}
		public void onError(String error) {
			fail(error);
		}
	});
	assertNotNull(look1);
	assertNotNull(look2);
	assertEquals(look1, look2);
	assertNotNull(details1);
	assertNotNull(details2);
	assertEquals(details1, details2);
	verify(gatewaySpy, times(1)).lookHotels(anyString(), (UseCases.Gateway.LookCallback) any());
	verify(gatewaySpy, times(1)).hotelDetails(anyInt(), anyInt(), (UseCases.Gateway.DetailsCallback) any());
}
@Test
public void testAlwaysFails() {
	UseCases.Gateway gatewaySpy = spy(new GatewayAlwaysFailsStub());
	CacheGatewayDecorator cacheDecorator = new CacheGatewayDecorator(gatewaySpy);
	for(int i = 0; i < 2; i++) {
		cacheDecorator.lookHotels("", new UseCases.Gateway.LookCallback() {
			public void onSuccess(List<Entities.HotelInfo> hotels) {
				fail();
			}
			public void onError(String error) {

			}
		});
		cacheDecorator.hotelDetails(0, 0, new UseCases.Gateway.DetailsCallback() {
			public void onSuccess(Entities.HotelDetails details) {
				fail();
			}
			public void onError(String error) {

			}
		});
	}
	verify(gatewaySpy, times(2)).lookHotels(anyString(), (UseCases.Gateway.LookCallback) any());
	verify(gatewaySpy, times(2)).hotelDetails(anyInt(), anyInt(), (UseCases.Gateway.DetailsCallback) any());
}
}