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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        boolean colorFilter = true;
        boolean urlFilter = true;
        boolean con = false;

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
		colorFilter = true;
		String randomLine = getRandomWord(count);
        System.out.println(randomLine);

	
	try {
		//Sets url to go to google images
		StringSelection selection = new StringSelection("google.com/search?q="+randomLine+"&tbm=isch");
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//copies the word
		clipboard.setContents(selection, selection);
		
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
			while(colorFilter) {
				data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor); 
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
				System.out.println(data);
			}
			//checks if the url returns 404
			while(urlFilter) {
				if(check404(data)) {
					//if it returns 404
					System.out.println("Bad url, using different image");
					robot.keyPress(KeyEvent.VK_RIGHT);
					robot.keyRelease(KeyEvent.VK_RIGHT);
					robot.delay(100);
					//copies new image
					schoolCopyAddress(winX, winY);
					//sets new image to data
					data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
					con = true;
				}
				else {
					break;
				}
			}
			if(con) {
				continue;
			}
			
			
			colorFilter = badColorFilter(data);
			//if the selected image is >50% a single color, selects a different image.
			if(colorFilter) {
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				robot.delay(100);
				schoolCopyAddress(winX, winY);
			}
			
			
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
		

		
		
		
		
      } catch (Exception e) {
      }}


	printIm(dataArray);
	
	
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
public static void printIm(String datas[]) throws IOException {
	//sets the first image as the background v
	System.out.println(datas);
	URL url = new URL(datas[0]);
	BufferedImage im = ImageIO.read(url);
	Graphics2D g = im.createGraphics();
	int randomH;
	int randomW;
	int ranDi;
	System.out.println("Background size (" + im.getWidth() + ", " + im.getHeight() + ")");
	for(int i = 1; i< datas.length; i ++) {
		
		double random = (double)(Math.random()*3 + 1);
		double random2 = (double)(Math.random()*3 + 1);
		
		url = new URL(datas[i]);
		BufferedImage im2 = ImageIO.read(url);
		
		//random size
		ranDi = (int) ((Math.random()*2)+2);
		
		Image im3 = im2.getScaledInstance(im.getWidth()/ranDi, im.getHeight()/ranDi, Image.SCALE_DEFAULT);
		
		//random coords
		randomH = (int) (Math.random()*(im.getHeight()-im3.getHeight(null)));
		randomW = (int) (Math.random()*(im.getWidth()-im3.getWidth(null)));
		
		
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		System.out.println("Image scaled down " + ranDi + " times");
		System.out.println("Drawing image at: (" + randomW + ", " + randomH + ")");

		g.drawImage(im3, randomW, randomH, null);
		
		
	}
	g.dispose();
	File file = new File("nft.jpg");
	file.delete();
	ImageIO.write(im, "jpeg", new File("nft.jpg"));
	display(im);
	
	
	
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
public static boolean badColorFilter(String urL) throws IOException, AWTException {
	Robot robot = new Robot();
	
	URL url = new URL(urL);
    BufferedImage im = ImageIO.read(url);
    int imArea = im.getHeight()*im.getWidth();
    int pixelUno = im.getRGB(0, 0);
    Color colorUno = new Color(pixelUno, true);
    int topLeftRGBcombo = colorUno.getRed()+colorUno.getBlue() + colorUno.getGreen();
    int numTL = 1;
    //Reading the image
   for (int y = 0; y < im.getHeight(); y++) {
      for (int x = 0; x < im.getWidth(); x++) {
         //Retrieving contents of a pixel
         int pixel = im.getRGB(x,y);
         //Creating a Color object from pixel value
         Color color = new Color(pixel, true);
         //Retrieving the R G B values
         int red = color.getRed();
         int green = color.getGreen();
         int blue = color.getBlue();
         if((red+green+blue) == topLeftRGBcombo) {
        	 numTL += 1;
         }
      }

   }
	System.out.println("color percent: " + (((double)numTL)/((double)imArea)*100));
   //checks if the the color in the top left of the image is dominant in a certain percent of the image
   if(((double)numTL)/((double)imArea) >= 0.5) {
	   System.out.println("Image has been deemed boring, selecting a different image.");
	   return true;
   }
   else {
	   return false;
   }
   
   
   
	
}

public static boolean check404(String url) throws IOException {
	//checks if the selected image's url 
	Scanner in = new Scanner(System.in);
	URL u = new URL (url);
	HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
	huc.setRequestMethod ("GET");
	huc.connect () ; 
	int code = huc.getResponseCode() ;
	if(code != 200) {
		//TRUE IF 404
		System.out.println("Code returned not 200. Code returned: " + code);
		return true;
	}
	else {
		return false;
	}
	
	
	
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
