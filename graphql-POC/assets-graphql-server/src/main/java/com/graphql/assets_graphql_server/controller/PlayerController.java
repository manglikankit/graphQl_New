package com.graphql.assets_graphql_server.controller;

import com.graphql.assets_graphql_server.Model.Player;
import com.graphql.assets_graphql_server.Model.Post;
import com.graphql.assets_graphql_server.Model.Team;
import com.graphql.assets_graphql_server.Model.User;
import com.graphql.assets_graphql_server.service.PlayerService;
import com.graphql.assets_graphql_server.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {
    //http://localhost:8084/graphiql
    private PlayerService playerService;

    private UserService userService;

    public PlayerController(PlayerService playerService, UserService userService) {
        this.playerService = playerService;
        this.userService = userService;
    }

    @QueryMapping
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @QueryMapping
    public Optional<Player> findOne(@Argument Integer id) {
        return playerService.findOne(id);
    }

    @MutationMapping
    public Player create(@Argument String name, @Argument Team team){
        return playerService.create(name, team);
    }

    @MutationMapping
    public Player update(@Argument Integer id,@Argument String name,@Argument Team team){
        return playerService.update(id, name, team);
    }

    @MutationMapping
    public Player delete(@Argument Integer id){
        return playerService.delete(id);
    }

    // Resolver for 'getUser' query
    @QueryMapping
    public User getUser(@Argument Integer id) {
        // Fetch the user by id (from database, for example)
        return userService.getUser(id);
    }

    // Resolver for 'posts' field within 'User' type
    @SchemaMapping(typeName = "User", field = "posts")
    public List<Post> getPosts(@Argument User user) {
        // Fetch posts for the given user
        return userService.getPosts(user);
    }
}
