import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.List;

public class PixelGroup {

    private List<Pixel> pixels;

    public PixelGroup() {
        this.pixels = new ArrayList<>();
    }

    public int getGrayValue() {
        int sum = 0;
        for (Pixel pixel : pixels) {
            sum += pixel.getGreyValue();
        }
        return sum / pixels.size();
    }

    public void add(Pixel pixel) {
        pixels.add(pixel);
    }
}
