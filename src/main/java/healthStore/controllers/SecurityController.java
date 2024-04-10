package healthStore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import healthStore.DOA.UserDAO;
import healthStore.DTO.TokenDTO;
import healthStore.DTO.UserDTO;
import healthStore.exceptions.ApiException;
import healthStore.persistance.User;
import healthStore.tokenUtils.TokenUtils;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;


public class SecurityController {

    private static UserDAO userDAO;
    private static SecurityController instance;

    public static SecurityController getInstance(Boolean isTesting) {
        if (instance == null) {
            instance = new SecurityController();
            userDAO = UserDAO.getInstance(isTesting);
        }
        return instance;
    }

    static ObjectMapper objectMapper = new ObjectMapper();
    public Handler authenticate() {
        // To check the users roles against the allowed roles for the endpoint (managed by javalins accessManager)
        // Checked in 'before filter' -> Check for Authorization header to find token.
        // Find user inside the token, forward the ctx object with userDTO on attribute
        // When ctx hits the endpoint it will have the user on the attribute to check for roles (ApplicationConfig -> accessManager)
        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode returnObject = objectMapper1.createObjectNode();
        return (ctx) -> {
            if(ctx.method().toString().equals("OPTIONS")) {
                ctx.status(200);
                return;
            }
            String header = ctx.header("Authorization");
            if (header == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header missing"));
                return;
            }
            String token = header.split(" ")[1];
            if (token == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header malformed"));
                return;
            }
            try {
                UserDTO verifiedTokenUser = TokenUtils.getUserFromValidToken(token);
                if (verifiedTokenUser == null) {
                    ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Invalid User or Token"));
                }
                System.out.println("USER IN AUTHENTICATE: " + verifiedTokenUser);
                // sætter den verified User fast på attribute "user"
                // så nu vil vi ramme et endpoint med en verified user og ApplicationConfig checkSecurityRoles og bliver tjekket der
                ctx.attribute("user", verifiedTokenUser);
            }catch (ApiException e){
                ctx.json(returnObject.put("msg", e.getMessage()));
            }
        };
    };
    public static void register(Context ctx) {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                User toPersist = new User(userInput.getUsername(),userInput.getPassword());
                User created = userDAO.create(toPersist);
                String token = TokenUtils.createToken(new UserDTO(created));
                ctx.status(HttpStatus.CREATED).json(new TokenDTO(token, userInput.getUsername()));
            } catch (EntityExistsException e) {
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(returnObject.put("msg", "User already exists"));
            }catch (ApiException e){
                // TODO
            }
    }

    public static void login(Context ctx) {
            ObjectNode returnObject = objectMapper.createObjectNode(); // for sending json messages back to the client
            try {
                UserDTO user = ctx.bodyAsClass(UserDTO.class);
                System.out.println("USER IN LOGIN: " + user);

                User verifiedUserEntity = userDAO.verifyUser(user.getUsername(), user.getPassword());
                String token = TokenUtils.createToken(new UserDTO(verifiedUserEntity));
                ctx.status(200).json(new TokenDTO(token, user.getUsername()));
            } catch (EntityNotFoundException | ValidationException e) {
                ctx.status(401);
                System.out.println(e.getMessage());
                ctx.json(returnObject.put("msg", e.getMessage()));
            }catch (ApiException e){
                // TODO
            }
    }
    public static void authenticate(Context ctx) {
        // To check the users roles against the allowed roles for the endpoint (managed by javalins accessManager)
        // Checked in 'before filter' -> Check for Authorization header to find token.
        // Find user inside the token, forward the ctx object with userDTO on attribute
        // When ctx hits the endpoint it will have the user on the attribute to check for roles (ApplicationConfig -> accessManager)
        ObjectNode returnObject = objectMapper.createObjectNode();
        if(ctx.method().toString().equals("OPTIONS")) {
            ctx.status(200);
            return;
        }
        String header = ctx.header("Authorization");
        if (header == null) {
            ctx.status(403).json(returnObject.put("msg", "Authorization header missing"));
            return;
        }
        String token = header.split(" ")[1];
        if (token == null) {
            ctx.status(403).json(returnObject.put("msg", "Authorization header malformed"));
            return;
        }
        try {
            UserDTO verifiedTokenUser = TokenUtils.getUserFromValidToken(token);
            if (verifiedTokenUser == null) {
                ctx.status(403).json(returnObject.put("msg", "Invalid User or Token"));
            }
            System.out.println("USER IN AUTHENTICATE: " + verifiedTokenUser);
            ctx.attribute("user", verifiedTokenUser);
        }catch (ApiException e){
            ctx.json(returnObject.put("msg", e.getMessage()));
        }
    };
}
