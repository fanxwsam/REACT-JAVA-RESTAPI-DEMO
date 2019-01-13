/**
 * MessageController provides API methods outward.
 * Invokes methods of MessageService to retrieve and update JSON file - message repository
 * @author Edualex 
 * Updated by Sam(fanxwsam@gmail.com), Jan 12 2019
 */

package challenge;

import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Provides Get, Post, Delete services outward
 * Invokes methods of MessageService to retrieve and update JSON file - message repository
 * Property: {MessageService} messageService - used for calling the Jason
 */
@CrossOrigin
@RestController("/api/message")
public class MessageController {
	
	/* By using MessageService, we can retrieve, remove and insert data with JSON file
	 * which is the message repository
	 * To be simple, I do not use Dependency Injection for this property and just create an instance of class MessageService.
	 */ 
    private MessageService messageService = new MessageService();

    
    /**
     * Provides GET method
     * Generate a default message list from repository
     * @return List<MessageModel>, a sorted message list.
     */
    @RequestMapping(method = RequestMethod.GET)
    List<MessageModel> listMessages() {
        
    	//Call getSortedMessageList() to get a sorted message list from MessageService
		List<MessageModel> messages = messageService.getSortedMessageList();

        return messages;
    }

    
    /**
     * Provides DELETE method
     * Delete a message from repository by parameter id
     * @return int, a code represents success or failure
     */
    @DeleteMapping("/api/message/{id}")
    int deleteMessageService( @PathVariable(value = "id") Long id) {
        
    	//Call deleteMessage() to delete a record in repository
        int retCode = messageService.deleteMessage(id);

        return retCode;
    }

    
    /**
     * Provides GET method
     * Retrieve a message from repository by parameter id
     * @return MessageModel, details of the message
     */
	@GetMapping("/api/message/{id}")
    MessageModel getMessageDetails( @PathVariable(value = "id") Long id) {
        
		//Call getMessageDetails() to retrieve a record in repository
        MessageModel msg = messageService.getMessageDetails(id);

        return msg;
    }


    /**
     * Provides POST method
     * Add a new message to repository by parameter id & message
     * @return int, a code represents success or failure
     */
	@RequestMapping(value = "/api/message/", method = RequestMethod.POST)
    public int createMessageService( @RequestParam  long id, String message) {		

        MessageModel msg = new MessageModel(id, message);

        //Call saveMessage() to insert a record into repository
		int retCode = messageService.saveMessage(msg);

        return retCode;		
    }
}