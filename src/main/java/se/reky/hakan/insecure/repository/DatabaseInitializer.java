package se.reky.hakan.insecure.repository;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.reky.hakan.insecure.model.User;

//Initialiserar databasen med lite data, se nedan
@Component
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;
    //Denna metod skapar två users och insertar dem i databasen
    @PostConstruct
    public void init() {
        //admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("adminpass");
        //vanliga user
        User user = new User();
        user.setUsername("user");
        user.setPassword("userpass");

        userRepository.save(admin);
        userRepository.save(user);
    }
}

