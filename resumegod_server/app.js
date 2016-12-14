var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('asdf');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var index = require('./routes/index');
var auth = require('./routes/auth');
var designations = require('./routes/designations');
var jobseekers = require('./routes/jobseekers');
var employers = require('./routes/employers')
var upload = require('./routes/upload');

var toobusy = require('toobusy-js');

var dbConfig = require('./db.js');

var config = require('./config.js');


var session = require('express-session');


var app = express();
app.set('api_secret', config.secret);
app.set('token_expire', config.token_expire);
app.use(session({secret: config.secret}));


app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', index);
app.use('/auth', auth);
app.use('/designation', designations);
app.use('/jobseekers', jobseekers);
app.use('/employers', employers);
app.use('/upload', upload);


app.use(function(req, res, next) {
  if (toobusy()) {
    res.send(503, "I'm busy right now, sorry.");
  } else {
    next();
  }
});

app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});


if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;
