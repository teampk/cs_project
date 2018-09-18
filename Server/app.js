var express = require('express');

var routerSurvey = require('./server/routes/router_survey');
var routerUser= require('./server/routes/router_user');

var mongoose = require('mongoose');
var app = express();
var router = express.Router();

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/cs_project');

app.use('/user', routerUser);
app.use('/survey', routerSurvey);

module.exports = app;

var server = app.listen(8080, function() {
    console.log('Express server listening on port 8080');
});
