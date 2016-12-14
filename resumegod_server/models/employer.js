var db = require('../databases/employers.js');

module.exports = db.model('Employer', new require('mongoose').Schema({
	email: String, //email
	company_name: String
}));
