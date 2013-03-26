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

function rateWindow(id) {
	document.getElementById("rateDiv_"+id).style.display = "block";
    if (document.getElementById("rateDiv_"+id).innerHTML == "")
	document.getElementById("rateDiv_"+id).innerHTML+= "<input type=\"hidden\" name=\"isPassenger\" value=\"true\" />Rate this driver: <select name='rate'><option value='0'>0 / 5</option><option value='1'>1 / 5</option><option value='2'>2 / 5</option><option value='3'>3 / 5</option><option value='4' selected='selected'>4 / 5</option><option value='5'>5 / 5</option><option value='-1'>A problem occured during my traject</option></select> <br /><textarea name='comment' rows='2' cols='50'></textarea><br /><input type='hidden' name='id' value='"+id+"' /><input type='submit' value='Confirm' /> <input type='button' value='Cancel' onclick='cancelRating("+id+")' />";
}

function cancelRating(id) {
	document.getElementById("rateDiv_"+id).style.display = "none";
	document.getElementById("rateDiv_"+id).innerHTML = "";
}