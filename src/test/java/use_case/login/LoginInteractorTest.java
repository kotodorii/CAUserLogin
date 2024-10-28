package use_case.login;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginInteractorTest {

    @Test
    public void successTest() {
        // Existing test code...
    }

    @Test
    public void successUserLoggedInTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // Before login, current user should be null
        assertNull(userRepository.getCurrentUser());

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData userData) {
                assertEquals("Paul", userData.getUsername());
                // After login, current user should be "Paul"
                assertEquals("Paul", userRepository.getCurrentUser());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);

        // Before execution, current user should be null
        assertNull(userRepository.getCurrentUser());

        interactor.execute(inputData);
    }

    @Test
    public void failurePasswordMismatchTest() {
        // Existing test code...
    }

    @Test
    public void failureUserDoesNotExistTest() {
        // Existing test code...
    }
}
