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

public class TTSRotaActivity extends Activity {

	private static HttpClient HTTP_CLIENT = new DefaultHttpClient();
	private static HttpGet HTTP_GET = new HttpGet();
	private static StringBuilder URL = new StringBuilder(
			"http://maps.google.com.br/maps/api/directions/json?origin=");

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
				// Chamando tela de apresentacao da rota
				Intent rota = new Intent(v.getContext(), RotaActivity.class);
				try {

					EditText origem = (EditText) findViewById(R.id.origem);
					EditText destino = (EditText) findViewById(R.id.destino);
					Spinner locomocao = (Spinner) findViewById(R.id.locomocao);

					URL.setLength(58);
					URL.append(origem.getText());
					URL.append("&destination=");
					URL.append(destino.getText());
					URL.append("&mode=");
					URL.append(locomocao.getSelectedItemPosition() == 0 ? "walking"
							: "driving");
					URL.append("&language=pt-BR&sensor=false");
					String url = URL.toString().replace(" ", "%20");
					HTTP_GET.setURI(new URI(url));

					HttpResponse httpResponse = HTTP_CLIENT.execute(HTTP_GET);

					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						rota.putExtra("rota",
								EntityUtils.toString(httpResponse.getEntity()));
					}

				} catch (Exception e) {
					rota.putExtra("rota",
							URL.toString() + " -- " + e.getMessage());
				}
				startActivity(rota);
			}
		});
	}
}