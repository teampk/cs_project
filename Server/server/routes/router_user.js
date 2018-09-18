var express = require('express');
var router = express.Router();

router.get('/', function(req,res){
  res.send('this is user page');

});

router.post('/register', function(req,res){
  
});

module.exports = router;
