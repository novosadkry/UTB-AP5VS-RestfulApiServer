package utb.fai.RESTAPIServer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser")
    public ResponseEntity<MyUser> getUserById(@RequestParam Long id) {
        return userRepository.findById(id)
            .map(myUser -> new ResponseEntity<>(myUser, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createUser")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser newUser) {
        if (newUser.isUserDataValid()) {
            var savedUser = userRepository.save(newUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/editUser")
    public ResponseEntity<MyUser> editUser(@RequestParam Long id, @RequestBody MyUser updatedUser) {
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                var savedUser = userRepository.save(existingUser);
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<Void> deleteAllUsers() {
        userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
