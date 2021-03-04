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


def call_easyurl_get(url: str, filepath: str):
    subp.call(['java', '-jar', str(JAR_PATH), 'get', url, filepath])


def setup():
    os.mkdir(QUERY_RESULT_PATH)


def cleanup():
    shutil.rmtree(QUERY_RESULT_PATH)


def run_tests():
    setup()
    #insert testcases here
    test_get()

    cleanup()


@test
def test_get():
    for endpoint in ENDPOINTS.keys():
        url = localhost_url(endpoint)
        filepath = QUERY_RESULT_PATH.joinpath(f'{endpoint}.html')
        call_easyurl_get(url, filepath)

    for endpoint, returned_data in ENDPOINTS.items():
        filepath = QUERY_RESULT_PATH.joinpath(f'{endpoint}.html')
        with open(filepath, 'r') as file:
            content = file.read()
            assert content == returned_data, (content, returned_data)


if __name__ == '__main__':
    run_tests()