import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputManager {

    static private String userName = "utente";

    static public void setName(String name) {

        userName = name;
    }

    /**
	 * Disegna il prompt per informare l'utente che deve immetere dell'input.
	 */
	static public void prompt() {
		System.out.print(Colors.green(userName) + ": ");
	}

    /**
	 * Stampa un messaggio di sistema.
	 */
	static public void systemMessage(String str, boolean newLine) {
        if (str.startsWith(" ") || str.startsWith("\t"))
    		System.out.print(str);
        else
    		System.out.print(Colors.blue("sistema") + ": " + str);

        if (newLine)
            System.out.println();
	}
	static public void systemMessage(String str) {
        systemMessage(str, true);
    }

    /**
	 * Stampa un messaggio di errore.
	 */
	static public void error(String str, boolean newLine) {
		System.err.print(Colors.red("errore") + ": " + str);

        if (newLine)
            System.out.println();
	}
	static public void error(String str) {
        error(str, true);
    }
    static public void error() {
        error("");
    }

    /**
	 * Stampa un messaggio di attenzione.
	 */
	static public void warn(String str, boolean newLine) {
		System.err.print(Colors.yellow("warning") + ": " + str);

        if (newLine)
            System.out.println();
	}
	static public void warn(String str) {
        warn(str, true);
    }
    static public void warn() {
        warn("");
    }

    /**
     * Controlla se la tringa `str` rispetta l'espressione regolare `regex`. Se non
     * la rispetta lancia un errore.
     * @param str
     * @param regex
     * @return true se la `str` rispetta `regex`
     */
    static private boolean matches(String str, String regex) {
        /** Crea un'espressione regolare dalla stringa `regex` */
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        /** Genera un matcher per `pattern` */
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * Converte una stringa in un double oppure lancia un errore human-frendly.
     * @param s
     * @return Il numero parsato.
     * @throws InputTypeException
     */
    static public double parseDouble(String s) throws InputTypeException {

        /** Prova a parsare la stringa come un double... */
        try {
            return Double.parseDouble(s);
        /** ... altrimenti lancia un errore. */
        } catch (Exception e) {
            throw new InputTypeException("\"" + s + "\" non è un valore numerico!");
        }
    }

    /**
     * Converte una stringa in un intero oppure lancia un errore human-frendly.
     * @param s
     * @return Il numero parsato.
     * @throws InputTypeException
     */
    static public int parseInt(String s) throws InputTypeException {

        /** Prova a parsare la stringa come un intero... */
        try {
            return Integer.parseInt(s);
        /** ... altrimenti lancia un errore. */
        } catch (Exception e) {
            throw new InputTypeException("\"" + s + "\" non è un valore intero!");
        }
    }

    /**
     * Controlla se la stringa passata in input è valida come dominio per un Attribute.
     * @param s
     * @return Il dominio se è valido.
     * @throws InputTypeException
     */
    static public String parseDomain(String s) throws InputTypeException {
        
        /** Testata sul sito https://regex101.com/ */
        String p = "^[a-zA-Z0-9]+(?:,[a-zA-Z0-9]+){1,}$";
        /** Se la stringa rispetta il pattern qui su allore ritorna la stringa... */
        if (matches(s, p))
            /** Rimuovi tutti gli spazi vicini alle virgole dal dominio. */
            return s.replaceAll("\\s{1,}?,\\s{1,}?", ",");
        /** ... altrimenti lancia un errore. */
        else
            throw new InputTypeException("\"" + s + "\" non è un dominio valido!");
    }
}
