var user = require('../models/user');
var bcrypt = require('bcryptjs');
var nodemailer = require('nodemailer');
var randomstring = require("randomstring");
var config = require('../config/config.json');

exports.changePassword = (email, password, newPassword) =>

	new Promise((resolve, reject) => {

		user.find({ mId: email })

		.then(users => {

			let user = users[0];
			var hashed_password = user.hashed_password;

			if (bcrypt.compareSync(password, hashed_password)) {

				var salt = bcrypt.genSaltSync(10);
				var hash = bcrypt.hashSync(newPassword, salt);

				user.hashed_password = hash;

				return user.save();

			} else {

				reject({ status: 401, message: 'Invalid Old Password !' });
			}
		})

		.then(user => resolve({ status: 200, message: 'Password Updated Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});

exports.resetPasswordInit = email =>

	new Promise((resolve, reject) => {

		var random = randomstring.generate(8);

		user.find({ mId: email })

		.then(users => {

			if (users.length == 0) {

				reject({ status: 404, message: 'User Not Found !' });

			} else {

				let user = users[0];

				var salt = bcrypt.genSaltSync(10);
				var hash = bcrypt.hashSync(random, salt);

				user.temp_password = hash;
				user.temp_password_time = new Date();

				return user.save();
			}
		})

		.then(user => {

			var transporter = nodemailer.createTransport(`smtps://${config.email}:${config.password}@smtp.gmail.com`);

			var mailOptions = {

    			from: `"${config.name}" <${config.email}>`,
    			to: email,
    			subject: 'Reset Password Request ',
    			html: `Hello ${user.name},<br><br>
    			&nbsp;&nbsp;&nbsp;&nbsp; Your reset password token is <b>${random}</b>.
    			If you are viewing this mail from a Android Device click this <a href = "http://learn2crack/${random}">link</a>.
    			The token is valid for only 2 minutes.<br><br>
    			Thanks,<br>
    			Learn2Crack.`

			};

			return transporter.sendMail(mailOptions);

		})

		.then(info => {

			console.log(info);
			resolve({ status: 200, message: 'Check mail for instructions' })
		})

		.catch(err => {

			console.log(err);
			reject({ status: 500, message: 'Internal Server Error !' });

		});
	});

exports.resetPasswordFinish = (email, token, password) =>

	new Promise((resolve, reject) => {

		user.find({ mId: email })

		.then(users => {

			let user = users[0];

			var diff = new Date() - new Date(user.temp_password_time);
			var seconds = Math.floor(diff / 1000);
			console.log(`Seconds : ${seconds}`);

			if (seconds < 120) {

				return user;

			} else {

				reject({ status: 401, message: 'Time Out ! Try again' });
			}
		})

		.then(user => {

			if (bcrypt.compareSync(token, user.temp_password)) {

				var salt = bcrypt.genSaltSync(10);
				var hash = bcrypt.hashSync(password, salt);
				user.hashed_password = hash;
				user.temp_password = undefined;
				user.temp_password_time = undefined;

				return user.save();

			} else {

				reject({ status: 401, message: 'Invalid Token !' });
			}
		})

		.then(user => resolve({ status: 200, message: 'Password Changed Sucessfully !' }))

		.catch(err => reject({ status: 500, message: 'Internal Server Error !' }));

	});
