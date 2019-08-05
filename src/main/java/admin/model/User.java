package admin.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class User {

    @Id
    private String _id;

    private String username;

    private String email;

    private String password;

    List<Role> roles;
}
