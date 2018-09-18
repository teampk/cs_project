var user = require('../models/user');
var bcrypt = require('bcryptjs');

exports.loginUser = (mId, password) =>

	new Promise((resolve,reject) => {
		console.log("id:"+mId);

		user.find({mId: mId})
		.then(users => {

			if (users.length == 0) {
				reject({ status: 404, message: 'User Not Found !' });
			} else {
				return users[0];
			}
		})

		.then(users => {

			var hashed_password = users.hashed_password;

			if (bcrypt.compareSync(password, hashed_password)) {

				resolve({ status: 200, message: email });

			} else {

				reject({ status: 401, message: 'Wrong Password!' });
			}
		})

		.catch(err => reject({ status: 500, message: 'Internal Server Error!' }));

	});
