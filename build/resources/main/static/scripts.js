function deleteDish(index, name){
    $.ajax({
        url: "/app/api/delete/dish?name=" + name,
        success: function(result) {
            $("#line" + index).hide();
        }
    });
}

function toggle(idelem, idbutton, showhide) {
    var e = $(idelem)[0];
    var button = $(idbutton)[0];
    if(e.style.display == "block"){
        e.style.display = "none";
        button.innerText = showhide[1];
    } else {
        e.style.display = "block";
        button.innerText = showhide[0];
    }
}