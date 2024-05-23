package se.reky.hakan.insecure.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.reky.hakan.insecure.model.User;
/*
    Denna typ av interface har en speciell innerbörd i Spring Boot.
    Spring kommer själv att skapa en implementation av interfacet
    och denna implementation får tillgång till metoder som ansluter
    till databasen.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    //Denna query använder named parameters och är inte känslig för SQL-injection
    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
    User loginUser(@Param("username") String username, @Param("password") String password);
}


