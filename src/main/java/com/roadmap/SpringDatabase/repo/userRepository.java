package com.roadmap.SpringDatabase.repo;

import com.roadmap.SpringDatabase.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository //@Repository is a specialization of the @Component annotation with the additional function of throwing exceptions if it encounters any SQL exception.
public class userRepository
{
    private final DataSource dataSource;

    @Autowired //@Autowired is used to inject the DataSource bean into the constructor.
    public userRepository(DataSource dataSource) //constructor
    {
        this.dataSource = dataSource;
    }

    public User create(User user) throws Exception
    {
        String sql = "INSERT INTO user(name, email, password) VALUES (?, ?, ?)";
           try(Connection connect = dataSource.getConnection();
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.executeUpdate();
                try(ResultSet generatedKeys = ps.getGeneratedKeys())
                {
                    if(generatedKeys.next())
                    {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            }
           return user;
    }

    public User read(long id) throws Exception
    {
        String sql = "SELECT * FROM springdatabase.user WHERE id = ?";
        try (Connection connect = dataSource.getConnection();
             PreparedStatement ps = connect.prepareStatement(sql))
        {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    User user = new User(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                    return user;
                }
            }
        }
        return null;
    }

    public List<User> readAll() throws Exception
    {
        String sql = "SELECT * FROM springdatabase.user";
        try (Connection connect = dataSource.getConnection();
             PreparedStatement ps = connect.prepareStatement(sql))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                List<User> users = new ArrayList<>();
                while (rs.next())
                {
                    User user = new User(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                    users.add(user);
                }
                return users;
            }
        }
    }

    public void update(User user) throws Exception
    {
        String sql = "UPDATE springdatabase.user SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection connect = dataSource.getConnection();
             PreparedStatement ps = connect.prepareStatement(sql))
        {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws Exception
    {
        String sql = "DELETE FROM springdatabase.user WHERE id = ?";
        try (Connection connect = dataSource.getConnection();
             PreparedStatement ps = connect.prepareStatement(sql))
        {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
