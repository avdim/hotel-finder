package ru.tutu.presenters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.List;

import ru.tutu.entities.Entities;
import ru.tutu.use_cases.UseCases;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class TestMainPresenter {
private MainPresenter.View view;
private UseCases useCasesSpy;
private MainPresenter presenter;
@Before
public void init() {
	view = mock(MainPresenter.View.class);
	useCasesSpy = null;
	presenter = null;
}
@Test
public void testAlwaysSuccess() {
	useCasesSpy = spy(new UseCasesAlwaysSuccessStub());
	presenter = new MainPresenter(view, useCasesSpy);
	presenter.searchHotels("some query");

	verify(useCasesSpy, atLeastOnce()).lookHotels(anyString(), (UseCases.LookCallback) any());

	verify(view, atLeastOnce()).showLoading();
	verify(view, atLeastOnce()).showHotels(ArgumentMatchers.<List<Entities.HotelInfo>>any());
	verify(view, atLeastOnce()).hideLoading();

	verify(view, never()).showError((String) any());
}
@Test
public void testAlwaysFails() {
	useCasesSpy = spy(new UseCasesAlwaysFailsStub());
	presenter = new MainPresenter(view, useCasesSpy);
	presenter.searchHotels("some query");

	verify(useCasesSpy, atLeastOnce()).lookHotels(anyString(), (UseCases.LookCallback) any());

	verify(view, atLeastOnce()).showLoading();
	verify(view, atLeastOnce()).showError((String) any());

	verify(view, never()).showHotels(ArgumentMatchers.<List<Entities.HotelInfo>>any());
}
}
