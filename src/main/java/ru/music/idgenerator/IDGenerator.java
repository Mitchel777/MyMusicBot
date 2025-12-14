package ru.music.idgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IDGenerator {
    private List<String> userIDs;
    private List<String> playlistIDs;

    public IDGenerator()
    {
        userIDs = new ArrayList<>();
        playlistIDs = new ArrayList<>();
    }

    public String generateIDToUser()
    {
        return UUID.randomUUID().toString();
    }

    public String generateIDToPlaylist()
    {
        return UUID.randomUUID().toString();
    }
}
