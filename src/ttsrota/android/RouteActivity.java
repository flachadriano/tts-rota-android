package ttsrota.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class RouteActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route);

		String route = (String) getIntent().getSerializableExtra("route");
		EditText routeEdit = (EditText) findViewById(R.id.route);

		routeEdit.setText(route);
	}
}