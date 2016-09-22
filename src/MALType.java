/**
 * Created by barracuda on 20.09.16.
 */
public enum MALType {
    UNKNOWN(0, "Unknown"),
    TV(1, "TV"),
    OVA(2, "OVA"),
    MOVIE(3, "Movie"),
    SPECIAL(4, "Special"),
    ONA(5, "ONA");

    MALType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private final int code;
    private final String name;

    public static MALType findByKey(int i) {
        MALType[] testEnums = MALType.values();
        for (MALType testEnum : testEnums) {
            if (testEnum.code == i) {
                return testEnum;
            }
        }
        return UNKNOWN;
    }

    public static MALType findByValue(String s) {
        MALType[] testEnums = MALType.values();
        for (MALType testEnum : testEnums) {
            if (testEnum.name.equals(s)) {
                return testEnum;
            }
        }
        return UNKNOWN;
    }

    public String toString() {
        return name;
    }
}
