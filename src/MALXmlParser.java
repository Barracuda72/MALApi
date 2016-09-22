import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by barracuda on 22.09.16.
 */
public class MALXmlParser {
    private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public static List<MALEntry> parseUserList(String uri) {
        ArrayList <MALEntry> list = new ArrayList<>();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(uri));
            //visit(doc,0);

            Node rootNode = doc.getChildNodes().item(0); // Получаем корневой элемент списка

            NodeList nl = rootNode.getChildNodes(); // Получаем список элементов внутри тэга <myanimelist>
            for (int i = 0; i < nl.getLength(); i++) {
                Node childNode = nl.item(i); // текущий элемент
                if (childNode.getNodeName().equals("anime") ||
                        childNode.getNodeName().equals("manga") ||
                        rootNode.getNodeName().equals("anime") ||
                        rootNode.getNodeName().equals("manga"))
                    list.add(parseNode(childNode,
                            childNode.getNodeName().equals("anime") || rootNode.getNodeName().equals("anime")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static MALEntry parseNode(Node node, boolean isAnime) {
        NodeList list = node.getChildNodes();
        MALEntry entry = null;

        int id = 0;
        String title = "EMPTY";
        String synonims = "NONE";
        MALType type = MALType.UNKNOWN;
        MALRunStatus runStatus = MALRunStatus.UNKNOWN;
        String runStart = "0000-00-00";
        String runEnd = "0000-00-00";
        String imageUri = "https://myanimelist.cdn-dena.com/images/anime/12/21418.jpg";
        String myStart = "0000-00-00";
        String myFinish = "0000-00-00";
        MALMyStatus myStatus = MALMyStatus.PLANNED;
        MALScore myScore = MALScore.APPALLING;
        String synopsis = "NONE";
        long lastUpdated = 1474538118;
        int chapters = 0;
        int volumes = 0;
        int readChapters = 0;
        int readVolumes = 0;
        int episodes = 0;
        int watchedEpisodes = 0;
        
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i);
            String text = childNode.getTextContent();
            int code = 0;
            try {
                code = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                // Ничего не делаем
                code = -1;
            }

            switch (childNode.getNodeName().toLowerCase()) {
                case "series_mangadb_id":
                case "series_animedb_id":
                case "id":
                    id = code;
                    break;

                case "series_title":
                //case "title":
                case "english":
                    title = text;
                    break;

                case "series_synonyms":
                case "synonims":
                    synonims = text;
                    break;

                case "series_type":
                case "type":
                    if (code != -1)
                        type = MALType.findByKey(code);
                    else
                        type = MALType.findByValue(text);
                    break;

                case "series_status":
                case "status":
                    runStatus = MALRunStatus.findByKey(code);
                    break;

                case "series_start":
                case "start_date":
                    runStart = text;
                    break;

                case "series_end":
                case "end_date":
                    runEnd = text;
                    break;

                case "series_image":
                case "image":
                    imageUri = text;
                    break;

                case "synopsis":
                    synopsis = text;
                    break;

                case "my_start_date":
                    myStart = text;
                    break;

                case "my_finish_date":
                    myFinish = text;
                    break;

                case "my_score":
                    myScore = MALScore.findByKey(code);
                    break;

                case "my_status":
                    myStatus = MALMyStatus.findByKey(code);
                    break;

                case "my_last_updated":
                    lastUpdated = code;
                    break;

                case "series_chapters":
                    chapters = code;
                    break;

                case "series_volumes":
                    volumes = code;
                    break;

                case "my_read_chapters":
                    readChapters = code;
                    break;

                case "my_read_volumes":
                    readVolumes = code;
                    break;

                case "series_episodes":
                    episodes = code;
                    break;

                case "my_watched_episodes":
                    watchedEpisodes = code;
                    break;

                default:
                    break;
            }
        }

        if (isAnime)
            return new MALAnime(id, title, synonims, type, runStatus, runStart, runEnd, imageUri, myStart, myFinish,
                    myStatus, myScore, synopsis, lastUpdated, episodes, watchedEpisodes);
        else
            return new MALManga(id, title, synonims, type, runStatus, runStart, runEnd, imageUri, myStart, myFinish,
                    myStatus, myScore, synopsis, lastUpdated, chapters, volumes, readChapters, readVolumes);
    }

    public static void visit(Node node, int level) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i); // текущий нод
            process(childNode, level + 1); // обработка
            visit(childNode, level + 1); // рекурсия
        }
    }

    public static void process(Node node, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print('\t');
        }
        System.out.print(node.getNodeName());
        if (node instanceof Element) {
            Element e = (Element) node;
            // работаем как с элементом (у него есть атрибуты и схема)
        }
        System.out.println();
    }
}
