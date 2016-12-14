var db = require('../databases/jobseekers.js');

module.exports = db.model('Jobseeker', new require('mongoose').Schema({
	email: String, //email
	first_name: String, //password
	last_name: String, //Profile picture stored in static location
	phone_number: String,
	profile_picture: String,
	skill_1: String, //Conversations they are a part of
	skill_1_rating: Number, //What they have for sale
	skill_2: String, //Are they adminstrators?
	skill_2_rating: Number,
	skill_3: String,
	skill_3_rating: Number,
	location_lat: Number,
	location_long: Number
}));
