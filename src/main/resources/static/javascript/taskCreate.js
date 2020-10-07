document.querySelector("form.userRecord").addEventListener("submit",function(stop){
    stop.preventDefault();
    let formElements = document.querySelector("form.userRecord").elements;
    let name = formElements["name"].value;
    let timezone = formElements["timezone"].value;
    console.log(name);
    console.log(timezone);
    createUser(name,timezone);
})


function createUser(name,timezone){

    fetch("http://localhost:8082/task/create/", {
        method: 'post',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "name": name,
            "timezone": timezone,
          })
      })
      .then(res => res.json())
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        window.alert("User Successfully Created!");
        document.getElementById("form").reset();
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
}
