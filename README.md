# easyurl
A simple command line program to send HTTP get requests, made with Java.


This program was made for my university's programming course for  automating resource downloading from the web.


## Building with Maven:

Compilation:

    mvn compile

Run unit tests:

    mvn test

Build .jar-file:

    mvn package


## Build with Python script:

Download JUnit 5 dependencies:

  - apiguardian-api-1.0.0.jar        
  - jars/junit-jupiter-5.X.Y.jar,
  - junit-jupiter-api-5.X.Y.jar
  - junit-jupiter-engine-5.X.Y.jar
  - junit-jupiter-params-5.X.Y.jar

These can be downloaded from [Maven repositories](https://search.maven.org/), more info in
JUnit [docs](https://junit.org/junit5/docs/current/user-guide/#dependency-metadata).

Save these .jar-files into your machine and insert the install path to the Python script:

```python
JARS_LIB_PATH = '/path/to/jars'
```

Insert the standalone console platform jar name:

```python
JUNIT_JAR_PATH = os.path.join(JARS_LIB_PATH, 'junit-platform-console-standalone-X.Y.Zjar')
```

Remember to change the package versions also:

```python
options.classpath = ('.',
    os.path.join(JARS_LIB_PATH, 'apiguardian-api-1.0.0.jar'),        
    os.path.join(JARS_LIB_PATH, 'jars/junit-jupiter-5.X.Y.jar'),
    os.path.join(JARS_LIB_PATH, 'junit-jupiter-api-5.X.Y.jar'),
    os.path.join(JARS_LIB_PATH, 'junit-jupiter-engine-5.X.Y.jar'),
    os.path.join(JARS_LIB_PATH, 'junit-jupiter-params-5.X.Y.jar'),
    )
```

Run the script:

    python build.py


This will run the JUnit tests and build the .jar-file.


## End-To-End Tests

First create Python virtual environment:

    python -m venv venv

Install required packages:

    ./venv/bin/python -m pip install -r requirements.txt

Run the test script:

    ./venv/bin/python tests/main.py


## Use

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