document.getElementById("editForm").addEventListener('submit', function(event) {

    const formData = {
        image: document.getElementById('image').value,
        subjectId: document.querySelector('select[name="chonmon"]').value,
        priceOfSession: document.getElementById('price').value,
        description: document.getElementById('description').value,
        role: document.querySelector('select[name="role"]').value
    };
    const token = localStorage.getItem("token");
    fetch(`http://localhost:8080/v1/tutor-services/create`,{
        method: "POST",
        headers: {
            ContentType: "application/json",
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(formData)
    })


    fetch(`http://localhost:8080/v1/tutor-avail/create`,{
        method: "POST",
        headers: {
            ContentType: "application/json",
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(formDataa)
    })
})