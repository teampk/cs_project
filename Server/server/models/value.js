var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var valueSchema = mongoose.Schema({
  ownerId : String,
  created_at		: String,
  value1 : String,
  value2 : String,
  value3 : String,
  value4 : String,
  value5 : String,
  value6 : String,
  value7 : String,
  value8 : String,
  value9 : String,
  value10 : String
});

module.exports = mongoose.model('value', valueSchema);
