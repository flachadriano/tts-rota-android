package ttsrota.android;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TTSRotaActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// carregando as opcoes de locomocao
		Spinner locomocao = (Spinner) findViewById(R.id.locomocao);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.locomocao_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locomocao.setAdapter(adapter);

		// solicitar rota
		final Button solicitarRota = (Button) findViewById(R.id.solicitarRota);
		solicitarRota.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					HttpClient hc = new DefaultHttpClient();
					HttpPost post = new HttpPost("http://maps.google.com.br/maps/api/directions/json?origin=Rua José Kasteler, Jaraguá Esquerdo, Jaraguá do Sul - Santa Catarina&destination=Rua Arduino Pradi, São Luis, Jaraguá do Sul - Santa Catarina&language=pt-BR&sensor=false");

					HttpResponse rp = hc.execute(post);

					String rota = "";
					if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						rota = EntityUtils.toString(rp.getEntity());
					}
					EditText rotaEdit = (EditText) findViewById(R.id.rota);
					rotaEdit.setText(rota);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}