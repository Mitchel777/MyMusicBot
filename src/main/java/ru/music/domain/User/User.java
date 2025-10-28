package ru.music.domain.User;

public class User implements IUser
{
    private String UserId;

    public User() {}

    public  User(String UserId)
    {
        this.UserId = UserId;
    }
    public String getUserId()
    {
        return UserId;
    }

}

