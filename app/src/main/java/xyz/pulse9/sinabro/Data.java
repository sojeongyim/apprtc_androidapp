package xyz.pulse9.sinabro;

class Data {
    String videoUrl=null;
    String videoCode=null;
    // List Draw
    int[] drawables;
    int type=0;

    public Data(int[] drawables ) {
        this.drawables=drawables;
        this.type=1;
    }

    public Data(String videoCode) {
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

    public String getVideoCode() {
        return videoCode;
    }
}
