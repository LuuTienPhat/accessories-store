package com.example.demo.api;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.models.ResponseObject;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserResource {
	private final UserService userService;

//	@GetMapping(value = "")
//	public ResponseEntity<List<User>>getUsers() {
//		return ResponseEntity.ok().body(userService.getUsers());
//	}

	@GetMapping(value = "")
	@ResponseBody
	public ResponseEntity<ResponseObject> allButLimit(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "search", required = false) String keyword) {
		ResponseEntity<ResponseObject> responseEntity = null;

		List<User> result = null;
		;
		if (keyword != null) {
//			result = userService.getUsers(keyword);
		} else {
			result = userService.getUsers();
		}
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch users successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch users!", result));
		}

		return responseEntity;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> one(@PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;
		User result = userService.getUser(id);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "User found!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "User NOT found!", result));
		}

		return responseEntity;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseObject> replaceUser(@RequestBody FormUser formUser, @PathVariable Long id) {
		ResponseEntity<ResponseObject> responseEntity = null;		

		User newUser = userService.getUser(id);
		newUser.setId(id);
		newUser.setAddress(formUser.getAddress());
		newUser.setBirthday(LocalDate.parse(formUser.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		newUser.setPassword(formUser.getPassword());
		newUser.setEmail(formUser.getEmail());
		newUser.setFirstname(formUser.getFirstname());
		newUser.setLastname(formUser.getLastname());
		newUser.setGender(formUser.getGender());
		newUser.setUsername(formUser.getUsername());
		newUser.setPhone(formUser.getPhone());

//		Role role = new Role();
//		role.setId(formUser.getRoleId());
//		List<Role> roles = new ArrayList<Role>();
//		roles.add(role);
//		newUser.setRoles(roles);

		User result = userService.updateUser(id, newUser);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Update user successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to update user!", result));
		}

		return responseEntity;
	}

	@PostMapping(value = "")
	public ResponseEntity<ResponseObject> newUser(@RequestBody FormUser formUser) {

		User newUser = new User();
		newUser.setAddress(formUser.getAddress());
		newUser.setBirthday(LocalDate.parse(formUser.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		newUser.setPassword(formUser.getPassword());
		newUser.setEmail(formUser.getEmail());
		newUser.setFirstname(formUser.getFirstname());
		newUser.setLastname(formUser.getLastname());
		newUser.setGender(formUser.getGender());
		newUser.setUsername(formUser.getUsername());
		newUser.setPhone(formUser.getPhone());

		Role role = new Role();
		role.setId(formUser.getRoleId());
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		newUser.setRoles(roles);

		ResponseEntity<ResponseObject> responseEntity = null;
		User result = userService.saveUser(newUser);
		if (result != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Insert user successfully!", result));
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("failed", HttpStatus.NOT_IMPLEMENTED.value(), "Failed to insert user!", result));
		}

		return responseEntity;

	}

	@PostMapping(value = "/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping(value = "/addToUser")
	public ResponseEntity<?> count(@RequestBody RoleToUserForm form) {
		Long result = userService.count();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "User count", result));
	}

	@GetMapping(value = "/count")
	@ResponseBody
	ResponseEntity<ResponseObject> count() {
		Long result = userService.count();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", HttpStatus.OK.value(), "User count", result));

	}

	@PostMapping(value = "/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws StreamWriteException, DatabindException, IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {

				String refreshToken = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				String userId = decodedJWT.getSubject();
				User user = userService.getUser(Long.valueOf(userId));

				String accessToken = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("acces_token", accessToken);
				tokens.put("refresh_token", refreshToken);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());

				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
	
//	@GetMapping(value = "/favourite-products")
//	@ResponseBody
//	public ResponseEntity<ResponseObject> returnfavouriteProduct(
//			@RequestParam(value = "userId", required = false) Long userId) {
//
//		ResponseEntity<ResponseObject> responseEntity = null;
//		List<Product> result = null;
//		if (userId != null) {
//			result = productService.getProducts(userId, );
//		} else {
//			result = productService.getProducts();
//		}
//		if (result != null) {
//			responseEntity = ResponseEntity.status(HttpStatus.OK)
//					.body(new ResponseObject("ok", HttpStatus.OK.value(), "Fetch Products successfully!", result));
//		} else {
//			responseEntity = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed",
//					HttpStatus.NOT_IMPLEMENTED.value(), "Failed to fetch Products!", result));
//		}
//
//		return responseEntity;
//	}

}

@Data
class RoleToUserForm {
	private String username;
	private String roleName;
}

@Data
class FormUser {
	Long userId;
	Long roleId;
	String firstname;
	String lastname;
	Integer gender;
	String email;
	String phone;
	String birthday;
	String address;
	String username;
	String password;
}
