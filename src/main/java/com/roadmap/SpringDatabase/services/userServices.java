package com.roadmap.SpringDatabase.services;

import com.roadmap.SpringDatabase.entities.User;
import com.roadmap.SpringDatabase.repo.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //@Service is a specialization of the @Component annotation with the additional function of throwing exceptions if it encounters any SQL exception.
public class userServices
{
    private final userRepository userRepo;

    @Autowired //@Autowired is used to inject the userRepository bean into the constructor.
    public userServices(userRepository userRepo)
    {
        this.userRepo = userRepo;
    }

    public User createUser(User user) throws Exception
    {
        return userRepo.create(user);
    }

    public User getUser(long id) throws Exception
    {
        return userRepo.read(id);
    }

    public void updateUser(User user) throws Exception
    {
        userRepo.update(user);
    }

    public void deleteUser(long id) throws Exception
    {
        userRepo.delete(id);
    }

    public List<User> getAllUsers() throws Exception
    {
        return userRepo.readAll();
    }
}
