package se.reky.hakan.insecure.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

/*
Denna Controllers enda endpoint /greet använder en HTML-sida som gränssnitt (se filen
greet.html under src/resources/templates). Endpointen /greet tar emot en parameter.
Parametern skrivs ut precis som den är på HTML-sidan. Detta gör att denna endpoint
är öppen för cross-site-scripting (XSS).
Pröva att få ett enkelt javascript att köras genom att använda parametern name.

Uppgiften är sedan att försöka åtgärda denna sårbarhet.
Tips: läs om klassen org.springframework.web.util.HtmlUtils
 */
@Controller
public class GreetingController {



    @GetMapping("/greet")
    public String greet(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {

        String name2 = HtmlUtils.htmlEscape(name);

        model.addAttribute("name", name2);
        return "greet";
    }
}

