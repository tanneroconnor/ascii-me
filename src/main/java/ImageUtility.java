import java.awt.image.BufferedImage;

public class ImageUtility {

    public static BufferedImage toGrayScale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayscale = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb   = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red   = (rgb >> 16) & 0xFF;
                int green = (rgb >>  8) & 0xFF;
                int blue  =  rgb        & 0xFF;

                int gray  = (red + green + blue) / 3;

                rgb = alpha << 24 | gray << 16 | gray << 8 | gray;
                grayscale.setRGB(x, y, rgb);
            }
        }
        return grayscale;
    }

}
