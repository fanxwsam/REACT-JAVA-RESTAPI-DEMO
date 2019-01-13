# Edalex Developer Challenge

## Overview

The answer has developed a simple message board web app. It consists of two parts:

* A [React](https://reactjs.org/) front-end written in [Typescript](https://www.typescriptlang.org/)
* A [Spring Boot](https://spring.io/projects/spring-boot) REST API back-end written in Java

To test the challenge answer and check whether it meets the requirements or not.
Before starting, make sure you can successfully start the server and client as detailed in the 'Quick start'.

If you have any questions, please kindly contact with Sam Fan(fanxwsam@gmail.com, 0431288362)

## Quick start

First, it is assumed you have the following items installed:

* JDK 8
* NodeJS / NPM

### Server

Go into the `server` folder and use the Gradle wrapper to make sure all is working. On Linux and
MacOS that will look like:

    ./gradlew bootRun

On Windows:

    gradlew.bat bootRun

Compared with the original gradle configuration(in build.gradle), I have added one dependency(compile group: 'org.json', name: 'json', version: '20180130') for JSON data processing,
just build the source, and then complete a server start-up automatically by using the command above.

At which point you could be able to confirm all is working by going to
<http://localhost:8080/api/message>. That will return you a JSON response with the original four messages which are retrieved from file messages.json located in directory '/src'.

### Client

First, Go into the `client` folder and install the Node dependencies:

    npm install

Then start the local dev server/build environment:

    npm run start

After a few moments, you could be able to visit the client in your browser at:
<http://localhost:3000/>.


## Meeting the Requirements

The completed challenge includes:

* Display a list of messages ordered with the most recent at the top
* Allow a user to add a message
* Allow a user to remove a message
* View a single message (i.e. a permalink)

### User Interface

I just deliver a bare bones, plain HTML UI. I will try Material-UI next week.

### Data Persistence

I save data by using a JSON file called message.json which is located in directory '/src' for
persisting the data.

### Something needs to be mentioned

In only three days, I picked up some related skills of react, node, typescript and gradle. I installed a whole development environment by myself, 
and used VSCode for Typescript, Eclipse for Spring Framework to complete the task.

Thanks for your time.

Enjoy it :)