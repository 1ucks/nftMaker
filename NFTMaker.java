import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
public class NFTMaker{
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
		

	
	try {
        // Makes a new robot
        Robot robot = new Robot();
		//moves to the word & double clicks it
        robot.mouseMove(325, 765);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//waits
		Thread.sleep(50);
		//presses ctrl c to copy
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
      } catch (Exception e) {
      }
	
}}