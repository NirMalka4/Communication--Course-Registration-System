#ifndef CONNECTION_HANDLER__
#define CONNECTION_HANDLER__
                                           
#include <string>
#include <iostream>
#include <vector>
#include <boost/asio.hpp>
#include "unordered_map"
#include "../include/Encoder.h"
#include "../include/Decoder.h"

using boost::asio::ip::tcp;
using std::string;
using std::unordered_map;
using std::vector;

class ConnectionHandler {

private:
	const std::string host_;
	const short port_;
	boost::asio::io_service io_service_;   // Provides core I/O functionality
	tcp::socket socket_;
	Encoder _encoder;
	Decoder _decoder;

public:
    ConnectionHandler(std::string host, short port);
    virtual ~ConnectionHandler();
 
    // Connect to the remote machine
    bool connect();
 
    // Read a fixed number of bytes from the server - blocking.
    // Returns false in case the connection is closed before bytesToRead bytes can be read.
    bool getBytes(char bytes[], unsigned int bytesToRead);
 
	// Send a fixed number of bytes from the client - blocking.
    // Returns false in case the connection is closed before all the data is sent.
    bool sendBytes(const char bytes[], unsigned int bytesToWrite);
	
    // Read an ascii line from the server
    // Returns false in case connection closed before a newline can be read.
    bool getLine(std::string& line);
	
	// Send an ascii line from the server
    // Returns false in case connection closed before all the data is sent.
    bool sendLine(std::string& line);
 
    // Get Ascii data from the server until the delimiter character
    // Returns false in case connection closed before null can be read.
    bool getFrameAscii(std::string& frame, char delimiter);
 
    // Send a message to the remote host.
    // Returns false in case connection is closed before all the data is sent.
    bool sendFrameAscii(const std::string& frame, char delimiter);
	
    // Close down the connection properly.
    void close();
    // Encode keyboard input into UTF8 chars array(equivalent to uint8_t data type)
     string encode(const string&);
     //Decode bytes array received from the server into ASCI string
     //Update thread's(IO,Socket) termination flag
     string determineResponseType(char*,bool&,unsigned short&);
}; //class ConnectionHandler
 
#endif