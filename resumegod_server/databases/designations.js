var mongoose = require('mongoose');
var config = require('../db.js');
var db = mongoose.createConnection(config.db_designations);

module.exports = db