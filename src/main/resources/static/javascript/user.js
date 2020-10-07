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

        // for (let a of dataData){
        //   console.log("a",a);

        //   for (let b in a){
        //     console.log("b",a[b]);
        //   }
        // }

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
      let editHead = document.createElement("th");
      let editButtonTitle = document.createTextNode("Edit");
      editHead.appendChild(editButtonTitle);
      row.appendChild(editHead);

  }

  function createTableBody(table,dataData){
      for (let dataRecord of dataData){
          let row = table.insertRow();
          for (value in dataRecord){
            let cell = row.insertCell();
            let text = document.createTextNode(dataRecord[value]);
            // console.log(typeof(dataRecord[value]));
            // console.log(dataRecord[value]);
            if (typeof dataRecord[value] === 'object'){
              // console.log("arrayhomie");
              for (object in dataRecord[value]){
                // console.log(dataRecord[value][object].name);
                
                let taskText = document.createTextNode(dataRecord[value][object].name);
                cell.appendChild(taskText);

                if (dataRecord[value].indexOf(dataRecord[value][object]) < dataRecord[value].length-1) {
                  let comma = document.createTextNode(", ");
                  cell.appendChild(comma);
                }

                
              }
            } else{
              cell.appendChild(text);
            }
              // console.log(dataRecord[value]);
          }
          let editCell = row.insertCell();
          let editButton = document.createElement("a");
          editButton.className="btn btn-primary";
          editButton.href="userRecord.html?id="+dataRecord.id;
          editButton.innerHTML="Edit";
          editCell.appendChild(editButton);

                }
  }

