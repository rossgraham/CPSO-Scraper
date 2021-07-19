import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class scrapes {


    public static ArrayList<String> scraper(HtmlPage page) {

        ArrayList<String> names = new ArrayList<>();

        DomNodeList<DomNode> docs = page.querySelectorAll("#content > div > div > div.main > div.search > div.doctor-search-results > article.doctor-search-results--result");

        for (DomNode doc :
                docs) {
            String name = doc.querySelector("h3 > a").getTextContent();
            String specst = "hold";
            DomNodeList<DomNode> rest = doc.querySelectorAll("div > p");
            for (DomNode spec :
                    rest) {

                DomElement spoc = (DomElement) spec;
                if (spoc.getAttribute("class").equals("doctordetail")) {
                    continue;
                } else {

                    specst = spec.getTextContent();

                }

            }
            if (specst.equals("hold")) {
                names.add(name);
            } else {
                names.add(name + "; " + specst);
            }
        }

        return names;
    }


    public static ArrayList<String> dun(HtmlPage page) {


        ArrayList<String> docnames = new ArrayList<>();
        try {
            boolean hasNext = true;
            while (hasNext) {


                HtmlPage page1;
                HtmlPage page2;
                HtmlPage page3;
                HtmlPage page4;
                HtmlPage page5;
                DomElement pg1 = page.getElementById("p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_rptPages_ctl00_lnbPage");

                if (pg1 != null) {

                    page1 = pg1.click();
                    docnames.addAll(scraper(page1));
                }
                else {
                    docnames.addAll(scraper(page));
                }
                DomElement pg2 = page.getElementById("p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_rptPages_ctl01_lnbPage");
                if (pg2 != null) {

                    page2 = pg2.click();
                    docnames.addAll(scraper(page2));
                }
                DomElement pg3 = page.getElementById("p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_rptPages_ctl02_lnbPage");
                if (pg3 != null) {

                    page3 = pg3.click();
                    docnames.addAll(scraper(page3));
                }
                DomElement pg4 = page.getElementById("p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_rptPages_ctl03_lnbPage");
                if (pg4 != null) {

                    page4 = pg4.click();
                    docnames.addAll(scraper(page4));
                }
                DomElement pg5 = page.getElementById("p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_rptPages_ctl04_lnbPage");
                if (pg5 != null) {

                    page5 = pg5.click();
                    docnames.addAll(scraper(page5));
                }
                try {


                    DomElement nextgroup = (DomElement) page.querySelector("#p_lt_ctl01_pageplaceholder_p_lt_ctl03_CPSO_DoctorSearchResults_lnbNextGroup");


                    if (nextgroup.getAttribute("class").equals("next")) {
                        page = nextgroup.click();
                    } else {

                        hasNext = false;
                    }
                }
                catch (NullPointerException e){
                    return docnames;
                }
            }


        } catch (IOException e) {

        } catch (ElementNotFoundException e) {


        }


        return docnames;
    }


    public static void main(String[] args) {

        WebClient webClient = new WebClient();
        webClient.getOptions().setTimeout(120000);
        webClient.waitForBackgroundJavaScript(60000);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        try {
            HtmlPage homepage = webClient.getPage("https://doctors.cpso.on.ca/?search=general");
            HtmlSelect select = homepage.querySelector("#p_lt_ctl01_pageplaceholder_p_lt_ctl02_CPSO_AllDoctorsSearch_ddHospitalName");
            List<HtmlOption> options = select.getOptions();

            for (int i = 0; i < options.size() ; i++) {



                if (options.get(i).getText().equals("Mount Sinai Hospital")) {
                    options.get(i).setSelected(true);
                    DomElement submit = homepage.getElementById("p_lt_ctl01_pageplaceholder_p_lt_ctl02_CPSO_AllDoctorsSearch_btnSubmit1");
                    HtmlPage page = submit.click();


                    ArrayList docnames = dun(page);

                    FileWriter fw = new FileWriter(options.get(i).getText() + ".txt");
                    BufferedWriter bw = new BufferedWriter(fw);

                    System.out.println("=========================\n" + options.get(i).getText() + "\n============================");
                    for (Object a :
                            docnames) {
                        bw.write(a.toString());
                        bw.newLine();

                        System.out.println(a);
                    }


                    System.out.println(docnames.size());


                    options.get(i).setSelected(false);
                    bw.close();
                    fw.close();
                }

            }


        } catch (IOException e) {

        }


    }
}


