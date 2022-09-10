import java.io.IOException;
import java.awt.AWTException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class nftmaker{
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
		Scanner in = new Scanner(System.in);
		
		//declarations
		String chromePath = "";
		boolean sCPU = false;
        Robot robot = new Robot();
        int winX = 1100;
        int winY = 200;
        String[] dataArray;

	 	if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
			chromePath = "C:\\progra~2/Google/Chrome/Application/chrome.exe";
			System.out.println("Detected windows");
			System.out.print("Are you on a school computer?(y/n) ");
			String schoolComputer = in.nextLine();	
			if(schoolComputer.toLowerCase().startsWith("y")) {
				sCPU = true;
			}
			else {
				System.out.print("Who's computer are you on? (Kieran's, lux's, type 'add' to use a different computer) ");
				String cpUser = in.next();
				if(cpUser.toLowerCase().startsWith("k")) {
					//KIERAN ADD YOUR IMAGE COORDS HERE:
					winX = 1100;
					winY = 200;
				}
				else if(cpUser.toLowerCase().startsWith("l")){
					winX = 1000;
					winY = 200;
				}
				else {
					System.out.print("Enter the x value for a selected image in a google tab: ");
					winX = in.nextInt();
					System.out.print("Enter the y value for a selected image in a google tab: ");
					winY = in.nextInt();
				}
			}
			
		}else if(System.getProperty("os.name").toLowerCase().startsWith("linux")){
			chromePath = "/opt/google/chrome/chrome";
			System.out.println("Detected linux");
			winX = 1100;
			winY = 200;
			
		}else{
			System.out.print("Please enter the location of chrome.exe on your computer: ");
			chromePath = in.nextLine();
			System.out.print("Enter the x value for a selected image in a google tab: ");
			winX = in.nextInt();
			System.out.print("Enter the y value for a selected image in a google tab: ");
			winY = in.nextInt();
		}
		
        
		
		//counts number of lines in da file
		long count = 0;

	    // make a connection to the file
	    Path file = Paths.get("words.txt");
	    // read all lines of the file
		count = Files.lines(file).count();
		
		
//num of images in the pic
System.out.print("How many images do you want to use: ");
int numIm = in.nextInt();

dataArray = new String[numIm];

for(int i = 0; i < numIm; i++) {
		String randomLine = getRandomWord(count);
        System.out.println(randomLine);

	
	try {
		//Sets url to go to google images
		StringSelection selection = new StringSelection("google.com/search?q="+randomLine+"&tbm=isch");
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//copies the word
		clipboard.setContents(selection, selection);
		
		
		
		
		//KIERAN PUT ALL OF THE CODE BELOW INTO A FUNCTION IF YOU CAN SO WE CAN LOOK UP MULTIPLE IMAGES
		//opens google(hopefully)
		getsToGoogleAndPastes(sCPU, chromePath, robot);
			
		if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			//selects first image on school cpu
			
			robot.delay(2500);
			robot.mouseMove(0, 0);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(200);
			robot.keyPress(122);
			robot.keyRelease(122);
			robot.delay(300);
			
			//copies address of the selected image
			schoolCopyAddress(winX, winY);
			
			//gets the image address from the clipboard
			String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor); 
			
			//catches the weird image address copy thing
			while(data.toLowerCase().startsWith("data")) {
				System.out.println("Image adress invalid, selecting different image");
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				robot.delay(100);
				
				//copies address of the selected image
				schoolCopyAddress(winX, winY);
				
				//Reassigns data
				data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor); 
				
			}
			
			//closes current window
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_W);
			robot.delay(200);
			
			//sticks the address in an array
			dataArray[i] = data;
			System.out.println(data);
	        
	        
		}	        
          
		
		else if(System.getProperty("os.name").toLowerCase().startsWith("linux")){
			//Need to still write code for this.
		}
		
		//prints created image NEEDS FIXING FOR NUMBERS DIFFERENT THAN 2
		URL url = new URL(dataArray[0]);
        BufferedImage im = ImageIO.read(url);
        URL url2 = new URL(dataArray[1]);
        BufferedImage im2 = ImageIO.read(url2);
        Graphics2D g = im.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g.drawImage(im2, (im.getWidth()-im2.getWidth())/2, (im.getHeight()-im2.getHeight())/2, null);
        g.dispose();

        display(im);
		//below code saves the first nft into a file called sample_output.jpeg, removed bcuz gets anoying when pushing
        //ImageIO.write(im, "jpeg", new File("sample_output.jpeg"));
		
      } catch (Exception e) {
      }}
	  
}

//methods KIERAN ADD YOUR FUNCTIONS DOWN HERE
public static void display(BufferedImage image) {
	//Prints the created image
    JFrame f = new JFrame("aNFT");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(new JLabel(new ImageIcon(image)));
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);
}
public static void schoolCopyAddress(int x, int y) throws AWTException {
	//copies the selected image's address
    Robot robot = new Robot();
	robot.mouseMove(x, y);
	robot.delay(400);
	//copies image address
    robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
    robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
    robot.delay(400);
    robot.keyPress(KeyEvent.VK_UP);
    robot.keyRelease(KeyEvent.VK_UP);
    robot.keyPress(KeyEvent.VK_UP);
    robot.keyRelease(KeyEvent.VK_UP);
    robot.keyPress(KeyEvent.VK_UP);
    robot.keyRelease(KeyEvent.VK_UP);
	robot.keyPress(KeyEvent.VK_ENTER);
	robot.keyRelease(KeyEvent.VK_ENTER);
	robot.delay(400);
	
	
	
}
public static String getRandomWord(long count) {
	//randomly finds a line to read for a word
    String randLine = "";
	int lineNum = (int) (Math.random()*count)+1;
    try (Stream<String> lines1 = Files.lines(Paths.get("words.txt"))) {
        randLine = lines1.skip(lineNum).findFirst().get();
    } catch (Exception e) {
	      e.getStackTrace();
	    }

	return randLine;
}
public static void getsToGoogleAndPastes(boolean sCPU, String chromePath, Robot robot) throws IOException, AWTException {

	if(!sCPU) {
		Runtime run = Runtime.getRuntime();
		run.exec(chromePath);
		if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
			robot.delay(500);
		}
		else{
			robot.delay(5000);
		}
		
		
	}else {
			//opens google on the restricted school cpus
			robot.keyPress(KeyEvent.VK_WINDOWS);
			robot.keyRelease(KeyEvent.VK_WINDOWS);
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(100);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

		}
		//pastes
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(100);
		robot.keyPress(10);
		robot.keyRelease(10);

}
}
