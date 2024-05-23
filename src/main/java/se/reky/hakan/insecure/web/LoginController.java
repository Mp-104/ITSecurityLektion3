package se.reky.hakan.insecure.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.reky.hakan.insecure.model.User;
import se.reky.hakan.insecure.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;

/*
Denna klass är öppen för SQL-injection eftersom input från användaren
används direkt i SQL-frågan. Försök att logga in som användare admin
utan att skriva rätt lösenord!

Tips: använd en SQL-kommentar för att kommentera bort denna del
av SQL-frågan:
    AND password=
En SQL-kommentar ser ut så här: --
Din fråga behöver få detta format:
SELECT * FROM users WHERE username = 'admin' --AND password = 'vad_som_helst'

 Därefter är din uppgift att förhindra att SQL-injection kan användas.
 Tips: använd istället klassen UserRepository som finns i denna applikation.
 Du kan returnera hela User-objektet istället för endast en String, som
 metoden nu gör.

 Extrauppgift: endpoint-metoden login är öppen för sk Brute force-attack.
 Man kan försöka logga in med fel uppgifter hur många gånger som helst.
 Använd HttpSessions-klassen (är redan nu parameter till metoden login)
 för att hålla reda på antal inloggningsförsök.
 Tips: använd session.setAttribute och session.getAttribute för att skapa
 en counter. Returnera strängen "Acoount is locked" när tre felaktiga inloggningsförsök
 har gjorts.

 */
@RestController
public class LoginController {
//    private final JdbcTemplate jdbcTemplate;  replace with UserRepository user;
    private static final String LOGIN_ATTEMPTS_KEY ="login_attempts";

    UserRepository user;

    @Autowired
    public LoginController (UserRepository user) {
        this.user = user;
    }

//    @Autowired
//    public LoginController(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//
//    }

    @GetMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password, HttpSession session) {

        //user = new DatabaseInitializer();
        //User user = new User();
        //user.setUsername(username);
        //user.setPassword(password);
//        int count = 1;
//        Integer i = (Integer) session.getAttribute("Counter");
//
//        if (i != null ) {
//            count = i.intValue() + 1;
//        }
//        session.setAttribute("Counter", new Integer(count));
//
//        System.out.println(count);
//
//        if (count > 3) {
//            return "Account is locked";
//        }



        //Denna typ av query är öppen för SQL-injection
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        //http://localhost:8080/login?username=admin%27%20--&password=vadsomvad
        //http://localhost:8080/login?username=admin' --&password=vadsomvad
        if (user.loginUser(username, password) != null) {

            return username + " logged in";

        } else {

            int count = 1;
            Integer i = (Integer) session.getAttribute("Counter");

            if (i != null ) {
                count = i.intValue() + 1;
            }
            session.setAttribute("Counter", new Integer(count));

            System.out.println(count);

            if (count > 3) {
                return "Account is locked";
            }
            return "log in failed";
        }



//        try {
//            String result = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
//                return rs.getString("username");
//            });
//
//            return result + " logged in!";
//
//        } catch (Exception e) {
//
//            return "Login failed";
//        }

    }
}

