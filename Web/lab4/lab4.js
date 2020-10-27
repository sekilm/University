
window.onload=function(){
    let input = document.getElementById("text");
    if(input)
        input.addEventListener("keypress", function(event) {
            if (event.keyCode == 13 || event.which == 13) {
                let mytext = input.value;
                let combobox = document.getElementById("combobox");

                let option = document.createElement("option");
                option.text = mytext;
                combobox.add(option);
            }
        });
};

function replace() {
    document.getElementById("text").value = document.getElementById("combobox").value;
}
