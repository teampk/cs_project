var express = require('express');
var router = express.Router();
var registerValue = require('../functions/registerValue');


router.get('/', function(req,res){
  res.send('this is survey page mf');
  console.log('console log testing');

});

router.get('/value:id', function(req, res){
  res.send('hello how are you');
});

router.post('/register', function (req, res){
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

module.exports = router;
