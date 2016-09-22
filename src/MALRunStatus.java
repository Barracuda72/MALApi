/**
 * Created by barracuda on 20.09.16.
 */
public enum MALRunStatus {
    UNKNOWN(0, "Unknown"),
    ONAIR(1, "Currently Airing"),
    FINISHED(2, "Finished Airing"),
    NOTYET(3, "Not yet aired");

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private final int code;
    private final String name;


    MALRunStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MALRunStatus findByKey(int i) {
        MALRunStatus[] testEnums = MALRunStatus.values();
        for (MALRunStatus testEnum : testEnums) {
            if (testEnum.code == i) {
                return testEnum;
            }
        }
        return UNKNOWN;
    }

    public static MALRunStatus findByValue(String s) {
        MALRunStatus[] testEnums = MALRunStatus.values();
        for (MALRunStatus testEnum : testEnums) {
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
