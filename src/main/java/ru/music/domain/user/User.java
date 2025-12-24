package ru.music.domain.user;

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

