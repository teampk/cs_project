var user = require('../models/user');

exports.getProfile = mId =>

	new Promise(function(resolve,reject){

		user.find({ mId: mId }, { mId: 1, mName: 1, mGender:1, mBirth:1, mDepartment:1, created_at: 1, _id: 0 })
		.then(function(users){
			resolve(users[0]);
		})
		.catch(function(err){
			console.log('err:', err);
			reject({ status: 500, message: 'Internal Server Error !' });
		});
	});
