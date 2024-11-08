package com.graphql.assets_graphql_server.service;

import com.graphql.assets_graphql_server.Model.Player;
import com.graphql.assets_graphql_server.Model.Post;
import com.graphql.assets_graphql_server.Model.Team;
import com.graphql.assets_graphql_server.Model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<Post> users = new ArrayList<>();
    public User getUser(Integer id){
        return new User(id, "Ankit");
    }
    public List<Post> getPosts(User user) {
        return users;
    }
    @PostConstruct
    private void init(){
        users.add(new Post(15, "Post Title1", "Post Content1"));
        users.add(new Post(16, "Post Title2", "Post Content2"));
    }
}
