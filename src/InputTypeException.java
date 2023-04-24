
public class InputTypeException extends Exception {
    /**
     * Un errore generico per problemi col parsing delle stringhe.
     * @param errorMessage
     */
    public InputTypeException(String errorMessage) {
        super(errorMessage);
    }
}
