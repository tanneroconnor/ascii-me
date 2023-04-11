import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "AsciiMe",
        mixinStandardHelpOptions = true,
        version = "AsciiMe 1.0",
        description = "Converts an image into ASCII art.")
public class AsciiMe implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new AsciiMe()).execute(args);
        System.exit(exitCode);
    }

    private static final int DEFAULT_ART_WIDTH = 150;
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String THUMBS_UP_EMOJI = "\uD83D\uDC4D";


    @Parameters(index = "0", description = "Input image file. The image being converted into ASCII art.")
    private File input;

    @Option(names = { "-o", "--output" }, description = "Optional output file path for the ASCII art instead.")
    private File output;

    @Option(names = {"-i", "--invert"}, description = "Invert the brightness / darkness of the ASCII art.")
    private boolean isInverted = false;

    @Option(names = "-w", description = "Width of the output image (default: 150)")
    private int maxWidth = DEFAULT_ART_WIDTH;

    @Override
    public Integer call() {

        try {
            BufferedImage inputImage = readImage(input);
            ImageToAsciiArtConverter asciiArt = new ImageToAsciiArtConverter(inputImage, maxWidth);
            if (output != null) {
                asciiArt.writeAsciiArtToFile(isInverted, output);
                System.out.println(THUMBS_UP_EMOJI + ANSI_GREEN + " SUCCESS ");
                System.out.println(ANSI_RESET + "Your ASCII art was saved to " + output.getAbsolutePath());
            } else {
                asciiArt.printToConsole(isInverted);
            }
        } catch(IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            return 1;
        }

        return 0;
    }

    private BufferedImage readImage(File inputFile) throws IOException {
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Input file not found");
        }

        return ImageIO.read(inputFile);
    }

}
