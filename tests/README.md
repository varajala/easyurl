# easyurl

A simple command line program to send HTTP get requests, made with Java.


This program was made for my university's programming course.
It's main purpose is to provide a program to automate resource downloading from the web.

### Testing

If you want to extend this project, here are some testing tools to support it.
There is a small but extensible server and a main script written in Python to test
the application as a whole. They have some dependencies:

    - Python 3.7+
    - Flask
    - Microtest

Check the Python version with:

Linux, Mac:
```shell
$ python3 --version
```

Windows:
```shell
$ python --version
```

To install flask, run the following command:

Linux, Mac:
```shell
$ pip3 install flask
```

On Windows: 
```shell
$ pip install flask
```
Microtest is a personal project of mine, which you can get from [here](https://github.com/varajala/microtest).
It's a very minimalistic unit testing library.

To run the tests, launch the test server and after it is running execute the main test script.
