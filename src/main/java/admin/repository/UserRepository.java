package admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import admin.model.User;

public interface UserRepository extends MongoRepository<User, String> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  void deleteByUsername(String username);

}
