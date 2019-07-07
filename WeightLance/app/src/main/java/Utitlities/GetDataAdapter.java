package Utitlities;

public class GetDataAdapter {

    public static int flag = 0;
    public String ImageServerUrl;
    public String ImageTitleName;
    public String ExcerciseTime;

    public String getImageServerUrl() {
        return ImageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.ImageServerUrl = imageServerUrl;
    }

    public String getExerciseTime() {
        return ExcerciseTime;
    }

    public void setExcerciseTime(String excerciseTime) {
        this.ExcerciseTime = excerciseTime;
    }

    public String getImageTitleName() {
        return ImageTitleName;
    }

    public void setImageTitleNamee(String Imagetitlename) {
        this.ImageTitleName = Imagetitlename;
    }

}