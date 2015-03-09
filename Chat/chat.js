function run() {
  var appContainer = document.getElementsByClassName('chat')[0];
  appContainer.addEventListener('click', delegateEvent);
  
}

function delegateEvent(evtObj) {
  if (evtObj.id === "1")
    sendMessage (evtObj);
}

function sendMessage () {
  var userName = document.getElementById ('my name').value;
  if (userName === '')
    return;
  var mess = document.getElementById ('my message').value;
  if (mess === '')
    return;
  document.getElementById ('all messages').value += userName + ": " + mess + '\n';
  document.getElementById ('my message').value = '';
  return;
}