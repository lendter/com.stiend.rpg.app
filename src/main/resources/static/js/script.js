const BASE_URL = "http://localhost:8080/api/game";


async function init() {
	let map = await getRequest("/map");
	map = JSON.parse(map);
	let mapView = document.getElementById("map-view");
	let fields = map["fields"];
	Object.keys(fields).forEach(function(y) {
		let row = fields[y];
		let rowDiv = document.createElement("div");
		rowDiv.setAttribute("data-xPos", y);
		rowDiv.className = "stack horizontal";
		mapView.append(rowDiv);
		Object.keys(row).forEach(function(x) {
			let field = row[x];
			console.log(y, x, field);
			let fieldDiv = document.createElement("div");
			fieldDiv.id = x + ":" + y;
			$(fieldDiv).load("/templates/field.html", function() {
				rowDiv.append(fieldDiv);
				fieldDiv.addEventListener("dblclick", () => setWall(x, y), false);
				if (field["wall"] === true) {
					fieldDiv.children[0].classList.add("wall");
				}
			});
		})
	})
}

function createMap() {
	let body = {
		"size": 20
	};
	postRequest("/map", body);
}

async function setWall(x, y) {
	let body = {
			"x": x,
			"y": y
	}
	console.log(body);
	await postRequest("/wall", body);
}

function getRequest(path) {
	console.log("GET Request: " + BASE_URL + path);
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

function putRequest(path, body) {
	console.log("PUT Request: " + BASE_URL + path);
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
	console.log("POST Request: " + BASE_URL + path);
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
