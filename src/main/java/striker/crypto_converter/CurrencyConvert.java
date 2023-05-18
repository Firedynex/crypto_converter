package striker.crypto_converter;

/**
 * Class that is the response from API Ninja's Convert Currency API.
 */
public class CurrencyConvert {
    private double new_amount;

    /**
     * Accessor method for {@code new_amount}.
     * @return double The value of {@code new_amount}.
     */
    public double getNewAmount() {
        return this.new_amount;
    }
}
