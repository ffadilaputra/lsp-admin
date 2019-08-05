package admin.controller;

import javax.servlet.http.HttpServletRequest;

import admin.util.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import admin.dto.UserDataDTO;
import admin.dto.UserResponseDTO;
import admin.model.User;
import admin.service.UserService;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/auth")
  @ApiOperation(value = "${UserController.signin}")
  public String login(@RequestBody UserDataDTO user) {
    return userService.signin(user.getUsername(), user.getPassword());
  }

  @PostMapping("/register")
  @ApiOperation(value = "${UserController.signup}")
  public String signup(@ApiParam("Signup User") @RequestBody User user) {
    return userService.signup(modelMapper.map(user, User.class));
  }

  @DeleteMapping(value = "/{username}")
  @ApiOperation(value = "${UserController.delete}")
  public String delete(@ApiParam("Username") @PathVariable String username) {
    userService.delete(username);
    return username;
  }

  @GetMapping(value = "/{username}")
  ResponseEntity<Response> getByUsername(@PathVariable("username") String username) {
    String nameOfCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
    Response response = new Response();
    response.setService(this.getClass().getName() + nameOfCurrMethod);
    response.setMessage("Get Data by username");

    response.setData(userService.findByUsername(username));

    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
  }
//  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
//  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
//    return modelMapper.map(userService.search(username), UserResponseDTO.class);
//  }

  @GetMapping(value = "/me")
  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }

  @GetMapping("/refresh")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }

  @GetMapping("all")
  ResponseEntity<Response> findAll() {
    String nameOfCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
    Response res = new Response();
    res.setService(this.getClass().getName() + nameOfCurrMethod);
    res.setMessage("Get All data");
    res.setData(userService.findAll());
    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(res);
  }

//  @PatchMapping(value = "/{username}")
//  ResponseEntity<Response> update(@PathVariable("username") String username, @RequestBody @Validated User user){
//    String nameOfCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
//    Response response = new Response();
//    response.setService(this.getClass().getName() + nameOfCurrMethod);
//    response.setMessage("Data Updated Successfully");
//    response.setData(userService.updateByUsername(username,user));
//    return ResponseEntity
//            .status(HttpStatus.OK)
//            .contentType(MediaType.APPLICATION_JSON)
//            .body(response);
//  }
  @PatchMapping(value = "/{_id}")
  ResponseEntity<Response> updateById(@PathVariable("_id") String id, @RequestBody @Validated User user){
    String nameOfCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
    Response response = new Response();
    response.setService(this.getClass().getName() + nameOfCurrMethod);
    response.setMessage("Data Updated Successfully");
    response.setData(userService.update(id,user));
    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
  }

}
