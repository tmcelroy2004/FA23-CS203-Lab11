import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Encrypter {

    private int shift;
    private String encrypted;
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHABETUp = ALPHABET.toUpperCase();
   

    /**
     * Default Constructor
     */
    public Encrypter() {
        this.shift = 1;
        this.encrypted = "";
    }

    /**
     * Non-default Constructor
     * @param s - custom shift amount
     */
    public Encrypter(int s) {
        this.shift = s;
        this.encrypted = "";
    }

    /**
     * Encrypts the content of a file and writes the result to another file.
     *
     * @param inputFilePath      the path to the file containing the text to be encrypted
     * @param encryptedFilePath the path to the file where the encrypted text will be written
     * @throws Exception if an error occurs while reading or writing the files
     */
    public void encrypt(String inputFilePath, String encryptedFilePath) throws Exception {
        // original text is the text from the encrypted file.
    	String originText = readFile(inputFilePath); 
    	String encryptText = "";
    	
    	// for loop to move throught the string and ecrypt the string.
    	for (int i = 0; i < originText.length(); i++)   
        {   
    		char aChar = originText.charAt(i);
    		// if the char is a lowwer case letter.
    		if ( (aChar >= 'a' && aChar <= 'z') && Character.isLowerCase(aChar)) {	
             // find the index of where the character occurs in the alphabet string.
            int position = ALPHABET.indexOf(originText.charAt(i));   
              
             //find the new postion of the encrypted char .
            int encryptPositon = (shift + position) % 26; 
            // new char is created to store the encrypted character.
            char encryptChar = ALPHABET.charAt(encryptPositon);   
              
            //add the new ecrypted char to the string.
            encryptText += encryptChar; 
        }
    		// if the character is a upper case letter.
    		else if ( (aChar >= 'A' && aChar <= 'Z') && Character.isUpperCase(aChar)) {
    			
    		 // find the index of where the character occurs in the alphabet string.
    			char char1 = originText.charAt(i);
    			
    		   // converts the char to a lowwer case char.
    			char1 = Character.toLowerCase(char1);
    			
    		   // find the index of where the character occurs in the alphabet string.
                int position = ALPHABET.indexOf(char1);
                
              //find the new postion of the encrypted char.
                int encryptPositon = (shift + position) % 26;
                
             // new char is created to store the encrypted character.
                char encryptChar = ALPHABET.charAt(encryptPositon);
                
             //add the new ecrypted char to the string. 
                encryptChar = Character.toUpperCase(encryptChar);
                encryptText += encryptChar;
        }
    		// if the char is not a letter.
    		else {
    			// add char to the string
    			encryptText += aChar;
    		} 
          
        // write to the new ecrypted file.  
    	writeFile(encryptText,encryptedFilePath );   
    }
    	
    	
    }

    /**
     * Decrypts the content of an encrypted file and writes the result to another file.
     *
     * @param messageFilePath    the path to the file containing the encrypted text
     * @param decryptedFilePath the path to the file where the decrypted text will be written
     * @throws Exception if an error occurs while reading or writing the files
     */
    public void decrypt(String messageFilePath, String decryptedFilePath) throws Exception {
        
    	// reads the message path file and adds the contents to a string.
    	String originText = readFile(messageFilePath);
    	String decryptText = "";
    	
    	//for loop to move through the string and decrypt it.
    	for (int i = 0; i < originText.length(); i++)   
        {  
    		char aChar = originText.charAt(i);
    		// if the char is a upper case letter.
    		if ((aChar >= 'A' && aChar <= 'Z') && Character.isUpperCase(aChar)  ) {
    			 
    			// converts the char to a lowwer case char.
    			char char1 = (originText.charAt(i));
    			char1 = Character.toLowerCase(char1);
    			
    			//find the index of the encrypted char in the string.
                int postion = ALPHABET.indexOf(char1);   
                  
                //decrypt the char.   
                int decryptPosition = (postion - shift) % 26;   
                  
                 // if the decrypted char goes out of bounds of the string find the correct char. 
                if (decryptPosition < 0){   
                	decryptPosition = ALPHABET.length() + decryptPosition;   
                } 
                
                // create a new char to hold the decrypted char.
                char decryptChar = ALPHABET.charAt(decryptPosition);  
                // make the char uppercase.
                decryptChar = Character.toUpperCase(decryptChar);
                 // add char to the string.  
                decryptText += decryptChar;	
    		}
    		// if the char is a lowwer case letter.
    		else if ( (aChar >= 'a' && aChar <= 'z') && Character.isLowerCase(aChar)) {
    			//find the index of the encrypted char in the string. 
                int position = ALPHABET.indexOf(originText.charAt(i));   
                  
              //decrypt the char.  
                int decryptPosition = (position - shift) % 26;   
                  
             // if the decrypted char goes out of bounds of the string find the correct char.   
                if (decryptPosition < 0){   
                    decryptPosition = ALPHABET.length() + decryptPosition;   
                }  
                
                // create a new char to hold the decrypted char.
                char decryptChar = ALPHABET.charAt(decryptPosition);   
                // add char to the string.
                decryptText += decryptChar;	
    		}
    		// if the char is not a letter.
    		else {
    			// add char to the string.
    			decryptText += aChar;
    		}
        }   
    	//write the new string into the decrypted file path.
    	writeFile(decryptText,decryptedFilePath );
    } 
    
    

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath the path to the file to be read
     * @return the content of the file as a string
     * @throws Exception if an error occurs while reading the file
     */
    private static String readFile(String filePath) throws Exception {
        String message = "";
        // uses try and catch blocks to create a new scanner.
        try (Scanner fileScanner = new Scanner(Paths.get(filePath))) {
        	// while the file has lines to read 
        	while (fileScanner.hasNext()) {
        		//add the file lines to the string.
        		message += fileScanner.nextLine() + "\n";
        		
        	}
        	
        	// catch block to catch exceptions
        } catch (Exception e) {
        	//if an error is caught print out string.
        System.out.println("Error " + e.toString());	
        }
      //return the string holding file text.
        return message;
    }

    /**
     * Writes data to a file.
     *
     * @param data     the data to be written to the file
     * @param filePath the path to the file where the data will be written
     */
    private static void writeFile(String data, String filePath) {
    	  //uses try and catch blocks to create a new printwriter.
    	try(PrintWriter output = new PrintWriter (filePath)) 
    	{
    		//prints the data string into the file and then closes the printwriter. 
    		output.println(data);
    		output.close();
    		// catch block to catch exceptions.
    	} catch (Exception e) {
    		// if an exception is caught print the string.
            System.out.println("Error " + e.toString());	
            }
    	
     
    }

    /**
     * Returns a string representation of the encrypted text.
     *
     * @return the encrypted text
     */
    @Override
    public String toString() {
        return encrypted;
    }
}
