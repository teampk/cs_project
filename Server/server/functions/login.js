var user = require('../models/user');
var bcrypt = require('bcryptjs');

exports.loginUser = (email, password) =>

	new Promise((resolve,reject) => {

		user.find({mId: email})
		.then(users => {
			if (users.length == 0) {
				reject({ status: 404, message: 'User Not Found !' });
			} else {
				return users[0];
			}
		})

		.then(user => {
			var hashed_password = user.hashed_password;
			if (bcrypt.compareSync(password, hashed_password)) {
				resolve({ status: 200, message: email });
			} else {
				reject({ status: 401, message: 'Wrong Password !' });
			}
		})

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});
