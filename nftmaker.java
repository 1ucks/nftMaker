import java.io.IOException;
import java.awt.AWTException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.Scanner;
public class nftmaker{
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
		Scanner in = new Scanner(System.in);
		
		//declarations
		String randLine = ""; 
		String chromePath = "";


		//determines what kind of computer u are using and sets the chrome file path
		if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
            chromePath = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe";
			System.out.println("Detected windows");
        }else if(System.getProperty("os.name").toLowerCase().startsWith("linux")){
			chromePath = "/opt/google/chrome/chrome";
			System.out.println("Detected linux");
		}else{
			System.out.print("Please enter the location of chrome.exe on your computer: ");
			chromePath = in.nextLine();
		}


		
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
		//randomly finds a line to read for a word
		int lineNum = (int) (Math.random()*count)+1;
        try (Stream<String> lines = Files.lines(Paths.get("words.txt"))) {
            randLine = lines.skip(lineNum).findFirst().get();
        }
        System.out.println(randLine);
	
	
	try {
        // Makes a new robot
        Robot robot = new Robot();
		StringSelection selection = new StringSelection(randLine);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//copies the word
		clipboard.setContents(selection, selection);
		

		//opens google(hopefully)

		Runtime run = Runtime.getRuntime();
		run.exec(chromePath);

		//pastes
		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(100);
		robot.keyPress(10);
		robot.keyRelease(10);

      } catch (Exception e) {
      }
	  
}}