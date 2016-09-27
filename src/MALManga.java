/**
 * Created by barracuda on 20.09.16.
 */
public class MALManga extends MALEntry {
    public MALManga(int id, String title, String synonims, MALType type, MALRunStatus runStatus, String runStart, String runEnd,
                    String imageUri, String myStart, String myFinish, MALMyStatus myStatus, MALScore myScore,
                    String synopsis, long lastUpdated,
                    int chapters, int volumes, int readChapters, int readVolumes) {
        super(id, title, synonims, type, runStatus, runStart, runEnd, imageUri, myStart, myFinish, myStatus,
                myScore, synopsis, lastUpdated);
        this.chapters = chapters;
        this.volumes = volumes;
        this.readChapters = readChapters;
        this.readVolumes = readVolumes;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public int getVolumes() {
        return volumes;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public int getReadChapters() {
        return readChapters;
    }

    public void setReadChapters(int readChapters) {
        this.readChapters = readChapters;
    }

    public int getReadVolumes() {
        return readVolumes;
    }

    public void setReadVolumes(int readVolumes) {
        this.readVolumes = readVolumes;
    }

    protected String getMiscInfo()
    {
        return
                        readChapters+"/"+chapters+"<br/>"+
                        readVolumes+"/"+volumes+"<br/>";
    }

    public String toXML()
    {
        return
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
                    "<entry>"+
					"<chapter>"+getReadChapters()+"</chapter>"+
					"<volume>"+getReadVolumes()+"</volume>"+
					"<status>"+getMyStatus().getCode()+"</status>"+
					"<score>"+getMyScore().getCode()+"</score>"+
					"<times_reread></times_reread>"+
					"<reread_value></reread_value>"+
					"<date_start></date_start>"+
					"<date_finish></date_finish>"+
					"<priority></priority>"+
					"<enable_discussion></enable_discussion>"+
					"<enable_rereading></enable_rereading>"+
					"<comments></comments>"+
					"<scan_group></scan_group>"+
					"<tags></tags>"+
					"<retail_volumes></retail_volumes>"+
                    "</entry>";
    }

    private int chapters = 0;
    private int volumes = 0;
    private int readChapters = 0;
    private int readVolumes = 0;
}
