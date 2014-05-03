CXX = g++ -fPIC
NETLIBS= -lnsl -lpthread

all: projectServer

projectServer: projectServer.o
	$(CXX) -o $@ $@.o $(NETLIBS)

clean:
	rm -f *.o projectServer
