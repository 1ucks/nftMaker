import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NftMaker{
	public static void main(String[] args) throws IOException {
		//counts number of lines in da file
		long count = 0;
		try {

		      // make a connection to the file
		      Path file = Paths.get("words.txt");

		      // read all lines of the file
		      count = Files.lines(file).count();

		    } catch (Exception e) {
		      e.getStackTrace();
		    }
		//randomly finds a line to look read for a word
		int lineNum = (int) (Math.random()*count)+1;
		String randLine = Files.readAllLines(Paths.get("words.txt")).get(lineNum);
		System.out.println(randLine);

	}
}
