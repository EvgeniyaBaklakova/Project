window.addEventListener('DOMContentLoaded', () => {
function authProcess() {
    let formNode = document.querySelector('#formLogin');
    const url = "/api/auth/token";
    const form = new FormData(formNode);

    let data = {
        email: form.get('username'),
        password: form.get('password')
    }
    console.log(data)

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if(localStorage.key('jwtToken') != null)
                localStorage.removeItem('jwtToken');
            localStorage.setItem('jwtToken',data.jwtToken);
            window.location.replace('/main');
        })
        .catch(err => {
            formNode.reset();
            console.error(err)
        });
}
document.querySelector('#signBtn').addEventListener('click', authProcess);
})