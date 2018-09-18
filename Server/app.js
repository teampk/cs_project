var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var logger = require('morgan');
var routerSurvey = require('./server/routes/routerSurvey');
var routerUser= require('./server/routes/routerUser');
var mongoose = require('mongoose');
var app = express();
var router = express.Router();

app.use(bodyParser.json());
app.use(logger('dev'));

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/cs_project');

app.use('/user', routerUser);
app.use('/survey', routerSurvey);

module.exports = app;

var server = app.listen(8080, function() {
    console.log('Express server listening on port 8080');
});
