var express = require('express');
var router = express.Router();

var auth = require('basic-auth');
var jwt = require('jsonwebtoken');
var fs = require('fs');

var registerValue = require('../functions/registerValue');
var getValue = require('../functions/getValue');
var registerUser = require('../functions/registerUser');
var config = require('../config/config.json');
var multer = require('multer');
var path = require('path');

router.get('/', function(req,res){
  res.send('this is user page');

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


router.get('/getValue/:id', function(req, res){
  console.log("id="+req.params.id);

  getValue.getValue(req.params.id)
    .then(function(result) {
      console.log('value result : ' + result);
      res.json(result);
    })
    .catch(function(err) {
      console.log('value err : ' + err);
      res.status(err.status).json({
        message: err.message
      });
    });
});


router.post('/registerValue', function (req, res){
  var userId = req.body.userId;
  var createdAt = req.body.createdAt;
  var value1 = req.body.value1;
  var value2 = req.body.value2;
  var value3 = req.body.value3;
  var value4 = req.body.value4;
  var value5 = req.body.value5;
  var value6 = req.body.value6;
  var value7 = req.body.value7;
  var value8 = req.body.value8;
  var value9 = req.body.value9;
  var value10 = req.body.value10;


  console.log('id: ' + userId + '/' + createdAt + '/' +
  value1 + '/' + value2 + '/' + value3 + '/' +
  value4 + '/' + value5 + '/' + value6 + '/' +
  value7 + '/' + value8 + '/' + value9 + '/' + value10);

  if (!userId || !createdAt || !value1 || !value2 || !value3 || !value4 || !value5 || !value6 || !value7 || !value8 || !value9 || !value10 || !userId.trim() || !createdAt.trim() || !value1.trim() || !value2.trim() || !value3.trim() || !value4.trim() || !value5.trim() || !value6.trim() || !value7.trim() || !value8.trim() || !value9.trim() || !value10.trim()) {
    res.status(400).json({
      message: 'Invalid Request !'
    });
  }else {
    registerValue.registerValue(userId, createdAt, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10)
      .then(function(result) {
        console.log('post result: ' + result);
        res.setHeader('Location', '/register/' + userId);
        res.status(result.status).json({
          message: result.message
        });
      })
      .catch(function(err) {
        console.log('error: ' + err);
        res.status(err.status).json({
          message: err.message
        });
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
