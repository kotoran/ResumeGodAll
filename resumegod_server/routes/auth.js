var express = require('express');
var router = express.Router();

var Designation = require('../models/designation.js');




var jwt = require('jsonwebtoken');


router.post('/', function(req, res) {
  Designation.findOne({
    email: req.body.email
  }, function(err, user) {

    if (err) throw err;

    if (!user) {
      res.status(418);
      res.json({ success: false, message: 'Email was not found!' });
    } else if (user) {

 

        var token = jwt.sign(user.email, req.app.get('api_secret'), {
          expiresIn: req.app.get('token_exire') 
        });

        res.status(200);
        res.json({
          success: true,
          designation: user.designation,
          message: 'Authentication successful!',
          token: token
        });
      

    }

  });
});

router.get('/', function(req, res){
  var token = req.body.token || req.query.token || req.headers['x-access-token'];

  if (token) {

    jwt.verify(token, req.app.get('api_secret'), function(err, decoded) {      
      if (err) {
        console.log(err);
        return res.status(403).json({ message: 'Failed to authenticate token.' });    
      } else {

        return res.status(200).send({
          success: true,
          message: 'You have a valid token!'
        });
      }
    });

  } else {

    return res.status(403).send({ 
        success: false, 
        message: 'No token provided.' 
    });
  }
});




module.exports = router;
