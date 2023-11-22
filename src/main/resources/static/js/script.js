const BASE_URL = "http://localhost:8080/api/game";

async function init() {
	let map = await getRequest("/map");
	console.log(map);	
}

function createMap(){
	let body = {
		"size": 10
	};
	putRequest("/map", body);
}

function getRequest(path) {
	console.log("GET Request: " +BASE_URL+ path);
	return new Promise(resolve => {
		let request = new XMLHttpRequest();
		request.open("GET", BASE_URL + path);
		//request.setRequestHeader("Content-Type", "application/json");
		request.send();
		request.onload = function(result) {
			console.log(result.target.response);
			resolve(result.target.response);
		}
	})
}

function putRequest(path, body){
	console.log("PUT Request: "+ BASE_URL+path);
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
	console.log("POST Request: "+BASE_URL+ path);
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
