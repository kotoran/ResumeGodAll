var express = require('express');
var jwt = require('jsonwebtoken');
var config = require('../config.js');
var Designation = require('../models/designation.js');
var router = express.Router();

router.post('/', function(req, res, next) {
	var foundUser = false;
	Designation.findOne({
		email: req.body.email
	}, function(err, user){
		if(err) throw err;
		if(user)  {
			res.status(400);
			return res.json({
				success: false,
				message: "This email has already been registered!"
			});
		}else{
			var n = new Designation({
			email: req.body.email,
			designation: req.body.designation
			})

			n.save(function(err){
				if (err) throw err;
				res.status(200);
				return res.json({
				success: true,
					message: "Registration successful!"
				});
			});
	
	
		}
	});
});


module.exports = router;
