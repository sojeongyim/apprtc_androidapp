package xyz.pulse9.sinabro;

import java.util.Random;

class TimelineData {
    String videoUrl=null;
    String videoCode=null;
    // List Draw
    int[] drawables;
    int type=1;
    String cardnewsCode;

    public TimelineData(int[] drawables ) {
        this.drawables=drawables;
        Random rand = new Random();
        this.cardnewsCode= Integer.toString(rand.nextInt(899999) + 100000);
        this.type=2;
    }

    public String getCardnewsCode(){return cardnewsCode;}

    public TimelineData(String videoCode) {
        this.videoCode=videoCode;
        this.videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+videoCode+"\" frameborder=\"0\" allowfullscreen></iframe>";
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int[] getDrawables(){return drawables;}

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getType(){return type;}

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public void setDrawables(int[] drawables) {
        this.drawables = drawables;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCardnewsCode(String cardnewsCode) {
        this.cardnewsCode = cardnewsCode;
    }

    public String getVideoCode() {
        return videoCode;
    }
}
