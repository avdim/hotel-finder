package ru.tutu.entities;
public class Entities {
public static class HotelInfo {
	public int locationId;
	public String label;
	public int id;
	public String fullName;
	public String name;
	public GPS location;
	public String locationName;
}
public static class GPS {
	public float lon;
	public float lat;
}
public static class HotelDetails {
	public String hotelName;
	public int priceAvg;
	public int locationId;
	public int hotelId;
	public int priceFrom;
	public int stars;
}
}
