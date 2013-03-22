function listdisplay(id){
    var sum = document.getElementById("list_sum_"+id);
    var data = document.getElementById("list_"+id);
    var trjlist = sum.parentNode.parentNode.children;
    for(i=0; i<trjlist.length; i++){
        item = trjlist[i];
        if (item.getAttribute("class") != "trajectlist")
            continue;
        item.children[0].style.display = "block";
        item.children[1].style.display = "none";
    }
    sum.style.display = "none";
    data.style.display = "block";
}