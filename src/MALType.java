/**
 * Created by barracuda on 20.09.16.
 */
public enum MALType {
    UNKNOWN(0, "Unknown", "Unknown"),
    TV(1, "TV", "Manga"),
    OVA(2, "OVA", "Novel"),
    MOVIE(3, "Movie", ""),
    SPECIAL(4, "Special", ""),
    ONA(5, "ONA", "");

    private final String animeName;
    private final String mangaName;

    MALType(int code, String animeName, String mangaName) {
        this.code = code;
        this.animeName = animeName;
        this.mangaName = mangaName;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return MALSettings.isAnime()?animeName:mangaName;
    }

    private final int code;

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
