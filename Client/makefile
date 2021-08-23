# All Targets
CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread

all: bin/BGRSclient 

bin/BGRSclient: bin/BGRSclient.o bin/connectionHandler.o bin/Decoder.o bin/echoClient.o bin/Encoder.o bin/TaskIO.o bin/TaskSocket.o 
	@echo 'Building target: main'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/BGRSclient  bin/BGRSclient.o bin/connectionHandler.o bin/Decoder.o bin/echoClient.o bin/Encoder.o bin/TaskIO.o bin/TaskSocket.o $(LDFLAGS) 
	@echo 'Finished building target: main'
	@echo ' '

#bin/main.o: src/main.cpp
#	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/main.o src/main.cpp

bin/BGRSclient.o: src/BGRSclient.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/BGRSclient.o src/BGRSclient.cpp

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp

bin/Decoder.o: src/Decoder.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/Decoder.o src/Decoder.cpp

bin/echoClient.o: src/echoClient.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/echoClient.o src/echoClient.cpp

bin/Encoder.o: src/Encoder.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/Encoder.o src/Encoder.cpp

bin/TaskIO.o: src/TaskIO.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/TaskIO.o src/TaskIO.cpp

bin/TaskSocket.o: src/TaskSocket.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/TaskSocket.o src/TaskSocket.cpp

clean:
	rm -f bin/*
