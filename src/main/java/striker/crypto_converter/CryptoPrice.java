package striker.crypto_converter;

/**
 * Class that represents a price for a crypto currency.
 * Stores the response from API ninja's crypto price api.
 * Converts the crypto price into USD.
 */
public class CryptoPrice {
    private double price;

    /**
     * Accessor method for {@code price}.
     * @return double Price in USD of the cryptocurrency.
     */
    public double getPrice() {
        return this.price;
    }
}
