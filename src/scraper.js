
var next = document.querySelector("p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_lnbNextGroup");
var names = []

function f(i) {


        var docs = document.querySelectorAll("#content > div > div > div.main > div.search > div.doctor-search-results > article.doctor-search-results--result");
        var article = docs[i].querySelector("h3 > a");
        var docname = article.innerHTML;
        return docname;

}
function nextpage(i) {


     nextpage = "p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_rptPages_ctl0"+i+"_lnbPage";
     window.location.href = document.querySelector(nextpage);

}

function nextgroup() {

    var next = document.querySelector("#p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_lnbNextGroup");
    if (next.className = "next"){
        window.location.href = next.querySelector("href");
        return "true";
    }
    else{
        return "false";
    }

}

