 # Requester
A multi request tool

usage: 
java -jar Requester.jar false address.txt

first parameter: 
    
if true, prints URLs to the console (less efficient CPU usage)

if false, no URLs are printed (better CPU performance); you can see what's happening by running htop on the command line.

second parameter:
    
a text file with the sequence of URLs that should be included.
    The URLs should be one per line. For example:
    
    www.someurl.com
    
    www.anotherurl.com
    
    www.onemoreurl.com

An example file is located under resources: https://github.com/immutable2022/Go/blob/master/src/main/resources/address.txt

log files will be created in the same folder where the jar is run.
It will contain the IP address of your machine. Check the log to be sure
your IP address is for example under your VPN or TOR network.


This is meant to be executed on Debian Linux so you can run "htop" on the command line, and you'll see the curl commands being spawned and respective URLs.
