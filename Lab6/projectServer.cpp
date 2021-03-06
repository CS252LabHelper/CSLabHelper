//Dylan Smith's Code for CS252 Project6


void child(int sig);
int isNumericAndValid(char * tmp);
void createThreadForEachRequest(int masterSocket);
void dispatchHTTP(int slaveSocket);
void* dispatchHTTP(void* slaveSocket);
void work( int slaveSocket);


int defaultPORT = 39001;
int QueueLength = 20;

const char * usage =
"                                                               \n"
"	projectServer useage:					\n"
"	Made by: Dylan, Alex, Cameron, and Denver		\n"
"	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
"								\n"
"	Type in help to read this message.			\n"
"								\n"
"	Input can include a port number, if a port number is not\n"
"	designated then a default port number will be used.	\n"
"								\n"
"	This program will respond to queries made by an app 	\n"
"	written for android phones. It will send them a text 	\n"
"	file with information on class times and student group	\n"
"	meetings.						\n"
"								\n"
"	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
"								\n"
"								\n";

const char * badInput = 
"								\n"
"	Failure: Invalid port number recieved			\n"
"								\n"
"	Valid Input for first argument is a numeric value:	\n"
"	Greater than 1024, Less than 65536.			\n"
"								\n"
"	Uses Default Port otherwise (Port: 39001)		\n"
"								\n";



#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <pthread.h>
#include <signal.h>
#include <sys/wait.h>
#include <errno.h>
#include <sys/stat.h>
#include <fcntl.h>

int
main( int argc, char ** argv )
{

 	if ( argc == 2 && strcmp(argv[1],"help\0") == 0) {
 		fprintf( stderr, "%s", usage );
 	   	exit( -1 );
  	}
	else if(argc == 2 && (defaultPORT = isNumericAndValid(argv[1])) == -1)
	{
		fprintf( stderr, "%s", badInput );
 	   	exit( -1 );
	}

	
	struct sigaction signalAction;
	signalAction.sa_handler = child;

	
  	// Set the IP address and port for this server
 	struct sockaddr_in serverIPAddress; 
 	memset( &serverIPAddress, 0, sizeof(serverIPAddress) );
  	serverIPAddress.sin_family = AF_INET;
  	serverIPAddress.sin_addr.s_addr = INADDR_ANY;
  	serverIPAddress.sin_port = htons((u_short) defaultPORT);
  
	
  	// Allocate a socket
  	int masterSocket =  socket(PF_INET, SOCK_STREAM, 0);
  	if( masterSocket < 0) 
	{
  		perror("socket");
    		exit( -1 );
  	}

	
	// Set socket options to reuse port. Otherwise we will
  	// have to wait about 2 minutes before reusing the sae port number
  	int optval = 1; 
  	int err = setsockopt(masterSocket, SOL_SOCKET, SO_REUSEADDR,(char *) &optval, sizeof( int ) );
   
	
  	// Bind the socket to the IP address and port
  	int error = bind( masterSocket,(struct sockaddr *)&serverIPAddress,sizeof(serverIPAddress) );
  	if( error )
	{
   		perror("bind");
    		exit( -1 );
  	}
  
  	// Put socket in listening mode and set the 
  	// size of the queue of unprocessed connections
  	error = listen( masterSocket, QueueLength);
  	if( error ) 
	{
    		perror("listen");
    		exit( -1 );
  	}
	

	//Enter into infinate loop, wait till death.
	createThreadForEachRequest( masterSocket );
}

int isNumericAndValid(char * tmp)
{
	char * tmp2;
	int tmp3;

	tmp2 = tmp;

	while(*tmp2 != '\0')
	{
		if(*tmp2 < 48 || *tmp2 > 57)
		{
			return -1;
		}
		tmp2++;
	}

	tmp3 = atoi(tmp);

	if(tmp3 > 65536 || tmp3 < 1024)
	{
		return -1;
	}

	return tmp3;
}

void child(int sig)
{
	while(waitpid(-1, NULL, WNOHANG) > 0); 
}

