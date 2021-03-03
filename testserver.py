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


if __name__ == '__main__':
    app.run(port=PORT, debug=True)
