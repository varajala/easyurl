import os
import tempfile
import subprocess as subp

import microtest
import microtest.utils as utils

import server


SERVER_PORT = 5000
SERVER_URL = f'http://127.0.0.1:{SERVER_PORT}'

EASYURL_JAR_NAME = 'easyurl-1.0.0-SNAPSHOT.jar'
EASYURL_JAR_PATH = os.path.join(os.path.dirname(os.path.dirname(__file__)), EASYURL_JAR_NAME)


def call_easyurl(*args) -> str:
    cmd = ['java', '-jar', EASYURL_JAR_PATH, *args]
    proc = subp.run(cmd, capture_output=True, text=True)
    return proc.stdout


@microtest.setup
def setup():
    if not os.path.exists(EASYURL_JAR_PATH):
        raise RuntimeError('Unable to locate .jar-file. Build the application before running this test script...')
    
    fd, path = tempfile.mkstemp()
    wsgi_server = server.create_app()
    proc = utils.start_wsgi_server(wsgi_server, port=SERVER_PORT)

    microtest.add_resource('path', path)
    microtest.add_resource('fd', fd)
    microtest.add_resource('wsgi_server_proc', proc)


@microtest.reset
def reset(path):
    with open(path, 'w') as file:
        file.write('EMPTY\n')


@microtest.cleanup
def cleanup(wsgi_server_proc, path, fd):
    wsgi_server_proc.terminate()
    os.unlink(path)
    os.close(fd)


@microtest.test
def test_get_request_from_cli(path):
    call_easyurl('get', SERVER_URL, path)
    with open(path, 'r') as file:
        output = file.read()
    assert '<p>paragraph</p>' in output


@microtest.test
def test_get_request_from_file(path):
    with tempfile.NamedTemporaryFile(mode='w+') as req_file:
        req_file.write(f'get {SERVER_URL} {path}\n')
        req_file.flush()
        
        call_easyurl('--file', req_file.name)
        with open(path, 'r') as file:
            output = file.read()
        assert '<p>paragraph</p>' in output


if __name__ == '__main__':
    microtest.run()
