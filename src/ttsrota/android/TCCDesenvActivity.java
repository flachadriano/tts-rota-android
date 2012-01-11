package ttsrota.android;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TCCDesenvActivity extends Activity {

	private static HttpClient HTTP_CLIENT = new DefaultHttpClient();
	private static HttpGet HTTP_GET = new HttpGet();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// loading mode options
		Spinner mode = (Spinner) findViewById(R.id.mode);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mode_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mode.setAdapter(adapter);

		// request route action
		final Button requestRoute = (Button) findViewById(R.id.request_route);
		requestRoute.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// show route screen
				Intent intent = new Intent(v.getContext(), RouteActivity.class);
				intent.putExtra("route", searchRouteOnGoogleMapsAPI());
				startActivity(intent);
			}
		});
	}

	public String searchRouteOnGoogleMapsAPI() {
		String route = "";
		try {

			EditText origin = (EditText) findViewById(R.id.origin);
			EditText destination = (EditText) findViewById(R.id.destination);
			Spinner mode = (Spinner) findViewById(R.id.mode);

			if (origin.getText().toString().equals("")) {
				route = "Clique no botão de retorno e insira um local de origem";

			} else if (destination.getText().toString().equals("")) {
				route = "Clique no botão de retorno e insira um local de destino";

			} else {
				HTTP_GET.setURI(URI.create(GoogleMaps.getURL(origin.getText().toString().trim(), destination.getText().toString().trim(), mode.getSelectedItemPosition())));
				HttpResponse httpResponse = HTTP_CLIENT.execute(HTTP_GET);

				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					route = EntityUtils.toString(httpResponse.getEntity());
				}
			}

		} catch (Exception e) {
			route = e.getMessage();
		}
		return route;
	}
}