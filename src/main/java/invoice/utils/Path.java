package invoice.utils;

public class Path {

    public static final String html = "/Users/home/invoice.html";
    public static final String css = "/Users/home/invoice.css";
    public static final String pdf = "/Users/home/invoice.pdf";
    public static final String user = getDownloadPath();
    
    public static String getDownloadPath() {
        String home = System.getProperty("user.home");
        return home+"/Downloads/invoice00.pdf";
    }

}
