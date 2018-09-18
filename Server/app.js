var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var logger = require('morgan');
var routes = require('./server/routes/routes');
var mongoose = require('mongoose');
var app = express();
var router = express.Router();

app.use(bodyParser.json());
app.use(logger('dev'));

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/cs_project', { useNewUrlParser: true });

app.use('/api/v1', routes);

module.exports = app;

var server = app.listen(8080, function() {
    console.log('Express server listening on port 8080');
});
