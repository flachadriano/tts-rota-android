package ttsrota.android;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleMaps {

	private static StringBuilder URL = new StringBuilder("http://maps.googleapis.com/maps/api/directions/json?origin=");

	public static String getRouteJSON(JSONObject json) throws JSONException {
		String error = verifyStatusMessage(json.getString("status"));
		if (!error.equals("")) {
			return error;
		}
		
		StringBuilder route = new StringBuilder();
		
		JSONObject nodeRoute = json.getJSONArray("routes").getJSONObject(0);
		if (nodeRoute != null) {
			
			JSONObject nodeLeg = nodeRoute.getJSONArray("legs").getJSONObject(0);
			if (nodeLeg != null) {
				
				route.append("Origem: ").append(nodeLeg.getString("start_address")).append("\n");
				route.append("Destino: ").append(nodeLeg.getString("end_address")).append("\n");
				
				JSONObject nodeDistance = nodeLeg.getJSONObject("distance");
				if (nodeDistance != null) {
					route.append("Distância: ").append(nodeDistance.getString("text")).append("\n");
				}
				
				JSONObject nodeDuration = nodeLeg.getJSONObject("duration");
				if (nodeDuration != null) {
					route.append("Duração: ").append(nodeDuration.getString("text")).append("\n");
				}
				
				JSONArray nodeSteps = nodeLeg.getJSONArray("steps");
				if (nodeSteps != null) {
					
					route.append("\n");
					JSONObject nodeStep = null;
					for (int i = 0; i < nodeSteps.length(); i++) {
						nodeStep = nodeSteps.getJSONObject(i);
						
						String sStepIntructions = nodeStep.getString("html_instructions");
						if (!sStepIntructions.equals("")) {
							route.append(sStepIntructions);

							JSONObject nodeStepDistance = nodeStep.getJSONObject("distance");
							if (nodeDistance != null) {
								route.append(" -siga ").append(nodeStepDistance.getString("text")).append("-").append("\n");
							}							
						}
					}
				}
			}
		}

		return route.toString();
	}

	public static String getURL(String origin, String destination, Integer mode) throws UnsupportedEncodingException {
		URL.setLength(59);
		URL.append(origin);
		URL.append("&destination=").append(destination);
		URL.append("&mode=").append(mode == 0 ? "walking" : "driving");
		URL.append("&language=pt-BR&sensor=false");
		return URL.toString().replace(" ", "%20");
	}

	/**
	 * @return Satus codes The "status" field within the Directions response
	 *         object contains the status of the request, and may contain
	 *         debugging information to help you track down why the Directions
	 *         service failed. The "status" field may contain the following
	 *         values: OK indicates the response contains a valid result.
	 *         NOT_FOUND indicates at least one of the locations specified in
	 *         the requests's origin, destination, or waypoints could not be
	 *         geocoded ZERO_RESULTS indicates no route could be found between
	 *         the origin and destination. MAX_WAYPOINTS_EXCEEDED indicates that
	 *         too many waypointss were provided in the request The maximum
	 *         allowed waypoints is 8, plus the origin, and destination. (
	 *         Google Maps Premier customers may contain requests with up to 23
	 *         waypoints.) INVALID_REQUEST indicates that the provided request
	 *         was invalid. OVER_QUERY_LIMIT indicates the service has received
	 *         too many requests from your application within the allowed time
	 *         period. REQUEST_DENIED indicates that the service denied use of
	 *         the directions service by your application. UNKNOWN_ERROR
	 *         indicates a directions request could not be processed due to a
	 *         server error. The request may succeed if you try again.
	 */
	private static String verifyStatusMessage(String status) {
		if (status.equals("OK")) {
			return ""; // Resultado da pesquisa válido.
		} else {
			
			StringBuilder msg = new StringBuilder();
			if (status.equals("NOT_FOUND")) {
				msg.append("Não foi possível encontrar as coordenadas geográficas para os endereços.");
			} else if (status.equals("ZERO_RESULTS")) {
				msg.append("Nenhuma rota foi encontrada entre os endereços de origem e destino.");
			} else if (status.equals("MAX_WAYPOINTS_EXCEEDED")) {
				msg.append("Quantidade de 8 pontos ultrapassada.");
			} else if (status.equals("INVALID_REQUEST")) {
				msg.append("Pesquisa inválida.");
			} else if (status.equals("OVER_QUERY_LIMIT")) {
				msg.append("Quantidade de pesquisas por hora foi ultrapassada. Tente realizar a pesquisa novamente dentro de 1 hora.");
			} else if (status.equals("REQUEST_DENIED")) {
				msg.append("Serviço negado para a aplicação.");
			} else if (status.equals("UNKNOWN_ERROR")) {
				msg.append("Pesquisa não processada devido a um erro no servidor. A pesquisa pode ter sucesso se você tentar novamente.");
			}
			msg.append(" Pressione a tecla voltar e faça uma nova pesquisa.");
			return msg.toString();
		}
	}

}
