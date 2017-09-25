package ru.tutu.presenters;

import org.junit.Before;
import org.junit.Test;

import ru.tutu.entities.Entities;
import ru.tutu.use_cases.UseCases;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class TestDetailsPresenter {
private DetailsPresenter.View view;
private UseCases useCasesSpy;
private DetailsPresenter presenter;
@Before
public void init() {
	view = mock(DetailsPresenter.View.class);
	useCasesSpy = null;
	presenter = null;
}
@Test
public void testAlwaysSuccess() {
	useCasesSpy = spy(new UseCasesAlwaysSuccessStub());
	presenter = new DetailsPresenter(view, useCasesSpy, 0, 0);

	verify(useCasesSpy, atLeastOnce()).hotelDetails(anyInt(), anyInt(), (UseCases.DetailsCallback) any());

	verify(view, atLeastOnce()).showLoading();
	verify(view, atLeastOnce()).showData((Entities.HotelDetails) any());
	verify(view, atLeastOnce()).hideLoading();

	verify(view, never()).showError((String) any());
}
@Test
public void testAlwaysFails() {
	useCasesSpy = spy(new UseCasesAlwaysFailsStub());
	presenter = new DetailsPresenter(view, useCasesSpy, 0, 0);

	verify(useCasesSpy, atLeastOnce()).hotelDetails(anyInt(), anyInt(), (UseCases.DetailsCallback) any());

	verify(view, atLeastOnce()).showLoading();
	verify(view, atLeastOnce()).showError((String) any());

	verify(view, never()).showData((Entities.HotelDetails) any());
}
}
