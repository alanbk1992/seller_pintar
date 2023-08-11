package project.kimora.sellerpintar.models;
public class ModelHome {

    private int homeImage;
    private String homeTitle;


    public ModelHome(int homeImage, String homeTitle) {
        this.homeImage = homeImage;
        this.homeTitle = homeTitle;

    }

    public ModelHome() {
    }

    public int getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(int homeImage) {
        this.homeImage = homeImage;
    }

    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }


}

