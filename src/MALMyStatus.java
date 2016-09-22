/**
 * Created by barracuda on 20.09.16.
 */
public enum MALMyStatus {
    UNKNOWN(0, "Unknown"),
    INPROGRESS (1, "In Progress"),
    COMPLETED (2, "Completed"),
    ONHOLD (3, "On Hold"),
    DROPPED (4, "Dropped"),
    PLANNED (6, "Planned");

    MALMyStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MALMyStatus findByKey(int i) {
        MALMyStatus[] testEnums = MALMyStatus.values();
        for (MALMyStatus testEnum : testEnums) {
            if (testEnum.code == i) {
                return testEnum;
            }
        }
        return UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private final int code;
    private final String name;

    public String toString() {
        return name;
    }
}
