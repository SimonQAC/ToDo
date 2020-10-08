const params = new URLSearchParams(window.location.search);

for (let param of params){
    let id = param[1];
    getSingleRecord(id)
}

function getSingleRecord(id){
fetch('http://localhost:8082/task/read/'+id)
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      console.log('Fetch Success')
      response.json().then(function(dataData) {
        console.log(dataData);

        document.getElementById("id").value=dataData.id;
        document.getElementById("name").value=dataData.name;
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
}

document.querySelector("form.taskRecord").addEventListener("submit",function(stop){
    stop.preventDefault();
    let formElements = document.querySelector("form.taskRecord").elements;
    let id = formElements["id"].value;
    let name = formElements["name"].value;
    console.log(id);
    console.log(name);
    updatetask(id,name);
})
document.getElementById("Delete").addEventListener("click",function(stop){
    stop.preventDefault();
    let confirm;
        let r = window.confirm("Are you sure you wish to delete record?");
        if (r == true) {
        confirm = "You pressed OK!";

    let formElements = document.querySelector("form.taskRecord").elements;
    let id = formElements["id"].value;
    fetch("http://localhost:8082/task/delete/"+id, {
        method: 'delete',
        headers: {
            "Content-type": "application/json"
        },
      })
      .then(res => res.json())
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
    } else {
        confirm = "You pressed Cancel!";
}
})

function updatetask(id,name){
    let updateId = parseInt(id);

    fetch("http://localhost:8082/task/update/"+id, {
        method: 'put',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "id": updateId,
            "name": name,
          })
      })
      .then(res => res.json())
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
}
