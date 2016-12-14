var express = require('express');
var jwt = require('jsonwebtoken');
var Jobseeker = require('../models/jobseeker.js');
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

router.get('/all', function(req, res, next) {
  Jobseeker.find({}, function(err, jobseeker){
    if(err) throw err;

    if(jobseeker){
      res.status(200);
      return res.json(jobseeker);
    }else{
      res.status(418);
      return res.json({success: false, message: "No job seekers available!"});
    }
  })
});

router.get('/', function(req, res, next) {

  if(req.query.email) searchId = req.query.email;

  Jobseeker.findOne({
    email : searchId
  }, function(err, user){
    if(err) throw err;
    if(user){
        res.status(200);
        return res.json(user);
    }else{
      res.status(418);
      return res.json({success: false, message: "Could not find that job seeker!"});
    }
  });
});

router.post('/create', function(req, res, next) {
  console.log(req.id);
  var n = new Jobseeker({
    email: req.id,
    profile_picture: req.body.profile_picture,
    first_name: req.body.first_name,
    last_name: req.body.last_name,
    phone_number: req.body.phone_number,
    skill_1: req.body.skill_1,
    skill_1_rating: req.body.skill_1_rating,
    skill_2: req.body.skill_2,
    skill_2_rating: req.body.skill_2_rating,
    skill_3: req.body.skill_3,
    skill_3_rating: req.body.skill_3_rating,
    location_lat: req.body.location_lat,
    location_long: req.body.location_long
  });

  n.save(function(err, jobseeker){
    res.status(200);
    return res.json(jobseeker);
  })

});

module.exports = router;
