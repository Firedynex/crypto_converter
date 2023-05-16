package striker.crypto_converter;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Converter {

    private String[] currencies; 
    private String ninjaKey;

    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object
    
    /**
     * Constructor that initializes instance variables.
     */
    public Converter() {
        super();       
        getKey();
        getSymbols();
    }

    /**
     * Method that calls the cryptosymbbols api from API Ninjas.
     * Stores the response into {@code currencies}.
     */
    public void getSymbols() {
        String url = "https://api.api-ninjas.com/v1/cryptosymbols?";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("X-API-KEY", ninjaKey)
        .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
            String responseBody = response.body();
            CryptoSymbols cryptoSymbols = GSON.<CryptoSymbols>fromJson(responseBody, CryptoSymbols.class);
            currencies = cryptoSymbols.getSymbols();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gets the API Key.
     */
    public void getKey() {
        try {
            Properties props = new Properties();
            BufferedInputStream apiKeyFile = new BufferedInputStream(new FileInputStream("D:\\Coding Projects\\Private_Keys.properties"));
            props.load(apiKeyFile);
            ninjaKey = props.getProperty("API_NINJAS_KEY");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
