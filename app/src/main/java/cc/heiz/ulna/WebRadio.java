package cc.heiz.ulna;

import java.io.Serializable;

public class WebRadio implements Serializable {
    public String title;
    public String url;
    public int image;
    public WebRadio(String title, String url, int image) {
        this.title = title;
        this.url = url;
        this.image = image;
    }
}
