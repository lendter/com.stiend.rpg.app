const BASE_URL = "http://localhost:8080/api/game";

function init() {
	let map = getRequest("/map");
	
}

function createMap(){
	let body = {
		"size": 10
	};
	putRequest("/map", body);
}

function getRequest(path) {
	console.log("GET Request: " + path);
	return new Promise(resolve => {
		let request = new XMLHttpRequest();
		request.open("GET", BASE_URL + path);
		request.send();
		request.onload = function(result) {
			console.log(result.target.response);
			resolve(result.target.response);
		}
	})
}

function putRequest(path, body){
	console.log("PUT Request: "+ path);
	return new Promise(resolve => {
		let request = new XMLHttpRequest();
		request.open("PUT", BASE_URL + path);
		request.setRequestHeader("Content-Type", "application/json");
		request.send(JSON.stringify(body));
		request.onload = function(result) {
			console.log(result.target.response);
			resolve(result.target.response);
		}
	})
}

function postRequest(path, body) {
	console.log("POST Request: "+ path);
	return new Promise(resolve => {
		let request = new XMLHttpRequest();
		request.open("POST", BASE_URL + path);
		request.setRequestHeader("Content-Type", "application/json");
		request.send(JSON.stringify(body));
		request.onload = function(result) {
			console.log(result.target.response);
			resolve(result.target.response);
		}
	})
}
