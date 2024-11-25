package org.jala.university.application.service.service_card;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/***
 * Class responsible for cep API tools
 */
public class CepApplication {



    public static boolean validateCep(String cep) {
        return cep.length() == 8;
    }

    /***
     * function responsible to call the viacep api and return the result
     * @param cep is the customer cep
     * @return the address
     */
    private static String getCepData(String cep) {
        try {
            String urlString = "https://viacep.com.br/ws/" + cep + "/json/";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            return "Fail " + e.getMessage();
        }
    }

    /***
     *function responsible for taking only specific address fields
     * @param cep is the customer cep
     * @return a List with the address
     */
    public static List<String> getCep(String cep) {
        List<String> addressDetails = new ArrayList<>();
        cep = cep.replaceAll("[^0-9]", "");
        if (!cep.isEmpty()) {
            String jsonResult = getCepData(cep);

            JsonElement jsonElement = JsonParser.parseString(jsonResult);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("erro")) {
                    addressDetails.add("CEP doesn't match");
                } else {
                    String logradouro = jsonObject.has("logradouro") ? jsonObject.get("logradouro").getAsString() : "N/A";
                    String cidade = jsonObject.has("localidade") ? jsonObject.get("localidade").getAsString() : "N/A";
                    String uf = jsonObject.has("uf") ? jsonObject.get("uf").getAsString() : "N/A";

                    addressDetails.add(logradouro);
                    addressDetails.add(cidade);
                    addressDetails.add(uf);
                }
            } else {
                addressDetails.add("Fail");
            }
        } else {
            addressDetails.add("Fails");
        }

        return addressDetails;
    }
}
