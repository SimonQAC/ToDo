fetch('http://localhost:8082/user/readall')
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

        let table = document.querySelector("table");
        let data = Object.keys(dataData[0]);

        createTableHead(table,data);
        createTableBody(table,dataData);


      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });

  function createTableHead(table,data){
      let thead = table.createTHead();
      let row = thead.insertRow();

      for (let keys of data){
          let th = document.createElement("th");
          let text = document.createTextNode(keys);
          th.appendChild(text);
          row.appendChild(th);
      }
      let th2 = document.createElement("th");
      let text2 = document.createTextNode("Edit");
      th2.appendChild(text2);
      row.appendChild(th2);
  }

  function createTableBody(table,dataData){
      for (let dataRecord of dataData){
          let row = table.insertRow();
          for (values in dataRecord){
            //   console.log(dataRecord[values]);
              let cell = row.insertCell();
              let text = document.createTextNode(dataRecord[values]);
              cell.appendChild(text);
          }
          let newCell = row.insertCell();
          let viewButton = document.createElement("a");
          viewButton.className="btn btn-primary";
          viewButton.href="userRecord.html?id="+dataRecord.id;
          viewButton.innerHTML="View";
          newCell.appendChild(viewButton);
      }
  }