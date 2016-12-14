var express = require('express');
var jwt = require('jsonwebtoken');
var Employer = require('../models/employer.js');
var router = express.Router();

router.use(function(req, res, next){
var token = req.body.token || req.query.token || req.headers['x-access-token'];

  // decode token
  if (token) {


    // verifies secret and checks exp
    jwt.verify(token, req.app.get('api_secret'), function(err, decoded) {      
      if (err) {
      	console.log(err);
        return res.status(403).json({ message: 'Failed to authenticate token.' });    
      } else {
        // if everything is good, save to request for use in other routes
        req.id = decoded;
        next();
      }
    });

  } else {

    // if there is no token
    // return an error
    return res.status(403).send({ 
        success: false, 
        message: 'No token provided.' 
    });
  }
});

router.get('/me', function(req, res, next) {

  Employer.findOne({
    email : req.id
  }, function(err, user){
    if(err) throw err;
    if(user){
        res.status(200);
        return res.json(user);
    }else{
      res.status(400);
      return res.json({
        success: false,
        message: "No user was found with that ID!"
      })
    }
  });
});


router.post('/create', function(req, res, next) {

  var n = new Employer({
    email: req.id,
    company_name: req.body.company_name
  });
  n.save(function(err, employer){
    if (err) throw err;
    res.status(200);
    return res.json(employer);
  });
});

module.exports = router;
