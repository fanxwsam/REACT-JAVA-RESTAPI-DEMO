/**
 * MessageModel is the data model of this message application.
 * @author Edualex
 * Updated by Sam(fanxwsam@gmail.com), Jan 12 2019
 */

package challenge;

import java.util.Comparator;

/**
 * Data model of this message application
 * This class implements a method Comparator which can be used to sort messages in a List.
 */
public class MessageModel {
	private final long id;
	private final String message;

	public MessageModel(long id, String message) {
		this.id = id;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String toString() {
		return this.id + ", " + this.message;
	}

	
	/**
	 * Comparator for sorting by message id in a list   
	 */
	public static Comparator<MessageModel> idDesc = new Comparator<MessageModel>() {
        //The method for comparing
		public int compare(MessageModel s1, MessageModel s2) {

			long id1 = s1.getId();
			long id2 = s2.getId();

			//For descending order
			return (int) (id2 - id1);
		}
	};
}