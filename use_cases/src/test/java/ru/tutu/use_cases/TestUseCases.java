package ru.tutu.use_cases;

import org.junit.Test;

import static org.mockito.Mockito.*;
public class TestUseCases {

@Test
public void testMock() {
	UseCases.Gateway gateway = mock(UseCases.Gateway.class);
	UseCases useCases = new UseCases(gateway);
	useCases.lookHotels("any", null);
	useCases.hotelDetails(0, 0, null);
	verify(gateway, atLeastOnce()).lookHotels(anyString(), (UseCases.Gateway.LookCallback) any());
	verify(gateway, atLeastOnce()).hotelDetails(anyInt(), anyInt(), (UseCases.Gateway.DetailsCallback) any());
}

@Test
public void testStub() {
	//todo
}
}
