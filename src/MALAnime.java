/**
 * Created by barracuda on 20.09.16.
 */
public class MALAnime extends MALEntry {
    public MALAnime(int id, String title, String synonims, MALType type, MALRunStatus runStatus, String runStart, String runEnd,
                    String imageUri, String myStart, String myFinish, MALMyStatus myStatus, MALScore myScore,
                    String synopsis, long lastUpdated, int episodes, int watchedEpisodes) {
        super(id, title, synonims, type, runStatus, runStart, runEnd, imageUri,
                myStart, myFinish, myStatus, myScore, synopsis, lastUpdated);
        this.episodes = episodes;
        this.watchedEpisodes = watchedEpisodes;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getWatchedEpisodes() {
        return watchedEpisodes;
    }

    public void setWatchedEpisodes(int watchedEpisodes) {
        this.watchedEpisodes = watchedEpisodes;
    }

    protected String getMiscInfo()
    {
        return
                watchedEpisodes+"/"+episodes+"<br/>";
    }

    public String toXML()
    {
        return
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                        "<entry>"+
                        "<episode >" +getWatchedEpisodes()+ "</episode >"+
                        "<status > "+getMyStatus().getCode()+" </status >"+
                        "<score > "+getMyScore().getCode()+" </score >"+
                        "<storage_type ></storage_type >"+
                        "<storage_value ></storage_value >"+
                        "<times_rewatched ></times_rewatched >"+
                        "<rewatch_value ></rewatch_value >"+
                        "<date_start ></date_start >"+
                        "<date_finish ></date_finish >"+
                        "<priority ></priority >"+
                        "<enable_discussion ></enable_discussion >"+
                        "<enable_rewatching ></enable_rewatching >"+
                        "<comments ></comments >"+
                        "<fansub_group ></fansub_group >"+
                        "<tags ></tags >"+
                        "</entry>";
    }

    private int episodes = 0;
    private int watchedEpisodes = 0;
}
