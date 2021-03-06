from flask import Flask

PORT = 8000

app = Flask(__name__)
@app.route('/resource1')
def resource_1():
    return '<p>RESOURCE1</p>'

@app.route('/resource2')
def resource_2():
    return '<p>RESOURCE2</p>'

@app.route('/resource3')
def resource_3():
    return '<p>RESOURCE3</p>'

@app.route('/resource4')
def resource_4():
    return '<p>RESOURCE4</p>'

@app.route('/404')
def always_404():
    return '<p>Not found</p>', 404


def launch_server():
    try:
        app.run(port=PORT, debug=True)
    except OSError:
        print('Failed to launch the test server...')


# valid endpoints where data can be recieved
# used in main test script to test GET requests
# key = resource location, value = data expected to recieve
ENDPOINTS = {
    'resource1':'<p>RESOURCE1</p>',
    'resource2':'<p>RESOURCE2</p>',
    'resource3':'<p>RESOURCE3</p>',
    'resource4':'<p>RESOURCE4</p>',
}


if __name__ == '__main__':
    launch_server()
    
