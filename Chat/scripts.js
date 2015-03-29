var message = function(user, text) {
	return {		
		name: user,
		mess: text,
		id: i++,
	};
};

var messagesList = [];

function run() {
	var appContainer = document.getElementsByClassName('chat')[0];
	appContainer.addEventListener('click', delegateEvent);
	var list = restore();
	createAllList (list);
}

var i=0;
var id;
var ind;
var ed = false;

function createAllList (list) {
	for (var j=0; j<list.length; j++)
	{
		addMessage (list[j].name, list[j].mess);
	}
}

function delegateEvent(evtObj) {

	if (evtObj.id === "send"){
		if (evtObj.value === "Edit"){
			replaceMessage (evtObj);
		return;
		}
    	sendMessage (evtObj);
	return;
	}

	if (evtObj.id === "delete"){
    		deleteMessage (evtObj);
		ed = false;
		return;
	}

	if (evtObj.id === "edit" && ed===true){
		editMessage (evtObj);
		ed = false;
		return;
	}

	if (+evtObj.id || evtObj.id === "0"){
 		id = evtObj.id;
		ind = find (evtObj.id);
		ed = true;
	}
}

function sendMessage (evtObj) {
  	var userName = document.getElementById ('my name').value;

  	if (userName === '')
    		return;

  	var mess = document.getElementById ('my message').value;

  	if (mess === '')
    		return;

  	addMessage (userName, mess);
  	document.getElementById ('my message').value = '';
}

function addMessage (userName, mess) {
	var newMessage = message (userName, mess);
  	createMessage (newMessage);
	messagesList.push (newMessage);
	store (messagesList);
}

function createMessage (mess) {
	var newMess = document.createElement ("div");
 	newMess.innerHTML = "<p><b>" + mess.name + "</b><p></p>" + mess.mess + "</p>";
 	newMess.setAttribute ("id", mess.id);
  	newMess.setAttribute ("class", "mess");
  	newMess.setAttribute ("onClick", "delegateEvent(this)");
  	document.getElementById ("rect").appendChild (newMess);

	var block = document.getElementById("rect");
 	block.scrollTop = block.scrollHeight;
}

function deleteMessage (evtObj) {
  	document.getElementById ("rect").removeChild(document.getElementById(id));
	messagesList.splice (ind, 1);
	localStorage.removeItem ("MessagesList");
	store (messagesList);
}

function editMessage (evtObj) {
	document.getElementById ("my message").value = messagesList[ind].mess;
	document.getElementById ("send").value = "Edit";
}

function replaceMessage (evtObj) {
	var newDiv = document.getElementById (id);
	newDiv.value = document.getElementById ("my message").value;
	newDiv.innerHTML = "<p><b>" + messagesList[ind].name + "</b><p></p>" + newDiv.value + "</p>";
	var oldDiv = document.getElementById("rect").replaceChild(newDiv, document.getElementById (id));
	document.getElementById ("send").value = "send";
	document.getElementById("my message").value = '';
	messagesList[id].mess = newDiv.value;
	localStorage.removeItem ("MessagesList");
	store (messagesList);
}
 
function store(listToSave) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	localStorage.setItem("MessagesList", JSON.stringify(listToSave));
}

function restore() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	var mess = localStorage.getItem("MessagesList");
	return mess && JSON.parse(mess);
}

function find (value) {
	for (var j=0; j<messagesList.length; j++)
		if (messagesList[j].id == value)
		{
			return j;
		}
}