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
      let editHead = document.createElement("th");
      let editButtonTitle = document.createTextNode("Edit");
      editHead.appendChild(editButtonTitle);
      row.appendChild(editHead);
      // let deleteHead = document.createElement("th");
      // let deleteButtonTitle = document.createTextNode("Delete");
      // deleteHead.appendChild(deleteButtonTitle);
      // row.appendChild(deleteHead);
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
          let editCell = row.insertCell();
          let editButton = document.createElement("a");
          editButton.className="btn btn-primary";
          editButton.href="userRecord.html?id="+dataRecord.id;
          editButton.innerHTML="Edit";
          editCell.appendChild(editButton);

          // let deleteCell = row.insertCell();
          // let deleteButton = document.createElement("a");
          // deleteButton.className="btn btn-danger";
          // // deleteButton.href="userDelete.html?id="+dataRecord.id;
          // deleteButton.innerHTML="Delete";
          // deleteCell.appendChild(deleteButton);

          // deleteButton.onclick = function(){
          //   delUser(userRecord.id);return false;
          // }
          
      }
  }

  // function delUser(id){
  //     console.log("deltest");
  // }