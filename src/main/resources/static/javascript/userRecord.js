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
    console.log(formElements);
})
