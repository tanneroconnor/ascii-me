
public class Pixel {
    private final int greyValue;

    public Pixel(int rgb) {
        int red   = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8)  & 0xFF;
        int blue  =  rgb        & 0xFF;
        this.greyValue  = (red + green + blue) / 3;
    }

    public int getGreyValue() {
        return greyValue;
    }

}
