package striker.crypto_converter;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
     * @throws IllegalArgumentException if the user chooses an invalid cryptocurrency that's not in the list.
     * @return String User's valid crypto currency.
     */
    public String getCurrency() {
        String userChoice = "";
        List<String> currenciesList = Arrays.asList(currencies);
        try {
            userChoice = INPUT.nextLine() + "USD";
            if (currenciesList.contains(userChoice)) {
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
     * This method takes in the user's choice of cryptocurrency and returns the USD conversion of it.
     * @param userCrypto User's choice of cryptocurrency.
     * @return double price of the user's crypto into USD.
     */
    public double getPrice(String userCrypto) {
        String url = "https://api.api-ninjas.com/v1/cryptoprice?symbol=" + userCrypto;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("X-API-KEY", ninjaKey)
            .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
            String responseBody = response.body();
            CryptoPrice price = GSON.<CryptoPrice>fromJson(responseBody, CryptoPrice.class);
            return price.getPrice();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Method that gets the user's desired country for currency conversion.
     * @return String of the country's currency symbol.
     */
    public String getCountrySymbol() {
        String url = "https://api.api-ninjas.com/v1/country?name=";
        String countryName = "";
        try {
            countryName = URLEncoder.encode(INPUT.nextLine(), StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url + countryName))
            .header("X-API-KEY", ninjaKey)
            .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
            String responseBody = response.body();
            Country[] country = GSON.<Country[]>fromJson(responseBody, Country[].class);
            return country[0].getCurrency().getCode();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return countryName;
    }
    
    /**
     * This method converts the USD value of the cryptocurrency into the currency
     * of the user's desired country.
     * @param currencySymbol The symbol of the currency the user wants to convert to.
     * @param usdPrice Price of the cryptocurrency in USD.
     * @return double Converted currency of the desired crypto currency.
     */
    public double convert(String currencySymbol, double usdPrice) {
        String url = "https://api.api-ninjas.com/v1/convertcurrency?have=USD&want=" +
        currencySymbol + "&amount=" + usdPrice;
        try {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("X-API-KEY", ninjaKey)
            .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
            String responseBody = response.body();
            CurrencyConvert convertedCurrency = GSON.<CurrencyConvert>fromJson(responseBody, CurrencyConvert.class);
            return convertedCurrency.getNewAmount();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0.0;
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
