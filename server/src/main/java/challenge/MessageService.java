/**
 * MessageService provides encapsulation methods of JSON file processing.
 * By using org.json.jar, retrieve and update JSON file which is the message repository
 * @author Sam(fanxwsam@gmail.com), Jan 12 2019 
 */

package challenge;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Provides JSON file loading, elements querying, elements removing and inserting
 * Property DATA_FILE_NAME is the location and file name for the repository
 */
public class MessageService 
{
    private final static String DATA_FILE_NAME = "src/messages.json";

    /**
     * Load JSON file and transfer elements to MessageModel objects
     * Generate a default message list from repository
     * @return List<MessageModel>, a sorted message list.
     */
	public ArrayList<MessageModel> getSortedMessageList() {

		//The list for return
		ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();

		//Load JSON file to string
		String jsonStr = readJsonFile();

		try {
			//The whole JSONObject
			JSONObject jsonObject = new JSONObject(jsonStr);

			//Get the messages - JSON Array from the completed JSONObject
			JSONArray jsonArray = jsonObject.getJSONArray("messages");
			
			//Generate message list
			for (int i = 0; i < jsonArray.length(); i++) {
				MessageModel msg = new MessageModel(jsonArray.getJSONObject(i).getLong("id"),
						jsonArray.getJSONObject(i).getString("message"));
				msgList.add(msg);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

        //Sort the message list
		Collections.sort(msgList, MessageModel.idDesc);

		return msgList;
	}

	
    /**
     * Load JSON file and transfer elements to MessageModel objects
     * Generate one message item from repository
     * @return MessageModel, one message
     */
	public MessageModel getMessageDetails(long id) {
		
		//The message for return
		MessageModel msg = null;

		//Load JSON file to string
		String jsonStr = readJsonFile();

		try {
			//The whole JSONObject
			JSONObject jsonObject = new JSONObject(jsonStr);

			//Get the messages - JSON Array from the completed JSONObject
			JSONArray jsonArray = jsonObject.getJSONArray("messages");
			
			//Compare the specific id and retrieve message data, generate the message
			for (int i = 0; i < jsonArray.length(); i++) {
				//Compare the ids
				if (id == jsonArray.getJSONObject(i).getLong("id")){
					msg = new MessageModel(id, jsonArray.getJSONObject(i).getString("message")); 
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		

		return msg;
	}

	
    /**
     * Load JSON file and transfer elements to MessageModel objects
     * Find the specific message and remove it from the JSON repository
     * @return int, a code represents success or failure
     */
	public int deleteMessage(long id) {
		//The default return code
		int retCode = 100;

		//Load JSON file to string
		String jsonStr = readJsonFile();

		//Writer for writing data to file
		BufferedWriter writer = null;
		
		//JSONObject for representing the completed JSONObject
		JSONObject jsonObject = null;

		try {
			try {
				//The whole JSONObject generated from the JSON String
				jsonObject = new JSONObject(jsonStr);

				//Open the JSON file for write data
				writer = new BufferedWriter(new FileWriter(MessageService.DATA_FILE_NAME));

				//Get the messages - JSON Array from the completed JSONObject
				JSONArray jsonArray = jsonObject.getJSONArray("messages");
				
				//Compare the specific message id to all the ids in the array and remove it
				for (int i = 0; i < jsonArray.length(); i++) {
					long msgID = jsonArray.getJSONObject(i).getLong("id");

					//find the right id and remove the object
					if (id == msgID) {
						jsonArray.remove(i);
						break;
					}
				}

			} catch (JSONException e) {
				retCode = -100;
				e.printStackTrace();
			}

			//Write the new JSON String to message file and save
			writer.write(jsonObject.toString());
			writer.flush();
			writer.close();

		} catch (IOException e) {
			retCode = -100;
			e.printStackTrace();
		}

		return retCode;
	}


    /**
     * Load JSON file and transfer elements to MessageModel objects
     * Insert a new specific message and insert it to JSON object
     * Save the the file - the JSON repository
     * @return int, a code represents success or failure
     */
	public int saveMessage(MessageModel msg) {
		//The default return code
		int retCode = 100;
		
		//Load JSON file to string
		String jsonStr = readJsonFile();

		//Writer for writing data to file
		BufferedWriter writer = null;
		
		//JSONObject for representing the completed JSONObject
		JSONObject jsonObject = null;

		try {
			try {
				//The whole JSONObject generated from the JSON String
				jsonObject = new JSONObject(jsonStr);

				//Open the JSON file for write data
				writer = new BufferedWriter(new FileWriter(MessageService.DATA_FILE_NAME));

				//Get the messages - JSON Array from the completed JSONObject
				JSONArray jsonArray = jsonObject.getJSONArray("messages");
				
				//Generate a new JSONObject for the message
				JSONObject newMsgJson = new JSONObject();
				newMsgJson.put("id", msg.getId());
				newMsgJson.put("message", msg.getMessage());
				
				//Add the new message object to message list - JSON Array
				jsonArray.put(newMsgJson);

			} catch (JSONException e) {
				retCode = -100;
				e.printStackTrace();
			}

			//Write the new JSON String to message file and save
			writer.write(jsonObject.toString());
			writer.flush();
			writer.close();

		} catch (IOException e) {
			retCode = -100;
			e.printStackTrace();
		}

		return retCode;
	}

	
    /**
     * Read JSON file and transfer it to a String 
     * @return String, JSON String
     */	
	public String readJsonFile() {
		
		//For the return String. StringBuffer has better performance
		StringBuffer jsonStr = new StringBuffer();
		
		//One line string from the JSON file
		String tmpLine = null;

		//The File reader
		BufferedReader reader = null;

		try {
			//Open the file
			reader = new BufferedReader(new FileReader(MessageService.DATA_FILE_NAME));

			//Read all the lines of the file and append to the String Buffer
			while ((tmpLine = reader.readLine()) != null) {
				jsonStr.append(tmpLine);
			}
			
			//Close the reader
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException el) {
				}
			}
		}

		// return the final JSON String
		return jsonStr.toString();
	}	
}