package tools;

import data.Achievement;
import data.Activity;
import data.LoginDetails;
import data.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class Requests {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String url = "http://localhost:8080/";

    /**
     * Sends signup request to the server.
     * @param user - user signing up
     * @return response from the server.
     */
    public static String signupRequest(User user) {
        return restTemplate.postForEntity(url + "/signup",user,String.class).getBody();
    }

    /**
     * Sends login request to the server.
     * @param loginDetails - login details of user wanting to log in
     * @return response from server
     */
    public static User loginRequest(LoginDetails loginDetails) {
        return restTemplate.postForEntity(url + "/login", loginDetails, User.class).getBody();
    }

    /**
     * Get User identified by username or email from server.
     * @param identifier - username or email
     * @return - the User of the identifier (if any)
     */
    public static User getUserRequest(String identifier) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/getUser")
                .queryParam("identifier", identifier);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Sends a friend request from one user to another.
     * @param sender - user sending the friend request
     * @param receiver - user receiving the friend request
     * @return - returns the User who sent the request
     */
    public static User sendFriendRequest(String sender, String receiver) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/friendrequest")
                .queryParam("sender", sender)
                .queryParam("receiver", receiver);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Request from a user to accept a friend request.
     * @param sender - user who sent the friend request
     * @param accepting - user who accepts the request
     * @return - User accepting the friend request
     */
    public static User acceptFriendRequest(String sender, String accepting) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/acceptfriend")
                .queryParam("sender", sender)
                .queryParam("accepting", accepting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Request from a user to reject a friend request.
     * @param sender - user who sent the request
     * @param rejecting - user who is rejecting the request
     * @return - User rejecting the friend request
     */
    public static User rejectFriendRequest(String sender, String rejecting) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/rejectfriend")
                .queryParam("sender", sender)
                .queryParam("rejecting", rejecting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Sends request to server/db to check if user is valid.
     * @param identifier - username or email
     * @return - returns true if user is validated, false if not.
     */
    public static boolean validateUserRequest(String identifier) {
        return restTemplate.postForEntity(url + "/validateUser",
                 identifier, String.class).getBody().equals("OK");
    }

    /**
     * Adds an activity to a User.
     * @param activity - what activity to add to the user.
     * @param username - of the User to add an activity
     * @return - User the activity was added to.
     */
    public static User addActivityRequest(Activity activity, String username) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/addActivity")
                .queryParam("identifier", username);

        return restTemplate.postForEntity(uriBuilder.toUriString(), activity, User.class).getBody();
    }

    /**
     * Gets matching user based on keyword.
     * @param keyword - keyword to match
     * @param loginDetails - to authenticate
     * @return - a list of users matching the keyword
     */
    public static List getMatchingUsersRequest(String keyword, LoginDetails loginDetails) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/searchUsers")
                .queryParam("keyword", keyword);

        return restTemplate.postForEntity(
                uriBuilder.toUriString(), loginDetails, List.class).getBody();
    }

    /**
     * Requets to retrieve list of friends.
     * @param loginDetails - login details of User requesting their friends.
     * @return - list of friends.
     */
    public static List<User> getFriends(LoginDetails loginDetails) {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {
        };
        return restTemplate.exchange(url + "/getFriends",
                HttpMethod.POST, new HttpEntity<>(loginDetails), typeRef).getBody();
    }

    /**
     * Request to get all achievements.
     * @return a list of achievements
     */
    public static List<Achievement> getAllAchievements() {
        ParameterizedTypeReference<List<Achievement>> typeRef =
                new ParameterizedTypeReference<List<Achievement>>() {};
        return restTemplate.exchange(url + "/getAllAchievements",HttpMethod.GET,
                new HttpEntity<>(""),typeRef).getBody();

    }

    /**
     * Request to get the Top Users.
     * @param loginDetails username and password for auth
     * @param top a limit on the number of users to return
     * @return
     */
    public static List<User> getTopUsers(LoginDetails loginDetails, int top) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/getTopUsers")
                .queryParam("top", top);

        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};
        return restTemplate.exchange(uriBuilder.toUriString(),HttpMethod.POST,
                new HttpEntity<LoginDetails>(loginDetails),typeRef).getBody();
    }
}