import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by barracuda on 27.09.16.
 */
public class MALHttp {
    private static final String baseAddress = "https://myanimelist.net/";
    private static final String apiUrl = "api/%slist/%s/%d.xml";
    private static final String searchUrl = "api/%s/search.xml?q=%s";
    private static final String userListUrl = "malappinfo.php?u=%s&status=all&type=%s";
    private static final String verifyCredsUrl = "api/account/verify_credentials.xml";

    private static String getType(boolean anime)
    {
        if (anime)
            return "anime";
        else
            return "manga";
    }

    public static String verifyCreds(String user, String password) throws MALException {
        return loadPage(baseAddress+verifyCredsUrl, user, password, "GET");
    }

    public static String loadUserList(String user, String password, boolean anime) throws MALException {
        return loadPage(baseAddress+String.format(userListUrl, user, getType(anime)), user, password, "GET");
    }

    public static void addEntry(String user, String password, MALEntry e) throws MALException {
        boolean anime = e instanceof MALAnime;
        loadPage(baseAddress+String.format(apiUrl, getType(anime), "add", e.getId()), user, password, "POST", e.toXML());
    }

    public static void deleteEntry(String user, String password, MALEntry e) throws MALException {
        boolean anime = e instanceof MALAnime;
        loadPage(baseAddress+String.format(apiUrl, getType(anime), "delete", e.getId()), user, password, "POST");
    }

    public static void updateEntry(String user, String password, MALEntry e) throws MALException {
        boolean anime = e instanceof MALAnime;
        loadPage(baseAddress+String.format(apiUrl, getType(anime), "update", e.getId()), user, password, "POST", e.toXML());
    }

    public static String searchEntry(String user, String password, String query, boolean anime) throws MALException {
        try {
            return loadPage(baseAddress+String.format(searchUrl, getType(anime), URLEncoder.encode(query, "UTF-8")), user, password, "GET");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String loadPage(String urlStr, String user, String password, String method) throws MALException {
        return loadPage(urlStr, user, password, method, null);
    }

    public static String loadPage(String urlStr, String user, String password, String method, String postData) throws MALException {
        System.out.println("Loading "+urlStr);

        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            System.out.println("MalformedUrlException: " + e.getMessage());
            e.printStackTrace();
            return "";
        }

        HttpURLConnection uc = null;
        try {
            uc = (HttpURLConnection)url.openConnection();
            uc.setRequestMethod(method);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
            return "";
        }

        String userpass = user + ":" + password;
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

        uc.setRequestProperty("Authorization", basicAuth);
        InputStream is = null;

        if (postData != null) {
            System.out.println(postData);
            byte[] postDataBytes = ("data="+postData).getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postDataBytes.length;

            uc.setDoOutput( true );
            uc.setInstanceFollowRedirects( false );
            uc.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            uc.setRequestProperty( "charset", "utf-8");
            uc.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            uc.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( uc.getOutputStream())) {
                wr.write(postDataBytes);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        try {
            is = uc.getInputStream();
            //if (uc.getResponseCode() != uc.HTTP_OK)
                //throw new MALException(user);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
            throw new MALException(user);
        }

        BufferedReader buffReader = new BufferedReader(new InputStreamReader(is));
        StringBuffer response = new StringBuffer();

        String line = null;

        do {
            try {
                line = buffReader.readLine();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
                e.printStackTrace();
                return "";
            }
            if (line != null)
                response.append(line);
        } while (line != null);

        try {
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        System.out.println("Response: " + response.toString());
        return response.toString();
    }
}
