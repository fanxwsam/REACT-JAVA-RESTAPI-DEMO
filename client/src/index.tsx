/**
 * @file The unique Typescript source file for this application.
 *       It can be divided to be several TSX source file for a better design.
 * @author Sam Fan(fanxwsam@gmail.com)
 * @since Jan 12 2019
 * @required React, Node and Typescript environment
 */

import * as React from "react";
import * as ReactDOM from "react-dom";
import * as request from "request";

import "./styles/style.css";
import { string } from "prop-types";


/**
 * @description Interface IState, The state of MessageApp.
 * @param {string} currentMessage - When add a new message, set this property.
 * @param {Array<IMessage>} messages - Message List of messages.
 * @param {string} ret - Returned hints when maintain the message list.
 */
interface IState {
    currentMessage: string;
    messages: Array<IMessage>;
    ret: string;
}


/**
 * @description Interface IMessage, The message infomation of one message item.
 * @param {number} id - The number of each message, generated automatically from time stamp.
 * @param {string} message - The content of each message.
 */
interface IMessage {
    id: number;
    message: string;    
}


/**
 * @class The main component of React in this application to process Message List.
 *        It includes original message list from backend system, adding new messages to the list, deleting and viewing messages.
 * @extends React.Component
 * @constructor - Init a message list through calling list init function
 * @function 
 * @param 
 * @param 
 */
class MessageApp extends React.Component<{}, IState> {

    //The constructor
    constructor(props: {}) {
        super(props);

        //Init all the display items' value of the application
        this.state = {
            currentMessage: "", 
            messages: [],
            ret: ""
        }

        //Init the default message list
        this.initMsgListService();        
    }


     /**
     * @description By using simple request to invoke backend rest API in order to retrieving default message list.
     *              Get the list, init the application's list.
     * @returns {async|void}
     */
    async initMsgListService() {
        request.get('http://localhost:8080/api/message/',  ( response: any, error: any, body: any)=>{
            
            //Check whether retrieving data from backend API correctly
            if(body != undefined){
                //Parse returned data and init the message list
                let msgs: Array<IMessage> = JSON.parse(body.toString()); 
                this.setState({messages: msgs});
            }else{  //If cannot get data from backend API, set the hints
                this.setState({ret: "Query Failed. Cannot connect to the API server. Please call: Sam, fanxwsam@gmail.com."});
            }                    
        });    
    }


    /**
     * @description By using simple request to invoke backend rest API in order to delete one message on the server.
     * @param {number} id - The id of the deleted message           
     * @returns {async|void}
     */
    async deleteMsgService(id: number) {
        request.delete('http://localhost:8080/api/message/'.concat(id.toString()),  ( response: any, error: any, body: any)=>{
            
            //Check whether retrieving data from backend API correctly
            if(body != undefined){
                let ret: string = JSON.parse(body.toString()); 
                
                //According to the returned value of backend API, provide hints.
                if(ret == "100"){
                    this.setState({ret: "Delete Successfully."});
                }
                else{
                    this.setState({ret: "Delete Failed."});
                }
            }
            else{  //If cannot get any data from backend API, set the hints
                this.setState({ret: "Delete Failed. Cannot connect to the API server. Please call: Sam, fanxwsam@gmail.com."});
            }
        });     
    }


    /**
     * @description By using simple request to invoke backend rest API in order to retrieve one message from the server.
     * @param {number} id - The id of the message
     * @returns {async|void}
     */
    async getMsgService(id: number){
        request.get('http://localhost:8080/api/message/'.concat(id.toString()),  ( response: any, error: any, body: any)=>{
        
        //Check whether retrieving data from backend API correctly
        if(body != undefined){
                let msg: IMessage = JSON.parse(body.toString()); 
                this.setState({ret: "The message details from API call: id - ".concat(id.toString()).concat(", message - ").concat(msg.message)});
            }else{  //If cannot get data from backend API, set the hints
                this.setState({ret: "Query Failed. Cannot connect to the API server. Please call: Sam, fanxwsam@gmail.com."});
            }
        });     
    }


