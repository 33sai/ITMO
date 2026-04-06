# Music Band Manager
 
A TCP client-server application for managing a collection of music bands, built from scratch in Java with no external frameworks.
## Overview
 
The server maintains a live in-memory collection of `MusicBand` objects and accepts commands from connected clients over TCP. The client provides a console interface for the user to interact with the collection in real time.

**Server** — single-threaded, non-blocking I/O using `SocketChannel` and `Selector`. Accepts client connections, deserializes incoming command objects, executes the corresponding operation on the collection, and sends back a serialized response.
 
**Client** — reads commands from the console, validates user input, serializes a typed command object, transmits it to the server, and displays the response. Handles temporary server unavailability gracefully without crashing.


## Getting Started
 
**Requirements:** Java 11+, Maven
 
```bash
# Clone the repository
git clone https://github.com/33sai/ITMO/edit/master/Programming/Labs/Lab6
 
# Build
mvn clean package
 
# Start the server
java -jar [JarName].jar server [port]
 
# In a separate terminal — start the client
java -jar [JarName] client [host] [port]
```
 
