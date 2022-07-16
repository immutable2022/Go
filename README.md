 # Requester
A multi request tool

usage: 
java -jar Requester.jar true address.txt

first parameter: 
    
if true, prints URLs to the console (less efficient CPU usage)

if false, no URLs are printed (better CPU performance)

second parameter:
    
a text file with the sequence of URLs that should be included.
    The URLs should be one per line. For example:
    www.someurl.com
    www.anotherurl.com
    www.onemoreurl.com

A dummy file is located under src/main/resources/address.txt

log files will be created in the same folder where the jar is run.
It will contain the IP address of your machine. Check the log to be sure
your IP address is for example under your VPN or TOR network.
