function deleteGame(button) {
    $(button).parents("tr").remove();
}

function addGame(button) {
    let myRow = $(button).closest("tr");
    let newRow = $("<tr align='center'><td><button onclick=\"deleteGame(this)\">x</button></td>" +
        "<td><input class='cell' type='text' onchange='turnToReadOnly()'></td>" +
        "<td><input class='cell' type='text' onchange='turnToReadOnly()'></td>" +
        "<td><input class='cell' type='text' onchange='turnToReadOnly()'></td>" +
        "<td><input class='cell' type='text' onchange='turnToReadOnly()'></td>" +
        "<td><input class='cell' type='text' onchange='turnToReadOnly()'></td>" +
        "<td><button onclick=\"addGame(this)\">+</button></td></tr>");
    myRow.after(newRow);
}

function turnToReadOnly() {
    let x = document.getElementsByClassName("cell");
    let check = true;
    for(let i of x) {
        if (i.value === "") {
            check = false;
        }
    }
    if(check) {
        for(let i of x) {
            i.readOnly = true;
        }
    }
}
