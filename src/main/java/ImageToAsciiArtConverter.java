import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageToAsciiArtConverter {

    private static final char[] CHARACTERS = {' ', '.', ':', '-', '=', '+', '*', '#', '%', '@'};
    private static final double CHARACTER_HEIGHT_COMPENSATION = 2.7;

    // Input Image
    private final BufferedImage image;
    private final int imageWidth;
    private final int imageHeight;
    private final PixelGroup[][] pixelGroups;

    // Ascii Art

    private final int asciiArtWidth;
    private final int asciiArtHeight;

    public ImageToAsciiArtConverter(BufferedImage image, int asciiArtWidth) {
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
        this.asciiArtWidth = asciiArtWidth;
        this.asciiArtHeight = (int) ((asciiArtWidth * imageHeight) / imageWidth / CHARACTER_HEIGHT_COMPENSATION);
        this.pixelGroups = createPixelGroups(asciiArtWidth, asciiArtHeight);
    }

    private PixelGroup[][] createPixelGroups(int artWidth, int artHeight) {
        if (artWidth <= 0 || artHeight <= 0) {
            throw new IllegalArgumentException("Art width and height must be greater than zero. Try a larger number.");
        }
        return new PixelGroup[artHeight][artWidth];
    }

    private void setPixelGroups() {

        int pixelGroupWidth = (int) Math.ceil((double) imageWidth / asciiArtWidth);
        int pixelGroupHeight = (int) Math.ceil((double) imageHeight / asciiArtHeight);

        int lastColumnIndex = asciiArtWidth - 1;
        int lastRowIndex = asciiArtHeight - 1;

        // Iterate over the groups of pixels and create each one
        for (int row = 0; row < imageHeight; row++) {
            for (int column = 0; column < imageWidth; column++) {

                Pixel currentPixel = new Pixel(image.getRGB(column, row));

                int x = Math.min(column / pixelGroupWidth, lastColumnIndex);
                int y = Math.min(row / pixelGroupHeight, lastRowIndex);

                if (pixelGroups[y][x] == null) pixelGroups[y][x] = new PixelGroup();

                pixelGroups[y][x].add(currentPixel);
            }
        }
    }


    public String generateAsciiArt(boolean invert) {
        setPixelGroups();
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < asciiArtHeight; y++) {
            for (int x = 0; x < asciiArtWidth; x++) {
                if (pixelGroups[y][x] == null) {
                    sb.append(" ");
                } else {
                    int greyValue = pixelGroups[y][x].getGrayValue();
                    sb.append(greyValueToChar(greyValue, invert));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void printToConsole(boolean invert) {
        String asciiArt = generateAsciiArt(invert);
        System.out.print(asciiArt);
    }

    public void writeAsciiArtToFile(boolean isInverted, File outputFile)
            throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.print(this.generateAsciiArt(isInverted));

        }
    }

    private Character greyValueToChar(int greyValue, boolean invert) {
        int mappedValue = (greyValue * CHARACTERS.length) / 256;
        return invert ? CHARACTERS[mappedValue] : CHARACTERS[CHARACTERS.length - mappedValue - 1];
    }

}
