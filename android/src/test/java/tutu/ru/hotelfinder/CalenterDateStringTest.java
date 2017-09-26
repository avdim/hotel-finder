package tutu.ru.hotelfinder;

import org.junit.Test;

import java.util.GregorianCalendar;


import static org.junit.Assert.*;
public class CalenterDateStringTest {
@Test
public void test() {
	//Почему-то частое явление что месяц начинается с 0 (январь) по 11(декабрь)
	//В нашем случае сентябрь будет иметь значение 8
	int month = 8;
	String result = GatewayImpl.dateString(new GregorianCalendar(2017, month, 26));
	assertEquals("2017-09-26", result);
}
}
