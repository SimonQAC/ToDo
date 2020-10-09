document.querySelector("form.taskRecord").addEventListener("submit",function(stop){
    stop.preventDefault();
    let formElements = document.querySelector("form.taskRecord").elements;
    let name = formElements["name"].value;
    let userid = formElements["user_id"].value;
    createTask(name,userid);
})


function createTask(name,userid){

    fetch("http://localhost:8082/task/create/", {
        method: 'post',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "taskName": name,
            "user": {
                "id": userid
            }
          })
      })
      .then(res => res.json())
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        window.alert("Task Successfully Created!");
        document.getElementById("form").reset();
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
}

