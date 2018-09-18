var modelValue = require('../models/value');
var fs = require('fs');

exports.registerValue = (userId, createdAt, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10) =>

	new Promise(
		function(resolve,reject){

	 	var newValue = new modelValue({
      userId: usreId,
      createdAt: new Date(),
      value1: value1,
      value2: value2,
      value3: value3,
      value4: value4,
      value5: value5,
      value6: value6,
      value7: value7,
      value8: value8,
      value9: value9,
      value10: value10
		});

		newValue.save()
		.then(function(){
			console.log('register value complete');
			resolve({ status: 201, message: 'Value Registered Sucessfully !' });
		})
		.catch(function(err){
			if (err.code == 11000) {
				reject({ status: 409, message: 'Value Already Registered !' });
			} else {
				reject({ status: 500, message: 'Internal Server Error !' });
			}
		});
	});
