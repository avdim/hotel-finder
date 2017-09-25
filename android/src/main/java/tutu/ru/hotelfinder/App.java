package tutu.ru.hotelfinder;

import android.app.Application;

import ru.tutu.use_cases.UseCases;

public class App extends Application {
private static UseCases useCases;

@Override
public void onCreate() {
	super.onCreate();
	useCases = new AndroidUseCasesImpl();
}
public static UseCases getUseCases() {
	return useCases;
}
}
