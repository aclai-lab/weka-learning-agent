public class Colors {
    static public String T_BLACK     = "\u001B[30m";
    static public String T_RED       = "\u001B[31m";
    static public String T_GREEN     = "\u001B[32m";
    static public String T_YELLOW    = "\u001B[33m";
    static public String T_BLUE      = "\u001B[34m";
    static public String T_MAGENTA   = "\u001B[35m";
    static public String T_CYAN      = "\u001B[36m";
    static public String T_WHITE     = "\u001B[37m";
    static public String T_RESET     = "\u001B[39m";
    static public String BG_BLACK    = "\u001B[40m";
    static public String BG_RED      = "\u001B[41m";
    static public String BG_GREEN    = "\u001B[42m";
    static public String BG_YELLOW   = "\u001B[43m";
    static public String BG_BLUE     = "\u001B[44m";
    static public String BG_MAGENTA  = "\u001B[45m";
    static public String BG_CYAN     = "\u001B[46m";
    static public String BG_WHITE    = "\u001B[47m";
    static public String BG_RESET    = "\u001B[49m";
    static public String T_BG_RESET  = "\u001B[00m";
    static public String T_BLINK     = "\u001B[05m";
    static public String T_BLINK_OFF = "\u001B[25m";

    static private String color(String color, String str) {
        return color + str + T_BG_RESET;
    }

    static public String blink(String str) {
        return T_BLINK + str + T_BLINK_OFF;
    }

    static public String black(String str)   { return color(T_BLACK, str); }
    static public String red(String str)     { return color(T_RED, str); }
    static public String green(String str)   { return color(T_GREEN, str); }
    static public String yellow(String str)  { return color(T_YELLOW, str); }
    static public String blue(String str)    { return color(T_BLUE, str); }
    static public String magenta(String str) { return color(T_MAGENTA, str); }
    static public String cyan(String str)    { return color(T_CYAN, str); }
    static public String white(String str)   { return color(T_WHITE, str); }

}
