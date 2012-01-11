package ttsrota.android;

import java.io.UnsupportedEncodingException;

public class GoogleMaps {

	private static StringBuilder URL = new StringBuilder("http://maps.googleapis.com/maps/api/directions/json?origin=");

	public static String getURL(String origin, String destination, Integer mode) throws UnsupportedEncodingException {
		URL.setLength(59);
		URL.append(origin);
		URL.append("&destination=").append(destination);
		URL.append("&mode=").append(mode == 0 ? "walking" : "driving");
		URL.append("&language=pt-BR&sensor=false");
		return URL.toString().replace(" ", "%20");
	}

}
