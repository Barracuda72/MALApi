import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.prefs.Preferences;

/**
 * Created by barracuda on 27.09.16.
 */
public class MALSettings {
    private static String user = "movs";
    private static String password = "QmFycmFj";
    private static boolean anime = true;

    private static final String prefStr = "/net/myanimelist/Settings";
    private static final String userNameStr = "username";
    private static final String passwordStr = "password";
    private static final String animeStr = "anime";

    private static byte [] getMac() {
        try {
            return NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte [] getKey(String user) {
        byte[] mac = getMac();
        byte[] keyBytes = new byte[8];

        for (int i = 0; i < 6; i++)
            keyBytes[i] = mac[i];

        byte[] uname = user.getBytes();
        keyBytes[6] = uname[0];
        keyBytes[7] = uname[1];
        return keyBytes;
    }

    private static String encrypt(String user, String password) {
        try {
            byte[] keyBytes = getKey(user);
            byte[] input = password.getBytes();

            SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(Cipher.ENCRYPT_MODE, key/*, ivSpec*/);
            byte[] encrypted = new byte[cipher.getOutputSize(input.length)];
            int enc_len = cipher.update(input, 0, input.length, encrypted, 0);
            enc_len += cipher.doFinal(encrypted, enc_len);

            byte [] out = new byte[enc_len];
            System.arraycopy(encrypted, 0, out, 0, enc_len);

            return Base64.getEncoder().encodeToString(out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String decrypt(String user, String encPass) {
        if (encPass == null || user == null)
            return null;

        try {
            byte[] keyBytes = getKey(user);
            byte[] input = Base64.getDecoder().decode(encPass);

            SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(Cipher.DECRYPT_MODE, key/*, ivSpec*/);
            byte[] decrypted = new byte[cipher.getOutputSize(input.length)];
            int dec_len = cipher.update(input, 0, input.length, decrypted, 0);
            dec_len += cipher.doFinal(decrypted, dec_len);

            byte [] out = new byte[dec_len];
            System.arraycopy(decrypted, 0, out, 0, dec_len);

            return new String(out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void load()
    {
        Preferences prefs = Preferences.userRoot()
        .node(prefStr);

        user = prefs.get(userNameStr, null);
        password = decrypt(user, prefs.get(passwordStr, null));
        anime = prefs.getBoolean(animeStr, true);

        /*String enc = encrypt(user, password);
        System.out.println("Crypted: "+enc);
        System.out.println("Decrypted: "+decrypt(user, enc));*/
    }

    public static void save()
    {
        if (user != null) {
            Preferences prefs = Preferences.userRoot()
                    .node(prefStr);
            prefs.put(userNameStr, user);
            prefs.put(passwordStr, encrypt(user, password));
            prefs.putBoolean(animeStr, anime);

            try {
                prefs.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Settings saved");
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        MALSettings.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        MALSettings.password = password;
    }

    public static boolean isAnime() {
        return anime;
    }

    public static void setAnime(boolean anime) {
        MALSettings.anime = anime;
    }
}
