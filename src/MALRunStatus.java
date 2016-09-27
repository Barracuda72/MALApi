/**
 * Created by barracuda on 20.09.16.
 */
public enum MALRunStatus {
    UNKNOWN(0, "Unknown", "Unknown"),
    ONAIR(1, "Currently Airing", "Publishing"),
    FINISHED(2, "Finished Airing", "Finished"),
    NOTYET(3, "Not yet aired", "Not yet published");

    public int getCode() {
        return code;
    }

    public String getName() {
        return MALSettings.isAnime()?animeName:mangaName;
    }

    private final int code;
    private final String animeName;
    private final String mangaName;


    MALRunStatus(int code, String animeName, String mangaName) {
        this.code = code;
        this.animeName = animeName;
        this.mangaName = mangaName;
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
            if (testEnum.animeName.equals(s) || testEnum.mangaName.equals(s)) {
                return testEnum;
            }
        }
        return UNKNOWN;
    }

    public String toString() {
        return getName();
    }
}
