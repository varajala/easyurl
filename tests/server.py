from flask import Flask, Blueprint


bp = Blueprint('__name__', 'app')

@bp.route('/')
def index():
    return '<p>paragraph</p>'


@bp.route('/not-found')
def not_found():
    return (404, 'Not Found')


def create_app():
    app = Flask(__name__)
    app.register_blueprint(bp)
    return app
