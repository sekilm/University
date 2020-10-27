<html>
    <head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <style>
     body {
            background-color: #C3B2D0;
            color: #220033;
            font-weight: 777;
            font-family: "Times New Roman", Times, serif;
            height: 100%;
            width: 100%;
        }

        input {
            opacity: 40%;
        }

        input:focus {
            opacity: 90%;
        }
        
    </style>
    <script>
        var arr = [];
        function populate(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
                if(this.readyState == 4 && this.status == 200)
                    {
                        resultTable = "<table id = \"asset_table\"><tr><th>ID</th><th>Name</th><th>Description</th><th>Value</th></tr>";
                        var data;
                        try{
                            data = JSON.parse(this.responseText);
                            }
                         catch(err){
                             console.log("An error at json parsing from server occured");
                         }
                         for(var i in data){
                             if(data[i][3] >= 10)
                                {
                                    resultTable += "<tr style=\"background-color:red;\">" + "<td>" + data[i][0] + "</td>" + "<td>" + data[i][1] + "</td>" + "<td>" + data[i][2] + "</td>" + "<td>" + data[i][3] + "</td>";
                                    resultTable += "</tr>";
                                }
                                else
                                {
                                    resultTable += "<tr>" + "<td>" + data[i][0] + "</td>" + "<td>" + data[i][1] + "</td>" + "<td>" + data[i][2] + "</td>" + "<td>" + data[i][3] + "</td>";
                                    resultTable += "</tr>";
                                }
                         }
                         resultTable += "</table>";
                         document.getElementById("assets").innerHTML = resultTable;
                     }
                 };
                 xmlhttp.open("GET", "retriveAssets.php", true);
                     xmlhttp.send();
                     console.log(this.responseText);
                     console.log("end");
        }
        $document.ready(function() {
            $("addButton").click(function() {
                var aux = [];
                aux.push(document.getElementById("name").value);
                aux.push(document.getElementById("descr").value);
                aux.push(document.getElementById("value").value);
                arr.push(aux);
            });
            $("submitButton").click(function() {
                $ajax({
                    url:"retrieveAssets.php",
                    type:"post",
                    data: {
                        'arr': arr;
                    },
                    success: function(data){
                        alert("Successfully inserted!");
                    }
                });
            });
        });
    </script>
    </head>
<body onload="populate()">
    <div id="overall">
    <div id="table_area">
            <p id="assets" onclick=""></p>
    </div>
    <div id="data_input_area">
                <p style="font-size:250%;">Please input the correspounding information</p>
                <form action="" method="">
                    <div class="stuff">
                        <label class="_text">Name</label>
                        <input type="text" id="name" name="name">
                    </div>
                    <div class="stuff">
                        <label class="_text">Description</label>
                        <input type="text" id="descr" name="descr">
                    </div>
                    <div class="stuff">
                        <label class="_text">Value</label>
                        <input type="text" id="value" name="value">
                    </div>
                </form>
                <button id="addButton">Add Asset</button>
                <button id="submitButton">Submit Assets</button>
        </div>
        </div>
</body>
</html>