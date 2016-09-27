import org.omg.CORBA.BAD_CONTEXT;

/**
 * Created by barracuda on 20.09.16.
 */
public enum MALScore {
    UNKNOWN(0, "Unknown"),
    APPALLING(1, "Appaling"),
    HORRIBLE(2, "Horrible"),
    VERYBAD(3, "Very Bad"),
    BAD(4, "Bad"),
    AVERAGE(5, "Average"),
    FINE(6, "Fine"),
    GOOD(7, "Good"),
    VERYGOOD(8, "Very Good"),
    GREAT(9, "Great"),
    MASTERPIECE(10, "Masterpiece");

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private final int code;
    private final String name;

    MALScore(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MALScore findByKey(int i) {
        MALScore[] testEnums = MALScore.values();
        for (MALScore testEnum : testEnums) {
            if (testEnum.code == i) {
                return testEnum;
            }
        }
        return UNKNOWN;
    }

    public String toString() {
        return getName();
    }
}
