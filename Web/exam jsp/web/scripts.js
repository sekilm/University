function checkLogin() {
    let form = document.forms['Sign up'];

    let err = [];

    if(form['username'].value === "")
        err.push("Please enter a username!");

    if(form['password'].value === "")
        err.push("Please enter a password!");

    if(form['password'].value.length < 8)
        err.push("Your password cannot have less than 8 characters!");

    if(form['password'].value !== form['confirm-password'].value)
        err.push("Passwords do not match!");

    if(err.length !== 0)
        document.getElementById('error-box').innerHTML = err.join('<br>');
    else
        document.getElementById('error-box').innerHTML = '';
}