    /**
     * @description By using simple request to invoke backend rest API in order to add one message to the server.
     * @param {IMessage} msg - The added message
     * @returns {async|void}
     */    
    async addMsgService(msg : IMessage){ 
        var request = require("request"); 

        //POST method to add one message to the server 
        request({
                uri: "http://localhost:8080/api/message/",
                method: "POST",
                form: {
                    id : msg.id,
                    message: msg.message,
                    headers:{'Content-Type':"application/x-www-form-urlencoded"}            
                }
            }, ( response: any, error: any, body: any)=>{
                    //Check whether retrieving data from backend API correctly
                    if(body != undefined){
                        let ret: string = JSON.parse(body.toString()); 
             
                        //According to the returned value of backend API, provide hints.
                        if(ret == "100"){
                            this.setState({ret: "Add Successfully."});
                        }
                        else{
                            this.setState({ret: "Add Failed."});
                        }   
                    }else{  //If cannot get any data from backend API, set the hints
                        this.setState({ret: "Add Failed. Cannot connect to the API server. Please call: Sam, fanxwsam@gmail.com."});
                    }
                });
        }


    /**
     * @description Add one message on the page and invoke addMsgService() to add message on the backend server.
     * @fires _timeInMilliseconds(), addMsgService(msg)
     * @param {React.FormEvent<HTMLFormElement>} e - The adding event
     * @returns {void}
     */   
    public addMessage(e: React.FormEvent<HTMLFormElement>): void {

        //Prevent adding the default or empty values
        e.preventDefault();
        
        //Generate an id by using time stamp funciton
        let newID : number = this._timeInMilliseconds();

        //set the value of new message
        var msg = {
            id: newID,
            message: this.state.currentMessage
        }

        //Invoke adding message service
        this.addMsgService(msg);        

        //Update message list on the page
        this.setState({
            currentMessage: "",
            messages: [
                {
                    id: newID,
                    message: this.state.currentMessage
                },
                ...this.state.messages                
            ]
        })
    }


    /**
     * @description Delete one message on the page and invoke deleteMsgService(id) to delete message from the backend server.
     * @fires deleteMsgService(id)
     * @param {number} id - The id of the message
     * @returns {void}
     */   
    public deleteMessage(id: number): void {

        //Delete from the message list from the web page
        const messages: Array<IMessage> = this.state.messages.filter(
            (msg: IMessage) => msg.id !== id
        );
        
        this.setState({ messages });

        //Delete message from the backend server
        this.deleteMsgService(id);
    }


    /**
     * @description A method by using time stamp to generate a unique number, used to represent unique message id.
     * @returns {number}
     */   
    private _timeInMilliseconds(): number {
        const date: Date = new Date();
        return date.getTime();
    }


    /**
     * @description From JSX.Element[], render the message list.
     */   
    public  renderMessageList(): JSX.Element[] {     
        //Construct the message list
        return this.state.messages.map((msg: IMessage, index: number) => {           
            return (                
                        <div key={msg.id} className="table-row-group">  
                            <ul className="table-row">  
                                <li className="table-cell">{msg.id}</li>  
                                <li className="table-cell"><a href="#" onClick={() => this.getMsgService(msg.id)}>{msg.message}</a></li>  
                                <li className="table-cell"><button onClick={() => this.deleteMessage(msg.id)}>Delete</button></li>  
                            </ul> 
                        </div>
            )
        })
    }


    /**
     * @description From JSX.Element[], render the adding message form and call renderMessageList() to render message list.
     */       
    public render(): JSX.Element {
        return (
            <div>
                <h1> Working React App - Edualex Message List</h1>
                
                {/*Message adding and submit form. */}
                <form onSubmit={(e) => this.addMessage(e)}>
                    <input 
                        type="text"
                        className="msg-input"
                        placeholder="Add a Message Here." 
                        value={ this.state.currentMessage } 
                        onChange={e => this.setState({ currentMessage: e.target.value })}/>
                    <button 
                        type="submit"
                        disabled={ this.state.currentMessage == ""}
                    >Add Message
                    </button>
                </form>
                
                {/*The message list. */}
                <section>{ this.renderMessageList() }</section>
                
                <br/>

                {/*The hints desplayed here when maintain the message list. */}
                <div id="ret" className="opt-hint">{this.state.ret}</div>
            </div>
        )
    }
}

const ROOT = document.getElementById('root') as HTMLElement;

ReactDOM.render(<MessageApp />, ROOT);