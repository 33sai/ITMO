# Music Band Manager
 
A TCP client-server application for managing a collection of music bands, backed by PostgreSQL.

![](https://user-images.githubusercontent.com/74038190/225813708-98b745f2-7d22-48cf-9150-083f1b00d6c9.gif)
## Overview
 
The server maintains a live in-memory collection of `MusicBand` objects loaded from a PostgreSQL database and accepts commands from connected clients over TCP. The client provides a console interface for the user to interact with the collection in real time.


## Getting Started
 
**Requirements:** Java 17+, Maven, PostgreSQL
 
```bash
# Clone the repository and navigate to the subdirectory
git clone https://github.com/33sai/ITMO.git

# Build
mvn clean package
 
# Configure the database connection
# Edit DbConfig.java with your PostgreSQL credentials
 
# Start the server
java -jar [server].jar [port]
 
# In a separate terminal — start the client
java -jar [client].jar [host] [port]
