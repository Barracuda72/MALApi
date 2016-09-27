/**
 * Created by barracuda on 27.09.16.
 */
public class MALException extends Exception {
    private String username;

    public MALException(String u) {
        username = u;
    }

    public String toString() {
        return "Error authorizing user "+username;
    }
}
