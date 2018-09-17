var express = require('express');

var routerSurvey = require('./server/routes/router_survey');
var routerUser= require('./server/routes/router_user');

var app = express();
var router = express.Router();

app.use('/user', routerUser);
app.use('/survey', routerSurvey);

module.exports = app;

var server = app.listen(8080, function() {
    console.log('Express server listening on port 8080');
});
