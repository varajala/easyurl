# easyurl
A simple command line program to send HTTP get requests, made with Java.


This program was made for my university's programming course.
It's main purpose is to provide a program to automate resource downloading from the web.


### Installation

Download the .zip folder, or fork and clone this project to your local machine.
After this you can use the provided .jar package or built the project yourself.

### Use

Basic use from the command line:

```shell
$ java -jar easyurl.jar get [url] [filepath]
```

This will send a HTTP get request to the given URL and write the resulting data to the given filepath.
Currently both of these are required. The URL has t include the http or https scheme.
For example the following command will send a get request to google.com:

```shell
$ java -jar easyurl.jar get https://www.google.com google.html
```

To send requests in a bulk, you can create a simple script file to perform multiple requests in a single command.
For this create a simple script file, where a single line represents similiar command as given from the command line.

**In script file:**
```
get [url] [filepath]
get [url] [filepath]
get [url] [filepath]
get [url] [filepath]
get [url] [filepath]
get [url] [filepath]
...
```

To run this *script* file execute the following command:
```shell
$ java -jar easyurl.jar --file [path_to_script_file]
```
This will execute all the commands inside the file consecutively.