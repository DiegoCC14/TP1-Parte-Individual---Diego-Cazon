document.getElementById('myForm').addEventListener('submit', function(event) {
    event.preventDefault()
    const username = document.getElementById('username').value
    const email = document.getElementById('email').value

    if (username.length < 8) {
        alert('Nombre Usuario debe tener mas de 8 letras')
        return
    }
    if(username && email) {
        console.log("Entrada Correcta -->")
        console.log("Usuario: ${username}")
        console.log("Email: ${email}")
        
    } else {
        console.log('Algunos campos están vacíos')
    }
})