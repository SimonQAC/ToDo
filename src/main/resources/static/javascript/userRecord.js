const params = new URLSearchParams(window.location.search);

for (let param of params){
    let id = param[1];
    getSingleRecord(id)
}

function getSingleRecord(id){
fetch('http://localhost:8082/user/read/'+id)
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
        document.getElementById("timezone").value=dataData.timezone;
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
}

document.querySelector("form.userRecord").addEventListener("submit",function(stop){
    stop.preventDefault();
    let formElements = document.querySelector("form.userRecord").elements;
    // console.log(formElements);
    let id = formElements["id"].value;
    let name = formElements["name"].value;
    let timezone = formElements["timezone"].value;
    console.log(id);
    console.log(name);
    console.log(timezone);
    updateUser(id,name,timezone);
})

function updateUser(id,name,timezone){
    let updateId = parseInt(id);

    fetch("http://localhost:8082/user/update/"+id, {
        method: 'put',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "id": updateId,
            "name": name,
            "timezone": timezone,
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
