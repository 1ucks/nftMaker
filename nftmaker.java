import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
public class nftmaker{
	public static void main(String[] args) throws IOException {
		//declarations
		String randLine = ""; 

		
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
		randLine = Files.readAllLines(Paths.get("words.txt")).get(lineNum);
		System.out.println(randLine);
	
	
	
	try {
        // Makes a new robot
        Robot robot = new Robot();
		StringSelection selection = new StringSelection(randLine);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
      } catch (Exception e) {
      }
	  
}}