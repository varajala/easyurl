import os
import shutil
import time
import subprocess as subp
from pathlib import Path

from microtest.decorators import test

from testserver import PORT, ENDPOINTS


QUERY_RESULT_PATH = Path(__file__).parent.joinpath('test')
JAR_PATH = Path(__file__).parent.joinpath('../easyurl.jar').resolve()

localhost_url = lambda extension: f'http://127.0.0.1:{PORT}/{extension}'
test_filepath = lambda filename: QUERY_RESULT_PATH.joinpath(filename) 


def setup():
    global ERROR_W_STREAM, ERROR_R_STREAM
    os.mkdir(QUERY_RESULT_PATH)
    error_fp = test_filepath('stream.dat')
    ERROR_W_STREAM = open(error_fp, 'wb')
    ERROR_R_STREAM = open(error_fp, 'rb')


def cleanup():
    ERROR_W_STREAM.close()
    ERROR_R_STREAM.close()
    shutil.rmtree(QUERY_RESULT_PATH)


def call_easyurl_get(url: str, filepath: str):
    subp.run(
        ['java', '-jar', str(JAR_PATH), 'get', url, filepath],
        stdout=subp.DEVNULL,
        stderr=ERROR_W_STREAM,
        )


def run_tests():
    setup()
    #insert testcases here
    test_get()
    test_invalid_response()

    cleanup()


@test
def test_get():
    for endpoint in ENDPOINTS.keys():
        url = localhost_url(endpoint)
        filepath = test_filepath(f'{endpoint}.html')
        call_easyurl_get(url, filepath)

    for endpoint, returned_data in ENDPOINTS.items():
        filepath = test_filepath(f'{endpoint}.html')
        with open(filepath, 'r') as file:
            content = file.read()
            assert content == returned_data, (content, returned_data)


@test
def test_invalid_response():
    url = localhost_url('404')
    filepath = test_filepath('404.dat')
    call_easyurl_get(url, filepath)
    assert ERROR_R_STREAM.read() == b''


if __name__ == '__main__':
    run_tests()