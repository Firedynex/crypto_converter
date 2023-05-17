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

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Converter {

    private String[] currencies; 
    private String ninjaKey;
    private static Scanner INPUT = new Scanner(System.in);

    /** HTTP client. */
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    private static Gson GSON = new GsonBuilder()
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
     * This method asks the user to select a cryptocurrency that is in the {@code currencies}.
     * The user must input the desired cryptocurrency's 3 character symbol along with USD.
     * @throws IllegalArgumentException if the user chooses an invalid cryptocurrency that's not in the list or if they forget to 
     * put in USD.
     * @return String User's valid crypto currency.
     */
    public String getCurrency() {
        String userChoice = "";
        String usd = "USD";
        List<String> currenciesList = Arrays.asList(currencies);
        try {
            userChoice = INPUT.nextLine();
            if (currenciesList.contains(userChoice) && userChoice.contains(usd)) {
                return userChoice;
            } else {
                throw new IllegalArgumentException("The given cryptocurrency is not valid!");
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        
        return userChoice;
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
