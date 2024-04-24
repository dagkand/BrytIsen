package com.gruppe24.backend.service;

import com.gruppe24.backend.dto.UserDTO;
import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.service.GameListService;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.exception.InvalidDtoException;
import com.gruppe24.backend.exception.UserExistsException;
import com.gruppe24.backend.exception.UserNotFoundException;
import com.gruppe24.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <strong>Service layer for managing {@link User} entities.</strong>
 *
 * <p>
 * This service encapsulates the business logic for user management, acting as
 * an intermediary
 * between the controller layer and the data access layer. It leverages the
 * {@link UserRepository}
 * for CRUD operations on {@link User} entities, ensuring that business rules
 * and logic are
 * consistently applied. This abstraction allows for cleaner controllers and
 * promotes the separation
 * of concerns within the application.
 * </p>
 *
 * <p>
 * Methods in this service are transactional, ensuring data integrity and
 * consistency during
 * operations that involve multiple steps or queries. This transactional
 * management is crucial
 * for operations that modify data, safeguarding against partial updates and
 * data anomalies.
 * </p>
 *
 * <ul>
 * <strong>Key Functionalities Include:</strong>
 * <li>Retrieving all users from the database.</li>
 * <li>Retrieving a specified user by its username</li>
 * <li>Create a user with given userDTO</li>
 * <li>Update a specified user by given userDTO</li>
 * <li>Delete a specified user bu give username</li>
 * </ul>
 *
 * <p>
 * Usage of this service should be limited to interaction through higher-level
 * components
 * such as REST controllers or other services requiring user manipulation and
 * retrieval.
 * </p>
 */
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves all users from the repository.
   *
   * @return A list of {@link User} entities.
   */
  @Transactional
  public List<User> readUsers() {
    return userRepository.findAll();
  }

  /**
   * Retrieves a user from the repository.
   *
   * @return A {@link User} entity.
   * @throws UserNotFoundException when it doesn't find the user by given username
   */
  @Transactional
  public User getUser(String username) {
    return userRepository.findById(username).orElseThrow(UserNotFoundException::new);
  }

  /**
   * Creates a user from the {@link UserDTO} entity.
   *
   * @return Created {@link User}
   * @throws InvalidDtoException if the UserDTO does not have required information
   */
  @Transactional
  public User createUser(UserDTO userDTO) {
    if (userDTO.getUserName().isEmpty() || userDTO.getEmail().isEmpty()) {
      throw new InvalidDtoException();
    }
    User user = new User();
    user.setUserName(userDTO.getUserName().get());
    user.setEmail(userDTO.getEmail().get());
    return userRepository.save(user);
  }

  /**
   * Updates the attributes of a user with information from the {@link UserDTO}
   * entity.
   *
   * @throws UserExistsException when given username is already in use
   */
  @Transactional
  public void updateUser(User user, UserDTO userDTO) {
    String newName = userDTO.getUserName().orElseThrow(InvalidDtoException::new);

    if (userRepository.findById(newName).isPresent()) {
      throw new UserExistsException("Username is taken");
    }

    userDTO.getUserName().ifPresent(user::setUserName);
    userRepository.save(user);
  }

  /**
   * Deletes a {@link User} from the repository.
   *
   * @throws UserNotFoundException when user is not found by given username
   */
  @Transactional
  public void deleteUser(String username) {
    User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
    userRepository.delete(user);
  }

}