void createThreadForEachRequest ( int masterSocket)
{
	printf("Here\n");
	while(1)
	{
		printf("making\n");
		struct sockaddr_in clientIPAddress;
    		int alen = sizeof( clientIPAddress );
    		int slaveSocket = accept( masterSocket,(struct sockaddr*) &clientIPAddress,(socklen_t*) &alen);
		printf("making attempt\n");
		pthread_t thread;
		if(slaveSocket >= 0)
		{
			printf("making complete\n");
			pthread_attr_t attr;
			pthread_attr_init(&attr);
			pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
			pthread_create(&thread, &attr, dispatchHTTP, (void*) &slaveSocket);
		}
		else
		{
			close(slaveSocket);
		}
	}
}

void* dispatchHTTP(void* slaveSocket)
{
	dispatchHTTP( *(int *)slaveSocket);
	return NULL;
}


void dispatchHTTP(int slaveSocket)
{
	printf("working\n");
	work(slaveSocket);
	shutdown(slaveSocket,2);
	close(slaveSocket);
}

void work(int slaveSocket)
{
	/* Unknown reliableness
	const int maxChars = 1000;
	char input[1000],input2[1000];
	int length = 0;
	unsigned char next;
	unsigned char last = 0;
	int n;

	int tempi;
	for(tempi = 0; tempi <4;tempi++){
		n = read(slaveSocket, &next, sizeof(next));
	}

	while(length < maxChars &&( n = read( slaveSocket, &next, sizeof(next)))>0)
	{
		if(next == ' ')
		{
			break;
		}
		input[length] = next;
		length++;
		last = next;
	}
	input[length] = '\0';

	length = 0;
	while(length < maxChars &&( n = read( slaveSocket, &next, sizeof(next)))>0)
	{
		input2[length] = next;
		length++;
		if(last == '\r' && next == '\n')
		{
			length--;
			end=true;
			break;
		}
		last = next;
	}
	input2[length] = '\0';

	while(length < maxChars &&( n = read( slaveSocket, &next, sizeof(next)))>0)
	{
		//printf("%i\n", length);
		if(last == '\r' && next == '\n')
			break;
		else if(last == '\r' && next == '\n')
			end = true;
		else
			end = false;
		last = next;
		//length++;
	}
	*/

	/* How recieved messages will appear:

	POST <sp> /<script>?{<var>=<val>&}*{<var>=<val>}<sp> HTTP/1.0 <crlf>
   	{<Other Header Information> <crlf>}*
    	<crlf>
	

	POST<sp>/see-DAY-TIME- HTTP/1.0 <crlf>
	{other <crlf>}*
	<crlf>

	POST<sp>/add-DAY-TIME-NAME-CLASS-ROOM- HTTP/1.0<crlf>
	{other <crlf>}*
	<crlf>

	*/

	const int maxChars = 1000;
	char input1[100],input2[100],input3[100],input4[100],input5[100],input6[100];
	int length = 0;
	unsigned char next;
	unsigned char last = 0;
	int n;
	int tempi;

printf("working attempt\n");

	for(tempi = 0; tempi < 4;tempi++){
		n = read(slaveSocket, &next, sizeof(next));
	}
	
printf("attempt exists\n");

	for(tempi = 0; tempi < 4; tempi++)
	{
		n = read(slaveSocket, &next, sizeof(char));
		input1[tempi] = next;
	}
	input1[tempi] = '\0';
printf("%s\n", input1);
printf("Attempt passed\n");

	read(slaveSocket, &next, sizeof(next));
	
	if(strcmp(input1,"/add\0") == 0)								//see
	{
		tempi = 0;
		next = 0;
		while(next != '-')
		{
			n = read(slaveSocket, &next, sizeof(next));
			input2[tempi] = next;
			tempi++;
		}
		input2[tempi-1] = '\0';	
printf("%s\n",input2);
		tempi = 0;
		next = 0;
		while(next != '-')
		{	
			n = read(slaveSocket, &next, sizeof(next));
			input3[tempi] = next;
			tempi++;
		}
		input3[tempi-1] = '\0';	
printf("%s\n",input3);
		tempi = 0;
		next = 0;
		while(next != '-')
		{
			n = read(slaveSocket, &next, sizeof(next));
			input4[tempi] = next;
			tempi++;
		}
		input4[tempi-1] = '\0';	
printf("%s\n",input4);
		tempi = 0;
		next = 0;
		while(next != '-')
		{
			n = read(slaveSocket, &next, sizeof(next));
			input5[tempi] = next;
			tempi++;
		}
		input5[tempi-1] = '\0';	
printf("%s\n",input5);
		tempi = 0;
		next = 0;
		while(next != '-')
		{
			n = read(slaveSocket, &next, sizeof(next));
			input6[tempi] = next;
			tempi++;
		}
		input6[tempi-1] = '\0';
printf("%s\n",input6);
		bool end = false;

		while(1)
		{
			//printf("yay\n");
			read(slaveSocket, &next, sizeof(next));
			if(end == true && next == '\n' && last == '\r')
			{
				break;
			}
			else if (next == '\n' && last == '\r')
			{
printf("<crlf>\n");
				break;
			}
			else
			{
				end = false;
			}
			last = next;
		}	
	
	}
	else 												//add
	{
		tempi = 0;
		next = 0;
		while(next != '-')
		{
			n = read(slaveSocket, &next, sizeof(next));
			input2[tempi] = next;
			tempi++;
		}
		input2[tempi-1] = '\0';	
printf("%s\n",input2);
		tempi = 0;
		next = 0;
		while(next != '-')
		{
			n = read(slaveSocket, &next, sizeof(next));
			input3[tempi] = next;
			tempi++;
		}
		input3[tempi-1] = '\0';	
printf("%s\n",input3);
		bool end = false;
		while(1)
		{

			read(slaveSocket, &next, sizeof(next));
			if(end == true && next == '\n' && last == '\r')
			{
				break;
			}
			else if (next == '\n' && last == '\r')
			{
				break;
				end = true;
			}
			else
			{
				end = false;
			}
			last = next;
		}
	}

printf("Hello\n");	

	write(slaveSocket,"HTTP/1.1",8);
	write(slaveSocket," ",1);
	write(slaveSocket,"200",3);
	write(slaveSocket," ",1);
	write(slaveSocket,input1,strlen(input1));
	write(slaveSocket," ",1);
	write(slaveSocket,"follows",7);
	write(slaveSocket,"\r\n",2);
	write(slaveSocket,"Server:",7);
	write(slaveSocket," ",1);
	write(slaveSocket,"CS",2);
	write(slaveSocket," ",1);
	write(slaveSocket,"252",3);
	write(slaveSocket," ",1);
	write(slaveSocket,"lab6",4);
	write(slaveSocket,"\r\n",2);
	write(slaveSocket,"\r\n",2);

	/*
	envvars list:		/add-DAY-TIME-NAME-CLASS-ROOM-
		serverTimeVar	input3
		serverDayVar	input2
		serverNameVar	input4
		serverClassVar	input5
		serverLabRoom	input6

	*/

	int ret;
	setenv("serverDayVar",input2,1);
	setenv("serverTimeVar",input3,1);
	int std;
	std = dup(1);
	dup2(slaveSocket,1);
	char cwd[256];
	char * cwdP;
	cwdP = cwd;
	strcpy(getcwd(cwdP,254),cwd);
	
	if(strcmp(input1,"/add") == 0)
	{
		//set envvars and then execute
		setenv("serverNameVar",input4,1);
		setenv("serverClassVar",input5,1);
		setenv("serverLabRoom",input6,1);
		
		ret = fork();			//fork
		if(ret == -1)			
		{}
		else if(ret == 0){		//if ret == 0
			close(std);	
			strcat(cwdP, "/add.sh\0");	
			execvp(cwdP,NULL);	//	execvp
			exit(-1);
		}
		else
		{				//else parent
			wait(NULL);		//	wait(NULL)
		}
		
	}
	strcpy(getcwd(cwdP,254),cwd);
	
	ret = fork();
	if(ret == -1)
	{}
	else if(ret == 0)
	{
		strcat(cwdP, "/see.sh\0");
		close(std);
		execvp("./see",NULL);
		exit(-1);
	}
	else
	{
		wait(NULL);
	}
	dup2(std,1);
	close(std);
	printf("all complete\n");
}

	


