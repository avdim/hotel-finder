package ru.tutu.presenters;

import org.junit.Before;
import org.junit.Test;

import ru.tutu.entities.Entities;
import ru.tutu.use_cases.*;
import ru.tutu.use_cases.GatewayAlwaysSuccessStub;


import static org.mockito.Mockito.*;

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
        useCasesSpy = spy(new UseCases(new GatewayAlwaysSuccessStub()));
        presenter = new DetailsPresenter(view, useCasesSpy, 0, 0);

        verify(useCasesSpy, atLeastOnce()).hotelDetails(anyInt(), anyInt(), (UseCases.Gateway.DetailsCallback) any());

        verify(view, atLeastOnce()).showLoading();
        verify(view, atLeastOnce()).showData((Entities.HotelDetails) any());
        verify(view, atLeastOnce()).hideLoading();

        verify(view, never()).showError((String) any());
    }

    @Test
    public void testAlwaysFails() {
        useCasesSpy = spy(new UseCases(new ru.tutu.use_cases.GatewayAlwaysFailsStub()));
        presenter = new DetailsPresenter(view, useCasesSpy, 0, 0);

        verify(useCasesSpy, atLeastOnce()).hotelDetails(anyInt(), anyInt(), (UseCases.Gateway.DetailsCallback) any());

        verify(view, atLeastOnce()).showLoading();
        verify(view, atLeastOnce()).showError((String) any());

        verify(view, never()).showData((Entities.HotelDetails) any());
    }
}
