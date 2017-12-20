package itcom.gangstersquirrel.Tools;

/**
 * This class defines the structure of a resolution object
 */
public class ResolutionObject {

    private String aspectRatio;
    private int width;
    private int height;

    /**
     * An object to represent a certain screen resolution
     * @param aspectRatio the aspect ratio as String in the format width:height
     * @param width the width in pixels
     * @param height the height in pixels
     */
    public ResolutionObject(String aspectRatio, int width, int height) {
        this.aspectRatio = aspectRatio;
        this.width = width;
        this.height = height;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
