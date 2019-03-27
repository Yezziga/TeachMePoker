package gui;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
/**
 * Class for saving the game.
 * @author Zederfeldt
 *
 */
public class SaveGame {
	private String filename = "saves/gamesave.dat";
	private LinkedList<?> incomingList = new LinkedList<Object>();
	/**
	 * Constructor that receives an list of data that will be saved
	 * @param incomingList the list that will be saved
	 * @throws NotSerializableException 
	 */
	public SaveGame (LinkedList<?> incomingList) throws NotSerializableException {
		this.incomingList = incomingList;
		this.save();
	}
	/**
	 * Method that writes to the text file for saving an game
	 */
	public void save() throws NotSerializableException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			oos.writeObject(incomingList);
			oos.flush();
			oos.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
