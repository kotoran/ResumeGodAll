var db = require('../databases/designations.js');

module.exports = db.model('Designation', new require('mongoose').Schema({
	email: String, //email
	designation: Number
}));
