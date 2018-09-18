var value = require('../models/value');

exports.getValue = userId =>

	new Promise(function(resolve,reject){

		value.find({ userId: userId })

		.then(function(values){
			resolve(values);
		})
		.catch(function(err){
			reject({ status: 500, message: 'Internal Server Error !' });
		});

	});
