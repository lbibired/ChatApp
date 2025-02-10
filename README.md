# Chat Application

## Description

This is a real-time chat application built using **Spring Boot** for the back-end and **React.js** for the front-end. The app supports user authentication, public chatrooms, private messaging, and allows users to search and add other users by their username. It is designed for ease of use, scalability, and a seamless messaging experience.

## Features

- **User Authentication**: Register, log in, and log out securely.
- **Public Chatroom**: Engage with all users in a public space.
- **Private Messaging**: Send direct messages to specific users.
- **User Search and Add**: Search for users by their username and add them to your contact list.
- **Real-Time Communication**: Instant message delivery with WebSockets.
- **Responsive Design**: Optimized for both desktop and mobile devices.
- **Message History**: View past messages in chatrooms.
- **User-Friendly Interface**: Clean, intuitive UI for easy navigation.

## Tech Stack

### Front-end

- **React.js**: For building user interfaces.
- **SockJS**: WebSocket client for real-time communication.
- **STOMP**: Protocol for WebSocket messaging.
- **React Router**: For client-side routing.
- **Axios**: For HTTP requests.
- **Tailwind CSS**: For styling.

### Back-end

- **Spring Boot**: Java framework for web applications.
- **Spring WebSocket**: WebSocket support in Spring.
- **Spring Data JPA**: For database interactions.
- **MySQL**: Relational database for storing user data.
- **Lombok**: For reducing boilerplate code in Java.

### Others

- **Maven**: Build and dependency management tool.
- **Postman**: For API testing and development.

## How to Use

1. **Register**: Create a new account with a unique username and email.
2. **Login**: Enter your credentials to access the application.
3. **Public Chat**: Join the public chatroom to engage in group conversations.
4. **Private Messaging**: Click on a user's name to initiate a private chat.
5. **Search Users**: Use the search feature to find other users by their username and add them to your contacts.

## Setup Instructions

### Prerequisites

Ensure the following are installed on your system:

- **Node.js**: [Download Node.js](https://nodejs.org/)
- **Java JDK**: [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **Maven**: [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL**: [Download MySQL](https://dev.mysql.com/downloads/)

### Front-end Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/chat-application.git
   cd chat-application/frontend

2. Install dependencies
   npm install

3. Start the React development server:
   npm run dev
The app will be running at http://localhost:5173.

   Back-end Setup

1. Navigate to the back-end directory:
   cd ../backend
2. Configure MySQL database:
   1. Create a MySQL database named chat.
    2. Open src/main/resources/application.properties and update the database configurations:


 spring.datasource.url=jdbc:mysql://localhost:3306/chat
 spring.datasource.username=your_mysql_username
 spring.datasource.password=your_mysql_password

 3. Build and run the Spring Boot application:
     mvn spring-boot:run
    The back-end server will be running at http://localhost:8080.

WebSocket Configuration
Ensure the WebSocket connection is set up correctly in your React front-end (App.js or relevant file):

const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

