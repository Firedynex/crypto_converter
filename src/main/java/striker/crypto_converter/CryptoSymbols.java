package striker.crypto_converter;

/**
 * Class that stores the response from API Ninja's 
 * Crypto Symbols api.
 */
public class CryptoSymbols {
    private String[] symbols;

    /**
     * Accessor method for {@code symbols}.
     * @return String[] array for all the crypto symbols.
     */
    public String[] getSymbols() {
        return this.symbols;
    }
}