package project.kimora.sellerpintar.models;

public class ModelStatisticHome {


    private String title , count;


    public ModelStatisticHome(String count, String title) {
        this.title = title;
        this.count = count;

    }

    public ModelStatisticHome() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}

