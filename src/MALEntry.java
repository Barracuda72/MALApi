import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by barracuda on 20.09.16.
 */
public abstract class MALEntry {
    public MALEntry(int id, String title, String synonims, MALType type, MALRunStatus runStatus, String runStart, String runEnd,
                    String imageUri, String myStart, String myFinish, MALMyStatus myStatus, MALScore myScore,
                    String synopsis, long lastUpdated) {
        this.id = id;
        this.title = title;
        this.synonims = synonims;
        this.type = type;
        this.runStatus = runStatus;
        this.runStart = runStart;
        this.runEnd = runEnd;
        this.imageUri = imageUri;
        this.myStart = myStart;
        this.myFinish = myFinish;
        this.myStatus = myStatus;
        this.myScore = myScore;
        this.synopsis = synopsis;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynonims() {
        return synonims;
    }

    public void setSynonims(String synonims) {
        this.synonims = synonims;
    }

    public MALType getType() {
        return type;
    }

    public void setType(MALType type) {
        this.type = type;
    }

    public MALRunStatus getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(MALRunStatus runStatus) {
        this.runStatus = runStatus;
    }

    public String getRunStart() {
        return runStart;
    }

    public void setRunStart(String runStart) {
        this.runStart = runStart;
    }

    public String getRunEnd() {
        return runEnd;
    }

    public void setRunEnd(String runEnd) {
        this.runEnd = runEnd;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getMyStart() {
        return myStart;
    }

    public void setMyStart(String myStart) {
        this.myStart = myStart;
    }

    public String getMyFinish() {
        return myFinish;
    }

    public void setMyFinish(String myFinish) {
        this.myFinish = myFinish;
    }

    public MALMyStatus getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(MALMyStatus myStatus) {
        this.myStatus = myStatus;
    }

    public MALScore getMyScore() {
        return myScore;
    }

    public void setMyScore(MALScore myScore) {
        this.myScore = myScore;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    private String addLinebreaks(String input, int maxLineLength) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (lineLen + word.length() > maxLineLength) {
                output.append("<br/>\n");
                lineLen = 0;
            }
            output.append(word+" ");
            lineLen += word.length()+1;
        }
        return output.toString();
    }

    protected abstract String getMiscInfo();

    private String getBgcolor(boolean isSelected) {
        return isSelected ? "#DCDCDC" : "#FFFFFF";
    }

    public String getFullText(boolean isSelected)
    {
        return
                "<html><body bgcolor='"+
                        getBgcolor(isSelected)+"'><h1><b>"+title+"</b></h1><br/>"+
                        "<u>"+type.getName()+", "+runStatus.getName()+"</u><br/>"+
                        "<i>"+myStatus.getName()+"</i><br/>"+
                        getMiscInfo()+
                        "<font color='green'>"+ myScore.getName()+"</font><br/>"+
                        "<h7>"+new Date(lastUpdated*1000)+"</h7>"+
                        "<br/><i>"+addLinebreaks(getSynopsis(), 200)+"</i>"
                        +"</body></html>";
    }

    public abstract String toXML();

    protected int id = 0;
    protected String title = "";
    protected String synonims = "";
    protected MALType type = MALType.UNKNOWN;
    protected MALRunStatus runStatus = MALRunStatus.UNKNOWN;
    protected String runStart = "";
    protected String runEnd = "";
    protected String imageUri = "";
    protected String myStart = "";
    protected String myFinish = "";
    protected MALMyStatus myStatus = MALMyStatus.PLANNED;
    protected MALScore myScore = MALScore.APPALLING;
    protected String synopsis = "";
    protected long lastUpdated = 0;
}
