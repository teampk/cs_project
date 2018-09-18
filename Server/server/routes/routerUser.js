var express = require('express');
var router = express.Router();
var registerUser = require('../functions/registerUser');

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
