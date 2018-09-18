var express = require('express');
var router = express.Router();

var auth = require('basic-auth');
var jwt = require('jsonwebtoken');
var fs = require('fs');

var registerUser = require('../functions/registerUser');
var profile = require('../functions/profile');
var login = require('../functions/login');

var config = require('../config/config.json');
var multer = require('multer');
var path = require('path');

router.get('/', function(req,res){
  res.send('this is user page');

});

router.post('/authenticate', function(req, res) {
  var credentials = auth(req);
  if (!credentials) {
    res.status(400).json({
      message: 'Invalid Request !'
    });
  } else {
    login.loginUser(credentials.name, credentials.pass)
      .then(function(result) {
        console.log("result:"+result);
        var token = jwt.sign(result, config.secret, {
          expiresIn: 1440
        });
        res.status(result.status).json({
          message: result.message,
          token: token
        });
      })
      .catch(function(err) {
        res.status(err.status).json({
          message: err.message
        });
      });
  }
});

router.post('/users', function(req,res){
  var id = req.body.mId;
  var password = req.body.mPassword;
  var name = req.body.mName;
  var gender = req.body.mGender;
  var birth = req.body.mBirth;
  var department = req.body.mDepartment;
  if (!id || !password || !name || !gender || !birth || !department || !id.trim() || !password.trim() || !name.trim() || !gender.trim() || !birth.trim() || !department.trim()) {
    res.status(400).json({
      message: 'Invalid Request !'
    });
  } else {
    registerUser.registerUser(id, password, name, gender, birth, department)
      .then(function(result) {
        console.log('post result for user: ' + result);
        res.setHeader('Location', '/users/' + id);
        res.status(result.status).json({
          message: result.message
        });
      })
      .catch(function(err) {
        res.status(err.status).json({
          message: err.message
        });
      });
  }
});

router.get('/users/:id', function(req, res) {

  if (checkToken(req)) {
    profile.getProfile(req.params.id)
      .then(function(result) {
        console.log('user result : ' + result);
        res.json(result);
      })
      .catch(function(err) {
        console.log('user err : ' + err);
        res.status(err.status).json({
          message: err.message
        });
      });
  } else {
    res.status(401).json({
      message: 'Invalid Token !'
    });
  }
});


function checkToken(req) {
  var token = req.headers['x-access-token'];
  if (token) {
    try {
      var decoded = jwt.verify(token, config.secret);
      return decoded.message === req.params.id;
    } catch (err) {
      return false;
    }
  } else {
    return false;
  }
}
module.exports = router;
