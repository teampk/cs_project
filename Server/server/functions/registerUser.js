var user = require('../models/user');
var bcrypt = require('bcryptjs');

exports.registerUser = (id, password, name, gender, birth, department) =>

	new Promise((resolve,reject) => {

	  var salt = bcrypt.genSaltSync(10);
		var hash = bcrypt.hashSync(password, salt);

		var newUser = new user({

      mId: id,
      mName: name,
      mGender: gender,
      mBirth: birth,
      mDepartment: department,
			hashed_password: hash,
			created_at: new Date()

		});

		newUser.save()

		.then(function(){
			resolve({ status: 201, message: 'User Registered Sucessfully !' });
		})

		.catch(function(err){

			if (err.code == 11000) {

				reject({ status: 409, message: 'User Already Registered !' });

			} else {

				reject({ status: 500, message: 'Internal Server Error !' });
			}
		});
	});
