var user = require('../models/user');

exports.getProfile = userId =>

	new Promise(function(resolve,reject){

		user.find({ userId: userId })
		.then(function(users){
			resolve(users[0]);
		})
		.catch(function(err){
			console.log('err:', err);
			reject({ status: 500, message: 'Internal Server Error !' });
		});
	});
