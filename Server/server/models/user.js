var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var userSchema = mongoose.Schema({

	mId			: {type: String, unique: true},
  mName 			: String,
  mGender : String,
  mBirth : String,
  mDepartment : String,
	mSmile : String,
  
	hashed_password	: String,
	created_at		: String,
	temp_password	: String,
	temp_password_time: String

});

module.exports = mongoose.model('user', userSchema);
