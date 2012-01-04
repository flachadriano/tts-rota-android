package ttsrota.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class RotaActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rota);

		String rota = (String) getIntent().getSerializableExtra("rota");
		EditText rotaEdit = (EditText) findViewById(R.id.rota);
		
		rotaEdit.setText(rota);
	}
}