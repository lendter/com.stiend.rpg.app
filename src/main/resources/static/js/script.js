const BASE_URL = "http://localhost:8080/api/game/";

async function init() {
	document.addEventListener("contextmenu", e => e.preventDefault(), false);
	let responseEntity = await getRequest("info");
	responseEntity = JSON.parse(responseEntity);
	console.log(responseEntity);
	let map = responseEntity["map"];
	let mapView = document.getElementById("map-view");
	let fields = map["fields"];
	let mapWrap = await fillMap(fields);
	mapView.append(mapWrap);
}

function fillMap(fields) {
	return new Promise(resolve => {
		let mapWrap = document.createElement("div");
		Object.keys(fields).forEach(function(y) {
			let row = fields[y];
			let rowDiv = document.createElement("div");
			rowDiv.setAttribute("data-xPos", y);
			rowDiv.className = "stack horizontal";
			Object.keys(row).forEach(function(x) {
				let field = row[x];
				let fieldDiv = document.createElement("div");
				fieldDiv.id = x + ":" + y;
				fieldDiv.addEventListener("drop", (event) => drop(event), false);
				fieldDiv.addEventListener("dragover", (event) => allowDrop(event), false);
				$(fieldDiv).load("/templates/field.html", function() {
					rowDiv.append(fieldDiv);
					fieldDiv.addEventListener("dblclick", () => addFieldAttribute(x, y, "wall"), false);
					fieldDiv.addEventListener("auxclick", (e) => { e.preventDefault(); removeFieldAttribute(x, y, "wall"); }, false);
					if (field["wall"] === true) {
						fieldDiv.children[0].classList.add("wall");
					}
					if (field["character"] != null) {
						let character = field["character"];
						let type = character["type"].split(".")[1];
						console.log(type);
						let img = document.createElement("img");
						img.src = "/img/" + type + "-noBackground.png";
						img.className = "character";
						fieldDiv.children[0].append(img);
					}
					if (field["monster"] != null) {
						let img = document.createElement("img");
						img.src = "/img/Monster-noBackground.png";
						img.className = "character";
						fieldDiv.children[0].append(img);
					}

				});
			})
			mapWrap.append(rowDiv);
		})
		resolve(mapWrap);
	})
}

function createMap() {
	let body = {
		"size": 20
	};
	postRequest("map", body);
	window.location.reload();
}

async function addFieldAttribute(x, y, attribute, obj) {
	console.log("Add Attribute: " + attribute + "  " + JSON.stringify(obj));
	let body;
	if (obj != null) {
		body = {
			"position": { "x": x, "y": y },
		}
		body[attribute] = obj;
	} else {
		body = {
			"x": x,
			"y": y
		}
		document.getElementById(x+":"+y).children[0].classList.add(attribute);
	}
	await postRequest(attribute, body);
	if(obj != null){
		let div = document.getElementById(x+":"+y);
		let img = document.createElement("img");
		const type = attribute.charAt(0).toUpperCase() + attribute.slice(1);
		img.className = "character";
		img.src = "/img/"+type+"-noBackground.png";
		div.children[0].append(img);	
	}
}

async function removeFieldAttribute(x, y, attribute) {
	let body = {
		"x": x,
		"y": y
	}
	await postRequest(attribute, body);
	document.getElementById(x + ":" + y).children[0].classList.remove(attribute);
}

function getRequest(path) {
	console.log("GET Request: " + BASE_URL + path);
	return new Promise(resolve => {
		let request = new XMLHttpRequest();
		request.open("GET", BASE_URL + path);
		request.send();
		request.onload = function(result) {
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
			resolve(result.target.response);
		}
	})
}

function postRequest(path, body) {
	console.log("POST Request: " + BASE_URL + path);
	console.log(body);
	return new Promise(resolve => {
		let request = new XMLHttpRequest();
		request.open("POST", BASE_URL + path);
		request.setRequestHeader("Content-Type", "application/json");
		request.send(JSON.stringify(body));
		request.onload = function(result) {
			resolve(result.target.response);
		}
	})
}

function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	console.log(ev);
	ev.dataTransfer.setData("data-dragItem", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	let dropId = ev.dataTransfer.getData("data-dragItem");
	let idArr = ev.target.parentElement.id.split(":");
	switch (dropId) {
		case "wall":
			addFieldAttribute(idArr[0], idArr[1], dropId);
			break;
		case "knight":
			let knight = mockKnight();
			addFieldAttribute(idArr[0], idArr[1], dropId, knight);
			break;
		case "mercenary":
			let mercenary = mockMercenary();
			addFieldAttribute(idArr[0], idArr[1], dropId, mercenary);
			break;
		case "monster":
			let monster = mockMonster();
			addFieldAttribute(idArr[0], idArr[1], dropId, monster);
			break;
		case "sorcerer":
			let sorcerer = mockSorcerer();
			addFieldAttribute(idArr[0], idArr[1], dropId, sorcerer);
			break;
		case "wizard":
			let wizard = mockWizard();
			addFieldAttribute(idArr[0], idArr[1], dropId, wizard);
			break;
	}
}

function mockKnight() {
	return { "name": "Ritter des Rechts", "hp": 100, "intelligence": 30, "strength": 50, "constitution": 10, "dexterity": 15, "weapon": null };
}

function mockMercenary() {
	return { "name": "Der Söldner", "hp": 100, "intelligence": 30, "strength": 50, "constitution": 10, "dexterity": 15, "weapon": null };
}

function mockMonster() {
	return { "name": "Mike Glotzkowski", "hp": 100, "intelligence": 30, "strength": 50, "constitution": 10, "dexterity": 15 };
}

function mockSorcerer() {
	return { "name": "Harry Potter", "hp": 100, "intelligence": 30, "strength": 50, "constitution": 10, "dexterity": 15, "talent": null };
}

function mockWizard() {
	return { "name": "Dumbledore", "hp": 100, "intelligence": 30, "strength": 50, "constitution": 10, "dexterity": 15, "talent": null };
}
